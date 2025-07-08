package kraine.app.eq_inventory.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.validation.constraints.NotNull;
import kraine.app.eq_inventory.YearMonthConveter;

import java.time.YearMonth;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
// @Cacheable
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

// @NamedEntityGraphs({
//     @NamedEntityGraph(
//         name = "Equipment.fullDetails",
//         attributeNodes = {
//             @NamedAttributeNode(value = "model", subgraph = "model.manufacturer"),
//             @NamedAttributeNode(value = "location", subgraph = "location.property")
//         },
//         subgraphs = {
//             @NamedSubgraph(
//                 name = "model.manufacturer",
//                 attributeNodes = @NamedAttributeNode("manufacturer")
//             ),
//             @NamedSubgraph(
//                 name = "location.property",
//                 attributeNodes = {
//                     @NamedAttributeNode("property"),
//                     @NamedAttributeNode(value = "property", subgraph = "property.regionAndUser")
//                 }
//             ),
//             @NamedSubgraph(
//                 name = "property.regionAndUser",
//                 attributeNodes = {
//                     @NamedAttributeNode("region"),
//                     @NamedAttributeNode(value = "user", subgraph = "user.role")
//                 }
//             ),
//             @NamedSubgraph(
//                 name="user.role",
//                 attributeNodes = @NamedAttributeNode("role")
//             )

//         }
//     ),
//     @NamedEntityGraph(
//         name = "Equipment.basicDetails",
//         attributeNodes = {
//             @NamedAttributeNode("model"),
//             @NamedAttributeNode("location")
//         }
//     )
// })



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
    // @JsonIgnore
    @NotNull(message = "Model cannot be null")
    @JoinColumn(name = "model") //column in other table this is linked to
    private Model model;

    @ManyToOne
    // @JsonIgnore
    @NotNull(message = "Location cannot be null")
    @JoinColumn(name = "location") // column in other table this is linked to
    private Location location;

}
