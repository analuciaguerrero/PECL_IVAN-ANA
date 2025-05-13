package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion;

import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.Apocalipsis;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorController {

    private Apocalipsis apocalipsis = new Apocalipsis();
    private Boolean hayCambioEstado = false;
    private ServerSocket socketServidor;
    private Boolean estaDetenido = false;

    @FXML private TextField HumanosZonaDescanso;
    @FXML private TextField HumanosComedor;
    @FXML private TextField HumanosComida;
    @FXML private TextField HumanosZonaComun;
    @FXML private TextField EntradaTunel1, Tunel1, SalidaTunel1;
    @FXML private TextField EntradaTunel2, Tunel2, SalidaTunel2;
    @FXML private TextField EntradaTunel3, Tunel3, SalidaTunel3;
    @FXML private TextField EntradaTunel4, Tunel4, SalidaTunel4;
    @FXML private TextField HumanosRiesgo1, ZombiesRiesgo1;
    @FXML private TextField HumanosRiesgo2, ZombiesRiesgo2;
    @FXML private TextField HumanosRiesgo3, ZombiesRiesgo3;
    @FXML private TextField HumanosRiesgo4, ZombiesRiesgo4;

    public void iniciarServidor() {
        new Thread(() -> {
            try {
                socketServidor = new ServerSocket(5002);
                System.out.println("Servidor en ejecución...");
                apocalipsis.ejecutarEnSegundoPlano();

                while (true) {
                    Socket clienteConectado = socketServidor.accept();
                    manejarConexionCliente(clienteConectado);
                }
            } catch (IOException e) {
                System.out.println("Fallo al arrancar el servidor");
            }
        }).start();
    }

    public void manejarConexionCliente(Socket clienteSocket) {
        try {
            ObjectOutputStream salida = new ObjectOutputStream(clienteSocket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(clienteSocket.getInputStream());

            while (true) {
                estaDetenido = (Boolean) entrada.readObject();
                hayCambioEstado = (Boolean) entrada.readObject();

                if (hayCambioEstado) {
                    if (estaDetenido) {
                        apocalipsis.detener();
                    } else {
                        apocalipsis.reanudar();
                    }
                }

                // Recolectar datos
                int comida = apocalipsis.getComedor().getListaHumanosComedor().size();
                int descanso = apocalipsis.getZonaDescanso().getListaHumanosDescansando().size();
                int comun = apocalipsis.getZonaComun().getListaHumanosZonaComun().size();

                int[] ent = new int[4];
                int[] tunel = new int[4];
                int[] regreso = new int[4];
                int[] zombies = new int[4];
                int[] humanosRiesgo = new int[4];

                for (int i = 0; i < 4; i++) {
                    ent[i] = apocalipsis.getTunel()[i].getColaEspera().size();
                    tunel[i] = apocalipsis.getTunel()[i].getColaAtravesando().size();
                    regreso[i] = apocalipsis.getTunel()[i].getColaRegreso().size();
                    zombies[i] = apocalipsis.getZonaRiesgo()[i].getListaZombies().size();
                    humanosRiesgo[i] = apocalipsis.getZonaRiesgo()[i].getListaHumanos().size();
                }

                // Enviar datos al cliente
                salida.writeObject(comida);
                salida.writeObject(comun);
                salida.writeObject(descanso);
                for (int i = 0; i < 4; i++) salida.writeObject(ent[i]);
                for (int i = 0; i < 4; i++) salida.writeObject(tunel[i]);
                for (int i = 0; i < 4; i++) salida.writeObject(regreso[i]);
                for (int i = 0; i < 4; i++) salida.writeObject(zombies[i]);
                for (int i = 0; i < 4; i++) salida.writeObject(humanosRiesgo[i]);

                ArrayList<String> topZombies = apocalipsis.getClasificacion().generarTopZombies();
                salida.writeObject(topZombies);
                salida.flush();
                salida.reset();

                Platform.runLater(() -> {
                    HumanosComedor.setText(String.valueOf(comida));
                    HumanosZonaDescanso.setText(String.valueOf(descanso));
                    HumanosZonaComun.setText(String.valueOf(comun));
                    HumanosComida.setText(String.valueOf(comida));

                    EntradaTunel1.setText(String.valueOf(ent[0]));
                    EntradaTunel2.setText(String.valueOf(ent[1]));
                    EntradaTunel3.setText(String.valueOf(ent[2]));
                    EntradaTunel4.setText(String.valueOf(ent[3]));

                    Tunel1.setText(String.valueOf(tunel[0]));
                    Tunel2.setText(String.valueOf(tunel[1]));
                    Tunel3.setText(String.valueOf(tunel[2]));
                    Tunel4.setText(String.valueOf(tunel[3]));

                    SalidaTunel1.setText(String.valueOf(regreso[0]));
                    SalidaTunel2.setText(String.valueOf(regreso[1]));
                    SalidaTunel3.setText(String.valueOf(regreso[2]));
                    SalidaTunel4.setText(String.valueOf(regreso[3]));

                    HumanosRiesgo1.setText(String.valueOf(humanosRiesgo[0]));
                    HumanosRiesgo2.setText(String.valueOf(humanosRiesgo[1]));
                    HumanosRiesgo3.setText(String.valueOf(humanosRiesgo[2]));
                    HumanosRiesgo4.setText(String.valueOf(humanosRiesgo[3]));

                    ZombiesRiesgo1.setText(String.valueOf(zombies[0]));
                    ZombiesRiesgo2.setText(String.valueOf(zombies[1]));
                    ZombiesRiesgo3.setText(String.valueOf(zombies[2]));
                    ZombiesRiesgo4.setText(String.valueOf(zombies[3]));
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Fallo en la comunicación con el cliente");
            e.printStackTrace();
        }
    }
}
