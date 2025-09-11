package kraine.app.eq_inventory.API;

import kraine.app.eq_inventory.DTO.LoginResponse;
import kraine.app.eq_inventory.DTO.UserDTO;
import kraine.app.eq_inventory.model.LoginModel;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<?> loginAPI(@RequestBody LoginModel loginModel, HttpServletRequest request) {
        try {
            LoginResponse response = authService.loginFromMobile(loginModel, request);

            switch (response.getStatus()) {
                case SUCCESSFUL:
                    return ResponseEntity.ok(UserDTO.from(response.getUser()));
                case TEMPORARY:
                    return ResponseEntity.ok(Map.of(
                            "message", "Your password is temporary, please reset it.",
                            "user", UserDTO.from(response.getUser())));

                case LOCKED:
                    return ResponseEntity.status(HttpStatus.LOCKED)
                            .body(Map.of("error", "Account is locked"));

                case FAILED:
                default:
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error", "User not found."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error: " + e.getMessage()));
        }
    }



    @GetMapping("/get-all-users")
    public List<UserDTO> findAllUsers() {
        return us.getAllUserDTOs();
    }
}
