package kraine.app.eq_inventory.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterModel {

    @NotBlank(message = "This field cannot be empty.")
    private String firstName;

    @NotBlank(message = "This field cannot be empty.")
    private String lastName;

    @NotBlank(message = "This field cannot be empty.")
    @Email(message = "Invalid email.")
    private String email;

    // @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^*&()]).{8,}$", message = "Password must be at least 8 characters and include uppercase, lowercase, a number, and a special character")
    // private String password;
    
    private Role role;
    
    public static User toUser(RegisterModel registerModel){
        if(registerModel == null){
            throw new  IllegalArgumentException("RegisterModel cannot be null");
        }
        return  User.builder()
                .firstName(registerModel.getFirstName())
                .lastName(registerModel.getLastName())
                .email(registerModel.getEmail())
                .password(null)
                .role(registerModel.getRole())
                .build();
    }
}
