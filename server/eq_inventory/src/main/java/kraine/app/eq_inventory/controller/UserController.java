package kraine.app.eq_inventory.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kraine.app.eq_inventory.exception.DuplicateUserException;
import kraine.app.eq_inventory.exception.PasswordNotFoundException;
import kraine.app.eq_inventory.exception.UserNotFoundException;
import kraine.app.eq_inventory.model.RoleType;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.service.RoleService;
import kraine.app.eq_inventory.service.UserService;



@Controller
public class UserController {

    private final UserService us;
    private final RoleService rs;


    public UserController(UserService us, RoleService rs) {
        this.us = us;
        this.rs = rs;
    }


    @GetMapping("")
    public String loadWelcome(Model model, HttpSession session) {
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/app/admin")
    public String loadAdminPanel(Model model, HttpSession session) {
        model.addAttribute("user", new User());
        return "admin-panel";
    }



    @PostMapping("/app/register")
    public String addUser(@Valid User user, BindingResult bindingResult, @RequestParam(name = "role") String role,
            Model model, HttpSession session) {
        user.setRole(rs.getRole(role.contains("admin") ? RoleType.ADMINISTRATOR : RoleType.EDITOR));

        try {
            User newUser = us.addUser(user);
        } catch (DuplicateUserException e) {

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println(e.getMessage());
            return "admin-panel";
        }

        return "redirect:/app/admin";
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
            model.addAttribute("errorMessage", "User not found.");
            model.addAttribute("error", true);
            return "index";
        }

        return "main"; // Proceed to main page upon successful login
    }

}
