package br.com.fmt.M02S09.services;

import br.com.fmt.M02S09.controllers.dto.LoginRequestDTO;
import br.com.fmt.M02S09.entities.Role;
import br.com.fmt.M02S09.entities.Usuario;
import br.com.fmt.M02S09.repositories.NutricionistaRepository;
import br.com.fmt.M02S09.repositories.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final RoleService roleService;
    private final NutricionistaRepository nutricionistaRepository;
    private final UsuarioDetailService usuarioDetailService;

    public UsuarioService(BCryptPasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, RoleService roleService, NutricionistaRepository nutricionistaRepository, UsuarioDetailService usuarioDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.roleService = roleService;
        this.nutricionistaRepository = nutricionistaRepository;
        this.usuarioDetailService = usuarioDetailService;
    }

    public Usuario validaUsuario(LoginRequestDTO request) {
    Usuario usuario = usuarioRepository
            .findByUsername(request.username())
            .orElseThrow(
                    () -> new RuntimeException("Não existe pessoa usuária com o nome " + request.username())
            );

        if (!passwordEncoder.matches(request.password(), usuario.getPassword())){
        throw new RuntimeException("Senha incorreta para o nome " + request.username());
    }
        return usuario;
    }


    public void cadastraUsuario(LoginRequestDTO cadastroRequest) {
        if (usuarioRepository.findByUsername(cadastroRequest.username()).isPresent()){
            throw new RuntimeException("Já existe cadastro com o nome de pessoa usuária " + cadastroRequest.username());
        }

        Role role = roleService.validaPerfil(cadastroRequest.nomePerfil());

        Usuario usuario = new Usuario();
        usuario.setUsername(cadastroRequest.username());
        usuario.setPassword(passwordEncoder.encode(cadastroRequest.password()));
        usuario.setRoleList(Set.of(role));
        usuario.setNutricionista(nutricionistaRepository.findById(cadastroRequest.idNutricionista()).orElse(null));

        usuarioRepository.save(usuario);
    }
}
