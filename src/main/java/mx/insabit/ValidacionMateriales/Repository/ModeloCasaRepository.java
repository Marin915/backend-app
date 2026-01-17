
package mx.insabit.ValidacionMateriales.Repository;

import java.util.List;
import java.util.Optional;
import mx.insabit.ValidacionMateriales.Entity.ModeloCasa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Marin
 */

public interface ModeloCasaRepository extends JpaRepository<ModeloCasa, Long>{
    
        // Cambiar a:
        List<ModeloCasa> findByNombre(String nombre);
    
}
