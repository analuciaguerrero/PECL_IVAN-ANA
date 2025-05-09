package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InterfazApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaz.fxml"));
        AnchorPane root = loader.load();

        // Crear el logger para la simulación
        ApocalipsisLogger logger = new ApocalipsisLogger("simulacion_log.txt");

        // Conectar el logger al controlador
        Interfaz controlador = loader.getController();
        controlador.setLogger(logger);  // Pasa el logger al controlador

        Scene scene = new Scene(root);
        primaryStage.setTitle("Simulación Apocalipsis Zombie");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Iniciar la simulación al iniciar la GUI
        Apocalipsis.iniciarSimulacion();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

