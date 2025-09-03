package kraine.app.eq_inventory.API;

import kraine.app.eq_inventory.model.LoginModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserAPI {

    private final UserService us;

    public UserAPI(UserService us) { // Constructor for injecting the dependency.
        this.us = us;
    }


    @PostMapping("/register")
    public ResponseEntity<User> addUserAPI(@RequestBody User user) {
        User newUser = us.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<User> loginAPI(@RequestBody LoginModel loginModel) {
        System.out.println("#################################### " + loginModel);
        User retrievedUser = us.attemptLogin(loginModel);
        return new ResponseEntity<>(retrievedUser, HttpStatus.OK) ;
    }
}
