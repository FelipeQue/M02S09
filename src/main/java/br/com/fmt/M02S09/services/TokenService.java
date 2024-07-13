package br.com.fmt.M02S09.services;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    private final JwtDecoder jwtDecoder;

    public TokenService(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public void validaToken(String token, String perfil) {
        String tokenReal = token.split(" ")[1];

        List<String> perfisToken = List.of(jwtDecoder
                .decode(tokenReal)
                .getClaim("scope")
                .toString()
                .split(" "));

        if(!perfisToken.contains(perfil)){
            throw new RuntimeException("Essa pessoa usuária não tem acesso a " + perfil);
        }

    }




}
