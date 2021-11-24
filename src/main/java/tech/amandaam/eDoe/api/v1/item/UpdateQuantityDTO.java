package tech.amandaam.eDoe.api.v1.item;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UpdateQuantityDTO implements Serializable {
    private Long newQuantity;
}
