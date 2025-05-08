package kraine.app.eq_inventory.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {
    public Boolean attemptLogin(String email, String password, HttpServletRequest request) {
        return true;
    }
}
