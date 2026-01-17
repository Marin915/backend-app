package mx.insabit.ValidacionMateriales.Controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import mx.insabit.ValidacionMateriales.DTO.AsignacionMaterialDTO;
import mx.insabit.ValidacionMateriales.DTO.AsignacionMaterialRequest;
import mx.insabit.ValidacionMateriales.DTO.CasaDTO;
import mx.insabit.ValidacionMateriales.DTO.CasaDetalleDTO;
import mx.insabit.ValidacionMateriales.DTO.MaterialCasaDTO;
import mx.insabit.ValidacionMateriales.DTO.MaterialDTO;
import mx.insabit.ValidacionMateriales.DTO.MaterialResumenDTO;
import mx.insabit.ValidacionMateriales.DTO.ModeloCasaSimpleDTO;
import mx.insabit.ValidacionMateriales.DTO.MovimientoMaterialDTO;
import mx.insabit.ValidacionMateriales.DTO.SalidaCasaDTO;
import mx.insabit.ValidacionMateriales.Entity.Casa;
import mx.insabit.ValidacionMateriales.Entity.Material;
import mx.insabit.ValidacionMateriales.Entity.ModeloCasa;
import mx.insabit.ValidacionMateriales.Entity.MovimientoMaterial;
import mx.insabit.ValidacionMateriales.Repository.CasaRepository;
import mx.insabit.ValidacionMateriales.Repository.ModeloCasaRepository;
import mx.insabit.ValidacionMateriales.Service.CasaService;
import mx.insabit.ValidacionMateriales.Service.MaterialCasaService;
//import mx.insabit.ValidacionMateriales.DTO.MaterialesDTO;
import mx.insabit.ValidacionMateriales.Service.MaterialService;
import mx.insabit.ValidacionMateriales.Service.MaterialServiceImpl;
import mx.insabit.ValidacionMateriales.Service.MovimientoMaterialService;
import mx.insabit.ValidacionMateriales.Service.ReporteService;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
public class MaterialController {

    private static final Logger logger =
            LoggerFactory.getLogger(MaterialController.class);

   private final ReporteService reporteService;
   private final CasaService casaService;
    private final MaterialService materialService;
    private final MovimientoMaterialService movimientoService;
    private final MaterialCasaService materialCasaService;
  
    
    private final CasaRepository casaRepository;
    private final ModeloCasaRepository modeloCasaRepository;


public MaterialController(
        MaterialService materialService,
        MovimientoMaterialService movimientoService,
        ReporteService reporteService,
        CasaService casaService,
        MaterialCasaService materialCasaService,
        CasaRepository casaRepository,
        ModeloCasaRepository modeloCasaRepository
) {
    this.materialService = materialService;
    this.movimientoService = movimientoService;
    this.reporteService = reporteService;
    this.casaService = casaService;
    this.materialCasaService = materialCasaService;
    this.casaRepository = casaRepository;
    this.modeloCasaRepository = modeloCasaRepository;
}


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

    @GetMapping("/{id:\\d+}")
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

    @GetMapping("/{id}/stock")
    public ResponseEntity<Integer> stock(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.obtenerStock(id));
    }

@PostMapping("/{id}/movimientos")
public ResponseEntity<MovimientoMaterialDTO> registrarMovimiento(
        @PathVariable Long id,
        @RequestBody MovimientoMaterialDTO dto) {

    Material material = materialService.obtenerPorId(id);

    MovimientoMaterial movimiento = new MovimientoMaterial();
    movimiento.setMaterial(material);
    movimiento.setTipo(dto.getTipo()); // ✅ ENUM
    movimiento.setCantidad(dto.getCantidad());

    MovimientoMaterial guardado = movimientoService.registrar(movimiento);

    MovimientoMaterialDTO response = new MovimientoMaterialDTO();
    response.setId(guardado.getId());
    response.setTipo(guardado.getTipo()); // ✅ ENUM
    response.setCantidad(guardado.getCantidad());
    response.setFecha(guardado.getFecha());
    response.setMaterialId(material.getId());

    return ResponseEntity.ok(response);
}

   
    @GetMapping("/resumen-todo")
    public ResponseEntity<List<MaterialResumenDTO>> listarResumen() {
        logger.info("Entrando a listarResumen()");
        List<MaterialResumenDTO> resumen =
                materialService.listarResumen();
        logger.info("Resumen obtenido: {}", resumen.size());
        return ResponseEntity.ok(resumen);
    }
    
    
 
    @GetMapping("/reporte")  // Ruta clara y sin conflicto
    public ResponseEntity<byte[]> descargarReporteExcel() {
        try {
            byte[] excelBytes = reporteService.generarReporteInventarioExcel();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("Reporte_Inventario_Materiales.xlsx")
                    .build());
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/{id}/detalle")
    public ResponseEntity<CasaDetalleDTO> obtenerDetalle(@PathVariable Long id) {
        logger.info("Consultando detalle de casa id {}", id);
        CasaDetalleDTO detalle = casaService.obtenerDetalleCasa(id);
        return ResponseEntity.ok(detalle);
    }
    
    
    @GetMapping("/paginados")
public ResponseEntity<Map<String, Object>> obtenerPaginas(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size) {
    if (page < 1) page = 1;

    // 👇 SOLO validar size si NO es -1
    if (size != -1 && (size < 1 || size > 100)) {
        size = 10;
    }
    Page<MaterialResumenDTO> archivosPage =
        materialService.obtenerPaginasResumen(page, size);
    Map<String, Object> response = Map.of(
        "archivos", archivosPage.getContent(),
        "totalItems", archivosPage.getTotalElements(),
        "totalPages", archivosPage.getTotalPages(),
        "currentPage", archivosPage.getNumber() + 1
    );
    return ResponseEntity.ok(response);
}

@PostMapping("/salida-casa")
public ResponseEntity<?> registrarSalidaCasa(
        @RequestBody SalidaCasaDTO dto
) {
    try {

        materialCasaService.registrarSalida(
            dto.getCasaId(),
            dto.getMaterialId(),
            dto.getCantidad()
        );

        return ResponseEntity.ok(
            Map.of(
                "mensaje", "Salida registrada correctamente",
                "casaId", dto.getCasaId(),
                "materialId", dto.getMaterialId(),
                "cantidad", dto.getCantidad()
            )
        );

    } catch (RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
    }
    
    
    
}

   @PostMapping("/crear-casa")
public ResponseEntity<CasaDTO> crear(@RequestBody Casa casa) {

    if (casa.getModelo() == null || casa.getModelo().getId() == null) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "El modelo es obligatorio"
        );
    }
    ModeloCasa modelo = modeloCasaRepository
        .findById(casa.getModelo().getId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Modelo no encontrado"
        ));
    casa.setModelo(modelo);
    casa.setProgreso(0);

    Casa guardada = casaRepository.save(casa);

    modelo.setTotalCasas(
        modelo.getTotalCasas() == null ? 1 : modelo.getTotalCasas() + 1
    );
    modeloCasaRepository.save(modelo);

    CasaDTO dto = toCasaDTO(guardada);

    return ResponseEntity.ok(dto);
}

private CasaDTO toCasaDTO(Casa casa) {
    CasaDTO dto = new CasaDTO();
    dto.setId(casa.getId());
    dto.setNombre(casa.getNombre());
    dto.setUbicacion(casa.getUbicacion());
    dto.setProgreso(casa.getProgreso());
    dto.setFechaCreacion(casa.getFechaCreacion());

    ModeloCasaSimpleDTO modeloDTO = new ModeloCasaSimpleDTO();
    modeloDTO.setId(casa.getModelo().getId());
    modeloDTO.setNombre(casa.getModelo().getNombre());

    dto.setModelo(modeloDTO);

    return dto;
}
    
@PostMapping("/asignar-material")
public ResponseEntity<AsignacionMaterialDTO> asignarMaterial(@RequestBody @Valid AsignacionMaterialRequest request) {
    AsignacionMaterialDTO dto = casaService.asignarMaterial(request.getCasaId(), request.getMaterialId(), request.getRequerido());
    return ResponseEntity.ok(dto);
}


@GetMapping("/casas")
public ResponseEntity<List<CasaDTO>> listarCasas() {
    List<CasaDTO> casas = casaService.listarTodasCasas();
    return ResponseEntity.ok(casas);
}

@GetMapping("/casas/{id}/materiales")
public ResponseEntity<List<MaterialCasaDTO>> obtenerMaterialesPorCasa(@PathVariable Long id) {
    List<MaterialCasaDTO> materiales = casaService.listarMaterialesPorCasa(id);
    return ResponseEntity.ok(materiales);
}
 
  /*  // Paginacion 
    @GetMapping("/paginados")
    public ResponseEntity<Map<String, Object>> obtenerPaginas(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size) {
        if (page < 1) page = 1;
    // 👇 SOLO validar size si NO es -1
        if (size != -1 && (size < 1 || size > 100)) {
        size = 10;
     }
        Page<Material> archivosPage =
            materialService.obtenerPaginas(page, size);
        Map<String, Object> response = Map.of(
            "archivos", archivosPage.getContent(),
            "totalItems", archivosPage.getTotalElements(),
            "totalPages", archivosPage.getTotalPages(),
            "currentPage", archivosPage.getNumber() + 1
         );
         return ResponseEntity.ok(response);
        }
    */
          @DeleteMapping("/{id}/movimientos/ultimo")
           public ResponseEntity<Void> deshacerUltimoMovimiento(@PathVariable Long id) {
             materialService.eliminarUltimoMovimiento(id);
              return ResponseEntity.noContent().build();


           }
}
/*
   private final MaterialService servicio;

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

