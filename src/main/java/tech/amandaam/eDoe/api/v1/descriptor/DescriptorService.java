package tech.amandaam.eDoe.api.v1.descriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amandaam.eDoe.api.util.StringUtil;
import tech.amandaam.eDoe.api.v1.descriptor.exception.DescriptorAlreadyExistsException;
import tech.amandaam.eDoe.api.v1.descriptor.exception.DescriptorOptionDoesNotExist;
import tech.amandaam.eDoe.api.v1.jwt.JwtService;
import tech.amandaam.eDoe.api.v1.jwt.exception.PermissionDeniedException;
import tech.amandaam.eDoe.api.v1.user.User;
import tech.amandaam.eDoe.api.v1.user.UserRoleEnum;
import tech.amandaam.eDoe.api.v1.user.UserService;

import java.util.List;
import java.util.Optional;


@Service
public class DescriptorService {
    @Autowired
    private DescriptorRepository descriptorRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    public boolean descriptorAlreadyExists (String descriptorName) {
        if (descriptorRepository.existsByName(descriptorName)){
            return true;
        }
        else {
            return false;
        }
    }

    public Optional<Descriptor> getDescriptorByName(String name){
        return descriptorRepository.findByName(name);
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
    public DescriptorDTO createDescriptor(DescriptorCreateDTO descriptorCreateDTO, String token) throws PermissionDeniedException, DescriptorAlreadyExistsException {
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email) && !checkUserRole(email)) {
            throw new PermissionDeniedException("Usu??rio nao tem permiss??o");
        }
        Descriptor newDescriptor = Descriptor.builder()
                .name(descriptorCreateDTO.getName()).build();
        newDescriptor.setName(StringUtil.normalize(newDescriptor.getName()));
        if (descriptorAlreadyExists(newDescriptor.getName())){
            throw new DescriptorAlreadyExistsException("O descritor " + newDescriptor.getName() + " j?? existe.");
        }
        descriptorRepository.save(newDescriptor);
        return DescriptorDTO.convertToDescriptorDTO(newDescriptor);
    }

    public Descriptor createDescriptor(String descriptorName, String token) throws PermissionDeniedException, DescriptorAlreadyExistsException {
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email) && !checkUserRole(email)) {
            throw new PermissionDeniedException("Usu??rio nao tem permiss??o");
        }
        Descriptor newDescriptor = Descriptor.builder()
                .name(descriptorName).build();
        newDescriptor.setName(StringUtil.normalize(newDescriptor.getName()));
        return descriptorRepository.save(newDescriptor);
    }

    public List<DescriptorDTO> getAll(String token, OptionsToGetTheDescriptorDTO optionsToGetTheDescriptorDTO) throws PermissionDeniedException, DescriptorOptionDoesNotExist {
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email)) {
            throw new PermissionDeniedException("Usu??rio nao tem permiss??o");
        }
        if (optionsToGetTheDescriptorDTO.getOptionsToGetTheDescriptorEnum().equals(OptionsToGetTheDescriptorEnum.DESC)){
            return DescriptorDTO.convertToListDescriptorDTO(descriptorRepository.findAllByOrderByNameDesc());
        } else if (optionsToGetTheDescriptorDTO.getOptionsToGetTheDescriptorEnum().equals(OptionsToGetTheDescriptorEnum.ASC)){
            return DescriptorDTO.convertToListDescriptorDTO(descriptorRepository.findAllByOrderByNameAsc());
        } else {
            throw new DescriptorOptionDoesNotExist("A op????o " + optionsToGetTheDescriptorDTO.getOptionsToGetTheDescriptorEnum() + " n??o ?? v??lida. Escolha ASC para ordem alfab??tica ascendente, ou DESC para ordem alfab??tica descendente.");
        }
    }

    public Optional<Descriptor> getById(Long id){
        return descriptorRepository.findById(id);
    }

}
