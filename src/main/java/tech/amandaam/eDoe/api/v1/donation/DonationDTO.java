package tech.amandaam.eDoe.api.v1.donation;

import lombok.Builder;
import lombok.Getter;
import tech.amandaam.eDoe.api.v1.user.SimpleUserDTO;

import java.util.Date;

@Getter
@Builder
public class DonationDTO {
    private Long id;
    private Date donationDate;
    private SimpleUserDTO donorUser;
    private SimpleUserDTO receivingUser;
    private String descriptionDonatedItem;
    private Long quantityOfDonatedItem;

    public static DonationDTO convertToDonationDTO(Donation donation){
        return DonationDTO.builder().id(donation.getId())
                .donationDate(donation.getDonationDate())
                .donorUser(SimpleUserDTO.convertToSimpleUserDTO(donation.getDonorUser()))
                .receivingUser(SimpleUserDTO.convertToSimpleUserDTO(donation.getReceivingUser()))
                .descriptionDonatedItem(donation.getDescriptionDonatedItem())
                .quantityOfDonatedItem(donation.getQuantityOfDonatedItem()).build();
    }
}
