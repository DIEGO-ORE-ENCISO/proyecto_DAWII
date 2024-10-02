package com.proyecto.daw.services.impl;

import com.proyecto.daw.dto.RegisterUsuario;
import com.proyecto.daw.dto.SaveUsuario;
import com.proyecto.daw.dto.auth.LoginRequest;
import com.proyecto.daw.dto.auth.AuthResponse;
import com.proyecto.daw.exceptions.ObjectNotFoundException;
import com.proyecto.daw.persistence.entity.Usuario;
import com.proyecto.daw.persistence.repository.UsuarioRepo;
import com.proyecto.daw.services.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    public RegisterUsuario registerUsuario(SaveUsuario saveUsuario) {

        Usuario usuario = userService.guardarUsuario(saveUsuario);

        RegisterUsuario userDto = new RegisterUsuario();
        userDto.setUsuario(new Usuario());
        userDto.getUsuario().setId(usuario.getId());
        userDto.getUsuario().setNombre(usuario.getNombre());
        userDto.getUsuario().setApellido(usuario.getApellido());
        userDto.getUsuario().setEmail(usuario.getEmail());
        userDto.getUsuario().setPassword(usuario.getPassword());
        userDto.getUsuario().setRol(usuario.getRol());
        userDto.getUsuario().setEstado(usuario.getEstado());
        userDto.setMensaje("Sa ha registrado su cuenta correctamente");

        return  userDto;
    }

    //AGREGAR AL JWT INFORMACIÓN
    public Map<String, Object> generateExtraClaims(Usuario user){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("nombre", user.getNombre() + " " + user.getApellido());
        extraClaims.put("rol", user.getRol());

        return extraClaims;
    }


    //LOGIN
    public AuthResponse login(LoginRequest request) {

        //** SI LA CONTRASEÑA ES INCORRECTA SALDRA EL ERROR DE CREDECNIALES INVALIDAS
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword())
            );
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        //** SI USUARIO ES INCORRECTO SALDRA EL ERROR DE USERNAME NO ENCONTRADO
        UserDetails user = usuarioRepo.findByUsername(request.getUsername()).orElseThrow(() -> new ObjectNotFoundException("Usuario no encontrado"));
        String token = jwtService.getToken(generateExtraClaims((Usuario) user), user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUsuario((Usuario) user);
        authResponse.setToken(token);

        return authResponse;
    }

    //METODO PARA VALIDAR TOKEN GET
    public AuthResponse validateToken(String jwt) {

        AuthResponse authResponse = new AuthResponse();
        try {
            String username = jwtService.getUsernameFromToken(jwt);

            Usuario usuario = usuarioRepo.findByUsername(username).orElseThrow();

            authResponse.setUsuario(usuario);
            authResponse.setToken(jwt);

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return authResponse;
    }


}
