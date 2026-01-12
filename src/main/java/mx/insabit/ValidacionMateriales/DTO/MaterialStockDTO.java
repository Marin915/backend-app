package mx.insabit.ValidacionMateriales.DTO;

import java.math.BigDecimal;

public class MaterialStockDTO {
    
    private Long id;
    private String clave;
    private String descripcion;
    private String unidadMedida;
    private Integer entradas;
    private Integer salidas;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private String categoria;

    public MaterialStockDTO() {
    }
    
    public MaterialStockDTO(Long id, String clave, String descripcion, String unidadMedida, Integer entradas, Integer salidas, Integer cantidad, BigDecimal precioUnitario, String categoria) {
        this.id = id;
        this.clave = clave;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.entradas = entradas;
        this.salidas = salidas;
        this.cantidad = cantidad;
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

    public Integer getEntradas() {
        return entradas;
    }

    public void setEntradas(Integer entradas) {
        this.entradas = entradas;
    }

    public Integer getSalidas() {
        return salidas;
    }

    public void setSalidas(Integer salidas) {
        this.salidas = salidas;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
