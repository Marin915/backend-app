
package mx.insabit.ValidacionMateriales.Controller;


import org.springframework.web.bind.annotation.*;

import java.util.List;
import mx.insabit.ValidacionMateriales.DTO.MaterialesDTO;
import mx.insabit.ValidacionMateriales.Response.ApiResponse;
import mx.insabit.ValidacionMateriales.Service.MaterialService;

@RestController
@RequestMapping("/api/materiales")
@CrossOrigin("*")
public class MaterialController {
    
  
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
}
