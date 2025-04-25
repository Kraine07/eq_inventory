package kraine.app.eq_inventory.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "users")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto increment
    private Long id;

    @Column(nullable = false, unique = true, name = "description")
    private RoleType roleType;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
