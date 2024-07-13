package br.com.fmt.M02S09.repositories;

import br.com.fmt.M02S09.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNomePerfil(String nomePerfil);

}