package tech.amandaam.eDoe.api.v1.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amandaam.eDoe.api.v1.donation.exception.NumberOfItemsRequiredInvalidException;
import tech.amandaam.eDoe.api.v1.item.Item;
import tech.amandaam.eDoe.api.v1.item.ItemRepository;
import tech.amandaam.eDoe.api.v1.item.exceptions.ItemNotFoundException;
import tech.amandaam.eDoe.api.v1.jwt.JwtService;
import tech.amandaam.eDoe.api.v1.jwt.exception.PermissionDeniedException;
import tech.amandaam.eDoe.api.v1.user.User;
import tech.amandaam.eDoe.api.v1.user.UserRoleEnum;
import tech.amandaam.eDoe.api.v1.user.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemRepository itemRepository;


    private boolean checkUserRole (String email){
        Optional<User> user = this.userService.findUserByEmail(email);
        if (user.get().getUserRole().equals(UserRoleEnum.DONATOR_RECLIVE) ||
                user.get().getUserRole().equals(UserRoleEnum.ONLY_RECLIVE) ||
                user.get().getUserRole().equals(UserRoleEnum.ADMIN)){
            return true;
        } else {
            return false;
        }
    }

    public DonationDTO donation(DonationCreateDTO donationCreateDTO, String token){
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email) || !checkUserRole(email)) {
            throw new PermissionDeniedException("Usuário nao tem permissão");
        }
        Item requiredItem = itemRepository.findById(donationCreateDTO.getRequiredItemId()).orElseThrow(()->
                new ItemNotFoundException("O item de ID "+ donationCreateDTO.getRequiredItemId() + " não foi encontrado ou não existe."));
        Item donatedItem = itemRepository.findById(donationCreateDTO.getDonatedItemId()).orElseThrow(()->
                new ItemNotFoundException("O item de ID "+ donationCreateDTO.getDonatedItemId() + " não foi encontrado ou não existe."));

        if (donationCreateDTO.getAmountToBeDonated() <= 0){
            throw new NumberOfItemsRequiredInvalidException("O número de itens necessários deve ser maior do que zero.");
        }

        Date data = new Date();

        Long dtoQuantity = donationCreateDTO.getAmountToBeDonated();
        Long donate = donatedItem.getQuantity();
        Long required = requiredItem.getQuantity();

        Long resultD;
        Long resultR;
        Long quantity;

        if (dtoQuantity.equals(donate)){
            itemRepository.delete(donatedItem);
            resultD = 0L;
            quantity = donationCreateDTO.getAmountToBeDonated();
        } else if (dtoQuantity > donate){
            itemRepository.delete(donatedItem);
            resultD = Long.valueOf(dtoQuantity-donate);
            requiredItem.setQuantity(resultD);
            quantity = Long.valueOf(resultD);
        } else {
            resultD = Long.valueOf(donate - dtoQuantity);
            donatedItem.setQuantity(resultD);
            itemRepository.save(donatedItem);
            quantity = Long.valueOf(resultD);
        }

        if (dtoQuantity.equals(required)) {
            itemRepository.delete(requiredItem);
        } else if (dtoQuantity > required){
            itemRepository.delete(requiredItem);
        } else {
            resultR = Long.valueOf(required - dtoQuantity);
            requiredItem.setQuantity(resultR);
            itemRepository.save(requiredItem);
        }

        Donation newDonation = Donation.builder()
                .donationDate(data)
                .donorUser(donatedItem.getUser())
                .receivingUser(requiredItem.getUser())
                .descriptionDonatedItem(donatedItem.getDescriptionOrJustification())
                .quantityOfDonatedItem(quantity).build();
        newDonation = this.donationRepository.save(newDonation);
        return DonationDTO.convertToDonationDTO(newDonation);
    }


    public List<SimpleDonationDTO> getAll(){
        return SimpleDonationDTO.convertToListDonationDTO(this.donationRepository.findAll());
    }
}
