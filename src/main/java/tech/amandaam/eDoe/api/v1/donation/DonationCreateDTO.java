package tech.amandaam.eDoe.api.v1.donation;

import lombok.Getter;

@Getter
public class DonationCreateDTO {
    private Long requiredItemId;
    private Long donatedItemId;
    private Long amountToBeDonated;
}
