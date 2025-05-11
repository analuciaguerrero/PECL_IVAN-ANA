package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

public class SimuladorApp extends Application {
    private static final ApocalipsisLogger logger;

    static {
        try {
            logger = ApocalipsisLogger.getInstancia();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private SimuladorController controladorInterfaz;
    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Cargar la interfaz principal directamente
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/uah/matcomp/pcyd/proyectofinal/pecl_ivanana/interfaz.fxml"));
        Parent root = loader.load();
        controladorInterfaz = loader.getController(); // Obtener el controlador

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulador de Apocalipsis");
        primaryStage.setResizable(false);

        // Cerrar con confirmación
        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar salida");
            alert.setHeaderText("¿Estás seguro de salir?");
            alert.setContentText("Perderás el progreso actual de la simulación.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                logger.log("FIN DE LA EJECUCIÓN");
                Platform.exit();
                System.exit(0);
            } else {
                event.consume();
            }
        });

        primaryStage.show();

        // Iniciar la simulación inmediatamente
        iniciarSimulacion();
    }

    // Método que inicia la simulación después de la pantalla de carga
    private void iniciarSimulacion() {
        logger.log("INICIO DE LA EJECUCIÓN");
        // Inicialización de la simulación, solo después de que la pantalla de carga haya terminado
        new Thread(() -> {
            // Crear un zombie
            for (int i = 0; i < 1; i++) {
                Zombie z = new Zombie("Zombie-" + i, controladorInterfaz.getZonas(), logger);
                z.start();
            }

            // Crear los humanos
            for (int i = 1; i < 600; i++) {
                Humano ind = new Humano("Humano-" + i, controladorInterfaz.getZonas(), controladorInterfaz.getRefugio(), logger);
                ind.start();
                try {
                    Thread.sleep((long) (0.5 + (1.5 * random.nextDouble())) * 1000); // medio segundo
                } catch (InterruptedException e) {
                    System.out.println("ERROR | Clase -> SimuladorApp | Método -> iniciarSimulacion");
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args); // Llama al método start() de Application
    }
}

