
package mx.insabit.ValidacionMateriales.Repository;

import mx.insabit.ValidacionMateriales.Entity.Casa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marin
 */

@Repository
public interface CasaRepository extends JpaRepository<Casa, Long> {
    
}
