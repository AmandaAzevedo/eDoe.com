package tech.amandaam.eDoe.api.v1.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amandaam.eDoe.api.v1.descriptor.Descriptor;
import tech.amandaam.eDoe.api.v1.descriptor.DescriptorService;
import tech.amandaam.eDoe.api.v1.descriptor.exception.DescriptorDoesNotExistException;
import tech.amandaam.eDoe.api.v1.item.exceptions.InvalidDescriptionException;
import tech.amandaam.eDoe.api.v1.item.exceptions.ItemNotFoundException;
import tech.amandaam.eDoe.api.v1.item.exceptions.MandatoryFieldNotFilledIn;
import tech.amandaam.eDoe.api.v1.jwt.JwtService;
import tech.amandaam.eDoe.api.v1.jwt.exception.PermissionDeniedException;
import tech.amandaam.eDoe.api.v1.user.User;
import tech.amandaam.eDoe.api.v1.user.UserRoleEnum;
import tech.amandaam.eDoe.api.v1.user.UserService;

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

    private boolean checkUserRole (String email, ItemTypeEnum itemType){
        Optional<User> user = this.userService.findUserByEmail(email);
        if (itemType.equals(ItemTypeEnum.DONATION)){
            if (user.get().getUserRole().equals(UserRoleEnum.DONATOR_RECLIVE) ||
                    user.get().getUserRole().equals(UserRoleEnum.ONLY_DONATOR) ||
                    user.get().getUserRole().equals(UserRoleEnum.ADMIN)) {
                return true;
            } else {
                return false;
            }
        } else if (itemType.equals(ItemTypeEnum.GRANTEE)){
            if (user.get().getUserRole().equals(UserRoleEnum.DONATOR_RECLIVE) ||
                    user.get().getUserRole().equals(UserRoleEnum.ONLY_RECLIVE) ||
                    user.get().getUserRole().equals(UserRoleEnum.ADMIN)){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public ItemDTO createItem(String token, ItemCreateDTO itemCreateDTO) throws PermissionDeniedException{
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email) || !checkUserRole(email, itemCreateDTO.getItemType())) {
            throw new PermissionDeniedException("Usuário nao tem permissão");
        }
        if (itemCreateDTO.getUserEmail() == null ||
            itemCreateDTO.getUserEmail().equals("") ||
            itemCreateDTO.getQuantity() == null ||
            itemCreateDTO.getQuantity() < 0 ||
            itemCreateDTO.getDescriptors() == null ||
            itemCreateDTO.getItemType() == null ){
                throw new MandatoryFieldNotFilledIn("Campo obrigatório não foi preenchido corretamente");
        }
        Item newItem = Item.builder()
                .user(userService.findUserByEmail(itemCreateDTO.getUserEmail()).get())
                .quantity(itemCreateDTO.getQuantity())
                .descriptionOrJustification(itemCreateDTO.getDescriptionOrJustification())
                .descriptors(convertToDescriptors(itemCreateDTO.getDescriptors(), token))
                .itemType(itemCreateDTO.getItemType()).build();
        newItem = this.itemRepository.save(newItem);
        return ItemDTO.converteToItemDTO(newItem);
    }

    public void deleteItem(Long id, String token) throws PermissionDeniedException, ItemNotFoundException {
        String email = jwtService.getSujeitoDoToken(token);
        if (!itemRepository.existsById(id)) {
            throw new ItemNotFoundException("Não foi encontrado nenhum item com o ID " + id.toString());
        }
        String itemOwner = itemRepository.findById(id).get().getUser().getEmail();
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
        if (!userService.loggedUserExists(token, email) || !email.equals(itemSearch.get().getUser().getEmail())) {
            throw new PermissionDeniedException("Usuário não tem permissão");
        }
        if (updateQuantityDTO.getNewQuantity() == null || updateQuantityDTO.getNewQuantity() < 0) {
            throw new InvalidDescriptionException("O número de itens não pode ser nulo e tem que ser igual ou maior que zero.");
        }
        Item item = itemSearch.get();
        item.setQuantity(updateQuantityDTO.getNewQuantity());
        return ItemDTO.converteToItemDTO(this.itemRepository.save(item));
    }

    public ItemDTO updateDetailedDescriptionOrMotivation(Long id, UpdateDescriptionOrJustificationDTO updateDetailedDescription, String token) throws ItemNotFoundException, PermissionDeniedException, InvalidDescriptionException {
        Optional<Item> itemSearch = this.itemRepository.findById(id);
        if (!itemSearch.isPresent()) {
            throw new ItemNotFoundException("O item de ID " + id + " não foi encontrado.");
        }
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email) || !email.equals(itemSearch.get().getUser().getEmail())) {
            throw new PermissionDeniedException("Usuário não tem permissão");
        }
        if (updateDetailedDescription.getNewDescriptionOrJustification() == null || updateDetailedDescription.getNewDescriptionOrJustification().length() < 3){
            throw new InvalidDescriptionException("A descrição detalhada do item não pode ser nula e deve possuir mais de 2 caracteres");
        }
        Item item = itemSearch.get();
        item.setDescriptionOrJustification(updateDetailedDescription.getNewDescriptionOrJustification());
        return ItemDTO.converteToItemDTO(this.itemRepository.save(item));
    }

    public List<SimpleItemDTO> getItemsByDescriptorName(DonationItemsByDescriptorNameDTO donationItemsByDescriptorName, ItemTypeEnum itemType){
        List<SimpleItemDTO> list = new LinkedList<>();
        String nameSearch = donationItemsByDescriptorName.getName();
        for (Item i: this.itemRepository.findAllByItemType(itemType)){
                for (Descriptor d: i.getDescriptors()){
                    if (d.getName().contains(nameSearch)) {
                        list.add(SimpleItemDTO.converteToSimpleItemDTO(i));
                    } //if end 2
                } // for end 2
        } //for end 1
        return list;
    }

    private List<Item> checkDescriptors(List<Item> items, String descriptorNameSearch){
        List<Item> list = new LinkedList<>();
        for (Item i: items){
            for (Descriptor d: i.getDescriptors()){
                if (descriptorNameSearch.equals(d.getName())) {
                    list.add(i);
                }
            }
        }
        return list;
    }

    private List<Item> checkDescriptors(List<Item> items, Long descriptorIdSearch){
        List<Item> list = new LinkedList<>();
        for (Item i: items){
            for (Descriptor d: i.getDescriptors()){
                if (descriptorIdSearch == d.getId()) {
                    list.add(i);
                }
            }
        }
        return list;
    }

    public List<SimpleItemDTO> getItemsPerDescriptor(Long id, ItemTypeEnum itemTypeEnum){
     Optional<Descriptor> optionalDescriptor = descriptorService.getById(id);
    if(!optionalDescriptor.isPresent()){
        throw new DescriptorDoesNotExistException("O descritor de ID " + id + " não existe.");
    }

    return SimpleItemDTO.convertToListSimpleItemDTO(checkDescriptors(itemRepository.findAllByItemType(itemTypeEnum),optionalDescriptor.get().getId()));
    }

    public List<SimpleItemDTO> listTop10(ItemTypeEnum itemType){
        return SimpleItemDTO.convertToListSimpleItemDTO(itemRepository.findTop10ByItemTypeOrderByQuantityDesc(itemType));
    }

}
