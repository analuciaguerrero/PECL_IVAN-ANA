package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class ZonaDescanso {
    // Logger para registrar eventos relevantes
    private ApocalipsisLogger logger;
    // Referencia a la interfaz gráfica para actualizar la vista
    private InterfazServidor interfaz;
    // Lista concurrente de humanos que están descansando
    private LinkedBlockingQueue<Humano> listaDescanso = new LinkedBlockingQueue<>();

    // Constructor que inicializa el logger y la interfaz gráfica
    public ZonaDescanso(ApocalipsisLogger logger, InterfazServidor interfaz) {
        this.logger = logger;
        this.interfaz = interfaz;
    }

    // Devuelve la lista actual de humanos descansando
    public LinkedBlockingQueue<Humano> getListaHumanosDescansando() {
        return listaDescanso;
    }

    // Método que simula el descanso de un humano para recuperarse
    public void descansarHumano(Humano h) {
        try {
            listaDescanso.add(h); // Añadimos el humano a la lista de descanso
            // Actualizamos la interfaz en el hilo de eventos de Swing
            SwingUtilities.invokeLater(() -> interfaz.mostrandoHumanosDescansando());
            // Logueamos el inicio del descanso
            logger.log("Humano" + h.getIdHumanoNom() + " está descansando para recuperarse.");
            // Verificamos si hay pausa solicitada para el humano
            h.verificarPausa();
            // Quitamos el estado marcado del humano
            h.marcar(false);
            // Simulamos tiempo de descanso entre 3 y 5 segundos
            sleep(3000 + (int) (Math.random() * 2000));
            // Eliminamos al humano de la lista de descanso
            listaDescanso.remove(h);
            // Verificamos nuevamente pausas
            h.verificarPausa();
            // Actualizamos la interfaz para reflejar que el humano ya no está descansando
            SwingUtilities.invokeLater(() -> interfaz.mostrandoHumanosDescansando());
            // Logueamos la finalización del descanso
            logger.log("Humano " + h.getIdHumanoNom() + " se ha recuperado completamente.");
        } catch (InterruptedException ie) {
            System.out.println("Error durante el descanso de:" + h.getIdHumanoNom());
        }
    }

    // Método para descansar un humano que acaba de regresar del exterior
    public void descansar(Humano h) {
        if (!h.isMuerto()) { // Solo si el humano no está muerto
            try {
                listaDescanso.add(h); // Añadimos a la lista de descanso
                SwingUtilities.invokeLater(() -> interfaz.mostrandoHumanosDescansando());
                h.verificarPausa();
                // Logueamos el inicio del descanso tras volver del exterior
                logger.log("El humano " + h.getIdHumanoNom() + " está descansando tras regresar del exterior.");
                // Simulamos descanso entre 2 y 4 segundos
                sleep(2000 + (int) (2000 * Math.random()));
                listaDescanso.remove(h); // Quitamos al humano de la lista
                SwingUtilities.invokeLater(() -> interfaz.mostrandoHumanosDescansando());
                h.verificarPausa();
                // Logueamos el fin del descanso
                logger.log("Humano " + h.getIdHumanoNom() + " ha finalizado su descanso.");
            } catch (InterruptedException ie) {
                System.out.println("Interrupción mientras descansaba: " + h.getIdHumanoNom());
            }
        }
    }
}
