package br.com.fmt.M02S09.services;

import br.com.fmt.M02S09.infra.UsuarioDetails;
import br.com.fmt.M02S09.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioDetails usuario = usuarioRepository.findByUsername(username)
                .map(usuarioEntity -> new UsuarioDetails(usuarioEntity))
                .orElseThrow(() -> new UsernameNotFoundException("Nome de pessoa usuária não encontrado: " + username));
        return usuario;
    }
}
