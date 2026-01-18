
package mx.insabit.ValidacionMateriales.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author Marin
 */

@Entity
@Table ( name = "modelo_casa" )
public class ModeloCasa {
    
    @Id
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @Column(name = "total_casas", nullable = false)
    private Integer totalCasas;

    @OneToMany(mappedBy = "modelo", fetch = FetchType.LAZY)
    private List<Casa> casas;

    public ModeloCasa() {
    }

    public ModeloCasa(Long id, String nombre, String descripcion, Integer totalCasas, List<Casa> casas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.totalCasas = totalCasas;
        this.casas = casas;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTotalCasas() {
        return totalCasas;
    }

    public void setTotalCasas(Integer totalCasas) {
        this.totalCasas = totalCasas;
    }

    public List<Casa> getCasas() {
        return casas;
    }

    public void setCasas(List<Casa> casas) {
        this.casas = casas;
    }
    
   
}