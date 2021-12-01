package tech.amandaam.eDoe.api.v1.request;

import lombok.Getter;
import tech.amandaam.eDoe.api.v1.user.UserRoleEnum;

import java.io.Serializable;

@Getter
public class RequestCreateDTO implements Serializable {
    private String requestingUserEmail;
    private UserRoleEnum requestedRole;
}
