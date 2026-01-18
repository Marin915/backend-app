/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.insabit.ValidacionMateriales.DTO;

import java.time.LocalDate;

/**
 *
 * @author Marin
 */
public class MaterialEntregaDTO {
    
     private Long id;
    private String clave;
    private String descripcion;
    private String unidad;

    private Integer cantidadPresupuestada;
    private Integer cantidadEntregada;
    private Integer salidasPorEntregar;

    private LocalDate fechaEntrega;

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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Integer getCantidadPresupuestada() {
        return cantidadPresupuestada;
    }

    public void setCantidadPresupuestada(Integer cantidadPresupuestada) {
        this.cantidadPresupuestada = cantidadPresupuestada;
    }

    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Integer cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public Integer getSalidasPorEntregar() {
        return salidasPorEntregar;
    }

    public void setSalidasPorEntregar(Integer salidasPorEntregar) {
        this.salidasPorEntregar = salidasPorEntregar;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    
    
}
