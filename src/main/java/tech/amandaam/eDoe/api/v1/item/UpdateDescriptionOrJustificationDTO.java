package tech.amandaam.eDoe.api.v1.item;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UpdateDescriptionOrJustificationDTO implements Serializable {
    private String newDescriptionOrJustification;
}
