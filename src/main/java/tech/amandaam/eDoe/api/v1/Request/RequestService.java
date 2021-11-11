package tech.amandaam.eDoe.api.v1.Request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amandaam.eDoe.api.v1.Request.exception.RequestNotExistException;
import tech.amandaam.eDoe.api.v1.jwt.JwtService;
import tech.amandaam.eDoe.api.v1.jwt.exception.PermissionDeniedException;
import tech.amandaam.eDoe.api.v1.jwt.exception.UserNotExistException;
import tech.amandaam.eDoe.api.v1.user.User;
import tech.amandaam.eDoe.api.v1.user.UserRepository;
import tech.amandaam.eDoe.api.v1.user.UserRoleEnum;
import tech.amandaam.eDoe.api.v1.user.UserService;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private String returnedLoggedUserEmail(String token) throws UserNotExistException {
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.loggedUserExists(token,email)) {
            throw new UserNotExistException("Usuário não existe.");
        }
        return email;
    }

    public RequestDTO requestRoleChange(RequestCreateDTO requestCreateDTO, String token) throws PermissionDeniedException,UserNotExistException {
        String email = returnedLoggedUserEmail(token);
        if (!email.equals(requestCreateDTO.getRequestingUserEmail())){
            throw new PermissionDeniedException("Permissão negada, o usuário do token deve ser o mesmo da requisição.");
        }
        User loggedUser = this.userService.findUserByEmail(email).orElseThrow(()->new UserNotExistException("Usuário não existe."));
        Request request = Request.builder().requestingUser(loggedUser).requestedRole(requestCreateDTO.getRequestedRole()).evaluationResult(false).build();
        return RequestDTO.convertToRequestDTO(requestRepository.save(request));
    }

    public List<RequestDTO> getAllRequests(String token) throws PermissionDeniedException, UserNotExistException {
        String email = returnedLoggedUserEmail(token);
        User loggedUser = this.userService.findUserByEmail(email).orElseThrow(()->new UserNotExistException("Usuário não existe."));
        if (!loggedUser.getUserRole().equals(UserRoleEnum.ADMIN)){
            throw new PermissionDeniedException("Usuário não possui permissão.");
        }
        return RequestDTO.convertToListRequestDTO(requestRepository.findAll());
    }

    public List<RequestDTO> getRequestByUserEmail(String token, String emailSearch) throws UserNotExistException, PermissionDeniedException {
        String email = returnedLoggedUserEmail(token);
        User loggedUser = this.userService.findUserByEmail(email).orElseThrow(()->new UserNotExistException("Usuário não existe."));
        if (!(loggedUser.getUserRole().equals(UserRoleEnum.ADMIN) || email.equals(emailSearch))){
            throw new PermissionDeniedException("Usuário não possui permissão.");
        }
        return RequestDTO.convertToListRequestDTO(requestRepository.findAllByRequestingUserEmail(emailSearch));
    }

    public RequestDTO responseRequest(String token, ResponseRequestDTO responseRequestDTO) throws PermissionDeniedException, UserNotExistException{
        String email = returnedLoggedUserEmail(token);
        User loggedUser = this.userService.findUserByEmail(email).orElseThrow(()->new UserNotExistException("Usuário não existe."));
        if (!loggedUser.getUserRole().equals(UserRoleEnum.ADMIN)){
            throw new PermissionDeniedException("Usuário não possui permissão.");
        }
        Request request = requestRepository.findById(responseRequestDTO.getId()).orElseThrow(()->new RequestNotExistException("Requisição não existe."));
        if (responseRequestDTO.isAccept() == false){
            request.setEvaluationResult(false);
        } else {
            UserRoleEnum requestedRole= request.getRequestedRole();
            request.getRequestingUser().setUserRole(requestedRole);
            request.setEvaluationResult(true);
            userRepository.save(request.getRequestingUser());
        }
        requestRepository.save(request);
        return RequestDTO.convertToRequestDTO(request);
    }
}
