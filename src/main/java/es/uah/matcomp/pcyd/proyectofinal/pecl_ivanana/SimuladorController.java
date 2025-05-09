package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class SimuladorController {

    @FXML
    private TextField zonaComunes;
    @FXML
    private TextField tunelEntrada1;
    @FXML
    private TextField tunelEntrada2;
    @FXML
    private TextField tunelEntrada3;
    @FXML
    private TextField tunelEntrada4;
    @FXML
    private TextField tunel1;
    @FXML
    private TextField tunel2;
    @FXML
    private TextField tunel3;
    @FXML
    private TextField tunel4;
    @FXML
    private TextField salidaTunel1;
    @FXML
    private TextField salidaTunel2;
    @FXML
    private TextField salidaTunel3;
    @FXML
    private TextField salidaTunel4;
    @FXML
    private TextField humanosRiesgo1;
    @FXML
    private TextField humanosRiesgo2;
    @FXML
    private TextField humanosRiesgo3;
    @FXML
    private TextField humanosRiesgo4;
    @FXML
    private TextField humanosZonaDescanso;
    @FXML
    private TextField humanosComedor;
    @FXML
    private TextField cantidadComida;
    @FXML
    private TextField zombiesRiesgo1;
    @FXML
    private TextField zombiesRiesgo2;
    @FXML
    private TextField zombiesRiesgo3;
    @FXML
    private TextField zombiesRiesgo4;

    private Apocalipsis apocalipsis;
    private ApocalipsisLogger apocalipsisLogs = ApocalipsisLogger.getInstancia();

    public SimuladorController() throws IOException {
    }

    @FXML
    public void initialize() throws IOException {
        // Se inicializan los TextFields que se usan para mostrar la información
        TextField[] zonas = {
                zonaComunes, humanosZonaDescanso, humanosComedor,
                tunelEntrada1, tunelEntrada2, tunelEntrada3, tunelEntrada4,
                tunel1, tunel2, tunel3, tunel4,
                salidaTunel1, salidaTunel2, salidaTunel3, salidaTunel4,
                humanosRiesgo1, humanosRiesgo2, humanosRiesgo3, humanosRiesgo4
        };

        TextField[] zombies = {
                zombiesRiesgo1, zombiesRiesgo2, zombiesRiesgo3, zombiesRiesgo4
        };

        // Crear la instancia del "Apocalipsis", pasándole los TextFields y objetos necesarios
        apocalipsis = new Apocalipsis(zonas, cantidadComida, zombies);

        // Asignamos la interfaz al logger para que registre los eventos en los TextFields
        apocalipsisLogs.setInterfaz(zonas, cantidadComida, zombies);

        // Crear el primer zombie
        crearZombie();

        // Crear humanos en un hilo separado
        new Thread(this::crearHumanos).start();
    }

    @FXML
    protected void crearHumanos() {
        for (int i = 0; i < 30; i++) {
            try {
                String nombre = "H" + String.format("%04d", i);
                List<AreaRiesgo> zonas = apocalipsis.getZonas();
                Refugio refugio = apocalipsis.getRefugio();
                Humano humano = new Humano(nombre, zonas, refugio, apocalipsisLogs);
                humano.start();
                Thread.sleep((int)(Math.random() * 1500) + 500);
            } catch (Exception e) {
                apocalipsisLogs.log("Error al crear los humanos: " + e.getMessage());
            }
        }
    }


    @FXML
    protected void crearZombie() {
        try {
            List<AreaRiesgo> zonas = apocalipsis.getZonas();
            Zombie zombie = new Zombie("Z0000", zonas, apocalipsisLogs);
            zombie.start();
        } catch (Exception e) {
            apocalipsisLogs.log("Error al crear el zombie: " + e.getMessage());
        }
    }

}
