package mx.insabit.ValidacionMateriales.Controller;


import jakarta.validation.Valid;
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
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
public class MaterialController {

    private static final Logger logger =
            LoggerFactory.getLogger(MaterialController.class);

    private final MaterialService materialService;
    private final MovimientoMaterialService movimientoService;

    public MaterialController(MaterialService materialService,
                              MovimientoMaterialService movimientoService) {
        this.materialService = materialService;
        this.movimientoService = movimientoService;
    }

    /* =======================
       CREAR MATERIAL
       ======================= */
    @PostMapping
    public ResponseEntity<MaterialDTO> crear(@RequestBody MaterialDTO dto) {

        Material material = new Material();
        material.setClave(dto.getClave());
        material.setDescripcion(dto.getDescripcion());
        material.setUnidadMedida(dto.getUnidadMedida());
        material.setPrecioUnitario(dto.getPrecioUnitario());
        material.setCategoria(dto.getCategoria());
        material.setActivo(true);

        Material guardado = materialService.guardar(material);

        return ResponseEntity.ok(toDTO(guardado));
    }

    /* =======================
       LISTAR MATERIALES
       ======================= */
    @GetMapping
    public ResponseEntity<List<MaterialDTO>> listar() {

        List<MaterialDTO> lista = materialService.listar()
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    /* =======================
       OBTENER POR ID
       ======================= */
    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                toDTO(materialService.obtenerPorId(id))
        );
    }

    /* =======================
       ACTUALIZAR
       ======================= */
    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MaterialDTO dto) {

        Material material = materialService.obtenerPorId(id);

        material.setClave(dto.getClave());
        material.setDescripcion(dto.getDescripcion());
        material.setUnidadMedida(dto.getUnidadMedida());
        material.setPrecioUnitario(dto.getPrecioUnitario());
        material.setCategoria(dto.getCategoria());
        material.setActivo(dto.getActivo());

        Material actualizado = materialService.guardar(material);

        return ResponseEntity.ok(toDTO(actualizado));
    }

    /* =======================
       ELIMINAR
       ======================= */
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
       MOVIMIENTOS
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

    /* =======================
       MAPPER
       ======================= */
    private MaterialDTO toDTO(Material m) {

        MaterialDTO dto = new MaterialDTO();
        dto.setId(m.getId());
        dto.setClave(m.getClave());
        dto.setDescripcion(m.getDescripcion());
        dto.setUnidadMedida(m.getUnidadMedida());
        dto.setPrecioUnitario(m.getPrecioUnitario());
        dto.setCategoria(m.getCategoria());
        dto.setActivo(m.getActivo());

        return dto;
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

