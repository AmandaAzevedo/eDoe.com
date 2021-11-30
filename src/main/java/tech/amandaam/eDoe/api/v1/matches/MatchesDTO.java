package tech.amandaam.eDoe.api.v1.matches;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class MatchesDTO implements Serializable {
    private String descriptor;
}
