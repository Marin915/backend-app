
package mx.insabit.ValidacionMateriales.DTO;

/**
 *
 * @author Marin
 */

public class MaterialCasaDTO {
    
   private Long id;

    private Long materialId;
    private String clave;
    private String nombre;
    private String unidad;

    private Integer requerido;   // cantidadPresupuestada
    private Integer usado;       // salidas

    private String fechaEntrega;

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Integer getRequerido() {
        return requerido;
    }

    public void setRequerido(Integer requerido) {
        this.requerido = requerido;
    }

    public Integer getUsado() {
        return usado;
    }

    public void setUsado(Integer usado) {
        this.usado = usado;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    
    
}
