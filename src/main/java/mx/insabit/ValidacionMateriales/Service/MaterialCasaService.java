
package mx.insabit.ValidacionMateriales.Service;

import jakarta.transaction.Transactional;
import mx.insabit.ValidacionMateriales.Entity.*;
import mx.insabit.ValidacionMateriales.Repository.*;
import org.springframework.stereotype.Service;

import mx.insabit.ValidacionMateriales.Entity.MovimientoMaterial;

import mx.insabit.ValidacionMateriales.Entity.MaterialCasa;

@Service
public class MaterialCasaService {

    private final MaterialRepository materialRepository;
    private final CasaRepository casaRepository;
    private final MaterialCasaRepository materialCasaRepository;
    private final MovimientoMaterialRepository movimientoRepository;

    public MaterialCasaService(
        MaterialRepository materialRepository,
        CasaRepository casaRepository,
        MaterialCasaRepository materialCasaRepository,
        MovimientoMaterialRepository movimientoRepository
    ) {
        this.materialRepository = materialRepository;
        this.casaRepository = casaRepository;
        this.materialCasaRepository = materialCasaRepository;
        this.movimientoRepository = movimientoRepository;
    }

  @Transactional
  public void registrarSalida(
    Long casaId,
    Long materialId,
    int cantidad
) {
    if (cantidad <= 0) {
        throw new RuntimeException("Cantidad inválida");
    }

    Casa casa = casaRepository.findById(casaId)
        .orElseThrow(() -> new RuntimeException("Casa no encontrada"));

    Material material = materialRepository.findById(materialId)
        .orElseThrow(() -> new RuntimeException("Material no encontrado"));

    // ✅ stock real desde movimientos
    int stockActual = movimientoRepository.obtenerStock(materialId);

    if (stockActual < cantidad) {
        throw new RuntimeException(
            "Stock insuficiente. Disponible: " + stockActual
        );
    }

    MaterialCasa materialCasa = materialCasaRepository
        .findByCasaIdAndMaterialId(casaId, materialId)
        .orElseThrow(() ->
            new RuntimeException("Material no asignado a la casa")
        );

    // ➖ movimiento
    MovimientoMaterial mov = new MovimientoMaterial();
    mov.setMaterial(material);
    mov.setTipo(TipoMovimiento.SALIDA);
    mov.setCantidad(cantidad);

    movimientoRepository.save(mov);

    // 🏠 actualizar control de obra
    materialCasa.setSalidas(
        materialCasa.getSalidas() + cantidad
    );

    materialCasaRepository.save(materialCasa);
}

}