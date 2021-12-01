package tech.amandaam.eDoe.api.v1.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ResponseRequestDTO implements Serializable {
    private boolean accept;
    private Long id;
}
