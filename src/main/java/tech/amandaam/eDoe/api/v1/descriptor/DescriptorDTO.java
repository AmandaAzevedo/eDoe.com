package tech.amandaam.eDoe.api.v1.descriptor;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class DescriptorDTO {
    private Long id;
    private String name;

    public static DescriptorDTO convertToDescriptorDTO(Descriptor descriptor){
        return DescriptorDTO.builder()
                .id(descriptor.getId())
                .name(descriptor.getName()).build();
    }

    public static List<DescriptorDTO> convertToListDescriptorDTO(List<Descriptor> descriptors){
        List<DescriptorDTO> descriptorDTOList = new LinkedList<>();
        for (Descriptor d : descriptors){
            descriptorDTOList.add(convertToDescriptorDTO(d));
        }
        return descriptorDTOList;
    }
}
