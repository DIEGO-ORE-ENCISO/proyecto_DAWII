package com.proyecto.daw.persistence.repository;

import com.proyecto.daw.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    // Verifica si existe un usuario con el nombre de usuario especificado
    boolean existsByUsername(String username);

    // Verifica si existe un usuario con el correo electrónico especificado
    boolean existsByEmail(String email);
}
