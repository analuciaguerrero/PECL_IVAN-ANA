package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion;

import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.InterfazCliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ClienteController {
    // Instancia de la interfaz gráfica de cliente, que se pasa esta clase para actualizarla
    private InterfazCliente interfazCliente = new InterfazCliente(this);

    // Variables que representan el estado de distintas zonas y túneles
    private Integer zonaComun;
    private Integer zonaDescanso;
    private Integer comedor;

    private Integer enColaTunel1, enColaTunel2, enColaTunel3, enColaTunel4; // Número esperando para cruzar cada túnel
    private Integer cruzandoTunel1, cruzandoTunel2, cruzandoTunel3, cruzandoTunel4; // Número cruzando túnel
    private Integer regresandoTunel1, regresandoTunel2, regresandoTunel3, regresandoTunel4; // Número regresando de túnel

    private Integer zombieZonaRiesgo1, zombieZonaRiesgo2, zombieZonaRiesgo3, zombieZonaRiesgo4; // Zombies en zonas de riesgo
    private Integer zonaRiesgo1, zonaRiesgo2, zonaRiesgo3, zonaRiesgo4; // Humanos en zonas de riesgo

    // Lista con los zombis más destacados (top killers)
    private ArrayList<String> topZombies;

    // Flag para detener o reanudar la simulación
    private Boolean detener = false;
    // Flag para detectar si ha habido cambio en el estado
    private Boolean hayCambio = false;

    // Monitor para sincronizar acceso a variables críticas entre hilos
    private final Object monitor = new Object();

    // Constructor que hace visible la interfaz de cliente
    public ClienteController() {
        interfazCliente.setVisible(true);
    }

    // Métodos getter para acceder a las variables privadas (estado de la simulación)
    public Integer getZonaComun() { return zonaComun; }
    public Integer getZonaDescanso() { return zonaDescanso; }
    public Integer getComedor() { return comedor; }

    public Integer getEnColaTunel1() { return enColaTunel1; }
    public Integer getEnColaTunel2() { return enColaTunel2; }
    public Integer getEnColaTunel3() { return enColaTunel3; }
    public Integer getEnColaTunel4() { return enColaTunel4; }

    public Integer getCruzandoTunel1() { return cruzandoTunel1; }
    public Integer getCruzandoTunel2() { return cruzandoTunel2; }
    public Integer getCruzandoTunel3() { return cruzandoTunel3; }
    public Integer getCruzandoTunel4() { return cruzandoTunel4; }

    public Integer getRegresandoTunel1() { return regresandoTunel1; }
    public Integer getRegresandoTunel2() { return regresandoTunel2; }
    public Integer getRegresandoTunel3() { return regresandoTunel3; }
    public Integer getRegresandoTunel4() { return regresandoTunel4; }

    public Integer getZombieZonaRiesgo1() { return zombieZonaRiesgo1; }
    public Integer getZombieZonaRiesgo2() { return zombieZonaRiesgo2; }
    public Integer getZombieZonaRiesgo3() { return zombieZonaRiesgo3; }
    public Integer getZombieZonaRiesgo4() { return zombieZonaRiesgo4; }

    public Integer getZonaRiesgo1() { return zonaRiesgo1; }
    public Integer getZonaRiesgo2() { return zonaRiesgo2; }
    public Integer getZonaRiesgo3() { return zonaRiesgo3; }
    public Integer getZonaRiesgo4() { return zonaRiesgo4; }

    public ArrayList<String> getTopZombies() { return topZombies; }

    public Boolean isDetener() { return detener; }

    /**
     * Cambia el estado del flag detener, sincronizado para evitar condiciones de carrera.
     * Si la simulación se reanuda (detener = false), notifica a todos los hilos bloqueados.
     */
    public void alternarEstadoDetener() {
        synchronized (monitor) {
            detener = !detener;
            if (!detener) {
                monitor.notifyAll();
            }
        }
    }

    public Boolean isCambio() {
        return hayCambio;
    }

    public void activarCambio() {
        hayCambio = true;
    }

    public void desactivarCambio() {
        hayCambio = false;
    }

    /**
     * Método que inicia la conexión con el servidor.
     * En un bucle infinito, envía el estado de la simulación y recibe los estados actualizados de las distintas zonas,
     * además de la lista de zombis destacados, para después refrescar la interfaz gráfica.
     */
    public void iniciarConexion() {
        Socket conexion;
        try {
            // Conexión con el servidor local en puerto 5002
            conexion = new Socket(InetAddress.getLocalHost(), 5002);

            // Flujos de entrada y salida para objetos serializados
            ObjectInputStream entrada = new ObjectInputStream(conexion.getInputStream());
            ObjectOutputStream salida = new ObjectOutputStream(conexion.getOutputStream());

            while (true) {
                // Enviar flag de detener al servidor
                salida.writeObject(detener);

                // Enviar flag de cambio sólo si hay cambio, y luego desactivar el flag
                if(hayCambio) {
                    salida.writeObject(hayCambio);
                    desactivarCambio();
                } else {
                    salida.writeObject(hayCambio);
                }
                salida.flush();
                salida.reset();

                // Recibir estados de refugio
                comedor = (Integer) entrada.readObject();
                zonaComun = (Integer) entrada.readObject();
                zonaDescanso = (Integer) entrada.readObject();

                // Recibir estados de túneles (colas, cruzando, regresando)
                enColaTunel1 = (Integer) entrada.readObject();
                enColaTunel2 = (Integer) entrada.readObject();
                enColaTunel3 = (Integer) entrada.readObject();
                enColaTunel4 = (Integer) entrada.readObject();

                cruzandoTunel1 = (Integer) entrada.readObject();
                cruzandoTunel2 = (Integer) entrada.readObject();
                cruzandoTunel3 = (Integer) entrada.readObject();
                cruzandoTunel4 = (Integer) entrada.readObject();

                regresandoTunel1 = (Integer) entrada.readObject();
                regresandoTunel2 = (Integer) entrada.readObject();
                regresandoTunel3 = (Integer) entrada.readObject();
                regresandoTunel4 = (Integer) entrada.readObject();

                // Recibir número de zombis en zonas de riesgo
                zombieZonaRiesgo1 = (Integer) entrada.readObject();
                zombieZonaRiesgo2 = (Integer) entrada.readObject();
                zombieZonaRiesgo3 = (Integer) entrada.readObject();
                zombieZonaRiesgo4 = (Integer) entrada.readObject();

                // Recibir número de humanos en zonas de riesgo
                zonaRiesgo1 = (Integer) entrada.readObject();
                zonaRiesgo2 = (Integer) entrada.readObject();
                zonaRiesgo3 = (Integer) entrada.readObject();
                zonaRiesgo4 = (Integer) entrada.readObject();

                // Recibir lista de zombis destacados
                topZombies = (ArrayList<String>) entrada.readObject();

                // Actualizar la interfaz gráfica con los nuevos datos
                interfazCliente.refrescarInterfaz();
            }

        } catch (IOException | NullPointerException | ClassNotFoundException e) {
            System.out.println("Error en la conexión");
            throw new RuntimeException(e);
        }
    }

    /**
     * Método principal que lanza la aplicación cliente.
     * Crea una instancia de ClienteController y lanza la conexión en un hilo aparte.
     */
    public static void main(String args[]) {
        ClienteController clienteController = new ClienteController();
        new Thread(clienteController::iniciarConexion).start();
    }
}
