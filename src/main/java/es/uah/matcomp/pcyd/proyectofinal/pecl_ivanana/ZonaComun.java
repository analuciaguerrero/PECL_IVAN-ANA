package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class ZonaComun {
    // Logger para registrar eventos de la simulación
    private ApocalipsisLogger logger;
    // Interfaz gráfica para mostrar los humanos en la zona común
    private InterfazServidor interfaz;
    // Array de túneles disponibles para salir al exterior
    private Tunel[] tuneles;
    // Cola bloqueante que contiene los humanos presentes en la zona común
    private LinkedBlockingQueue<Humano> listaHumanos = new LinkedBlockingQueue<>();

    // Constructor que inicializa la zona común con túneles, logger e interfaz
    public ZonaComun(Tunel[] tunelesDisponibles, ApocalipsisLogger logger, InterfazServidor interfaz) {
        this.tuneles = tunelesDisponibles;
        this.logger = logger;
        this.interfaz = interfaz;
    }

    // Devuelve la lista de humanos que están actualmente en la zona común
    public LinkedBlockingQueue<Humano> getListaHumanosZonaComun() {
        return listaHumanos;
    }

    // Método para simular que un humano se prepara antes de salir al exterior
    public void prepararse(Humano h) {
        try {
            logger.log("Humano " + h.getIdHumanoNom() + " se esta preparando para salir.");
            // Pausa aleatoria entre 1 y 2 segundos
            sleep((long) (Math.random() * 1000 + 1000));
        } catch (InterruptedException e) {
            // Si se interrumpe el hilo, lanzamos RuntimeException para no ignorar el error
            throw new RuntimeException(e);
        }
    }

    // Método para que un humano entre a la zona común
    public void entrarZonaComun(Humano h) {
        listaHumanos.add(h); // Añade humano a la lista
        // Actualiza interfaz en el hilo de Swing para evitar problemas de concurrencia
        SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosZonaComun());
        logger.log("Humano " + h.getIdHumanoNom() + " ha entrado a la Zona Común.");
    }

    // Método para que un humano explore el exterior saliendo por un túnel aleatorio
    public void explorarExterior(Humano h) {
        // Selecciona aleatoriamente un túnel (0 a 3)
        int seleccion = (int) (Math.random() * 4);
        logger.log("El humano " + h.getIdHumanoNom() + " ha elegido el túnel " + seleccion + " para salir de la Zona Común.");
        listaHumanos.remove(h); // Elimina humano de la zona común
        SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosZonaComun()); // Actualiza interfaz
        tuneles[seleccion].salirAlExterior(h); // Llama al método para salir al exterior
        if (!h.isMuerto()) { // Si el humano no ha muerto
            tuneles[seleccion].regresarDelExterior(h); // Intenta regresar por el mismo túnel
        }
    }
}
