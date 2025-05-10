package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class SimuladorController {

    @FXML private TextField zonaComunes;
    @FXML private TextField tunelEntrada1, tunelEntrada2, tunelEntrada3, tunelEntrada4;
    @FXML private TextField tunel1, tunel2, tunel3, tunel4;
    @FXML private TextField salidaTunel1, salidaTunel2, salidaTunel3, salidaTunel4;
    @FXML private TextField humanosRiesgo1, humanosRiesgo2, humanosRiesgo3, humanosRiesgo4;
    @FXML private TextField humanosZonaDescanso, humanosComedor;
    @FXML private TextField cantidadComida;
    @FXML private TextField zombiesRiesgo1, zombiesRiesgo2, zombiesRiesgo3, zombiesRiesgo4;
    @FXML private TextField eventosLogTextField;

    private Apocalipsis apocalipsis;

    public SimuladorController() throws IOException {}

    @FXML
    public void initialize() throws IOException {
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

        // 1. Configurar logger con los campos de interfaz
        ApocalipsisLogger logger = ApocalipsisLogger.getInstancia();
        logger.setInterfaz(zonas, cantidadComida, zombies, eventosLogTextField);

        // 2. Crear instancia de Apocalipsis con logger ya listo
        apocalipsis = new Apocalipsis(logger);
    }

    @FXML
    protected void crearHumanos() {
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                try {
                    String nombre = "H" + String.format("%04d", i);
                    List<AreaRiesgo> zonas = apocalipsis.getZonas();
                    Refugio refugio = apocalipsis.getRefugio();
                    Humano humano = new Humano(nombre, zonas, refugio, apocalipsis.getLogger());
                    humano.start();

                    apocalipsis.getLogger().log("Humano " + nombre + " inicializado en el refugio.");  // 🔥 AÑADIDO

                    Thread.sleep((int)(Math.random() * 1500) + 500);
                } catch (Exception e) {
                    apocalipsis.getLogger().log("Error al crear los humanos: " + e.getMessage());
                }
            }
        }).start(); // <-- Para no congelar la interfaz
    }

    @FXML
    protected void crearZombie() {
        try {
            List<AreaRiesgo> zonas = apocalipsis.getZonas();
            Zombie zombie = new Zombie("Z0000", zonas, apocalipsis.getLogger());
            zombie.start();

            apocalipsis.getLogger().log("Zombie " + zombie.getZombieId() + " creado en zona de riesgo."); // 🔥 AÑADIDO

        } catch (Exception e) {
            apocalipsis.getLogger().log("Error al crear el zombie: " + e.getMessage());
        }
    }
}
