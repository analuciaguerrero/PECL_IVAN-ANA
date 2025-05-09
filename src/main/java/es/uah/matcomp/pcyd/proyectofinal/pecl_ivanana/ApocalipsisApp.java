package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApocalipsisApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoa5der loader = new FXMLLoader(getClass().getResource("mapa.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Simulación Apocalipsis Zombi");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Lanzamos la simulación al iniciar la GUI
        Apocalipsis.iniciarSimulacion();  // ← Llama a tu lógica
    }

    public static void main(String[] args) {
        launch(args);
    }
}

