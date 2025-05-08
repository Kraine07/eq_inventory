package kraine.app.eq_inventory.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {


    @NotBlank(message = "This field cannot be empty.")
    @Email(message = "Invalid email.")
    private String email;


    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^*&()]).{8,}$", message = "Password must be at least 8 characters and include uppercase, lowercase, a number, and a special character")
    private String password;
    
    public static User toUser(LoginModel loginModel){
       
         if(loginModel == null){
            throw new  IllegalArgumentException("LoginModel cannot be null");
        }
        return  User.builder()
                .email(loginModel.getEmail())
                .password(loginModel.getPassword())
                .build();
    }
}
