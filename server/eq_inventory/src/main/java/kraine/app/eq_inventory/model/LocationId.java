package kraine.app.eq_inventory.model;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationId implements Serializable{

    private Long property;
    private String name;
}
