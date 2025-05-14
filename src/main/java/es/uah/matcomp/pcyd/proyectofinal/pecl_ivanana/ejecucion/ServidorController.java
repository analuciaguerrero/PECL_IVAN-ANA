package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion;

import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.Apocalipsis;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServidorController {

    private final Apocalipsis apocalipsis = new Apocalipsis();
    private ServerSocket socketServidor;
    private Boolean estaDetenido = false;
    private final Lock lock = new ReentrantLock(); // Lock para evitar condiciones de carrera

    public ServidorController() {}

    public Apocalipsis getApocalipsis() {
        return apocalipsis;
    }

    private void manejarConexionCliente(Socket clienteSocket) {
        try (
                ObjectOutputStream salida = new ObjectOutputStream(clienteSocket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(clienteSocket.getInputStream())
        ) {
            while (true) {
                // Leer los datos enviados por el cliente
                estaDetenido = (Boolean) entrada.readObject();
                Boolean hayCambioEstado = (Boolean) entrada.readObject();

                if (hayCambioEstado) {
                    // Cambiar el estado del apocalipsis si es necesario
                    if (estaDetenido) {
                        detenerSimulacion();
                    } else {
                        reanudarSimulacion();
                    }
                }

                // Recolectar datos de la simulación
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

                // Enviar los datos al cliente
                enviarDatosAlCliente(salida, comida, comun, descanso, ent, tunel, regreso, zombies, humanosRiesgo, topZombies);

                // Controlar la frecuencia de las actualizaciones (pausa de 500 ms)
                Thread.sleep(500);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            System.out.println("Fallo en la comunicación con el cliente: " + e.getMessage());
            cerrarConexion(clienteSocket);
        }
    }

    private void enviarDatosAlCliente(ObjectOutputStream salida, int comida, int comun, int descanso, int[] ent,
                                      int[] tunel, int[] regreso, int[] zombies, int[] humanosRiesgo, ArrayList<String> topZombies) throws IOException {
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
    }

    private void cerrarConexion(Socket clienteSocket) {
        try {
            clienteSocket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión con el cliente");
        }
    }

    private void detenerSimulacion() {
        lock.lock();
        try {
            apocalipsis.detener();
        } finally {
            lock.unlock();
        }
    }

    private void reanudarSimulacion() {
        lock.lock();
        try {
            apocalipsis.reanudar();
        } finally {
            lock.unlock();
        }
    }

    public void iniciarServidor() {
        try {
            socketServidor = new ServerSocket(5002);
            System.out.println("Servidor en ejecución...");
            apocalipsis.ejecutarEnSegundoPlano();

            while (true) {
                Socket clienteConectado = socketServidor.accept();
                System.out.println("Cliente conectado: " + clienteConectado.getInetAddress().getHostAddress());
                try {
                    manejarConexionCliente(clienteConectado);
                } catch (Exception e) {
                    System.out.println("Ha sucedido una excepción en el servidor: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Fallo al arrancar el servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ServidorController servidorController = new ServidorController();
        servidorController.iniciarServidor();
    }
}
