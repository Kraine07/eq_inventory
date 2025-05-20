package kraine.app.eq_inventory.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import kraine.app.eq_inventory.SessionHandler;
import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.LoginStatus;
import kraine.app.eq_inventory.model.User;

@Service
public class AuthService {

    private final UserService us;

    public AuthService(UserService us){
        this.us = us;
    }

    public LoginStatus authenticateUser(String email, String password, HttpServletRequest request) {
        

            User authUser = us.attemptLogin(LoginModel.builder().email(email).password(password).build());
            // check if a user is found
//            if(authUser == null) throw new UserNotFoundException("Invalid credentials.");

            //check account status
            if(authUser.getIsSuspended())return LoginStatus.LOCKED;

            SessionHandler.addAttribute(request, "authUser", authUser);
            // check if password is temporary
            if(authUser.getIsTemporaryPassword()) return LoginStatus.TEMPORARY;

            return LoginStatus.SUCCESSFUL;


    }
}
