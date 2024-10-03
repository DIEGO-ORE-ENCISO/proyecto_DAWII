package com.proyecto.daw.persistence.repository;

import com.proyecto.daw.persistence.entity.CategoriaProd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository  extends JpaRepository<CategoriaProd, Long> {
}
