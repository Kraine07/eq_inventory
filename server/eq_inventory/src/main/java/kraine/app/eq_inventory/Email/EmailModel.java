package kraine.app.eq_inventory.Email;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmailModel {
    @Email private String recipient;
    private String messageBody;
    private String subject;
}
