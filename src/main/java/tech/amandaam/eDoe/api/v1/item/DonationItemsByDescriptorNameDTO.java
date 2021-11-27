package tech.amandaam.eDoe.api.v1.item;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class DonationItemsByDescriptorNameDTO implements Serializable {
    private String name;
}
