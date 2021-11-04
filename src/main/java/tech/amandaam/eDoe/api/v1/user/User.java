package tech.amandaam.eDoe.api.v1.user;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "user_system")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator",sequenceName = "user_sequence", allocationSize = 1)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String telephone;
    @Enumerated(EnumType.STRING)
    private UserCategoryEnum userCategory;
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

}
