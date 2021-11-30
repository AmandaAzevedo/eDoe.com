package tech.amandaam.eDoe.api.v1.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/request")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @PostMapping("/change-role")
    @ResponseStatus(code = HttpStatus.CREATED)
    public RequestDTO requestRoleChange(@RequestBody RequestCreateDTO requestCreateDTO, @RequestHeader("Authorization") String header) throws ServletException {
        return requestService.requestRoleChange(requestCreateDTO, header);
    }

    @GetMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public List<RequestDTO> getAllRequest(@RequestHeader("Authorization") String header) throws ServletException{
        return requestService.getAllRequests(header);
    }

    @GetMapping("/{email}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<RequestDTO> getRequestByUserEmail(@PathVariable("email") String email , @RequestHeader("Authorization") String header) throws ServletException{
        return requestService.getRequestByUserEmail(header, email);
    }

    @PatchMapping("/evaluate")
    @ResponseStatus(code = HttpStatus.OK)
    public RequestDTO responseRequest(@RequestBody ResponseRequestDTO responseRequestDTO, @RequestHeader("Authorization") String header) throws ServletException{
        return requestService.responseRequest(header, responseRequestDTO);
    }
}
