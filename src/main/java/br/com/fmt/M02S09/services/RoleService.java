package br.com.fmt.M02S09.services;

import br.com.fmt.M02S09.controllers.dto.RoleRequestDTO;
import br.com.fmt.M02S09.entities.Role;
import br.com.fmt.M02S09.repositories.PerfilRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final PerfilRepository perfilRepository;

    public RoleService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public void cadastraPerfil(RoleRequestDTO request) {
        if (perfilRepository.findByNomePerfil(request.nomePerfil()).isPresent()){
            throw new RuntimeException("Já existe perfil com o nome " + request.nomePerfil());
        }

        Role role = new Role();
        role.setNomePerfil(request.nomePerfil());
        perfilRepository.save(role);
    }

    public Role validaPerfil(String nomePerfil){
        Role role = perfilRepository.findByNomePerfil(nomePerfil)
                .orElseThrow(
                        () -> new RuntimeException("Não existe perfil com o nome: " + nomePerfil)
                );
        return role;
    }
}
