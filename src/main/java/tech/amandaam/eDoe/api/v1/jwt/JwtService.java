package tech.amandaam.eDoe.api.v1.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.amandaam.eDoe.api.v1.jwt.exception.LoginOrPasswordInvalideException;
import tech.amandaam.eDoe.api.v1.user.UserService;

import java.util.Date;

@Service
public class JwtService {

    @Autowired
    private UserService userService;

    @Value("${app.token-key}")
    private String TOKEN_KEY;

    public LoginResponseDTO authentication(UserLoginDTO user) throws LoginOrPasswordInvalideException {
        if (!userService.validateUser(user)) {
            throw new LoginOrPasswordInvalideException("Email e/ou senha inválido. Tente novamente.");
        }
        String token = generateToken(user.getEmail());
        return new LoginResponseDTO(token);
    }

    /**
     * Este método gera um token que possui validade de 30 dias.
     * @author AmandaAzevedo
     * @param email
     * @return token <String>
     */
    private String generateToken(String email) {
        return Jwts.builder().setHeaderParam("typ", "JWT").setSubject(email)
                .signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + 60000 * 60 * 24 * 30)).compact();
    }

    private boolean checkIfTheTokenIsBadlyFormattedOrNotExist(String authorizationHeader){
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new SecurityException("Token inexistente ou mal formatado!");
        }
        return false;
    }

    public String getSujeitoDoToken(String authorizationHeader) {
        checkIfTheTokenIsBadlyFormattedOrNotExist(authorizationHeader);
        // Extraindo apenas o token do cabecalho.
        String token = authorizationHeader.substring(JwtFilter.TOKEN_INDEX);
        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
        } catch (SignatureException e) {
            throw new SecurityException("Token invalido ou expirado!");
        }
        return subject;
    }

}
