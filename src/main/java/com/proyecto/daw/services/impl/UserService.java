package com.proyecto.daw.services.impl;

import com.proyecto.daw.dto.SaveUsuario;
import com.proyecto.daw.dto.UsuarioDTO;
import com.proyecto.daw.exceptions.InvalidPasswordException;
import com.proyecto.daw.exceptions.ObjectNotFoundException;
import com.proyecto.daw.exceptions.ValidacionRegisterUsuario;
import com.proyecto.daw.persistence.entity.Usuario;
import com.proyecto.daw.persistence.interfaces.IUsuario;
import com.proyecto.daw.persistence.repository.UsuarioRepo;
import com.proyecto.daw.persistence.util.EstadoUsuario;
import com.proyecto.daw.persistence.util.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserService  implements IUsuario {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Page<Usuario> listarUsuarios(Pageable pageable) {
        return usuarioRepo.findAll(pageable);
    }

    @Override
    public Optional<UsuarioDTO> buscarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepo.findById(id);
        return usuario.map(this::convertDto);
    }

    //OBTENER USUARIO SIN EL PASSWORD
    private UsuarioDTO convertDto(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }


    @Override
    public Usuario guardarUsuario(SaveUsuario saveUsuario) {

        //VALIDACIONES  USUARIO
        validacionUsuario(saveUsuario);

        //VALIDACION LLAMA AL METODO QUE VALIDA EL PASSWORD 2 VECES
        validacionPassword(saveUsuario);

        Usuario usuario = new Usuario();
        usuario.setUsername(saveUsuario.getUsername());
        usuario.setNombre(saveUsuario.getNombre());
        usuario.setApellido(saveUsuario.getApellido());
        usuario.setEmail(saveUsuario.getEmail());
        usuario.setPassword(passwordEncoder.encode(saveUsuario.getPassword()));
        usuario.setRol(Rol.USUARIO);
        usuario.setEstado(EstadoUsuario.INACTIVO);

        return usuarioRepo.save(usuario);
    }

    @Override
    public Usuario actualizarUsuario(SaveUsuario saveUsuario, Long id) {

        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("Usuario no encontrado"));

        //VALIDACIONES  USUARIO
        validacionUsuario(saveUsuario);

        //ACTUALIZAR USUARIO
        usuario.setUsername(saveUsuario.getUsername());
        usuario.setNombre(saveUsuario.getNombre());
        usuario.setApellido(saveUsuario.getApellido());
        usuario.setEmail(saveUsuario.getEmail());
        usuario.setPassword(passwordEncoder.encode(saveUsuario.getPassword()));
        usuario.setRol(Rol.USUARIO);
        usuario.setEstado(EstadoUsuario.ACTIVO);

        return usuarioRepo.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepo.deleteById(id);
    }

    //VALIDACION DE USUARIOS
    private void validacionUsuario(SaveUsuario dto){

        // Verificar si el username ya está en uso por otro usuario
        if(usuarioRepo.existsByUsername(dto.getUsername())){
            throw new ValidacionRegisterUsuario("Ya existe un usuario con el username '" + dto.getUsername() + "'");
        }

        // Verificar si el email ya está en uso por otro usuario
        if (usuarioRepo.existsByEmail(dto.getEmail())) {
            throw new ValidacionRegisterUsuario("Ya existe un usuario con el email '" + dto.getEmail() + "'");
        }


    }

    //VALIDACION DE PASSWORD  PARA 2 VECES ESCRIBIRLA
    private void validacionPassword(SaveUsuario dto){
        if(!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatPassword())){
            throw  new InvalidPasswordException("Las contraseñas no coinciden");
        }

        if(!dto.getPassword().equals(dto.getRepeatPassword())){
            throw  new InvalidPasswordException("Las contraseñas no coinciden");
        }
    }


}
