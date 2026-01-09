
package mx.insabit.ValidacionMateriales.Service;

import mx.insabit.ValidacionMateriales.Entity.MovimientoMaterial;
import mx.insabit.ValidacionMateriales.Repository.MovimientoMaterialRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marin
 */

@Service
public class MovimientoMaterialServiceImpl implements MovimientoMaterialService {

    private final MovimientoMaterialRepository repository;

    public MovimientoMaterialServiceImpl(MovimientoMaterialRepository repository) {
        this.repository = repository;
    }

    @Override
    public MovimientoMaterial registrar(MovimientoMaterial movimiento) {
        return repository.save(movimiento);
    }

    @Override
    public Integer obtenerStock(Long materialId) {
        return repository.obtenerStock(materialId);
    }
}
