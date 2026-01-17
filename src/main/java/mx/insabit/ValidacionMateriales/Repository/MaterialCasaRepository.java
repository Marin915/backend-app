
package mx.insabit.ValidacionMateriales.Repository;

import java.util.Optional;
import mx.insabit.ValidacionMateriales.Entity.MaterialCasa;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MaterialCasaRepository extends JpaRepository<MaterialCasa, Long> {
    
  Optional<MaterialCasa> findByCasaIdAndMaterialId(Long casaId, Long materialId);
  

}
