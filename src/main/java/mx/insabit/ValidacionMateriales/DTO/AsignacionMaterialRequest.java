
package mx.insabit.ValidacionMateriales.DTO;

/**
 *
 * @author Marin
 */
public class AsignacionMaterialRequest {
    
    private Long casaId;
    private Long materialId;
    private int requerido;

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
    
    
}
