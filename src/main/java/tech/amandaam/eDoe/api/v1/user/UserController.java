package tech.amandaam.eDoe.api.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDTO createNewUser(@RequestBody UserCreateDTO userCreateDTO){
        return userService.createNewUser(userCreateDTO);
    }

}
