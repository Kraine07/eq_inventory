package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.LoginAttempt;
import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.LoginStatus;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.repository.LoginAttemptsRepositoryInterface;



@Transactional
@Service
public class LoginAttemptsService {


    @Autowired
    LoginAttemptsRepositoryInterface lar;

    @Autowired
    UserService userService;

    private static final int MAX_ATTEMPTS = Integer.parseInt(System.getenv("MAX_LOGIN_ATTEMPTS"));

    public LoginAttempt recordSuccessfulAttempt(LoginAttempt loginAttempt, LoginModel loginModel) {
        User userFromDB = userService.findByEmail(loginModel);
        // reset failed attempts
        userFromDB.setFailedAttempts(0);
        loginAttempt.setStatus(LoginStatus.SUCCESSFUL);
        return lar.save(loginAttempt);
    }



    public void recordFailedAttempt(LoginModel loginModel, LoginAttempt loginAttempt, HttpServletRequest request) {
        User userFromDB = userService.findByEmail(loginModel);
        if (userFromDB != null) {

            // increment failed attempts
            userFromDB.setFailedAttempts(userFromDB.getFailedAttempts() + 1);
            if (userFromDB.getFailedAttempts() >= MAX_ATTEMPTS) {
                userFromDB.setIsSuspended(true);
                lockLogin(loginAttempt);
                // TODO trigger error message
            }
            userService.updateUser(userFromDB);
        }
        lar.save(loginAttempt);
    }



//    public LoginAttempt failedAttempt(LoginAttempt loginAttempt) {
//        loginAttempt.setStatus(LoginStatus.FAILED);
//        return lar.save(loginAttempt);
//    }

    
    
    public LoginAttempt lockLogin(LoginAttempt loginAttempt) {
        loginAttempt.setStatus(LoginStatus.LOCKED);
        return lar.save(loginAttempt);
    }

    public List<LoginAttempt> findByEmail(String email) {
        return lar.findByEmail(email);
    }
}
