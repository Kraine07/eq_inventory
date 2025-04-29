package kraine.app.eq_inventory.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kraine.app.eq_inventory.PasswordServices;
import kraine.app.eq_inventory.SessionHandler;
import kraine.app.eq_inventory.exception.DuplicateUserException;
import kraine.app.eq_inventory.exception.PasswordNotFoundException;
import kraine.app.eq_inventory.exception.UserNotFoundException;
import kraine.app.eq_inventory.model.RoleType;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.service.RoleService;
import kraine.app.eq_inventory.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class UserController {

    private final UserService us;
    private final RoleService rs;



    public UserController(UserService us, RoleService rs) {
        this.us = us;
        this.rs = rs;
    }


    // check if a user has been created and decide whether to load setup or index
    private String loadScreen(Model model, HttpServletRequest request){
        if (us.getUsers().isEmpty()) {
            return "setup";
        }
        else if(SessionHandler.hasSessionAttribute(request, "user")){
            return "admin-panel"; // calls loadAdminPanel method

        }
        return "index";
    }


    @GetMapping("")
    public String loadApp(Model model, HttpServletRequest request) {
        model.addAttribute("admin", rs.findByRoleType(RoleType.ADMINISTRATOR).getId());
        model.addAttribute("editor", rs.findByRoleType(RoleType.EDITOR).getId());
        model.addAttribute("user", new User());
        return loadScreen(model, request);
    }


    @GetMapping("/app/admin")
    public String loadAdminPanel(Model model) {
        List<User> UserList = us.getUsers();
        model.addAttribute("admin", rs.findByRoleType(RoleType.ADMINISTRATOR).getId());
        model.addAttribute("editor", rs.findByRoleType(RoleType.EDITOR).getId());
        model.addAttribute("userList", UserList);
        model.addAttribute("user", new User());
        return "admin-panel";
    }





    @PostMapping("/app/register")
    public String addUser(@Valid User user, BindingResult bindingResult,
            Model model, HttpServletRequest request) {


        String genPass = PasswordServices.generatePassword(12);
        // generate, set password then add to session
        user.setPassword(genPass);
        SessionHandler.addAttribute(request, "rawPass", genPass);
        SessionHandler.addAttribute(request, "recipient", user.getEmail());


        if (bindingResult.hasErrors()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", bindingResult.getAllErrors().toString());
        }

        try {
            if (us.addUser(user) != null) {
                //save user to be emailed
                return "redirect:/send-password";
            }
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Error creating user. Please try again.");
            return "";
        } catch (DuplicateUserException e) {

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
            return "";
        }

    }





    @PostMapping("/attempt-login")
    public String login(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model, HttpServletRequest request) {

        // Handle validation errors
        if (bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("password")) {
            return "index";
        }

        // Handle login logic
        try {
            User authenticatedUser = us.findByEmail(user);
            SessionHandler.addAttribute(request, "user", authenticatedUser);
            //check if password is temporary
            if (authenticatedUser.getIsTemporaryPassword()) {
                return "update-password";
            }

            return "redirect:";
        }
        catch (UserNotFoundException | PasswordNotFoundException e) {
            model.addAttribute("errorMessage", "User not found.");
            model.addAttribute("error", true);
            return "index";
        }// Proceed to main page upon successful login
    }




    @PostMapping("/update-password") // validate fields against password pattern
    public String updatePassword(Model model, @RequestParam(name = "old-password") String oldPassword,
            @RequestParam(name = "new-password") String newPassword,
            @RequestParam(name = "confirm-password") String confirmPassword, HttpServletRequest request) {


        // TODO CHECK FOR CORRECT OLD PASSWORD - service layer???

        // VALIDATE PASSWORDS AGAINST PATTERN
        if(!PasswordServices.validateFieldAgainstPattern(new User(), "password", oldPassword) || !PasswordServices.validateFieldAgainstPattern(new User(), "password", newPassword) || !PasswordServices.validateFieldAgainstPattern(new User(), "password", confirmPassword)){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Password must be at least 8 characters long with uppercase, lowercase, numeral and any special character from  !@#$%^*&()");
            return "update-password";
        }


        //CHECK IS NEW PASSWORD IS DIFFERENT FROM OLD PASSWORD
        else if (oldPassword.equals(newPassword)) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "New password must be different from old password. Please try again.");
            return "update-password";
        }


        // CHECK IF NEW AND CONFIRMED PASSWORDS ARE THE SAME
        else if (!confirmPassword.equals(newPassword)) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "New passwords do not match. Please try again.");
            return "update-password";
        }

        // UPDATE PASSWORD
        User userToBeUpdated = SessionHandler.getAttribute(request, "user", User.class);

        // UPDATE PASSWORD
        userToBeUpdated.setPassword(newPassword);
        userToBeUpdated.setIsTemporaryPassword(false);

        // UPDATE USER
        if (us.updateUser(userToBeUpdated) != null) {
            System.out.println("PASSWORD UPDATED");
            return "redirect:";
        }
        return "update-password";
    }


}
