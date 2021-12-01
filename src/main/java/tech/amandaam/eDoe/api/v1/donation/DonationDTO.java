package tech.amandaam.eDoe.api.v1.donation;

import lombok.Builder;
import lombok.Getter;
import tech.amandaam.eDoe.api.v1.user.User;

import java.util.Date;

@Getter
@Builder
public class DonationDTO {
    private Long id;
    private Date donationDate;
    private User donorUser;
    private User receivingUser;
    private String descriptionDonatedItem;
    private Long quantityOfDonatedItem;

    public static DonationDTO convertToDonationDTO(Donation donation){
        return DonationDTO.builder().id(donation.getId())
                .donationDate(donation.getDonationDate())
                .donorUser(donation.getDonorUser())
                .receivingUser(donation.getReceivingUser())
                .descriptionDonatedItem(donation.getDescriptionDonatedItem())
                .quantityOfDonatedItem(donation.getQuantityOfDonatedItem()).build();
    }
}
