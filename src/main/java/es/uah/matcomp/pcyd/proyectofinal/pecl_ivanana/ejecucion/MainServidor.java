package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion;

import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.Apocalipsis;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainServidor extends Application {

    private Apocalipsis apocalipsis;

    public MainServidor(Apocalipsis apocalipsis) {
        this.apocalipsis = apocalipsis;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfazServidor.fxml"));
        Parent root = loader.load();

        ServidorController controller = loader.getController();
        controller.iniciarServidor();  // Inicia el servidor desde el controlador

        primaryStage.setTitle("Servidor Apocalipsis Zombie");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
