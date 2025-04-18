package kraine.app.eq_inventory.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kraine.app.eq_inventory.exception.PasswordNotFoundException;
import kraine.app.eq_inventory.exception.UserNotFoundException;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.service.UserService;



@Controller
public class UserController {

    private final UserService us;

    public UserController(UserService us) {
        this.us = us;
    }


    @GetMapping("")
    public String loadWelcome(Model model, HttpSession session) {
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/app/admin")
    public String loadAdminPanel() {
        return "admin";
    }



    @PostMapping("/app/register")
    public ResponseEntity<User> addUser(@Valid User user, BindingResult bindingResult, Model model, HttpSession session) {
        User newUser = us.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PostMapping("/app/login")
    public String login(@Valid User user, BindingResult bindingResult, Model model, HttpSession session) {

        // Handle validation errors
        if (bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("password")) {
            return "index";
        }

        // Handle login logic
        try {
            User authenticatedUser = us.findByEmail(user);
            session.setAttribute("user", authenticatedUser); // Set user in session if login succeeds
        } catch (UserNotFoundException | PasswordNotFoundException e) {
            model.addAttribute("UserError", e.getMessage());
            return "index";
        }

        return "main"; // Proceed to main page upon successful login
    }

}
