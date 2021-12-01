package tech.amandaam.eDoe.api.v1.descriptor;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"name"})
@Table(name = "descriptor_table")
@Entity
public class Descriptor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "descriptor_generator")
    @SequenceGenerator(name="descriptor_generator",sequenceName = "descriptor_sequence", allocationSize = 1)
    private Long id;
    private String name;
}
