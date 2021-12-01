package tech.amandaam.eDoe.api.v1.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;


    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> authentication(@RequestBody UserLoginDTO user) throws ServletException {
        return new ResponseEntity<LoginResponseDTO>(jwtService.authentication(user), HttpStatus.OK);
    }

}