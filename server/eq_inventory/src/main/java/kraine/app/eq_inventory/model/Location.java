package kraine.app.eq_inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "property" }) }) // make name and property composite keys

@NamedEntityGraph(name="Location.fullDetails",
        attributeNodes = {
            @NamedAttributeNode(value = "property", subgraph="property.regionAndUser")
        },
        subgraphs = {
                @NamedSubgraph(name = "property.regionAndUser", attributeNodes = {
                        @NamedAttributeNode("region"),
                        @NamedAttributeNode(value = "user", subgraph = "user.role")
                }),
                @NamedSubgraph(name = "user.role", attributeNodes = @NamedAttributeNode("role"))

        })





public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Location name cannot be blank")
    private String name;

    @ManyToOne
    // @JsonIgnore
    @JoinColumn(name = "property")
    private Property property;
}
