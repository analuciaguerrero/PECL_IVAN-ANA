package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SimuladorController {

    @FXML private Text zonaComunes;
    @FXML private Text tunelEntrada1, tunelEntrada2, tunelEntrada3, tunelEntrada4;
    @FXML private Text tunel1, tunel2, tunel3, tunel4;
    @FXML private Text salidaTunel1, salidaTunel2, salidaTunel3, salidaTunel4;
    @FXML private Text humanosRiesgo1, humanosRiesgo2, humanosRiesgo3, humanosRiesgo4;
    @FXML private Text humanosZonaDescanso, humanosComedor;
    @FXML private Text cantidadComida;
    @FXML private Text zombiesRiesgo1, zombiesRiesgo2, zombiesRiesgo3, zombiesRiesgo4;
    @FXML private Text eventosLogTextField;

    private Apocalipsis apocalipsis;
    // Estructuras auxiliares
    private GestorHilos zonaComunLista, descansoLista, comedorLista;
    private GestorHilos[] entradasTunel, tunelesMedios, salidasTunel;
    private AreaRiesgo[] zonasRiesgoHumanos;
    private GestorHilos[] zonasZombies;
    public SimuladorController() throws IOException {}

    @FXML
    public void initialize() throws IOException {
        // Inicializar campos como listas de hilos
        zonaComunLista = new GestorHilos(zonaComunes);
        descansoLista = new GestorHilos(humanosZonaDescanso);
        comedorLista = new GestorHilos(humanosComedor);

        entradasTunel = new GestorHilos[] {
                new GestorHilos(tunelEntrada1),
                new GestorHilos(tunelEntrada2),
                new GestorHilos(tunelEntrada3),
                new GestorHilos(tunelEntrada4)
        };

        tunelesMedios = new GestorHilos[] {
                new GestorHilos(tunel1),
                new GestorHilos(tunel2),
                new GestorHilos(tunel3),
                new GestorHilos(tunel4)
        };

        salidasTunel = new GestorHilos[] {
                new GestorHilos(salidaTunel1),
                new GestorHilos(salidaTunel2),
                new GestorHilos(salidaTunel3),
                new GestorHilos(salidaTunel4)
        };

        zonasRiesgoHumanos = new AreaRiesgo[] {
                new AreaRiesgo(new GestorHilos(humanosRiesgo1)),
                new AreaRiesgo(new GestorHilos(humanosRiesgo2)),
                new AreaRiesgo(new GestorHilos(humanosRiesgo3)),
                new AreaRiesgo(new GestorHilos(humanosRiesgo4))
        };

        zonasZombies = new GestorHilos[] {
                new GestorHilos(zombiesRiesgo1),
                new GestorHilos(zombiesRiesgo2),
                new GestorHilos(zombiesRiesgo3),
                new GestorHilos(zombiesRiesgo4)
        };
        Text[] zonasZombies2 = new Text[] {
                zombiesRiesgo1, zombiesRiesgo2, zombiesRiesgo3, zombiesRiesgo4
        };
        // Configuración del logger
        ApocalipsisLogger logger = ApocalipsisLogger.getInstancia();
        Text[] zonas = {
                zonaComunes, humanosZonaDescanso, humanosComedor,
                tunelEntrada1, tunelEntrada2, tunelEntrada3, tunelEntrada4,
                tunel1, tunel2, tunel3, tunel4,
                salidaTunel1, salidaTunel2, salidaTunel3, salidaTunel4,
                humanosRiesgo1, humanosRiesgo2, humanosRiesgo3, humanosRiesgo4
        };

        logger.setInterfaz(zonas, cantidadComida, zonasZombies2, eventosLogTextField);

        // Inicializar Apocalipsis
        apocalipsis = new Apocalipsis(logger, zonaComunLista, descansoLista, comedorLista, entradasTunel, tunelesMedios, salidasTunel, zonasRiesgoHumanos, zonasZombies);
    }
    public List<AreaRiesgo> getZonas() {
        return Arrays.asList(zonasRiesgoHumanos);
    }

    public Refugio getRefugio() {
        return apocalipsis.getRefugio();
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
                    apocalipsis.getLogger().log("Humano " + nombre + " inicializado en el refugio.");
                    Thread.sleep((int)(Math.random() * 1500) + 500);
                } catch (Exception e) {
                    apocalipsis.getLogger().log("Error al crear los humanos: " + e.getMessage());
                }
            }
        }).start();
    }

    @FXML
    protected void crearZombie() {
        try {
            List<AreaRiesgo> zonas = apocalipsis.getZonas();
            Zombie zombie = new Zombie("Z" + System.currentTimeMillis(), zonas, apocalipsis.getLogger());
            zombie.start();
            apocalipsis.getLogger().log("Zombie " + zombie.getZombieId() + " creado en zona de riesgo.");
        } catch (Exception e) {
            apocalipsis.getLogger().log("Error al crear el zombie: " + e.getMessage());
        }
    }
}
