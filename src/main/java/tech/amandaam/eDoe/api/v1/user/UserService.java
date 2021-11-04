package tech.amandaam.eDoe.api.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amandaam.eDoe.api.v1.jwt.UserLoginDTO;
import tech.amandaam.eDoe.api.v1.user.exception.InvalidNumberOfCaractersException;
import tech.amandaam.eDoe.api.v1.user.exception.UserAlreadyExistsException;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO createNewUser(UserCreateDTO user) throws UserAlreadyExistsException, InvalidNumberOfCaractersException{
        if (checkIfUserExists(user.getEmail())){
            throw new UserAlreadyExistsException("O usuário " + user.getEmail() + " já existe.");
        } else if(checkIfTheCharacterNumberOfUserFields(user) == false) {
            throw new InvalidNumberOfCaractersException("O número de caracteres permitido nos campos são: name(60), email (40), password(30), e telephone(16).");
        }
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

    private boolean checkIfTheCharacterNumberOfUserFields(UserCreateDTO user) {
        if (user.getEmail().length() > 60 ){
            return false;
        } else if (user.getName().length() >  40){
            return false;
        } else if (user.getPassword().length() >  30){
            return false;
        } else if (user.getTelephone().length() >  16){
            return false;
        } else{
            return true;
        }
    }

    private boolean checkIfUserExists(String email) {
        if (userRepository.existsByEmail(email)){
            throw new UserAlreadyExistsException("O usuário " + email + " já existe.");
        }
        else {
            return false;
        }
    }

    public Optional<User> findUserByEmail(String email){
        Optional<User> searchedUser = userRepository.findByEmail(email);
        return searchedUser;
    }

    public boolean validateUser(UserLoginDTO userLogin){
        Optional<User> optionalUser = findUserByEmail(userLogin.getEmail());
        if (optionalUser.isPresent() &&
                optionalUser.get().getPassword().equals(userLogin.getPassword())){
            return true;
        } else {
        return false;
        }
    }

}
