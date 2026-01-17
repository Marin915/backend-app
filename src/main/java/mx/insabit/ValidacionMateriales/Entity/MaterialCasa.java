
package mx.insabit.ValidacionMateriales.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;


@Entity
@Table(name = "material_casa", uniqueConstraints = { @UniqueConstraint(columnNames = {"casa_id", "material_id"})})
public class MaterialCasa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "casa_id")
    private Casa casa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "cantidad_presupuestada", nullable = false)
    private Integer cantidadPresupuestada;

    @Column(nullable = false)
    private Integer salidas = 0;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    public MaterialCasa() {
    }

    public MaterialCasa(Long id, Casa casa, Material material, Integer cantidadPresupuestada, LocalDate fechaEntrega) {
        this.id = id;
        this.casa = casa;
        this.material = material;
        this.cantidadPresupuestada = cantidadPresupuestada;
        this.fechaEntrega = fechaEntrega;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getCantidadPresupuestada() {
        return cantidadPresupuestada;
    }

    public void setCantidadPresupuestada(Integer cantidadPresupuestada) {
        this.cantidadPresupuestada = cantidadPresupuestada;
    }

    public Integer getSalidas() {
        return salidas;
    }

    public void setSalidas(Integer salidas) {
        this.salidas = salidas;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }


    
    
}

