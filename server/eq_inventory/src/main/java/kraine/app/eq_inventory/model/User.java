package kraine.app.eq_inventory.model;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
// generate getters, setters, toString(), etc.
@Data
// constructors
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate



@Table(name= "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto increment
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "This field cannot be empty.")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "This field cannot be empty.")
    private String lastName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "This field cannot be empty.")
    @Email(message = "Invalid email.")
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^*&()]).{8,}$", message = "Password must be at least 8 characters and include uppercase, lowercase, a number, and a special character")
    private String password;

    @ManyToOne
    // @JsonIgnore
    @JoinColumn(name = "role")
    private Role role;

    @Column(nullable = false)
    private Boolean isTemporaryPassword;

    @Column(nullable = false)
    private Boolean isSuspended;

    @Column(nullable = false)
    private int failedAttempts;

    private Boolean isAdmin;

    public boolean getIsAdmin() {
        isAdmin = role.getRoleType() == RoleType.ADMINISTRATOR;
        return isAdmin;
    }


}