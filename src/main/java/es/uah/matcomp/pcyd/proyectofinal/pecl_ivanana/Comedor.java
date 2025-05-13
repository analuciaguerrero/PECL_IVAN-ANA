package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion.InterfazServidor;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Comedor {
    private ApocalipsisLogger logger;
    private InterfazServidor interfaz;
    private LinkedBlockingQueue<Humano> listaComedor = new LinkedBlockingQueue<>();
    private AtomicInteger comidaDisp = new AtomicInteger(0);
    private Semaphore turnoComida = new Semaphore(1, true);
    private ReentrantLock accesoComida = new ReentrantLock();
    private Condition sinComida = accesoComida.newCondition();

    public Comedor(ApocalipsisLogger log, InterfazServidor vista) {
        this.interfaz = vista;
        this.logger = log;
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
                SwingUtilities.invokeLater(() -> interfaz.actualizarHumanosComedor());

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
                h.verificarPausa();

                SwingUtilities.invokeLater(() -> interfaz.actualizarComida());
                logger.log("Humano " + h.getIdHumanoNom() + " está comiendo. Comida restante: " + comidaDisp.toString());

                Thread.sleep(3000 + (int) (2000 * Math.random())); // Simula el tiempo de alimentación
                logger.log("Humano " + h.getIdHumanoNom() + " terminó de comer.");

                listaComedor.remove(h); // Los eliminamos de la lista del comedor
                h.verificarPausa();

                SwingUtilities.invokeLater(() -> interfaz.actualizarHumanosComedor());

            } catch (InterruptedException e) {
                logger.log("Humano " + h.getIdHumanoNom() + " fue interrumpido mientras comía. Posiblemente ha muerto.");
            }
        } else {
            logger.log("Humano " + h.getIdHumanoNom() + " no puede comer porque está muerto.");
        }
    }

}
