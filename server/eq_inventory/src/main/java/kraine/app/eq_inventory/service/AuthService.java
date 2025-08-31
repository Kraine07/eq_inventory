package kraine.app.eq_inventory.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import kraine.app.eq_inventory.SessionHandler;
import kraine.app.eq_inventory.model.LoginAttempt;
import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.LoginStatus;
import kraine.app.eq_inventory.model.User;

@Service
public class AuthService {

    private final UserService us;
    private final LoginAttemptsService las;

    public AuthService(UserService us, LoginAttemptsService las){
        this.us = us;
        this.las = las;
    }

    public LoginStatus authenticateUser(String email, String password, HttpServletRequest request) {

        User authUser = us.attemptLogin(LoginModel.builder().email(email).password(password).build());


        //check account status
        if (authUser.getIsSuspended())
            return LoginStatus.LOCKED;

        SessionHandler.addAttribute(request, "authUser", authUser);
        // check if password is temporary
        if (authUser.getIsTemporaryPassword())
            return LoginStatus.TEMPORARY;

        return LoginStatus.SUCCESSFUL;

    }







    public LoginStatus login(LoginModel loginModel, HttpServletRequest request) {

        // clear session
        SessionHandler.clearSession(request);

        // construct login attempt
        LoginAttempt currentAttempt = LoginAttempt.builder()
            .email(loginModel.getEmail())
            .ipAddress(request.getRemoteAddr())
            .timestamp(LocalDateTime.now())
            .status(LoginStatus.SUCCESSFUL)
            .userAgent(request.getHeader("User-Agent"))
            .build();

        try {
            User authUser = us.attemptLogin(loginModel);

            // check account status
            if (authUser.getIsSuspended()) {
                las.recordLoginLocked(currentAttempt);
                return LoginStatus.LOCKED;
            }

            // add authUser to session
            SessionHandler.addAttribute(request, "authUser", authUser);

            // check if password is temporary
            if (authUser.getIsTemporaryPassword()) {
                las.recordSuccessfulAttempt(currentAttempt, loginModel);
                return LoginStatus.TEMPORARY;
            }


        } catch (Exception e) {
            throw e;
        }

        las.recordSuccessfulAttempt(currentAttempt, loginModel);
        return LoginStatus.SUCCESSFUL;
    }
}
