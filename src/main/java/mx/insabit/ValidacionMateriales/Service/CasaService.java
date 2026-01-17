package mx.insabit.ValidacionMateriales.Service;

import java.util.List;
import java.util.stream.Collectors;
import mx.insabit.ValidacionMateriales.DTO.CasaDetalleDTO;
import mx.insabit.ValidacionMateriales.DTO.MaterialCasaDTO;
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
    
      public MaterialCasa asignarMaterial(
        Long casaId,
        Long materialId,
        int requerido
    ) {
        Casa casa = casaRepository.findById(casaId)
            .orElseThrow(() -> new RuntimeException("Casa no encontrada"));

        Material material = materialRepository.findById(materialId)
            .orElseThrow(() -> new RuntimeException("Material no encontrado"));

        MaterialCasa mc = new MaterialCasa();
        mc.setCasa(casa);
        mc.setMaterial(material);
        mc.setCantidadPresupuestada(requerido);
        mc.setSalidas(0);

        return materialCasaRepository.save(mc);
    }
  
}

