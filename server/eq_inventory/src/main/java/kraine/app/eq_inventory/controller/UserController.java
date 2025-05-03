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
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class UserController {

    private final UserService us;



    public UserController(UserService us) {
        this.us = us;
    }




    // ROUTES
    @GetMapping("")
    public String loadApp(Model model, HttpServletRequest request) {


        // attribute to be used in user form
        model.addAttribute("user", new User());

        //check if any user have been created
        if (us.getUsers().isEmpty()) {
            return "setup";
        }

        // check if a user is logged in
        else if (SessionHandler.hasSessionAttribute(request, "authUser")) {
            return "redirect:/app/admin"; // redirect so app calls loadAdminPanel method
        }

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
        model.addAttribute("user", new User());
        return "admin-panel";
    }





    @PostMapping("/app/register")
    public String addUser(@Valid User user, BindingResult bindingResult,
            Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());


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
            return "redirect:/";
        } catch (DuplicateUserException e) {

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }

    }





    @PostMapping("/attempt-login")
    public String login(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model,
            HttpServletRequest request) {
        model.addAttribute("user", new User());

        // Handle validation errors
        if (bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("password")) {
            return "index";
        }

        // Handle login logic
        try {
            User authenticatedUser = us.findByEmail(user);
            SessionHandler.addAttribute(request, "authUser", authenticatedUser);
            //check if password is temporary
            if (authenticatedUser.getIsTemporaryPassword()) {
                return "update-password";
            }

            return "redirect:/";
        }
        catch (UserNotFoundException | PasswordNotFoundException e) {
            model.addAttribute("errorMessage", "User not found.");
            model.addAttribute("error", true);
            return "index";
        }// Proceed to main page upon successful login
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
