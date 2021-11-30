package tech.amandaam.eDoe.api.v1.donation;

import lombok.*;
import tech.amandaam.eDoe.api.v1.item.Item;
import tech.amandaam.eDoe.api.v1.user.User;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "donation")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "donation_generator")
    @SequenceGenerator(name="donation_generator",sequenceName = "donation_sequence", allocationSize = 1)
    private Long id;
    private Date donationDate;
    @ManyToOne
    private User donorUser;
    @ManyToOne
    private User receivingUser;
    private String descriptionDonatedItem;
    private Long quantityOfDonatedItem;
}
