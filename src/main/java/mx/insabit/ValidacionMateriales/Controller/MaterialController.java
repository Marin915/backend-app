package mx.insabit.ValidacionMateriales.Controller;


import org.springframework.web.bind.annotation.*;

import java.util.List;
import mx.insabit.ValidacionMateriales.DTO.MaterialResumenDTO;
import mx.insabit.ValidacionMateriales.Entity.Material;
import mx.insabit.ValidacionMateriales.Entity.MovimientoMaterial;
//import mx.insabit.ValidacionMateriales.DTO.MaterialesDTO;
import mx.insabit.ValidacionMateriales.Service.MaterialService;
import mx.insabit.ValidacionMateriales.Service.MovimientoMaterialService;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
public class MaterialController {

        private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);

    private final MaterialService materialService;
    private final MovimientoMaterialService movimientoService;

    public MaterialController(MaterialService materialService,
                              MovimientoMaterialService movimientoService) {
        this.materialService = materialService;
        this.movimientoService = movimientoService;
    }

    /* =======================
       CRUD MATERIALES
       ======================= */

    @PostMapping
    public ResponseEntity<Material> crear(@RequestBody Material material) {
        return ResponseEntity.ok(materialService.guardar(material));
    }

    @GetMapping
    public ResponseEntity<List<Material>> listar() {
        return ResponseEntity.ok(materialService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Material> actualizar(
            @PathVariable Long id,
            @RequestBody Material material) {
        return ResponseEntity.ok(materialService.actualizar(id, material));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        materialService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /* =======================
       STOCK
       ======================= */

    @GetMapping("/{id}/stock")
    public ResponseEntity<Integer> stock(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.obtenerStock(id));
    }

    /* =======================
       MOVIMIENTOS (ENTRADA / SALIDA)
       ======================= */

    @PostMapping("/{id}/movimientos")
    public ResponseEntity<MovimientoMaterial> registrarMovimiento(
        @PathVariable Long id,
        @RequestBody MovimientoMaterial movimiento) {

    Material material = materialService.obtenerPorId(id);
    movimiento.setMaterial(material);

    return ResponseEntity.ok(movimientoService.registrar(movimiento));
}

    @GetMapping("/resumen-todo")
    public ResponseEntity<List<MaterialResumenDTO>> listarResumen() {
    logger.info("Entrando a listarResumen()");
    List<MaterialResumenDTO> resumen = materialService.listarResumen();
    logger.info("Resumen obtenido: " + resumen.size());
    return ResponseEntity.ok(resumen);
}





}

    
   /* private final MaterialService servicio;

    public MaterialController(MaterialService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ApiResponse<MaterialesDTO> crear(@RequestBody MaterialesDTO dto) {
        return ApiResponse.success(servicio.crear(dto));
    }

    @GetMapping
    public ApiResponse<List<MaterialesDTO>> listar() {
        return ApiResponse.success(servicio.listar());
    }

    @GetMapping("/{id}")
    public ApiResponse<MaterialesDTO> obtener(@PathVariable Long id) {
        return ApiResponse.success(servicio.obtener(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<MaterialesDTO> actualizar(@PathVariable Long id, @RequestBody MaterialesDTO dto) {
        return ApiResponse.success(servicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ApiResponse.success("Eliminado correctamente");
    }  
    */

