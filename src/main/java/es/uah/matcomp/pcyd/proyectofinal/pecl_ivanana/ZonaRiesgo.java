package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Representa una zona de riesgo en la simulación del apocalipsis zombi,
 * donde pueden coexistir humanos y zombis.
 */
public class ZonaRiesgo {
    // Identificador único de la zona
    private int id;
    // Logger para registrar eventos relevantes
    private ApocalipsisLogger logger;
    // Interfaz gráfica para actualizar la UI
    private InterfazServidor interfaz;
    // Cola concurrente para zombis presentes en la zona
    private LinkedBlockingQueue<Zombie> listaZombies = new LinkedBlockingQueue<>();
    // Lista de humanos presentes en la zona
    private ArrayList<Humano> listaHumanos = new ArrayList<>();

    /**
     * Constructor que inicializa la zona de riesgo con su ID,
     * el sistema de logs y la interfaz gráfica.
     */
    public ZonaRiesgo(int idZona, ApocalipsisLogger logger, InterfazServidor ventanaUI) {
        this.id = idZona;
        this.logger = logger;
        this.interfaz = ventanaUI;
    }

    // Devuelve el ID de la zona
    public int getId() {
        return id;
    }

    // Devuelve la cola de zombis presentes
    public LinkedBlockingQueue<Zombie> getListaZombies() {
        return listaZombies;
    }

    // Devuelve la lista de humanos presentes
    public ArrayList<Humano> getListaHumanos() {
        return listaHumanos;
    }

    /**
     * Añade un zombi a la zona y actualiza la interfaz gráfica.
     */
    public void entrarZombie(Zombie z) {
        listaZombies.add(z);
        SwingUtilities.invokeLater(() -> interfaz.mostrarZombisZonaPeligro(id));
    }

    /**
     * Añade un humano a la zona y actualiza la interfaz gráfica.
     */
    public void entrarHumano(Humano h) {
        listaHumanos.add(h);
        SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosZonaPeligro(id));
    }

    /**
     * Elimina un zombi de la zona y actualiza la interfaz gráfica.
     */
    public void salirZombie(Zombie z) {
        listaZombies.remove(z);
        SwingUtilities.invokeLater(() -> interfaz.mostrarZombisZonaPeligro(id));
    }

    /**
     * Elimina un humano de la zona y actualiza la interfaz gráfica.
     */
    public void salirHumano(Humano h) {
        listaHumanos.remove(h);
        SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosZonaPeligro(id));
    }

    /**
     * Selecciona aleatoriamente un humano de la zona, lo elimina de la lista,
     * y actualiza la interfaz. Retorna null si no hay humanos disponibles.
     * Este método está sincronizado para evitar concurrencia en la lista.
     */
    public synchronized Humano seleccionarHumanoAleatorio(ZonaRiesgo zona) {
        if (zona.getListaHumanos().isEmpty()) {
            return null;
        } else {
            int totalHumanos = zona.getListaHumanos().size();
            int indice = (int) (totalHumanos * Math.random());
            Humano objetivo = zona.getListaHumanos().get(indice);
            zona.getListaHumanos().remove(objetivo);
            SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosZonaPeligro(id));
            return objetivo;
        }
    }
}
