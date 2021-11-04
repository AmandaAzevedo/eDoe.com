package tech.amandaam.eDoe.api.v1.user;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Getter
@Builder
public class UserDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String telephone;
    @Enumerated(EnumType.STRING)
    private UserCategoryEnum userCategory;
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    public static UserDTO convertToUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .telephone(user.getTelephone())
                .userCategory(user.getUserCategory())
                .userRole(user.getUserRole()).build();
    }
}
