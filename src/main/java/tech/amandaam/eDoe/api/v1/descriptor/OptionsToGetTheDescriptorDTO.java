package tech.amandaam.eDoe.api.v1.descriptor;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OptionsToGetTheDescriptorDTO implements Serializable {
    private OptionsToGetTheDescriptorEnum optionsToGetTheDescriptorEnum;
}
