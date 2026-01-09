
package mx.insabit.ValidacionMateriales.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Marin
 */

@Entity 
@Table ( name = "modelo_material")
public class ModeloMaterial {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    private ModeloCasa modelo;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false)
    private Integer cantidad;

    public ModeloMaterial() {
    }

    public ModeloMaterial(Long id, ModeloCasa modelo, Material material, Integer cantidad) {
        this.id = id;
        this.modelo = modelo;
        this.material = material;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModeloCasa getModelo() {
        return modelo;
    }

    public void setModelo(ModeloCasa modelo) {
        this.modelo = modelo;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
}
