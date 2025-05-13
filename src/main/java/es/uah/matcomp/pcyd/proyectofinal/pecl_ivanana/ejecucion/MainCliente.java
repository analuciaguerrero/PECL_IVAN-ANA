package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainCliente extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfazCliente.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Cliente Apocalipsis Zombie");
        stage.show();

        // Conexión
        ClienteController controller = loader.getController();
        new Thread(controller::iniciarConexion).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

