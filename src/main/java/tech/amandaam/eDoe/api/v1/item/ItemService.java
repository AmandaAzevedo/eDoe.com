package tech.amandaam.eDoe.api.v1.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amandaam.eDoe.api.v1.descriptor.Descriptor;
import tech.amandaam.eDoe.api.v1.descriptor.DescriptorService;
import tech.amandaam.eDoe.api.v1.item.exceptions.InvalidDescriptionException;
import tech.amandaam.eDoe.api.v1.item.exceptions.ItemNotFoundException;
import tech.amandaam.eDoe.api.v1.item.exceptions.MandatoryFieldNotFilledIn;
import tech.amandaam.eDoe.api.v1.jwt.JwtService;
import tech.amandaam.eDoe.api.v1.jwt.exception.PermissionDeniedException;
import tech.amandaam.eDoe.api.v1.user.User;
import tech.amandaam.eDoe.api.v1.user.UserRoleEnum;
import tech.amandaam.eDoe.api.v1.user.UserService;

import javax.servlet.ServletException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private DescriptorService descriptorService;


    private List<Descriptor> convertToDescriptors(List<String> descriptorsName, String token){
        List<Descriptor> descriptors = new LinkedList<Descriptor>();
        for (String name: descriptorsName){
            name = descriptorService.normalizesDescriptorName(name);
            if (descriptorService.descriptorAlreadyExists(name)){
                descriptors.add(descriptorService.getDescriptorByName(name).get());
            } else{
                descriptors.add(descriptorService.createDescriptor(name, token));
            }
        }
        return descriptors;
    }

    private boolean checkUserRole (String email){
        Optional<User> user = this.userService.findUserByEmail(email);
        if (user.get().getUserRole().equals(UserRoleEnum.DONATOR_RECLIVE) ||
                user.get().getUserRole().equals(UserRoleEnum.ONLY_DONATOR) ||
                user.get().getUserRole().equals(UserRoleEnum.ADMIN)) {
            return true;
        } else {
            return false;
        }
    }

    public ItemDTO createItem(String token, ItemCreateDTO itemCreateDTO) throws PermissionDeniedException{
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email) || !checkUserRole(email)) {
            throw new PermissionDeniedException("Usuário nao tem permissão");
        }
        if (itemCreateDTO.getOwnerEmail() == null ||
            itemCreateDTO.getOwnerEmail().equals("") ||
            itemCreateDTO.getQuantity() == null ||
            itemCreateDTO.getQuantity() < 0 ||
            itemCreateDTO.getDescriptors() == null){
                throw new MandatoryFieldNotFilledIn("Campo obrigatório não foi preenchido corretamente");
        }
        Item newItem = Item.builder()
                .owner(userService.findUserByEmail(itemCreateDTO.getOwnerEmail()).get())
                .quantity(itemCreateDTO.getQuantity())
                .detailedDescription(itemCreateDTO.getDetailedDescription())
                .descriptors(convertToDescriptors(itemCreateDTO.getDescriptors(), token)).build();
        newItem = this.itemRepository.save(newItem);
        return ItemDTO.converteToItemDTO(newItem);
    }

    public void deleteItem(Long id, String token) throws PermissionDeniedException, ItemNotFoundException {
        String email = jwtService.getSujeitoDoToken(token);
        if (!itemRepository.existsById(id)) {
            throw new ItemNotFoundException("Não foi encontrado nenhum item com o ID " + id.toString());
        }
        String itemOwner = itemRepository.findById(id).get().getOwner().getEmail();
        if (!userService.loggedUserExists(token, email) && !email.equals(itemOwner)) {
            throw new PermissionDeniedException("Usuário não tem permissão");
        }
        itemRepository.deleteById(id);
    }


    public ItemDTO updateQuantity(Long id, UpdateQuantityDTO updateQuantityDTO, String token) throws ItemNotFoundException, PermissionDeniedException, InvalidDescriptionException {
        Optional<Item> itemSearch = this.itemRepository.findById(id);
        if (!itemSearch.isPresent()) {
            throw new ItemNotFoundException("O item de ID " + id + " não foi encontrado.");
        }
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email) || !email.equals(itemSearch.get().getOwner().getEmail())) {
            throw new PermissionDeniedException("Usuário não tem permissão");
        }
        if (updateQuantityDTO.getNewQuantity() == null || updateQuantityDTO.getNewQuantity() < 0) {
            throw new InvalidDescriptionException("O número de itens não pode ser nulo e tem que ser igual ou maior que zero.");
        }
        Item item = itemSearch.get();
        item.setQuantity(updateQuantityDTO.getNewQuantity());
        return ItemDTO.converteToItemDTO(this.itemRepository.save(item));
    }

    public ItemDTO updateDetailedDescription(Long id, UpdateDetailedDescriptionDTO updateDetailedDescription, String token) throws ItemNotFoundException, PermissionDeniedException, InvalidDescriptionException {
        Optional<Item> itemSearch = this.itemRepository.findById(id);
        if (!itemSearch.isPresent()) {
            throw new ItemNotFoundException("O item de ID " + id + " não foi encontrado.");
        }
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email) || !email.equals(itemSearch.get().getOwner().getEmail())) {
            throw new PermissionDeniedException("Usuário não tem permissão");
        }
        if (updateDetailedDescription.getNewDescription() == null || updateDetailedDescription.getNewDescription().length() < 3){
            throw new InvalidDescriptionException("A descrição detalhada do item não pode ser nula e deve possuir mais de 2 caracteres");
        }
        Item item = itemSearch.get();
        item.setDetailedDescription(updateDetailedDescription.getNewDescription());
        return ItemDTO.converteToItemDTO(this.itemRepository.save(item));
    }


}
