package kraine.app.eq_inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
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

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime manufacteredDate;

    @ManyToOne
    @JoinColumn(name = "model") //column in other table this is linked to
    private Model model;

    @ManyToOne
    @JoinColumn(name = "location") // column in other table this is linked to
    private Location location;

}
