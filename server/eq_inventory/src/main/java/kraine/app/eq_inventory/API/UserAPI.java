package kraine.app.eq_inventory.API;

import kraine.app.eq_inventory.DTO.UserDTO;
import kraine.app.eq_inventory.model.LoginModel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.service.AuthService;
import kraine.app.eq_inventory.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserAPI {

    private final UserService us;
    private final AuthService authService;

    public UserAPI(UserService us, AuthService authService) { // Constructor for injecting the dependency.
        this.us = us;
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> addUserAPI(@RequestBody User user) {
        User newUser = us.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginAPI(@RequestBody LoginModel loginModel) {
        User retrievedUser;
        try {

            // TODO: use authservice`
            retrievedUser = us.attemptLogin(loginModel);
            return new ResponseEntity<>(UserDTO.from(retrievedUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/get-all-users")
    public List<UserDTO> findAllUsers() {
        return us.getAllUserDTOs();
    }
}
