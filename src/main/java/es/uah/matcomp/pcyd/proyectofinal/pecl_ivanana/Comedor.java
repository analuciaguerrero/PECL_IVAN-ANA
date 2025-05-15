package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;

public class Comedor {

    // Logger para registrar eventos importantes del comedor
    private ApocalipsisLogger logger;

    // Referencia a la interfaz gráfica para actualizar la UI
    private InterfazServidor interfaz;

    // Cola bloqueante con humanos que están actualmente en el comedor
    private LinkedBlockingQueue<Humano> listaComedor = new LinkedBlockingQueue<>();

    // Cantidad de comida disponible en el comedor (variable atómica para acceso concurrente)
    private AtomicInteger comidaDisp = new AtomicInteger(0);

    // Semáforo que controla que sólo un humano coma a la vez (turno justo)
    private Semaphore turnoComida = new Semaphore(1, true);

    // Lock para proteger el acceso a la comida disponible
    private ReentrantLock accesoComida = new ReentrantLock();

    // Condición para esperar cuando no hay comida disponible
    private Condition sinComida = accesoComida.newCondition();

    // Constructor que inicializa logger e interfaz gráfica
    public Comedor(ApocalipsisLogger log, InterfazServidor interfaz) {
        this.logger = log;
        this.interfaz = interfaz;
    }

    // Devuelve la cantidad de comida disponible (AtomicInteger)
    public AtomicInteger getComida() {
        return comidaDisp;
    }

    // Devuelve la lista de humanos actualmente en el comedor
    public LinkedBlockingQueue<Humano> getListaHumanosComedor() {
        return listaComedor;
    }

    // Método para que un humano entregue comida al comedor (incrementa comida disponible)
    public void entregarComida(Humano h) {
        // Solo si el humano no está marcado ni muerto
        if (!h.estaMarcado() && !h.isMuerto()) {
            h.verificarPausa();

            // Incrementa la comida disponible en 2 unidades
            comidaDisp.addAndGet(2);

            // Registro del evento
            logger.log("El humano " + h.getIdHumanoNom() + " ha traido 2 piezas de comida. Comida restante: " + comidaDisp.toString());

            h.verificarPausa();

            // Señala a los humanos que esperan porque ahora hay comida disponible
            accesoComida.lock();
            try {
                sinComida.signalAll();
            } finally {
                accesoComida.unlock();
            }
        }
    }

    // Método para que un humano coma en el comedor
    public void comer(Humano h) {
        // Solo si el humano no está muerto
        if (!h.isMuerto()) {
            try {
                h.verificarPausa(); // Verifica si el humano debe pausar su acción

                // Añade el humano a la lista de comedor y actualiza la interfaz
                listaComedor.add(h);
                SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosComedor());

                // Espera su turno para comer, el semáforo asegura que un humano coma a la vez
                turnoComida.acquire();

                // Bloquea el acceso para comprobar y consumir comida
                accesoComida.lock();

                h.verificarPausa(); // Verifica pausa nuevamente

                try {
                    // Espera mientras no haya comida disponible
                    while (comidaDisp.get() == 0) {
                        logger.log("Humano " + h.getIdHumanoNom() + " espera para comer, pero no hay comida.");
                        sinComida.await(); // Espera a ser notificado cuando haya comida
                    }
                } finally {
                    accesoComida.unlock();
                }

                h.verificarPausa();

                // Libera el semáforo para que el siguiente humano pueda comer
                turnoComida.release();

                // Decrementa la comida disponible (consume una unidad)
                comidaDisp.decrementAndGet();

                // Loguea el estado actual de la comida
                logger.log("Humano " + h.getIdHumanoNom() + " está comiendo. Comida restante: " + comidaDisp.toString());

                h.verificarPausa();

                // Actualiza la interfaz para reflejar la cantidad de comida disponible
                SwingUtilities.invokeLater(() -> interfaz.actualizarCantidadComida());

                // Simula el tiempo de la comida (entre 3 y 5 segundos)
                Thread.sleep(3000 + (int) (2000 * Math.random()));

                // Loguea que el humano terminó de comer
                logger.log("Humano " + h.getIdHumanoNom() + " terminó de comer.");

                // Quita al humano de la lista del comedor
                listaComedor.remove(h);

                h.verificarPausa();

                // Actualiza la interfaz de nuevo
                SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosComedor());

            } catch (InterruptedException e) {
                logger.log("Humano " + h.getIdHumanoNom() + " fue interrumpido mientras comía. Posiblemente ha muerto.");
            }
        } else {
            logger.log("Humano " + h.getIdHumanoNom() + " no puede comer porque está muerto.");
        }
    }

}
