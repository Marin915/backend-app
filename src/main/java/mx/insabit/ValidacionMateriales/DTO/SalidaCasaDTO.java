
package mx.insabit.ValidacionMateriales.DTO;

/**
 *
 * @author Marin
 */
public class SalidaCasaDTO {
    
       private Long casaId;
    private Long materialId;
    private int cantidad;

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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
    
}
