package tech.amandaam.eDoe.api.v1.item;

import lombok.Getter;
import java.io.Serializable;
import java.util.List;

@Getter
public class ItemCreateDTO implements Serializable {
    private String userEmail;
    private Long quantity;
    private String descriptionOrJustification;
    private List<String> descriptors;
    private ItemTypeEnum itemType;

}
