package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion;

import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.Apocalipsis;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorController {

    private Apocalipsis apocalipsis = new Apocalipsis();
    private Boolean hayCambioEstado = false;
    private ServerSocket socketServidor;
    private Boolean estaDetenido = false;

    public ServidorController(){}

    public Apocalipsis getApocalipsis() {
        return apocalipsis;
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

                ArrayList<String> topZombies = apocalipsis.getClasificacion().generarTopZombies();

                // Enviar datos al cliente
                salida.writeObject(comida);
                salida.writeObject(comun);
                salida.writeObject(descanso);
                for (int i = 0; i < 4; i++) salida.writeObject(ent[i]);
                for (int i = 0; i < 4; i++) salida.writeObject(tunel[i]);
                for (int i = 0; i < 4; i++) salida.writeObject(regreso[i]);
                for (int i = 0; i < 4; i++) salida.writeObject(zombies[i]);
                for (int i = 0; i < 4; i++) salida.writeObject(humanosRiesgo[i]);

                salida.writeObject(topZombies);
                salida.flush();
                salida.reset();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Fallo en la comunicación con el cliente");
            throw new RuntimeException(e);
        }
    }

    public void iniciarServidor() {
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
    }

    public static void main(String[] args) {
        ServidorController servidorController = new ServidorController();
        servidorController.iniciarServidor();
    }
}
