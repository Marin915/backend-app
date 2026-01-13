

package mx.insabit.ValidacionMateriales.Service;


import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import mx.insabit.ValidacionMateriales.DTO.MaterialResumenDTO;
import mx.insabit.ValidacionMateriales.Entity.Material;
import mx.insabit.ValidacionMateriales.Repository.MaterialRepository;
import mx.insabit.ValidacionMateriales.Repository.MovimientoMaterialRepository;
import mx.insabit.ValidacionMateriales.Repository.PaginacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MovimientoMaterialRepository movimientoRepository;
    private final PaginacionRepository paginacionRepository; // ✅ FINAL

    

    public MaterialServiceImpl(MaterialRepository materialRepository,
                               MovimientoMaterialRepository movimientoRepository,
                               PaginacionRepository paginacionRepository) {
        this.materialRepository = materialRepository;
        this.movimientoRepository = movimientoRepository;
        this.paginacionRepository = paginacionRepository;

    }

    
    /* ===== CRUD QUE YA TENÍAS ===== */

    @Override
    public Material guardar(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public Material actualizar(Long id, Material material) {
        Material existente = obtenerPorId(id);
        existente.setClave(material.getClave());
        existente.setDescripcion(material.getDescripcion());
        existente.setUnidadMedida(material.getUnidadMedida());
        existente.setPrecioUnitario(material.getPrecioUnitario());
        existente.setCategoria(material.getCategoria());
        return materialRepository.save(existente);
    }

    @Override
    public List<Material> listar() {
        return materialRepository.findAll();
    }

    @Override
    public Material obtenerPorId(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));
    }

    public void eliminar(Long id) {
    Material m = materialRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("No existe"));
    m.setActivo(false);
    materialRepository.save(m);
    }

    

    /* ===== RESUMEN DE INVENTARIO ===== */

 @Override
public List<MaterialResumenDTO> listarResumen() {

    List<Material> materiales = materialRepository.findAll();
    List<MaterialResumenDTO> lista = new ArrayList<>();

    for (Material m : materiales) {

       Integer entradas = movimientoRepository.obtenerEntradas(m.getId());
Integer salidas = movimientoRepository.obtenerSalidas(m.getId());

if (entradas == null) entradas = 0;
if (salidas == null) salidas = 0;

Integer stock = entradas - salidas;


        MaterialResumenDTO dto = new MaterialResumenDTO();
        dto.setId(m.getId());
        dto.setClave(m.getClave());
        dto.setDescripcion(m.getDescripcion());
        dto.setUnidadMedida(m.getUnidadMedida());
        dto.setCantidad(stock);
        dto.setEntradas(entradas);
        dto.setSalidas(salidas);
        dto.setPrecioUnitario(m.getPrecioUnitario());
        dto.setCategoria(m.getCategoria());

        lista.add(dto);
    }

    return lista;
}

        @Override
        public Page<Material> obtenerPaginas(int page, int size) {

        if (size == -1) {
            return paginacionRepository.findAll(Pageable.unpaged());
        }

        int adjustedPage = Math.max(page - 1, 0);

        Pageable pageable = PageRequest.of(
                adjustedPage,
                size,
                Sort.by("id").ascending()
        );

        return paginacionRepository.findAll(pageable);
    }




  /*
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
    */
}