package kraine.app.eq_inventory.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Property implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto increment
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Property name cannot be blank")
    private String name;

    @ManyToOne
    @JoinColumn(name = "region")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "\"user\"")
    private User user;
}

