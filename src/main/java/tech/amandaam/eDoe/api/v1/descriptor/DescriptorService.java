package tech.amandaam.eDoe.api.v1.descriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amandaam.eDoe.api.util.StringUtil;
import tech.amandaam.eDoe.api.v1.descriptor.exception.DescriptorAlreadyExistsException;
import tech.amandaam.eDoe.api.v1.descriptor.exception.DescriptorOptionDoesNotExist;
import tech.amandaam.eDoe.api.v1.jwt.JwtService;
import tech.amandaam.eDoe.api.v1.jwt.exception.PermissionDeniedException;
import tech.amandaam.eDoe.api.v1.user.UserService;

import java.util.List;


@Service
public class DescriptorService {
    @Autowired
    private DescriptorRepository descriptorRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    private String normalizesDescriptorName(String descriptorName){
        descriptorName = StringUtil.removeAccents(descriptorName);
        descriptorName = StringUtil.removeSpaces(descriptorName);
        descriptorName = StringUtil.removeSpaces(descriptorName);
        return descriptorName;
    }

    private boolean descriptorAlreadyExists (String descriptorName) {
        if (descriptorRepository.existsByName(descriptorName)){
            return true;
        }
        else {
            return false;
        }
    }

    public DescriptorDTO createDescriptor(DescriptorCreateDTO descriptorCreateDTO, String token) throws PermissionDeniedException, DescriptorAlreadyExistsException {
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email)) {
            throw new PermissionDeniedException("Usuário nao tem permissão");
        }
        Descriptor newDescriptor = Descriptor.builder()
                .name(descriptorCreateDTO.getName()).build();
        newDescriptor.setName(this.normalizesDescriptorName(newDescriptor.getName()));
        if (descriptorAlreadyExists(newDescriptor.getName())){
            throw new DescriptorAlreadyExistsException("O descritor " + newDescriptor.getName() + " já existe.");
        }
        descriptorRepository.save(newDescriptor);
        return DescriptorDTO.convertToDescriptorDTO(newDescriptor);
    }

    public List<DescriptorDTO> getAll(String token, OptionsToGetTheDescriptorDTO optionsToGetTheDescriptorDTO) throws PermissionDeniedException, DescriptorOptionDoesNotExist {
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token, email)) {
            throw new PermissionDeniedException("Usuário nao tem permissão");
        }
        if (optionsToGetTheDescriptorDTO.getOptionsToGetTheDescriptorEnum().equals(OptionsToGetTheDescriptorEnum.DESC)){
            return DescriptorDTO.convertToListDescriptorDTO(descriptorRepository.findAllByOrderByNameDesc());
        } else if (optionsToGetTheDescriptorDTO.getOptionsToGetTheDescriptorEnum().equals(OptionsToGetTheDescriptorEnum.ASC)){
            return DescriptorDTO.convertToListDescriptorDTO(descriptorRepository.findAllByOrderByNameAsc());
        } else {
            throw new DescriptorOptionDoesNotExist("A opção " + optionsToGetTheDescriptorDTO.getOptionsToGetTheDescriptorEnum() + " não é válida. Escolha ASC para ordem alfabética ascendente, ou DESC para ordem alfabética descendente.");
        }
    }
}
