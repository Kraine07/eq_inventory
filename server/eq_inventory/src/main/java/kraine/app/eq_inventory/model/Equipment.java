package kraine.app.eq_inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import kraine.app.eq_inventory.YearMonthConveter;

import java.time.YearMonth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true, unique = true)
    private String serialNumber;

    @Column(nullable = true)
    @Convert(converter = YearMonthConveter.class)
    private YearMonth manufacturedDate;

    @ManyToOne
    @NotNull(message = "Model cannot be null")
    @JoinColumn(name = "model") //column in other table this is linked to
    private Model model;

    @ManyToOne
    @NotNull(message = "Location cannot be null")
    @JoinColumn(name = "location") // column in other table this is linked to
    private Location location;

}
