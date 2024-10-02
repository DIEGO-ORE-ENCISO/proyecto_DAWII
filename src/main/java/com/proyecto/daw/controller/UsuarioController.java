package com.proyecto.daw.controller;

import com.proyecto.daw.persistence.entity.Usuario;
import com.proyecto.daw.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class UsuarioController {

    @Autowired
    private UserService userService;


    @GetMapping("/listar")
    public ResponseEntity<Page<Usuario>> listarUsuarios(Pageable pageable) {
        Page<Usuario> listar = userService.listarUsuarios(pageable);

        if(listar.hasContent()){
            return ResponseEntity.ok(listar);
        }
        return ResponseEntity.noContent().build();
    }

}
