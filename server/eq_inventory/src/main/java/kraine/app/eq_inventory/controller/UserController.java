package kraine.app.eq_inventory.controller;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kraine.app.eq_inventory.PasswordServices;
import kraine.app.eq_inventory.SessionHandler;
import kraine.app.eq_inventory.exception.PasswordNotFoundException;
import kraine.app.eq_inventory.exception.UserNotFoundException;
import kraine.app.eq_inventory.model.LoginAttempt;
import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.LoginStatus;
import kraine.app.eq_inventory.model.RegisterModel;
import kraine.app.eq_inventory.model.RoleType;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.service.AuthService;
import kraine.app.eq_inventory.service.LoginAttemptsService;
import kraine.app.eq_inventory.service.RoleService;
import kraine.app.eq_inventory.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class UserController {

    private final UserService us;
    private final LoginAttemptsService las;
    private final RoleService roleService;
    private final AuthService authService;



    public UserController(UserService us, LoginAttemptsService las, RoleService roleService, AuthService authService) {
        this.us = us;
        this.las = las;
        this.roleService = roleService;
        this.authService = authService;
    }


    // ROUTES
    @GetMapping("")
    public String loadApp(Model model, HttpServletRequest request) {

        // attribute to be used in user form
        

        //check if any users have been created
        if (us.getUsers().isEmpty()) {
            model.addAttribute("registerModel", new RegisterModel());
            model.addAttribute("editor","editor");
            model.addAttribute("admin","admin");
            return "setup";
        }

        // check if a user is logged in
        else if (SessionHandler.hasSessionAttribute(request, "authUser")) {
            return "redirect:/app/admin"; // redirect so app calls loadAdminPanel method
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

        //TODO  get list of equipment and property so populate respective list

        // get list of users to populate user list
        List<User> userList = us.getUsers();
        model.addAttribute("userList", userList);
        model.addAttribute("editor","editor");
        model.addAttribute("admin","admin");
        model.addAttribute("registerModel", new RegisterModel());
        return "admin-panel";
    }





    @PostMapping("/app/register")
    public String addUser(@Valid RegisterModel registerModel, BindingResult bindingResult,
            Model model, @RequestParam(name = "role") String role, HttpServletRequest request) {

        // generate, set password 
        String genPass = PasswordServices.generatePassword(12);
        registerModel.setPassword(genPass);
        
        // get and set role
        registerModel.setRole(role.contains("admin") ? roleService.getRole(RoleType.ADMINISTRATOR) : roleService.getRole(RoleType.EDITOR));
        
        // attributes to be used when sending email
        SessionHandler.addAttribute(request, "rawPass", genPass);
        SessionHandler.addAttribute(request, "recipient", registerModel.getEmail());


        if (bindingResult.hasErrors()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", bindingResult.getAllErrors().toString());
        }

        try {
            // send password to email if creation was successful
            if (us.addUser(RegisterModel.toUser(registerModel)) != null) return "redirect:/send-password";
            
            // error message
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Error creating user. Please try again.");
            
            return "redirect:/";
        }
        catch (UserNotFoundException e) {

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }

    }





    @PostMapping("/attempt-login")
    public String login(@Valid LoginModel loginModel, BindingResult bindingResult, Model model,
            HttpServletRequest request) {
        
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
            
            
            if(loginStatus == LoginStatus.FAILED){
                las.recordFailedAttempt(loginModel, currentAttempt);
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "User not found. Please try again.");
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

            // check if account is suspended
            if(loginStatus == LoginStatus.LOCKED){
                // TODO handle error message
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Account has been suspended. Please contact administrator.");
                las.recordLoginLocked(currentAttempt);
                return "index";
            }
            
        }
        
        catch(UserNotFoundException  e){
            
            // TODO implement global error message handler

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage()+" Please try again.");
            las.recordFailedAttempt(loginModel, currentAttempt);

            return "index";
        }
        catch(Exception e){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Something went wrong. Please try again.");
            las.recordFailedAttempt(loginModel, currentAttempt);
        }
                
        // TODO set access level
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






    @GetMapping("/logout")
    public String logout (HttpServletRequest request){
        SessionHandler.clearSession(request);
        return "redirect:/";
    }

}
