package tech.amandaam.eDoe.api.v1.request;

import lombok.*;
import tech.amandaam.eDoe.api.v1.user.User;
import tech.amandaam.eDoe.api.v1.user.UserRoleEnum;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class RequestDTO implements Serializable {

    private Long id;
    private User requestingUser;
    private UserRoleEnum requestedRole;
    private boolean evaluationResult;

    public static RequestDTO convertToRequestDTO(Request request){
        return RequestDTO.builder().id(request.getId())
                .requestingUser(request.getRequestingUser())
                .requestedRole(request.getRequestedRole())
                .evaluationResult(request.isEvaluationResult()).build();
    }

    public static List<RequestDTO> convertToListRequestDTO(List<Request> requests){
        List<RequestDTO> list = new LinkedList<RequestDTO>();
        for (Request r: requests){
            list.add(convertToRequestDTO(r));
        }
        return list;
    }

}
