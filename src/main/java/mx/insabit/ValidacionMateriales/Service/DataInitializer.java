/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.insabit.ValidacionMateriales.Service;

import java.util.List;
import mx.insabit.ValidacionMateriales.Entity.ModeloCasa;
import mx.insabit.ValidacionMateriales.Repository.ModeloCasaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Marin
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ModeloCasaRepository modeloCasaRepository;

    @Override
    public void run(String... args) {

        crearSiNoExiste("Lotes", "Casas tipo lote");
        crearSiNoExiste("Duplex", "Casas tipo dúplex");
        crearSiNoExiste("Renta", "Casas para renta");
    }

    private void crearSiNoExiste(String nombre, String descripcion) {
    List<ModeloCasa> modelos = modeloCasaRepository.findByNombre(nombre);
    if (modelos.isEmpty()) {
        ModeloCasa modelo = new ModeloCasa();
        modelo.setNombre(nombre);
        modelo.setDescripcion(descripcion);
        modelo.setTotalCasas(0);
        modeloCasaRepository.save(modelo);
    }
}
}
    

