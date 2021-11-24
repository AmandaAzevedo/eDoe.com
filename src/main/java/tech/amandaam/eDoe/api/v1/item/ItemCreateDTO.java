package tech.amandaam.eDoe.api.v1.item;

import lombok.Getter;
import java.io.Serializable;
import java.util.List;

@Getter
public class ItemCreateDTO implements Serializable {
    private String ownerEmail;
    private Long quantity;
    private String detailedDescription;
    private List<String> descriptors;
}
