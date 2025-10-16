package kraine.app.eq_inventory.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = true)
    private String serialNumber;

    @Column(nullable = true)
    @Convert(converter = YearMonthConveter.class)
    private YearMonth manufacturedDate;


    @ManyToOne
    @NotNull(message = "Model cannot be null")
    @JoinColumns({
        @JoinColumn(name = "model_manufacturer_id", referencedColumnName = "manufacturer_id"),
        @JoinColumn(name = "model_description", referencedColumnName = "description")
    })
    private Model model;



    @ManyToOne
    @NotNull(message = "Location cannot be null")
    @JoinColumns({
        @JoinColumn(name = "location_property_id", referencedColumnName = "property_id"),
        @JoinColumn(name = "location_name", referencedColumnName = "name")
    })
    private Location location;



    @OneToOne(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private EquipmentImage image;

}
