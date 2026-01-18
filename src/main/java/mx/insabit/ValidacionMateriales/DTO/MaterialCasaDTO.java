
package mx.insabit.ValidacionMateriales.DTO;

/**
 *
 * @author Marin
 */

public class MaterialCasaDTO {
    
      private Long id;
    private Long materialId;  // 👈 ID real del material
    private String clave;     // 👈 clave del material
    private String nombre;
    private String unidad;
    private Integer requerido;
    private Integer usado;
    private String fechaEntrega;  

    public MaterialCasaDTO(Long id, Long materialId, String clave, String nombre, String unidad, Integer requerido, Integer usado, String fechaEntrega) {
        this.id = id;
        this.materialId = materialId;
        this.clave = clave;
        this.nombre = nombre;
        this.unidad = unidad;
        this.requerido = requerido;
        this.usado = usado;
        this.fechaEntrega = fechaEntrega;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

   
    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }



    public MaterialCasaDTO() {
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
    
    
}
