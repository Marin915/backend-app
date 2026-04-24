

package mx.insabit.ValidacionMateriales.Service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import mx.insabit.ValidacionMateriales.DTO.MaterialResumenDTO;
import mx.insabit.ValidacionMateriales.Entity.EstadoHerramienta;
import mx.insabit.ValidacionMateriales.Entity.Herramienta;
import mx.insabit.ValidacionMateriales.Entity.Material;
import mx.insabit.ValidacionMateriales.Entity.MovimientoMaterial;
import mx.insabit.ValidacionMateriales.Entity.Persona;
import mx.insabit.ValidacionMateriales.Entity.RegistroHerramienta;
import mx.insabit.ValidacionMateriales.Entity.TipoMovimiento;
import mx.insabit.ValidacionMateriales.Repository.HerramientaRepository;
import mx.insabit.ValidacionMateriales.Repository.MaterialRepository;
import mx.insabit.ValidacionMateriales.Repository.MovimientoMaterialRepository;
import mx.insabit.ValidacionMateriales.Repository.PaginacionRepository;
import mx.insabit.ValidacionMateriales.Repository.PersonaRepository;
import mx.insabit.ValidacionMateriales.Repository.RegistroHerramientaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MovimientoMaterialRepository movimientoRepository;
    private final PaginacionRepository paginacionRepository;
    private final RegistroHerramientaRepository registroRepository;
    private final PersonaRepository personaRepository;
    private final HerramientaRepository herramientaRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository,
                               MovimientoMaterialRepository movimientoRepository,
                               PaginacionRepository paginacionRepository,
                               RegistroHerramientaRepository registroRepository,
                               PersonaRepository personaRepository,
                               HerramientaRepository herramientaRepository) {

        this.materialRepository = materialRepository;
        this.movimientoRepository = movimientoRepository;
        this.paginacionRepository = paginacionRepository;
        this.registroRepository = registroRepository;
        this.personaRepository = personaRepository;
        this.herramientaRepository = herramientaRepository;
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

    
    public Page<MaterialResumenDTO> obtenerPaginasResumen(int page, int size) {

    Page<Material> materiales = obtenerPaginas(page, size);

    return materiales.map(material -> {

        Integer entradas = movimientoRepository.obtenerEntradas(material.getId());
        Integer salidas  = movimientoRepository.obtenerSalidas(material.getId());

        int ent = entradas != null ? entradas : 0;
        int sal = salidas  != null ? salidas  : 0;

        MaterialResumenDTO dto = new MaterialResumenDTO();
        dto.setId(material.getId());
        dto.setClave(material.getClave());
        dto.setDescripcion(material.getDescripcion());
        dto.setUnidadMedida(material.getUnidadMedida());
        dto.setCantidad(ent - sal);
        dto.setEntradas(ent);
        dto.setSalidas(sal);
        dto.setPrecioUnitario(material.getPrecioUnitario());
        dto.setCategoria(material.getCategoria());

        return dto;
    });
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

       @Transactional
        public void eliminarUltimoMovimiento(Long materialId) {
        MovimientoMaterial ultimo =
            movimientoRepository
                .findTopByMaterialIdOrderByFechaDesc(materialId)
                .orElseThrow(() ->
                new RuntimeException("No hay movimientos para este material"));
            // ✅ Solo eliminamos el movimiento
            movimientoRepository.delete(ultimo);
        }

        @Override
        public int obtenerStock(Long materialId) {
       return movimientoRepository.obtenerStock(materialId);
        }

        /*
         public RegistroHerramienta guardar(RegistroHerramienta r){

        r.setEstado("PRESTADA");
        r.setFechaRegistro(LocalDateTime.now());
        r.setFolio("H-" + System.currentTimeMillis());

        return herramientaRepository.save(r);
    }

    @Override
    public List<RegistroHerramienta> listarDevueltas() {
        return herramientaRepository.findByEstado("DEVUELTA");
    }

    public RegistroHerramienta devolver(Long id) {

        RegistroHerramienta r = herramientaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        r.setEstado("DEVUELTA");
        r.setFechaDevolucion(LocalDate.now());

        return herramientaRepository.save(r);
    }
    */
    /* public RegistroHerramienta guardar(Long personaId,
                                        Long herramientaId,
                                        Integer cantidad){

        Persona persona = personaRepository.findById(personaId).orElseThrow();
        Herramienta herramienta = herramientaRepository.findById(herramientaId).orElseThrow();

        RegistroHerramienta r = new RegistroHerramienta();

        r.setPersona(persona);
        r.setHerramienta(herramienta);
        r.setCantidad(cantidad);
        r.setEstado("PRESTADA");
        r.setFechaPrestamo(LocalDate.now());
        r.setFechaRegistro(LocalDateTime.now());
        r.setFolio("HR-" + System.currentTimeMillis());

        return registroRepository.save(r);
    }
     */
  public RegistroHerramienta prestar(Long personaId,
                                       Long herramientaId,
                                       Integer cantidad){

        Persona persona = personaRepository.findById(personaId).orElseThrow();
        Herramienta herramienta = herramientaRepository.findById(herramientaId).orElseThrow();

        RegistroHerramienta r = new RegistroHerramienta();

        r.setPersona(persona);
        r.setHerramienta(herramienta);
        r.setCantidad(cantidad);
        r.setEstado(EstadoHerramienta.PRESTADA);
        r.setTipoMovimiento(TipoMovimiento.ENTRADA);
        r.setFechaPrestamo(LocalDate.now());
        r.setFechaRegistro(LocalDateTime.now());
        r.setFolio("HR-" + System.currentTimeMillis());

        return registroRepository.save(r);
    }

    public RegistroHerramienta devolver(Long registroId){

        RegistroHerramienta r = registroRepository.findById(registroId)
                .orElseThrow();

        r.setEstado(EstadoHerramienta.DEVUELTA);
        r.setTipoMovimiento(TipoMovimiento.SALIDA);
        r.setFechaDevolucion(LocalDate.now());

        return registroRepository.save(r);
    }

    public List<RegistroHerramienta> listarHerramientas(){
        return registroRepository.findAll();
    }
    
    @Override
    public RegistroHerramienta guardar(Long personaId,
                                   Long herramientaId,
                                   Integer cantidad) {
      return prestar(personaId, herramientaId, cantidad);
}

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
