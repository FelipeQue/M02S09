package br.com.fmt.M02S09.controllers;

import br.com.fmt.M02S09.controllers.dto.LoginRequestDTO;
import br.com.fmt.M02S09.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastraUsuario(@RequestBody LoginRequestDTO cadastroRequest){

        usuarioService.cadastraUsuario(cadastroRequest);

        return new ResponseEntity<>("Cadastro de pessoa usuária criado.", HttpStatus.CREATED);

    }

}
