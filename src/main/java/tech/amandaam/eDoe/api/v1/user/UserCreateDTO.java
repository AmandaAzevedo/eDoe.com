package tech.amandaam.eDoe.api.v1.user;

import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Getter
public class UserCreateDTO implements Serializable {
    private String name;
    private String email;
    private String password;
    private String telephone;
    @Enumerated(EnumType.STRING)
    private UserCategoryEnum userCategory;
}
