package mx.insabit.ValidacionMateriales.Service;

import java.util.List;
import java.util.stream.Collectors;
import mx.insabit.ValidacionMateriales.DTO.CasaDetalleDTO;
import mx.insabit.ValidacionMateriales.DTO.MaterialCasaDTO;
import mx.insabit.ValidacionMateriales.Entity.Casa;
import mx.insabit.ValidacionMateriales.Entity.ModeloCasa;
import mx.insabit.ValidacionMateriales.Repository.CasaRepository;
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

    public CasaService(CasaRepository casaRepository,
                       ModeloCasaRepository modeloCasaRepository) {
        this.casaRepository = casaRepository;
        this.modeloCasaRepository = modeloCasaRepository;
    }

    public CasaDetalleDTO obtenerDetalleCasa(Long casaId) {

        Casa casa = casaRepository.findById(casaId)
                .orElseThrow(() -> new RuntimeException("Casa no encontrada"));

        String nombreModelo = modeloCasaRepository
                .findById(casa.getModelo().getId())
                .map(ModeloCasa::getNombre)
                .orElse("Sin modelo");

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
}
