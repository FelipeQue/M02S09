package br.com.fmt.M02S09.controllers;

import br.com.fmt.M02S09.controllers.dto.LoginRequestDTO;
import br.com.fmt.M02S09.controllers.dto.LoginResponseDTO;
import br.com.fmt.M02S09.entities.Usuario;
import br.com.fmt.M02S09.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UsuarioService usuarioService;

    private static long TEMPO_EXPIRACAO = 36000L;

    public TokenController(JwtEncoder jwtEncoder, UsuarioService usuarioService) {
        this.jwtEncoder = jwtEncoder;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> geraToken(@RequestBody LoginRequestDTO request){

        Usuario usuario = usuarioService.validaUsuario(request);

        Instant agora = Instant.now();

        String scope = "ADMIN";

//        String scope = usuario.getAuthorities()
//                .stream()
//                .map(authority -> authority.getAuthority())
//                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(TEMPO_EXPIRACAO))
                .subject(usuario.getUsername())
                .claim("scope", scope)
                .build();

        var valorJwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(valorJwt, TEMPO_EXPIRACAO));

    }

}
