package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion;

import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.Apocalipsis;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorController {

    // Instancia principal que maneja la simulación del apocalipsis
    private Apocalipsis apocalipsis = new Apocalipsis();
    // Indica si hubo un cambio en el estado de pausa/reanudación solicitado
    private Boolean hayCambioEstado = false;
    // Socket del servidor que escucha conexiones entrantes
    private ServerSocket socketServidor;
    // Indica si la simulación está actualmente pausada
    private Boolean estaDetenido = false;

    // Constructor vacío
    public ServidorController(){}

    // Getter para acceder a la instancia Apocalipsis
    public Apocalipsis getApocalipsis() {
        return apocalipsis;
    }

    /**
     * Maneja la comunicación con un cliente conectado.
     * Recibe comandos para pausar o reanudar la simulación.
     * Envía continuamente datos actualizados de la simulación.
     * @param clienteSocket socket del cliente conectado
     */
    public void manejarConexionCliente(Socket clienteSocket) {
        try {
            // Flujo de salida para enviar datos al cliente
            ObjectOutputStream salida = new ObjectOutputStream(clienteSocket.getOutputStream());
            // Flujo de entrada para recibir datos del cliente
            ObjectInputStream entrada = new ObjectInputStream(clienteSocket.getInputStream());

            while (true) {
                // Recibe si la simulación debe detenerse o reanudarse
                estaDetenido = (Boolean) entrada.readObject();
                // Recibe si hubo un cambio en el estado (pausa/reanudación)
                hayCambioEstado = (Boolean) entrada.readObject();

                // Si hubo un cambio, actualiza el estado de la simulación
                if (hayCambioEstado) {
                    if (estaDetenido) {
                        apocalipsis.detener();
                    } else {
                        apocalipsis.reanudar();
                    }
                }

                // Recolectar datos actuales de las diferentes zonas de la simulación
                int comida = apocalipsis.getComedor().getListaHumanosComedor().size();
                int descanso = apocalipsis.getZonaDescanso().getListaHumanosDescansando().size();
                int comun = apocalipsis.getZonaComun().getListaHumanosZonaComun().size();

                // Arrays para las cantidades en cada túnel y zona de riesgo
                int[] ent = new int[4];
                int[] tunel = new int[4];
                int[] regreso = new int[4];
                int[] zombies = new int[4];
                int[] humanosRiesgo = new int[4];

                // Rellenar los arrays con datos de cada uno de los 4 túneles y zonas
                for (int i = 0; i < 4; i++) {
                    ent[i] = apocalipsis.getTunel()[i].getColaEspera().size();
                    tunel[i] = apocalipsis.getTunel()[i].getColaAtravesando().size();
                    regreso[i] = apocalipsis.getTunel()[i].getColaRegreso().size();
                    zombies[i] = apocalipsis.getZonaRiesgo()[i].getListaZombies().size();
                    humanosRiesgo[i] = apocalipsis.getZonaRiesgo()[i].getListaHumanos().size();
                }

                // Obtener lista con los zombies destacados (top zombies)
                ArrayList<String> topZombies = apocalipsis.getClasificacion().generarTopZombies();

                // Enviar los datos recolectados al cliente
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

    /**
     * Inicia el servidor en el puerto 5002.
     * Lanza la simulación en segundo plano.
     * Acepta conexiones de clientes y maneja cada conexión.
     */
    public void iniciarServidor() {
        try {
            socketServidor = new ServerSocket(5002);
            System.out.println("Servidor en ejecución...");
            // Ejecuta la simulación en un hilo aparte
            apocalipsis.ejecutarEnSegundoPlano();

            while (true) {
                // Espera a que un cliente se conecte
                Socket clienteConectado = socketServidor.accept();
                try {
                    // Maneja la conexión con el cliente
                    manejarConexionCliente(clienteConectado);
                }catch (Exception e) {
                    System.out.println("Ha sucedido una excepción en el servidor");
                }
            }
        } catch (IOException e) {
            System.out.println("Fallo al arrancar el servidor");
        }
    }

    // Método principal para iniciar el servidor
    public static void main(String[] args) {
        ServidorController servidorController = new ServidorController();
        servidorController.iniciarServidor();
    }
}
