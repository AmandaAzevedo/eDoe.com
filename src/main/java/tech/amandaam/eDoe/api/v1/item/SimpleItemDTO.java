package tech.amandaam.eDoe.api.v1.item;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class SimpleItemDTO implements Serializable {
    private String descriptionOrJustification;
    private Long quantity;

    public static SimpleItemDTO converteToSimpleItemDTO(Item item){
        return SimpleItemDTO.builder()
                .quantity(item.getQuantity())
                .descriptionOrJustification(item.getDescriptionOrJustification()).build();
    }

    public static List<SimpleItemDTO> convertToListSimpleItemDTO(List<Item> items){
        List<SimpleItemDTO> list = new LinkedList<SimpleItemDTO>();
        for (Item i: items){
            list.add(converteToSimpleItemDTO(i));
        }
        return list;
    }
}
