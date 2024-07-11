package br.com.fmt.M02S09.repositories;

import br.com.fmt.M02S09.entities.Nutricionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutricionistaRepository extends JpaRepository<Nutricionista,Long> {

    Optional<Nutricionista> findByNome(String nome);

}
