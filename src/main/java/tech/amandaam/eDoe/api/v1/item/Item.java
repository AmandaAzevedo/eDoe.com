package tech.amandaam.eDoe.api.v1.item;

import lombok.*;
import tech.amandaam.eDoe.api.v1.descriptor.Descriptor;
import tech.amandaam.eDoe.api.v1.user.User;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "item_table")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_generator")
    @SequenceGenerator(name="item_generator",sequenceName = "item_sequence", allocationSize = 1)
    private Long id;
    @ManyToOne
    private User user;
    private Long quantity;
    private String descriptionOrJustification;
    @ManyToMany
    @JoinTable(
            name = "item_descriptor",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "descriptor_id")
    )
    private List<Descriptor> descriptors;
    @Enumerated(EnumType.STRING)
    private ItemTypeEnum itemType;
}
