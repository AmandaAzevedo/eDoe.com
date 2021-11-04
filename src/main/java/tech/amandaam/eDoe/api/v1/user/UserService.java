package tech.amandaam.eDoe.api.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amandaam.eDoe.api.v1.user.exception.UserAlreadyExistsException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO createNewUser(UserCreateDTO user) throws UserAlreadyExistsException{
        if (checkIfUserExists(user.getEmail())) {
            throw new UserAlreadyExistsException("O usuário " + user.getEmail() + " já existe.");
        } else{
            User newUser = User.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .telephone(user.getTelephone())
                    .userCategory(user.getUserCategory()).build();
            newUser.setUserRole(UserRoleEnum.valueOf("ONLY_DONATOR"));
            newUser = this.userRepository.save(newUser);
            return UserDTO.convertToUserDTO(newUser);
        }
    }

    private boolean checkIfUserExists(String email){
        if (userRepository.existsByEmail(email)){
           return true;
        }
        else {
            return false;
        }
    }
}
