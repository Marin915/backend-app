/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.insabit.ValidacionMateriales.MapStruc;


import mx.insabit.ValidacionMateriales.DTO.MaterialDTO;
import mx.insabit.ValidacionMateriales.Entity.Material;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    Material toEntity(MaterialDTO dto);

    MaterialDTO toDTO(Material entity);
    /*
    MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

    MaterialesDTO toDTO(MaterialesEntity material);

    MaterialesEntity toEntity(MaterialesDTO dto);
    */
}