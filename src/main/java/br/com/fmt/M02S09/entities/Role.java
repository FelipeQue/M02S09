package br.com.fmt.M02S09.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table()
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long id;
    private String nomePerfil;

    public Role() {
    }

    public Role(Long id, String nomePerfil) {
        this.id = id;
        this.nomePerfil = nomePerfil;
    }

    @Override
    public String getAuthority() {
        return this.nomePerfil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }
}


