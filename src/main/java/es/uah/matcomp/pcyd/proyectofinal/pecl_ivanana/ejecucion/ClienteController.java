package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion;

import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.InterfazCliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ClienteController {
    private InterfazCliente interfazCliente = new InterfazCliente(this);
    private Integer zonaComun;
    private Integer zonaDescanso;
    private Integer comedor;
    private Integer enColaTunel1, enColaTunel2, enColaTunel3, enColaTunel4;
    private Integer cruzandoTunel1, cruzandoTunel2, cruzandoTunel3, cruzandoTunel4;
    private Integer regresandoTunel1, regresandoTunel2, regresandoTunel3, regresandoTunel4;
    private Integer zombieZonaRiesgo1, zombieZonaRiesgo2, zombieZonaRiesgo3, zombieZonaRiesgo4;
    private Integer zonaRiesgo1, zonaRiesgo2, zonaRiesgo3, zonaRiesgo4;
    private ArrayList<String> topZombies;
    private Boolean detener = false;
    private Boolean hayCambio = false;

    public ClienteController() {
        interfazCliente.setVisible(true);
    }

    public Integer getZonaComun() {
        return zonaComun;
    }
    public Integer getZonaDescanso() {
        return zonaDescanso;
    }
    public Integer getComedor() {
        return comedor;
    }

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

    public Integer getZombieZonaRiesgo1() {
        return zombieZonaRiesgo1;
    }
    public Integer getZombieZonaRiesgo2() {
        return zombieZonaRiesgo2;
    }
    public Integer getZombieZonaRiesgo3() {
        return zombieZonaRiesgo3;
    }
    public Integer getZombieZonaRiesgo4() {
        return zombieZonaRiesgo4;
    }

    public Integer getZonaRiesgo1() {
        return zonaRiesgo1;
    }
    public Integer getZonaRiesgo2() {
        return zonaRiesgo2;
    }
    public Integer getZonaRiesgo3() {
        return zonaRiesgo3;
    }
    public Integer getZonaRiesgo4() {
        return zonaRiesgo4;
    }

    public ArrayList<String> getTopZombies() {
        return topZombies;
    }

    public Boolean isDetener() {
        return detener;
    }

    public void alternarEstadoDetener() {
        detener = !detener;
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

    public void iniciarConexion() {
        Socket conexion;
        try {
            conexion = new Socket(InetAddress.getLocalHost(), 5002); // Conectamos al servidor
            ObjectInputStream entrada = new ObjectInputStream(conexion.getInputStream());
            ObjectOutputStream salida = new ObjectOutputStream(conexion.getOutputStream());
            while (true) {
                salida.writeObject(detener);
                if(hayCambio) {
                    salida.writeObject(hayCambio);
                    desactivarCambio();
                }
                else{
                    salida.writeObject(hayCambio);
                }
                salida.flush();
                salida.reset();

                // Refugio
                comedor = (Integer) entrada.readObject();
                zonaComun = (Integer) entrada.readObject();
                zonaDescanso = (Integer) entrada.readObject();

                // Esperando para entrar al túnel
                enColaTunel1 = (Integer) entrada.readObject();
                enColaTunel2 = (Integer) entrada.readObject();
                enColaTunel3 = (Integer) entrada.readObject();
                enColaTunel4 = (Integer) entrada.readObject();

                // Cruzando el túnel
                cruzandoTunel1 = (Integer) entrada.readObject();
                cruzandoTunel2 = (Integer) entrada.readObject();
                cruzandoTunel3 = (Integer) entrada.readObject();
                cruzandoTunel4 = (Integer) entrada.readObject();

                // Regresando del túnel
                regresandoTunel1 = (Integer) entrada.readObject();
                regresandoTunel2 = (Integer) entrada.readObject();
                regresandoTunel3 = (Integer) entrada.readObject();
                regresandoTunel4 = (Integer) entrada.readObject();

                // Zombies en las zonas de riesgo
                zombieZonaRiesgo1 = (Integer) entrada.readObject();
                zombieZonaRiesgo2 = (Integer) entrada.readObject();
                zombieZonaRiesgo3 = (Integer) entrada.readObject();
                zombieZonaRiesgo4 = (Integer) entrada.readObject();

                // Zonas de riesgo
                zonaRiesgo1 = (Integer) entrada.readObject();
                zonaRiesgo2 = (Integer) entrada.readObject();
                zonaRiesgo3 = (Integer) entrada.readObject();
                zonaRiesgo4 = (Integer) entrada.readObject();

                // Top Zombies
                topZombies = (ArrayList<String>) entrada.readObject();

                interfazCliente.refrescarInterfaz();
            }
        } catch (IOException | NullPointerException | ClassNotFoundException e) {
            System.out.println("Error en la conexión");
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) {
        ClienteController clienteController = new ClienteController();
        new Thread(clienteController::iniciarConexion).start();
    }
}
