package lt.codeacademy.restbagiamasis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lt.codeacademy.restbagiamasis.dto.UserDTO;
import lt.codeacademy.restbagiamasis.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private byte[] secret;

    @Value("${security.jwt.validity-min}")
    private long validityMin;

    private final ObjectMapper objectMapper;

    public JwtService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String createToken(User user) throws ParseException {
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("form-api")
                .setAudience("form-ui")
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(now).getTime() + validityMin * 60000))
                .claim("user", new UserDTO(user))
                .signWith(Keys.hmacShaKeyFor(secret), SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication parseToken (String jwt) throws JsonProcessingException {
        Claims parsedJwtBody;

        try{
            parsedJwtBody = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return null;
        }

        UserDTO userDTO = objectMapper.readValue(objectMapper.writeValueAsString(parsedJwtBody.get("user")), UserDTO.class);
        User user = new User(userDTO);

        return new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
    }
}
