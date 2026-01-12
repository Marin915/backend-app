package mx.insabit.ValidacionMateriales.DTO;

import java.util.List;

/**
 *
 * @author Marin
 */

public class CasaDetalleDTO {
    
    private Long id;
    private String nombre;
    private String modelo;
    private Integer progreso;
    private List<MaterialCasaDTO> materiales;

    public CasaDetalleDTO(Long id, String nombre, String modelo, Integer progreso, List<MaterialCasaDTO> materiales) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.progreso = progreso;
        this.materiales = materiales;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getProgreso() {
        return progreso;
    }

    public void setProgreso(Integer progreso) {
        this.progreso = progreso;
    }

    public List<MaterialCasaDTO> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<MaterialCasaDTO> materiales) {
        this.materiales = materiales;
    }
    
    
    
}
