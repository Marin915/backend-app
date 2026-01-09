/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.insabit.ValidacionMateriales.MapStruc;

import mx.insabit.ValidacionMateriales.DTO.MaterialDTO;
import mx.insabit.ValidacionMateriales.Entity.Material;

/**
 *
 * @author Marin
 */
public class MaterialMappers {
    
    public static MaterialDTO toDTO(Material material) {
        MaterialDTO dto = new MaterialDTO();
        dto.setId(material.getId());
        dto.setClave(material.getClave());
        dto.setDescripcion(material.getDescripcion());
        dto.setUnidadMedida(material.getUnidadMedida());
        dto.setPrecioUnitario(material.getPrecioUnitario());
        dto.setCategoria(material.getCategoria());
        dto.setActivo(material.getActivo());
        return dto;
    }

    public static Material toEntity(MaterialDTO dto) {
        Material material = new Material();
        material.setClave(dto.getClave());
        material.setDescripcion(dto.getDescripcion());
        material.setUnidadMedida(dto.getUnidadMedida());
        material.setPrecioUnitario(dto.getPrecioUnitario());
        material.setCategoria(dto.getCategoria());
        material.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return material;
    }
}
