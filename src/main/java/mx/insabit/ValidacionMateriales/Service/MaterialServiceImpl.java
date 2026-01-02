

package mx.insabit.ValidacionMateriales.Service;


import java.util.List;
import mx.insabit.ValidacionMateriales.DTO.MaterialesDTO;
import mx.insabit.ValidacionMateriales.Excepciones.NotFoundException;
import mx.insabit.ValidacionMateriales.MapStruc.MaterialMapper;
import mx.insabit.ValidacionMateriales.MaterialesEntity;
import mx.insabit.ValidacionMateriales.Repository.MaterialesRepository;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {

  private final MaterialesRepository repo;
    private final MaterialMapper mapper;

    public MaterialServiceImpl(MaterialesRepository repo, MaterialMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public MaterialesDTO crear(MaterialesDTO dto) {
        MaterialesEntity material = mapper.toEntity(dto);
        return mapper.toDTO(repo.save(material));
    }

    @Override
    public MaterialesDTO obtener(Long id) {
        MaterialesEntity material = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Material no encontrado"));
        return mapper.toDTO(material);
    }

    @Override
    public List<MaterialesDTO> listar() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public MaterialesDTO actualizar(Long id, MaterialesDTO dto) {

        MaterialesEntity material = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Material no encontrado"));

        material.setNombre(dto.getNombre());
        material.setDescripcion(dto.getDescripcion());
        material.setCantidad(dto.getCantidad());
        material.setUnidadMedida(dto.getUnidadMedida());
        material.setCategoria(dto.getCategoria());

        return mapper.toDTO(repo.save(material));
    }

    @Override
    public void eliminar(Long id) {
        if (!repo.existsById(id))
            throw new NotFoundException("Material no existe");

        repo.deleteById(id);
    }
}