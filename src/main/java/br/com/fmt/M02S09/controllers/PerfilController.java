package br.com.fmt.M02S09.controllers;

import br.com.fmt.M02S09.controllers.dto.LoginRequestDTO;
import br.com.fmt.M02S09.controllers.dto.PerfilRequestDTO;
import br.com.fmt.M02S09.services.PerfilService;
import br.com.fmt.M02S09.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PerfilService perfilService;

    public UsuarioController(UsuarioService usuarioService, PerfilService perfilService) {
        this.usuarioService = usuarioService;
        this.perfilService = perfilService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastraUsuario(@RequestBody LoginRequestDTO cadastroRequest){

        usuarioService.cadastraUsuario(cadastroRequest);

        return new ResponseEntity<>("Cadastro de pessoa usuária criado.", HttpStatus.CREATED);

    }

    @PostMapping("/perfil")
    public ResponseEntity<String> cadastraPerfil(@RequestBody PerfilRequestDTO request){

        perfilService.cadastraPerfil(request);

        return new ResponseEntity<>("Perfil criado.", HttpStatus.CREATED);

    }


}
