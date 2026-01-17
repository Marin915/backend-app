
package mx.insabit.ValidacionMateriales.DTO;

import java.time.LocalDateTime;

/**
 *
 * @author Marin
 */
public class CasaDTO {
    
    private Long id;
    private String nombre;
    private String ubicacion;
    private Integer progreso;
    private ModeloCasaSimpleDTO modelo; // solo datos básicos

    private LocalDateTime fechaCreacion;

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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getProgreso() {
        return progreso;
    }

    public void setProgreso(Integer progreso) {
        this.progreso = progreso;
    }

    public ModeloCasaSimpleDTO getModelo() {
        return modelo;
    }

    public void setModelo(ModeloCasaSimpleDTO modelo) {
        this.modelo = modelo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    
    
}
