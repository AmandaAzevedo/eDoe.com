package tech.amandaam.eDoe.api.v1.request;

import lombok.*;
import tech.amandaam.eDoe.api.v1.user.User;
import tech.amandaam.eDoe.api.v1.user.UserRoleEnum;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_generator")
    @SequenceGenerator(name="request_generator",sequenceName = "request_sequence", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User requestingUser;
    @Column(name = "user_role_request")
    @Enumerated(EnumType.STRING)
    private UserRoleEnum requestedRole;
    private boolean evaluationResult;

}
