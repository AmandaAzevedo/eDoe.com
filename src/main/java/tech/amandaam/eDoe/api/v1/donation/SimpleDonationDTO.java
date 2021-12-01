package tech.amandaam.eDoe.api.v1.donation;

import lombok.Builder;
import lombok.Getter;
import tech.amandaam.eDoe.api.v1.user.SimpleUserDTO;
import tech.amandaam.eDoe.api.v1.user.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class SimpleDonationDTO {
    private Long id;
    private Date donationDate;
    private SimpleUserDTO donorUser;
    private String descriptionDonatedItem;
    private Long quantityOfDonatedItem;

    public static SimpleDonationDTO convertToSimpleDonationDTO(Donation donation){
        return SimpleDonationDTO.builder().id(donation.getId())
                .donationDate(donation.getDonationDate())
                .donorUser(SimpleUserDTO.convertToSimpleUserDTO(donation.getDonorUser()))
                .descriptionDonatedItem(donation.getDescriptionDonatedItem())
                .quantityOfDonatedItem(donation.getQuantityOfDonatedItem()).build();
    }

    public static List<SimpleDonationDTO> convertToListDonationDTO(List<Donation> donations){
        List<SimpleDonationDTO> list = new LinkedList<SimpleDonationDTO>();
        for (Donation i: donations){
            list.add(convertToSimpleDonationDTO(i));
        }
        return list;
    }
}
