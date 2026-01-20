

package mx.insabit.ValidacionMateriales.Repository;

import java.util.List;
import java.util.Optional;
import mx.insabit.ValidacionMateriales.DTO.MaterialStockDTO;
import mx.insabit.ValidacionMateriales.Entity.MovimientoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marin
 */

@Repository
public interface MovimientoMaterialRepository extends JpaRepository<MovimientoMaterial, Long> {
    
    
/*@Query("""
    SELECT COALESCE(SUM(
        CASE 
            WHEN m.tipo = 'ENTRADA' THEN m.cantidad
            WHEN m.tipo = 'SALIDA' THEN -m.cantidad
            ELSE 0
        END
    ), 0)
    FROM MovimientoMaterial m
    WHERE m.material.id = :materialId
""")
Integer obtenerStock(@Param("materialId") Long materialId);*/
    
    @Query("""
    SELECT COALESCE(SUM(
        CASE 
            WHEN m.tipo = 'ENTRADA' THEN m.cantidad
            WHEN m.tipo = 'DEVOLUCION' THEN m.cantidad
            WHEN m.tipo = 'SALIDA' THEN -m.cantidad
            ELSE 0
        END
    ), 0)
    FROM MovimientoMaterial m
    WHERE m.material.id = :materialId
""")
Integer obtenerStock(@Param("materialId") Long materialId);


    @Query("""
    SELECT COALESCE(SUM(m.cantidad), 0)
    FROM MovimientoMaterial m
    WHERE m.material.id = :materialId
      AND m.tipo = 'ENTRADA'
""")
Integer obtenerEntradas(@Param("materialId") Long materialId);


@Query("""
    SELECT COALESCE(SUM(m.cantidad), 0)
    FROM MovimientoMaterial m
    WHERE m.material.id = :materialId
      AND m.tipo = 'SALIDA'
""")
Integer obtenerSalidas(@Param("materialId") Long materialId);

 //  Obtiene el último movimiento por material (ordenado por fecha)
    Optional<MovimientoMaterial>
    findTopByMaterialIdOrderByFechaDesc(Long materialId);

    
    
    @Query("""
    SELECT COALESCE(SUM(m.cantidad), 0)
    FROM MovimientoMaterial m
    WHERE m.material.id = :materialId
""")
Integer totalEntregado(@Param("materialId") Long materialId);

    
}
