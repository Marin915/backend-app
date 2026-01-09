
package mx.insabit.ValidacionMateriales.Service;

import mx.insabit.ValidacionMateriales.Entity.MovimientoMaterial;

/**
 *
 * @author Marin
 */
public interface MovimientoMaterialService {
   
    
    MovimientoMaterial registrar(MovimientoMaterial movimiento);

    Integer obtenerStock(Long materialId);
}
