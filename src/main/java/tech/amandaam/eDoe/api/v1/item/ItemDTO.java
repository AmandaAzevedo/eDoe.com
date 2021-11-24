package tech.amandaam.eDoe.api.v1.item;

import lombok.Builder;
import lombok.Getter;
import tech.amandaam.eDoe.api.v1.descriptor.DescriptorDTO;
import tech.amandaam.eDoe.api.v1.user.SimpleUserDTO;
import tech.amandaam.eDoe.api.v1.user.UserDTO;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class ItemDTO implements Serializable {
    private Long id;
    private SimpleUserDTO owner;
    private Long quantity;
    private String detailedDescription;
    private List<DescriptorDTO> descriptors;

    public static ItemDTO converteToItemDTO(Item item){
        return ItemDTO.builder().id(item.getId())
                .owner(SimpleUserDTO.convertToUserDTO(item.getOwner()))
                .quantity(item.getQuantity())
                .detailedDescription(item.getDetailedDescription())
                .descriptors(DescriptorDTO.convertToListDescriptorDTO(item.getDescriptors()))
                .build();
    }

    public static List<ItemDTO> convertToListItemDTO(List<Item> items){
        List<ItemDTO> list = new LinkedList<ItemDTO>();
        for (Item i: items){
            list.add(converteToItemDTO(i));
        }
        return list;
    }
}
