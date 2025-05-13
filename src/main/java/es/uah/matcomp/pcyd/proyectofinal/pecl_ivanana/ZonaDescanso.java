package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class ZonaDescanso {
    private ApocalipsisLogger logger;
    private InterfazServidor interfaz;
    private LinkedBlockingQueue<Humano> listaDescanso = new LinkedBlockingQueue<>();

    public ZonaDescanso(ApocalipsisLogger logger, InterfazServidor interfaz) {
        this.logger = logger;
        this.interfaz = interfaz;
    }

    public LinkedBlockingQueue<Humano> getListaHumanosDescansando() {
        return listaDescanso;
    }

    public void descansarHumano(Humano h) {
        try {
            listaDescanso.add(h); // Añadimos el humano a la lista de descanso
            SwingUtilities.invokeLater(() -> interfaz.mostrandoHumanosDescansando());
            logger.log("Humano" + h.getIdHumanoNom() + " está descansando para recuperarse.");
            h.verificarPausa();
            h.marcar(false); // Eliminamos el estado marcado
            sleep(3000 + (int) (Math.random() * 2000));
            listaDescanso.remove(h); // Eliminamos al humano de la lista
            h.verificarPausa();
            SwingUtilities.invokeLater(() -> interfaz.mostrandoHumanosDescansando());
            logger.log("Humano " + h.getIdHumanoNom() + " se ha recuperado completamente.");
        } catch (InterruptedException ie) {
            System.out.println("Error durante el descanso de:" + h.getIdHumanoNom());
        }
    }

    public void descansar(Humano h) {
        if (!h.isMuerto()) {
            try {
                listaDescanso.add(h); //Añadimos el humano a la lista de descanso
                SwingUtilities.invokeLater(() -> interfaz.mostrandoHumanosDescansando());
                h.verificarPausa();
                logger.log("El humano " + h.getIdHumanoNom() + " está descansando tras regresar del exterior.");
                sleep(2000 + (int) (2000 * Math.random())); // Simular el descanso
                listaDescanso.remove(h); // Quitamos el humano de la lista de descanso
                SwingUtilities.invokeLater(() -> interfaz.mostrandoHumanosDescansando());
                h.verificarPausa();
                logger.log("Humano " + h.getIdHumanoNom() + " ha finalizado su descanso.");
            } catch (InterruptedException ie) {
                System.out.println("Interrupción mientras descansaba: " + h.getIdHumanoNom());
            }
        }
    }
}
