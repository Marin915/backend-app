
package mx.insabit.ValidacionMateriales.Repository;

import mx.insabit.ValidacionMateriales.Entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marin
 */

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    
    
}
