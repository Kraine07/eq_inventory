package kraine.app.eq_inventory.model;

import java.time.LocalDateTime;

// import groovy.transform.builder.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto increment
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String ipAddress;


    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;


    // @Column(nullable = false)
    // private Long timestamp;


   
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LoginStatus status;

    @Column(length = 500)
    private String userAgent;

    // @Column(nullable = false)
    // private String status;

}
