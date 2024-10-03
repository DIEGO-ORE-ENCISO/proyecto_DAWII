package com.proyecto.daw.services.impl;

import com.proyecto.daw.dto.CategoriaDTO;
import com.proyecto.daw.exceptions.CategoriaNotFoundException;
import com.proyecto.daw.persistence.entity.CategoriaProd;
import com.proyecto.daw.persistence.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> getAllCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO getCategoriaById(Long id) throws CategoriaNotFoundException {
        CategoriaProd categoriaProd = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada"));
        return convertToDTO(categoriaProd);
    }

    public CategoriaDTO saveCategoria(CategoriaDTO categoriaDTO) {
        CategoriaProd categoriaProd = convertToEntity(categoriaDTO);
        return convertToDTO(categoriaRepository.save(categoriaProd));
    }

    public CategoriaDTO updateCategoria(Long id, CategoriaDTO categoriaDTO) throws CategoriaNotFoundException {
        CategoriaProd categoriaProd = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada"));

        categoriaProd.setNombreCategoriaProd(categoriaDTO.getNombreCategoriaProd());
        categoriaProd.setEstadoCategoria(categoriaDTO.getEstadoCategoria());
        return convertToDTO(categoriaRepository.save(categoriaProd));
    }

    public void deleteCategoria(Long id) throws CategoriaNotFoundException {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNotFoundException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO convertToDTO(CategoriaProd categoriaProd) {
        // Usamos el constructor generado por Lombok
        return new CategoriaDTO(
                categoriaProd.getId(),
                categoriaProd.getNombreCategoriaProd(),
                categoriaProd.getEstadoCategoria()
        );
    }

    private CategoriaProd convertToEntity(CategoriaDTO categoriaDTO) {
        CategoriaProd categoriaProd = new CategoriaProd();
        categoriaProd.setNombreCategoriaProd(categoriaDTO.getNombreCategoriaProd());
        categoriaProd.setEstadoCategoria(categoriaDTO.getEstadoCategoria());
        return categoriaProd;
    }
}
