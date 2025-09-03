package kraine.app.eq_inventory;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.ServletContext;

@Configuration
public class Config {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


    // disable URL rewriting
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return (ServletContext servletContext) -> servletContext.setSessionTrackingModes(
                java.util.Collections.singleton(SessionTrackingMode.COOKIE));
    }

}
