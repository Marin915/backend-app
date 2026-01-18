package mx.insabit.ValidacionMateriales.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import mx.insabit.ValidacionMateriales.DTO.AsignacionMaterialDTO;
import mx.insabit.ValidacionMateriales.DTO.CasaDTO;
import mx.insabit.ValidacionMateriales.DTO.CasaDetalleDTO;
import mx.insabit.ValidacionMateriales.DTO.MaterialCasaDTO;
import mx.insabit.ValidacionMateriales.DTO.MaterialEntregaDTO;
import mx.insabit.ValidacionMateriales.DTO.ModeloCasaSimpleDTO;
import mx.insabit.ValidacionMateriales.Entity.Casa;
import mx.insabit.ValidacionMateriales.Entity.Material;
import mx.insabit.ValidacionMateriales.Entity.MaterialCasa;
import mx.insabit.ValidacionMateriales.Entity.ModeloCasa;
import mx.insabit.ValidacionMateriales.Repository.CasaRepository;
import mx.insabit.ValidacionMateriales.Repository.MaterialCasaRepository;
import mx.insabit.ValidacionMateriales.Repository.MaterialRepository;
import mx.insabit.ValidacionMateriales.Repository.ModeloCasaRepository;
import mx.insabit.ValidacionMateriales.Repository.MovimientoMaterialRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marin
 */

@Service
public class CasaService {
   
  private final CasaRepository casaRepository;
    private final ModeloCasaRepository modeloCasaRepository;
    private final MaterialRepository materialRepository;
    private final MaterialCasaRepository materialCasaRepository;
    private final MovimientoMaterialRepository movimientoMaterialRepository;

    public CasaService(
            CasaRepository casaRepository,
            ModeloCasaRepository modeloCasaRepository,
            MaterialRepository materialRepository,
            MaterialCasaRepository materialCasaRepository,
            MovimientoMaterialRepository movimientoMaterialRepository
    ) {
        this.casaRepository = casaRepository;
        this.modeloCasaRepository = modeloCasaRepository;
        this.materialRepository = materialRepository;
        this.materialCasaRepository = materialCasaRepository;
        this.movimientoMaterialRepository = movimientoMaterialRepository;
    }


    public CasaDetalleDTO obtenerDetalleCasa(Long casaId) {

        Casa casa = casaRepository.findById(casaId)
                .orElseThrow(() -> new RuntimeException("Casa no encontrada"));

     ModeloCasa modelo = casa.getModelo();
     
        String nombreModelo = (modelo != null)
        ? modelo.getNombre()
            : "Sin modelo";



List<MaterialCasaDTO> materiales =
    casaRepository.obtenerMaterialesPorCasa(casaId)
        .stream()
        .map(row -> new MaterialCasaDTO(
            ((Number) row[0]).longValue(),   // id (relación casa_material)
            ((Number) row[1]).longValue(),   // materialId
            (String) row[2],                 // nombre
            (String) row[3],                 // unidad
            ((Number) row[4]).intValue(),    // requerido
            ((Number) row[5]).intValue()     // usado
        ))
        .collect(Collectors.toList());


        Integer progreso = casaRepository.calcularProgreso(casaId);
        if (progreso == null) progreso = 0;

    
        return new CasaDetalleDTO(
                casa.getId(),
                casa.getNombre(),
                nombreModelo,
                progreso,
                materiales
        );
    
    }
 public AsignacionMaterialDTO asignarMaterial(Long casaId, Long materialId, int requerido) {
    Casa casa = casaRepository.findById(casaId)
        .orElseThrow(() -> new NoSuchElementException("Casa no encontrada con id: " + casaId));

    Material material = materialRepository.findById(materialId)
        .orElseThrow(() -> new NoSuchElementException("Material no encontrado con id: " + materialId));

    Optional<MaterialCasa> optionalMc = materialCasaRepository.findByCasaIdAndMaterialId(casaId, materialId);

    MaterialCasa mc;

    if (optionalMc.isPresent()) {
        mc = optionalMc.get();
        mc.setCantidadPresupuestada(mc.getCantidadPresupuestada() + requerido); // Sumar la cantidad
    } else {
        mc = new MaterialCasa();
        mc.setCasa(casa);
        mc.setMaterial(material);
        mc.setCantidadPresupuestada(requerido);
        mc.setSalidas(0);
    }

    MaterialCasa saved = materialCasaRepository.save(mc);

    AsignacionMaterialDTO dto = new AsignacionMaterialDTO();
    dto.setCasaId(casa.getId());
    dto.setCasaNombre(casa.getNombre());
    dto.setMaterialId(material.getId());
    dto.setMaterialClave(material.getClave());
    dto.setRequerido(saved.getCantidadPresupuestada());
    dto.setFechaAsignacion(LocalDateTime.now());

    return dto;
}


  
      public CasaDTO toCasaDTO(Casa casa) {
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
 

       public List<CasaDTO> listarTodasCasas() {
        List<Casa> casas = casaRepository.findAll();
        return casas.stream().map(this::toCasaDTO).collect(Collectors.toList());
    }
 
       
   public List<MaterialCasaDTO> listarMaterialesPorCasa(Long casaId) {
    List<MaterialCasa> materiales = materialCasaRepository.findByCasaId(casaId);
    return materiales.stream().map(this::toDTO).collect(Collectors.toList());
}

private MaterialCasaDTO toDTO(MaterialCasa materialCasa) {
    MaterialCasaDTO dto = new MaterialCasaDTO();
    dto.setId(materialCasa.getId());

    // Aquí accedes a la entidad material relacionada para obtener el nombre
    dto.setNombre(materialCasa.getMaterial().getDescripcion()); // o getClave()

    dto.setUnidad(materialCasa.getMaterial().getUnidadMedida()); // o el getter correcto de unidad

    dto.setRequerido(materialCasa.getCantidadPresupuestada());
    dto.setUsado(materialCasa.getSalidas());

    return dto;
}


 public List<MaterialEntregaDTO> obtenerEntregas() {

        List<Material> materiales = materialRepository.findAll();

        return materiales.stream()
            .map(material -> {
                Integer entregado = movimientoMaterialRepository.totalEntregado(
                    material.getId()
                );
                return map(material, entregado);
            })
            .toList();
    }

    // TU MAP EXACTO
    private MaterialEntregaDTO map(Material material, Integer entregado) {

        MaterialEntregaDTO dto = new MaterialEntregaDTO();

        dto.setId(material.getId());
        dto.setClave(material.getClave());
        dto.setDescripcion(material.getDescripcion());
        dto.setUnidad(material.getUnidadMedida());

        Integer presupuestada = 0;
        Integer entregada = entregado != null ? entregado : 0;

        dto.setCantidadPresupuestada(presupuestada);
        dto.setCantidadEntregada(entregada);
        dto.setSalidasPorEntregar(presupuestada - entregada);

        dto.setFechaEntrega(
            material.getFechaCreacion() != null
                ? material.getFechaCreacion().toLocalDate()
                : null
        );

        return dto;
    }


}
