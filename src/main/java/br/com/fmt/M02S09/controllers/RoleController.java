package br.com.fmt.M02S09.controllers;

import br.com.fmt.M02S09.controllers.dto.RoleRequestDTO;
import br.com.fmt.M02S09.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/perfil")
    public ResponseEntity<String> cadastraPerfil(@RequestBody RoleRequestDTO request){

        roleService.cadastraPerfil(request);

        return new ResponseEntity<>("Perfil criado.", HttpStatus.CREATED);

    }


}
