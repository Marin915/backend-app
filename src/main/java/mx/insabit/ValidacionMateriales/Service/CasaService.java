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
import mx.insabit.ValidacionMateriales.DTO.ModeloCasaSimpleDTO;
import mx.insabit.ValidacionMateriales.Entity.Casa;
import mx.insabit.ValidacionMateriales.Entity.Material;
import mx.insabit.ValidacionMateriales.Entity.MaterialCasa;
import mx.insabit.ValidacionMateriales.Entity.ModeloCasa;
import mx.insabit.ValidacionMateriales.Repository.CasaRepository;
import mx.insabit.ValidacionMateriales.Repository.MaterialCasaRepository;
import mx.insabit.ValidacionMateriales.Repository.MaterialRepository;
import mx.insabit.ValidacionMateriales.Repository.ModeloCasaRepository;
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

    public CasaService(CasaRepository casaRepository,
                       ModeloCasaRepository modeloCasaRepository,
                       MaterialRepository materialRepository,
                       MaterialCasaRepository materialCasaRepository) {
        this.casaRepository = casaRepository;
        this.modeloCasaRepository = modeloCasaRepository;
        this.materialRepository = materialRepository;
        this.materialCasaRepository = materialCasaRepository;
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
                                ((Number) row[0]).longValue(),
                                (String) row[1],
                                (String) row[2],
                                ((Number) row[3]).intValue(),
                                ((Number) row[4]).intValue()
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
 

      
}
