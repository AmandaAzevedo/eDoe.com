package tech.amandaam.eDoe.api.v1.matches;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amandaam.eDoe.api.util.StringUtil;
import tech.amandaam.eDoe.api.v1.descriptor.Descriptor;
import tech.amandaam.eDoe.api.v1.descriptor.DescriptorRepository;
import tech.amandaam.eDoe.api.v1.item.Item;
import tech.amandaam.eDoe.api.v1.item.ItemDTO;
import tech.amandaam.eDoe.api.v1.item.ItemRepository;
import tech.amandaam.eDoe.api.v1.item.ItemTypeEnum;
import tech.amandaam.eDoe.api.v1.item.exceptions.ItemNotFoundException;
import tech.amandaam.eDoe.api.v1.jwt.JwtService;
import tech.amandaam.eDoe.api.v1.jwt.exception.PermissionDeniedException;
import tech.amandaam.eDoe.api.v1.user.User;
import tech.amandaam.eDoe.api.v1.user.UserRoleEnum;
import tech.amandaam.eDoe.api.v1.user.UserService;

import java.util.LinkedList;
import java.util.List;

@Service
public class MatchesService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private DescriptorRepository descriptorRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    private boolean checkUserRole(String email){
        User user = userService.findUserByEmail(email).get();
        if (user.getUserRole().equals(UserRoleEnum.DONATOR_RECLIVE)||
                user.getUserRole().equals(UserRoleEnum.ONLY_RECLIVE) ||
                user.getUserRole().equals(UserRoleEnum.ADMIN)){
            return true;
        }
        return false;
    }
    public List<ItemDTO> matches(MatchesDTO matchesDTO, String token){
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email) || !checkUserRole(email)) {
            throw new PermissionDeniedException("Usuário nao tem permissão");
        }
        Item item = itemRepository.findById(matchesDTO.getItemId()).orElseThrow(() -> new ItemNotFoundException("Item não foi encontrado ou não existe"));
        return ItemDTO.convertToListItemDTO(this.itemRepository.findAllDistinctByDescriptorsInAndItemType(item.getDescriptors(), ItemTypeEnum.DONATION));
    }

}
