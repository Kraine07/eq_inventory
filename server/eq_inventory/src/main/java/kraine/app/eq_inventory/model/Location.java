package kraine.app.eq_inventory.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@IdClass(LocationId.class) // Composite key classz


public class Location {


        @Id
        @ManyToOne
        @JoinColumn(name = "property_id", nullable = false)
        private Property property;


        @Id
        @Column(nullable = false)
        @NotBlank(message = "Location name cannot be blank")
        private String name;



        public LocationId getLocationId() {
                return new LocationId(property.getId(), name);
        }

}
