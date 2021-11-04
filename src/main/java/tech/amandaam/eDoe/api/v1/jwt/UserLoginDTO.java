package tech.amandaam.eDoe.api.v1.jwt;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserLoginDTO implements Serializable {
    private String email;
    private String password;
}
