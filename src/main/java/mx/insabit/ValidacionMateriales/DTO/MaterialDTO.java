
package mx.insabit.ValidacionMateriales.DTO;

import java.math.BigDecimal;

/**
 *
 * @author Marin
 */

public class MaterialDTO {
    
    private Long id;
    private String clave;
    private String descripcion;
    private String unidadMedida;
    private BigDecimal precioUnitario;
    private String categoria;

    public MaterialDTO() {
    }

    public MaterialDTO(Long id, String clave, String descripcion, String unidadMedida, BigDecimal precioUnitario, String categoria) {
        this.id = id;
        this.clave = clave;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.precioUnitario = precioUnitario;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
}
