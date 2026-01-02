
package mx.insabit.ValidacionMateriales.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.insabit.ValidacionMateriales.MaterialesEntity;


public interface MaterialesRepository extends JpaRepository<MaterialesEntity, Long> {

}