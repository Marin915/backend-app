
package mx.insabit.ValidacionMateriales.DTO;

import java.time.LocalDateTime;


public class AsignacionMaterialDTO {
   
    private Long casaId;
    private String casaNombre;
    private Long materialId;
    private String materialClave;
    private int requerido;
    private LocalDateTime fechaAsignacion;

    public Long getCasaId() {
        return casaId;
    }

    public void setCasaId(Long casaId) {
        this.casaId = casaId;
    }

    public String getCasaNombre() {
        return casaNombre;
    }

    public void setCasaNombre(String casaNombre) {
        this.casaNombre = casaNombre;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialClave() {
        return materialClave;
    }

    public void setMaterialClave(String materialClave) {
        this.materialClave = materialClave;
    }

    public int getRequerido() {
        return requerido;
    }

    public void setRequerido(int requerido) {
        this.requerido = requerido;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
    
    
}
