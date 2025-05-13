package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion.InterfazServidor;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class ZonaComun {
    private ApocalipsisLogger logger;
    private InterfazServidor interfaz;
    private Tunel[] tuneles;
    private LinkedBlockingQueue<Humano> listaHumanos = new LinkedBlockingQueue<>();

    public ZonaComun(Tunel[] tunelesDisponibles, ApocalipsisLogger logger, InterfazServidor interfaz) {
        this.tuneles = tunelesDisponibles;
        this.logger = logger;
        this.interfaz = interfaz;
    }

    public LinkedBlockingQueue<Humano> getListaHumanosZonaComun() {
        return listaHumanos;
    }

    public void prepararse(Humano h) {
        try {
            logger.log("Humano " + h.getIdHumanoNom() + " se esta preparando para salir.");
            sleep((long) (Math.random() * 1000 + 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void entrarZonaComun(Humano h) {
        listaHumanos.add(h);
        SwingUtilities.invokeLater(() -> interfaz.actualizarHumanosZonaComun());
        logger.log("Humano " + h.getIdHumanoNom() + " ha entrado a la Zona Común.");
    }

    public void explorarExterior(Humano h) {
        int seleccion = (int) (Math.random() * 4);
        logger.log("El humano " + h.getIdHumanoNom() + " ha elegido el túnel " + seleccion + " para salir de la Zona Común.");
        listaHumanos.remove(h);
        SwingUtilities.invokeLater(() -> interfaz.actualizarHumanosZonaComun());
        tuneles[seleccion].salirAlExterior(h);
        if (!h.isMuerto()) {
            tuneles[seleccion].regresarDelExterior(h);
        }
    }
}
