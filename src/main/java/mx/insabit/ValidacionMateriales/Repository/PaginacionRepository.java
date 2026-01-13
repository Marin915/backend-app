
package mx.insabit.ValidacionMateriales.Repository;

import mx.insabit.ValidacionMateriales.Entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaginacionRepository extends JpaRepository<Material, Long> {
    
Page<Material> findAll(Pageable pageable);  // Esto permite la paginación

}
