
package mx.insabit.ValidacionMateriales.DTO;

import java.time.LocalDate;

/**
 *
 * @author Marin
 */
public class AsignacionMaterialRequest {
    
    private Long casaId;
    private Long materialId;
    private int requerido;
        private LocalDate fechaEntrega;

    public Long getCasaId() {
        return casaId;
    }

    public void setCasaId(Long casaId) {
        this.casaId = casaId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public int getRequerido() {
        return requerido;
    }

    public void setRequerido(int requerido) {
        this.requerido = requerido;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    
    
}
