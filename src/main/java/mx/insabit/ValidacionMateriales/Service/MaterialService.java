
package mx.insabit.ValidacionMateriales.Service;

import java.util.List;
import mx.insabit.ValidacionMateriales.DTO.MaterialResumenDTO;
import mx.insabit.ValidacionMateriales.Entity.Material;
import org.springframework.data.domain.Page;


public interface MaterialService {
    
    
    
    
    Material guardar(Material material);

    Material actualizar(Long id, Material material);

    List<Material> listar();

    Material obtenerPorId(Long id);
    
    List<MaterialResumenDTO> listarResumen();


    void eliminar(Long id);
    
     Page<Material> obtenerPaginas(int page, int size);
     
     void eliminarUltimoMovimiento(Long materialId);

   /* MaterialesDTO crear(MaterialesDTO dto);

    MaterialesDTO obtener(Long id);

    List<MaterialesDTO> listar();

    MaterialesDTO actualizar(Long id, MaterialesDTO dto);

    void eliminar(Long id);
    */
}
