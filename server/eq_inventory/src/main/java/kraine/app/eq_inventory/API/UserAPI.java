package kraine.app.eq_inventory.API;

import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.LoginStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.service.AuthService;
import kraine.app.eq_inventory.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserAPI {

    @Autowired
    private final UserService us;

    @Autowired
    private AuthService authService;


    public UserAPI(UserService us) { // Constructor for injecting the dependency.
        this.us = us;
    }


    @PostMapping("/register")
    public ResponseEntity<User> addUserAPI(@RequestBody User user) {
        User newUser = us.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public User loginAPI(@RequestBody LoginModel loginModel, HttpServletRequest request) {
        User user= null;
        LoginStatus userStatus = authService.login(loginModel, request);
        if (userStatus == LoginStatus.SUCCESSFUL) {
            user = (User) request.getSession().getAttribute("authUser");
        }
        return user;
    }
}
