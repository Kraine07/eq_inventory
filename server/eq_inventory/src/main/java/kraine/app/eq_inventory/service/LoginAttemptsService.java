package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.LoginAttempt;
import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.LoginStatus;
import kraine.app.eq_inventory.repository.LoginAttemptsRepositoryInterface;



@Transactional
@Service
public class LoginAttemptsService {


    @Autowired
    LoginAttemptsRepositoryInterface lar;

    public LoginAttempt recordSuccessfulAttempt(LoginAttempt loginAttempt, LoginModel loginModel) {
        loginAttempt.setStatus(LoginStatus.SUCCESSFUL);
        return lar.save(loginAttempt);
    }



    public void recordFailedAttempt(LoginModel loginModel, LoginAttempt loginAttempt) {
        loginAttempt.setStatus(LoginStatus.FAILED);
        lar.save(loginAttempt);
    }

    
    
    public LoginAttempt recordLoginLocked(LoginAttempt loginAttempt) {
        loginAttempt.setStatus(LoginStatus.LOCKED);
        return lar.save(loginAttempt);
    }

    public List<LoginAttempt> findByEmail(String email) {
        return lar.findByEmail(email);
    }
}
