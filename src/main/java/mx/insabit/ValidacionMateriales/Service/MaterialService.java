
package mx.insabit.ValidacionMateriales.Service;

import java.util.List;
import mx.insabit.ValidacionMateriales.DTO.MaterialesDTO;


public interface MaterialService {
    
    
    MaterialesDTO crear(MaterialesDTO dto);

    MaterialesDTO obtener(Long id);

    List<MaterialesDTO> listar();

    MaterialesDTO actualizar(Long id, MaterialesDTO dto);

    void eliminar(Long id);
    
}
