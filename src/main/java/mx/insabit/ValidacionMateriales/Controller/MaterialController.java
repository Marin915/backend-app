package mx.insabit.ValidacionMateriales.Controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import mx.insabit.ValidacionMateriales.DTO.MaterialDTO;
import mx.insabit.ValidacionMateriales.DTO.MaterialResumenDTO;
import mx.insabit.ValidacionMateriales.DTO.MovimientoMaterialDTO;
import mx.insabit.ValidacionMateriales.Entity.Material;
import mx.insabit.ValidacionMateriales.Entity.MovimientoMaterial;
//import mx.insabit.ValidacionMateriales.DTO.MaterialesDTO;
import mx.insabit.ValidacionMateriales.Service.MaterialService;
import mx.insabit.ValidacionMateriales.Service.MovimientoMaterialService;
import mx.insabit.ValidacionMateriales.Service.ReporteService;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;



@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
public class MaterialController {

    private static final Logger logger =
            LoggerFactory.getLogger(MaterialController.class);

  private final ReporteService reporteService;
private final MaterialService materialService;
private final MovimientoMaterialService movimientoService;

public MaterialController(MaterialService materialService,
                          MovimientoMaterialService movimientoService,
                          ReporteService reporteService) {
    this.materialService = materialService;
    this.movimientoService = movimientoService;
    this.reporteService = reporteService;
}

    /* =======================
       CRUD MATERIALES
       ======================= */

    @PostMapping
    public ResponseEntity<Material> crear(@RequestBody MaterialDTO dto) {

    Material material = new Material();
    material.setClave(dto.getClave());
    material.setDescripcion(dto.getDescripcion());
    material.setUnidadMedida(dto.getUnidadMedida());
    material.setPrecioUnitario(dto.getPrecioUnitario());
    material.setCategoria(dto.getCategoria());
    material.setActivo(true);

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
            @Valid @RequestBody MaterialDTO dto) {

        logger.info("Actualizando material id {}", id);

        Material material = materialService.obtenerPorId(id);

        material.setClave(dto.getClave());
        material.setDescripcion(dto.getDescripcion());
        material.setUnidadMedida(dto.getUnidadMedida());
        material.setPrecioUnitario(dto.getPrecioUnitario());
        material.setCategoria(dto.getCategoria());
        material.setActivo(dto.getActivo());

        return ResponseEntity.ok(materialService.guardar(material));
    }

   @DeleteMapping("/{id}")
public ResponseEntity<?> eliminar(@PathVariable Long id) {

    try {
        materialService.eliminar(id);
        return ResponseEntity.noContent().build();

    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

    } catch (DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("No se puede eliminar el material porque tiene movimientos asociados");

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al eliminar material");
    }
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
public ResponseEntity<MovimientoMaterialDTO> registrarMovimiento(
        @PathVariable Long id,
        @RequestBody MovimientoMaterialDTO dto) {

    Material material = materialService.obtenerPorId(id);

    MovimientoMaterial movimiento = new MovimientoMaterial();
    movimiento.setMaterial(material);
    movimiento.setTipo(dto.getTipo());
    movimiento.setCantidad(dto.getCantidad());

    MovimientoMaterial guardado = movimientoService.registrar(movimiento);

    MovimientoMaterialDTO response = new MovimientoMaterialDTO();
    response.setId(guardado.getId());
    response.setTipo(guardado.getTipo());
    response.setCantidad(guardado.getCantidad());
    response.setFecha(guardado.getFecha());
    response.setMaterialId(material.getId());

    return ResponseEntity.ok(response);
}
    /* =======================
       RESUMEN INVENTARIO
       ======================= */

    @GetMapping("/resumen-todo")
    public ResponseEntity<List<MaterialResumenDTO>> listarResumen() {

        logger.info("Entrando a listarResumen()");

        List<MaterialResumenDTO> resumen =
                materialService.listarResumen();

        logger.info("Resumen obtenido: {}", resumen.size());

        return ResponseEntity.ok(resumen);
    }
    
    
    @GetMapping("/api/materiales/reporte")
    public ResponseEntity<byte[]> descargarReporteExcel() throws IOException {
        byte[] excelBytes = reporteService.generarReporteInventarioExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
            .filename("Reporte_Inventario_Materiales.xlsx")
            .build());

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
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

