package com.proyecto.daw.persistence.interfaces;

import com.proyecto.daw.dto.SaveUsuario;
import com.proyecto.daw.dto.UsuarioDTO;
import com.proyecto.daw.persistence.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUsuario {

    Page<Usuario> listarUsuarios(Pageable pageable);

    Optional<UsuarioDTO> buscarUsuario(Long id);

    Usuario guardarUsuario(SaveUsuario saveUsuario);

    Usuario actualizarUsuario(SaveUsuario saveUsuario,Long id);

    void eliminarUsuario(Long id);

}
