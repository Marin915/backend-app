
package mx.insabit.ValidacionMateriales.Controller;

/**
 *
 * @author Marin
 */

import mx.insabit.ValidacionMateriales.Entity.MovimientoMaterial;
import mx.insabit.ValidacionMateriales.Service.MovimientoMaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoMaterialController {

    private final MovimientoMaterialService service;

    public MovimientoMaterialController(MovimientoMaterialService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MovimientoMaterial> registrar(
            @RequestBody MovimientoMaterial movimiento) {
        return ResponseEntity.ok(service.registrar(movimiento));
    }
}

