package kraine.app.eq_inventory.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kraine.app.eq_inventory.RandomPasswordGenerator;
import kraine.app.eq_inventory.Email.EmailModel;
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
    private EmailModel email = new EmailModel();



    public UserController(UserService us, RoleService rs) {
        this.us = us;
        this.rs = rs;
    }


    // check if a user has been created and decide whether to load setup or index
    public String loadScreen(Model model, HttpSession session){
        if (us.getUsers().isEmpty()) {
            return "setup";
        }
        else if(session.getAttribute("user") != null){
            return "redirect:/app/admin";

        }
        return "index";
    }


    @GetMapping("")
    public String loadApp(Model model, HttpSession session) {
        model.addAttribute("admin", rs.findByRoleType(RoleType.ADMINISTRATOR).getId());
        model.addAttribute("editor", rs.findByRoleType(RoleType.EDITOR).getId());
        model.addAttribute("user", new User());
        return loadScreen(model, session);
    }


    @GetMapping("/app/admin")
    public String loadAdminPanel(Model model, HttpSession session) {
        List<User> UserList = us.getUsers();
        model.addAttribute("admin", rs.findByRoleType(RoleType.ADMINISTRATOR).getId());
        model.addAttribute("editor", rs.findByRoleType(RoleType.EDITOR).getId());
        model.addAttribute("userList", UserList);
        model.addAttribute("user", new User());
        return "admin-panel";
    }


    @PostMapping("/app/register")
    public String addUser(@Valid User user, BindingResult bindingResult, @RequestParam(name = "role") String role,
            Model model, HttpSession session) {
        String pGen = RandomPasswordGenerator.generatePassword(12);
        user.setPassword(pGen);

        if (bindingResult.hasErrors()) {

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", bindingResult.getAllErrors().toString());
        }
        session.setAttribute("pgen", pGen);

        email.setRecipient(user.getEmail());
        email.setSubject("Equipment Inventory App Verification");
        email.setMessageBody("Kindly click the link below to set password.\r <a>Link to be provided</a>");
        model.addAttribute("email", email);
        try {
            us.addUser(user);
            return "send-email";
        } catch (DuplicateUserException e) {

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
            return "";
        }

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
