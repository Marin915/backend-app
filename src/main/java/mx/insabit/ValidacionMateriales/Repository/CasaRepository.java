
package mx.insabit.ValidacionMateriales.Repository;

import java.util.List;
import mx.insabit.ValidacionMateriales.Entity.Casa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marin
 */

@Repository
public interface CasaRepository extends JpaRepository<Casa, Long> {
 // 🔹 Materiales requeridos + usados por casa
    @Query(value = """
        SELECT
          m.id              AS material_id,
          m.descripcion     AS nombre,
          m.unidad_medida   AS unidad,
          mm.cantidad       AS requerido,
          COALESCE(SUM(mv.cantidad), 0) AS usado
        FROM casa c
        JOIN modelo_material mm ON c.modelo_id = mm.modelo_id
        JOIN material m ON mm.material_id = m.id
        LEFT JOIN movimiento_material mv
          ON mv.material_id = m.id
          AND mv.casa_id = c.id
          AND mv.tipo = 'SALIDA'
        WHERE c.id = :casaId
        GROUP BY m.id, m.descripcion, m.unidad_medida, mm.cantidad
        """, nativeQuery = true)
            
    List<Object[]> obtenerMaterialesPorCasa(@Param("casaId") Long casaId);

    // 🔹 Progreso de la casa
    @Query(value = """
        SELECT
          ROUND(
            (
              SUM(LEAST(usado, requerido)) * 100.0
              /
              SUM(requerido)
            )
          ) AS progreso
        FROM (
          SELECT
            mm.material_id,
            mm.cantidad AS requerido,
            COALESCE(SUM(mv.cantidad), 0) AS usado
          FROM casa c
          JOIN modelo_material mm ON c.modelo_id = mm.modelo_id
          LEFT JOIN movimiento_material mv
            ON mv.material_id = mm.material_id
            AND mv.casa_id = c.id
            AND mv.tipo = 'SALIDA'
          WHERE c.id = :casaId
          GROUP BY mm.material_id, mm.cantidad
        ) t
        """, nativeQuery = true)
    Integer calcularProgreso(@Param("casaId") Long casaId);
    
        List<Casa> findByModeloId(Long modeloId);

}
