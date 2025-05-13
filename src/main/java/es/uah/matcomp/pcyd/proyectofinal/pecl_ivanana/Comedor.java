package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class Comedor {
    private ApocalipsisLogger logger;
    private InterfazServidor interfaz;
    private LinkedBlockingQueue<Humano> listaComedor = new LinkedBlockingQueue<>();
    private AtomicInteger comidaDisp = new AtomicInteger(0);
    private Semaphore turnoComida = new Semaphore(1, true);
    private ReentrantLock accesoComida = new ReentrantLock();
    private Condition sinComida = accesoComida.newCondition();

    public Comedor(ApocalipsisLogger log, InterfazServidor interfaz) {
        this.logger = log;
        this.interfaz = interfaz;
    }

    public AtomicInteger getComida() {
        return comidaDisp;
    }

    public LinkedBlockingQueue<Humano> getListaHumanosComedor() {
        return listaComedor;
    }

    public void entregarComida(Humano h) {
        if (!h.estaMarcado() && !h.isMuerto()) {
            h.verificarPausa();
            comidaDisp.addAndGet(2);

            logger.log("El humano " + h.getIdHumanoNom() + " ha traido 2 piezas de comida. Comida restante: " + comidaDisp.toString());
            h.verificarPausa();
            accesoComida.lock();
            try {
                sinComida.signalAll();
            }
            finally {
                accesoComida.unlock();
            }
        }
    }

    public void comer(Humano h) {
        if (!h.isMuerto()) {
            try {
                h.verificarPausa(); // Comprobar la pausa
                listaComedor.add(h); // Los añadimos a la lista del comedor
                // Actualiza la interfaz
                SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosComedor());

                turnoComida.acquire();
                accesoComida.lock();
                h.verificarPausa(); // Comprobamos la pausa de nuevo
                try {
                    while (comidaDisp.get() == 0) { // Comprobar si tienen que quedarse esperando porque no haya comida
                        logger.log("Humano " + h.getIdHumanoNom() + " espera para comer, pero no hay comida.");
                        sinComida.await(); // Espera hasta que alguien traiga comida
                    }
                }finally {
                    accesoComida.unlock();
                }
                h.verificarPausa();
                turnoComida.release(); // Permite dejar pasar al siguiente ya que dejan el semáforo
                comidaDisp.decrementAndGet(); // Decrementamos una unidad de comida ya que come
                // Log de la comida
                logger.log("Humano " + h.getIdHumanoNom() + " está comiendo. Comida restante: " + comidaDisp.toString());

                h.verificarPausa();

                SwingUtilities.invokeLater(() -> interfaz.actualizarCantidadComida());

                // Simula el tiempo de alimentación
                Thread.sleep(3000 + (int) (2000 * Math.random()));

                // Log de finalización de la comida
                logger.log("Humano " + h.getIdHumanoNom() + " terminó de comer.");

                // Elimina al humano de la lista del comedor
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
