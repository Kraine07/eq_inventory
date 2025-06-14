package kraine.app.eq_inventory.controller;


import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kraine.app.eq_inventory.PasswordServices;
import kraine.app.eq_inventory.SessionHandler;
import kraine.app.eq_inventory.exception.DuplicateUserException;
import kraine.app.eq_inventory.exception.PasswordNotFoundException;
import kraine.app.eq_inventory.exception.UserNotFoundException;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.model.LoginAttempt;
import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.LoginStatus;
import kraine.app.eq_inventory.model.RegisterModel;
import kraine.app.eq_inventory.model.RoleType;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.service.AuthService;
import kraine.app.eq_inventory.service.EquipmentService;
import kraine.app.eq_inventory.service.LocationService;
import kraine.app.eq_inventory.service.LoginAttemptsService;
import kraine.app.eq_inventory.service.ManufacturerService;
import kraine.app.eq_inventory.service.ModelService;
import kraine.app.eq_inventory.service.PropertyService;
import kraine.app.eq_inventory.service.RegionService;
import kraine.app.eq_inventory.service.RoleService;
import kraine.app.eq_inventory.service.UserService;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;





@Controller
public class UserController {

    private final UserService us;
    private final LoginAttemptsService las;
    private final RoleService roleService;
    private final AuthService authService;
    private final RegionService regionService;
    private final PropertyService propertyService;
    private final LocationService locationService;
    private final ManufacturerService manufacturerService;
    private final ModelService modelService;
    private final EquipmentService equipmentService;
    // Constructor injection for services



    public UserController(UserService us, LoginAttemptsService las, RoleService roleService, AuthService authService, RegionService regionService, PropertyService propertyService, LocationService locationService, ManufacturerService manufacturerService, ModelService modelService, EquipmentService equipmentService) {
        this.us = us;
        this.las = las;
        this.roleService = roleService;
        this.authService = authService;
        this.regionService = regionService;
        this.propertyService = propertyService;
        this.locationService = locationService;
        this.manufacturerService = manufacturerService;
        this.modelService = modelService;
        this.equipmentService = equipmentService;
    }



    @GetMapping("")
    public String loadApp(Model model, HttpServletRequest request) {


        //check if any users have been created
        if (us.getUsers().isEmpty()) {
            model.addAttribute("registerModel", new RegisterModel());
            model.addAttribute("editor","editor");
            model.addAttribute("admin","admin");
            return "setup";
        }

        // check if a user is logged in
        if (SessionHandler.hasSessionAttribute(request, "authUser")) {
            User authUser = SessionHandler.getAttribute(request, "authUser", User.class);
            // check if password is temporary
            if (authUser.getIsTemporaryPassword()) {
                return "update-password";
            }
            // check if user has admin privileges
            // if (authUser.getRole().getRoleType() == RoleType.ADMINISTRATOR)
            return "redirect:/app/admin";
            // return "redirect:/dashboard";
        }

        model.addAttribute("loginModel", new LoginModel());
        return "index";
    }


    @GetMapping("/app/admin")
    public String loadAdminPanel(Model model, HttpServletRequest request) {

        // check if session timed out
        if (!SessionHandler.hasSessionAttribute(request, "authUser")) {
            return "redirect:/";
        }


        // TODO research a more optimized method

        User authUser = SessionHandler.getAttribute(request, "authUser", User.class);

        // get list of all equipment
        List<Equipment> equipments = equipmentService.getAllEquipment();

        // filter by user
        List<Equipment> userEquipmentList = equipments.stream()
                .filter(equipment -> equipment.getLocation().getProperty().getUser().getId().equals(authUser.getId()))
                .collect(Collectors.toList());

        // group equipments by Manufacturer
        Map<String, List<Equipment>> equipmentByManufacturer = userEquipmentList.stream()
                .collect(Collectors.groupingBy(equipment -> equipment.getModel().getManufacturer().getName()));


        // get all locations
        List<Location> locations = locationService.getAllLocations();

        // filter by user
        List<Location> userLocations = locations.stream().filter(location -> location.getProperty().getUser().getId().equals(authUser.getId())).collect(Collectors.toList());

        // group locations by property
        Map<String, List<Location>> locationsByProperty = userLocations.stream().collect(Collectors.groupingBy(location -> location.getProperty().getName()));

        // TODO - logic to sort equipment by product dispensed



        // Pagination logic for equipment list
        int page = 0;
        int size = 10; // amount of rows
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
                if (page < 0)
                    page = 0;
            }
        }
        catch (NumberFormatException ignored) {}

        Page<Equipment> pagedEquipmentList = equipmentService.getPage(page, size);
        List<Equipment> equipmentList = pagedEquipmentList.getContent();
        List<Equipment> pagedUserEquipmentList = pagedEquipmentList.getContent().stream()
                .filter(equipment -> equipment.getLocation().getProperty().getUser().getId().equals(authUser.getId()))
                .collect(Collectors.toList());


        // add model attributes
        model.addAttribute("userEquipmentList", equipmentByManufacturer);
        model.addAttribute("pagedUserEquipmentList", pagedUserEquipmentList);
        model.addAttribute("pagedEquipmentList", pagedEquipmentList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) equipmentList.size() / size));
        var modelAttributes = Map.of(
            "regionList", regionService.getAllRegions(),
            "propertyList", propertyService.getAllProperties(),
            "locationList", locationsByProperty,
            "manufacturerList", manufacturerService.getAllManufacturers(),
            "modelList", modelService.getAllModels(),
            "equipmentList", equipmentList,
            "userList", us.getUsers(),
            "registerModel", new RegisterModel(),
            "editor", "editor",
            "admin", "admin"
        );
        modelAttributes.forEach(model::addAttribute);


        return "main";
    }





    @PostMapping("/app/register")
    public String addUser(@Valid RegisterModel registerModel, BindingResult bindingResult,
            Model model, @RequestParam(name = "role") String role, HttpServletRequest request) throws BindException {

        // generate, set password
        String genPass = PasswordServices.generatePassword(12);
        registerModel.setPassword(genPass);

        // get and set role
        registerModel.setRole(role.contains("admin") ? roleService.getRole(RoleType.ADMINISTRATOR) : roleService.getRole(RoleType.EDITOR));

        // attributes to be used when sending email
        SessionHandler.addAttribute(request, "rawPass", genPass);
        SessionHandler.addAttribute(request, "recipient", registerModel.getEmail());


        if (bindingResult.hasFieldErrors("firstName")) {
            throw new BindException(bindingResult);
        }

        try {
            // send password to email if creation was successful
            if (us.addUser(RegisterModel.toUser(registerModel)) != null) return "redirect:/send-password";


            return "redirect:/";
        }
        catch (DuplicateUserException e) {
            throw e;
        }
        catch(Exception e) {throw e;}

    }





    @PostMapping("/attempt-login")
    public String login(@Valid LoginModel loginModel, BindingResult bindingResult, Model model, HttpServletRequest request) {

        SessionHandler.clearSession(request);

        // construct login attempt
        LoginAttempt currentAttempt = LoginAttempt.builder()
                .email(loginModel.getEmail())
                .ipAddress(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .status(LoginStatus.SUCCESSFUL)
                .userAgent(request.getHeader("User-Agent"))
                .build();

        // Handle validation errors
        if (bindingResult.hasErrors()) return "index";


        try{
            LoginStatus loginStatus = authService.authenticateUser(loginModel.getEmail(), loginModel.getPassword(), request);

            // check if account is suspended
            if(loginStatus == LoginStatus.LOCKED){
                // TODO handle error message
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Account has been suspended. Please contact administrator.");
                las.recordLoginLocked(currentAttempt);
                return "index";
            }


             // when login is successful
            if(loginStatus == LoginStatus.SUCCESSFUL){
                las.recordSuccessfulAttempt(currentAttempt, loginModel);
                return "redirect:/";
            }

             //check if password is temporary
            if (loginStatus == LoginStatus.TEMPORARY) {
                las.recordSuccessfulAttempt(currentAttempt, loginModel);
                return "update-password";
            }


        }

        catch(UserNotFoundException  e){

            // model.addAttribute("error", true);
            // model.addAttribute("errorMessage", e.getMessage()+" Please try again.");
            las.recordFailedAttempt(loginModel, currentAttempt);
            throw e;
        }
        catch(Exception e){
            // model.addAttribute("error", true);
            // model.addAttribute("errorMessage", "Something went wrong. Please try again.");
            las.recordFailedAttempt(loginModel, currentAttempt);
            throw e;
        }

        // TODO set access level
        return "redirect:/";


    }




    @PostMapping("/update-user")
    public String updateUser(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "firstName")String firstName,
            @RequestParam(name = "lastName")String lastName,
            @RequestParam(name = "role") String role) {

        try {
            User retrievedUser = us.findById(Long.parseLong(id));
            if (retrievedUser != null) {
                retrievedUser.setFirstName(firstName);
                retrievedUser.setLastName(lastName);
                retrievedUser.setRole(role.contains("admin") ? roleService.getRole(RoleType.ADMINISTRATOR)
                        : roleService.getRole(RoleType.EDITOR));
            }
            us.updateUser(retrievedUser);
        }
        catch (Exception e) {
            throw e;
        }

        return "redirect:/";
    }


    @PostMapping("/change-status")
    public String postMethodName(@RequestParam(name="id")String id) {
        try {
            User retrievedUser = us.findById(Long.parseLong(id));
            if (retrievedUser != null) {
                retrievedUser.setFailedAttempts(0);
                retrievedUser.setIsSuspended(!retrievedUser.getIsSuspended());
                us.updateUser(retrievedUser);
            }
        } catch (Exception e) {
            throw e;
        }

        return "redirect:/";
    }




    @PostMapping("/update-password")
    public String updatePassword(Model model,
            @RequestParam(name = "old-password") String oldPassword,
            @RequestParam(name = "new-password") String newPassword,
            @RequestParam(name = "confirm-password") String confirmPassword,
            HttpServletRequest request) {

        User userToBeUpdated = SessionHandler.getAttribute(request, "authUser", User.class);

        // Check if new passwords match
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "New passwords do not match.");
            return "update-password";
        }

        // Check if new password is different
        if (oldPassword.equals(newPassword)) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "New password must be different.");
            return "update-password";
        }

        // Validate pattern (only for new password)
        if (!PasswordServices.validateFieldAgainstPattern(new User(), "password", newPassword)) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Invalid password format.");
            return "update-password";
        }

        // Set new password before service call
        userToBeUpdated.setPassword(newPassword);
        userToBeUpdated.setIsTemporaryPassword(false);

        try {
            User updatedUser = us.updatePassword(userToBeUpdated, oldPassword);
            if (updatedUser != null) {
                return "redirect:/";
            }
        } catch (PasswordNotFoundException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "An error occurred. Please try again.");
        }

        return "update-password";
    }


    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam(name="id")String id) {
        us.deleteUser(Long.parseLong(id));
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout (HttpServletRequest request){
        SessionHandler.clearSession(request);
        return "redirect:/";
    }

}
