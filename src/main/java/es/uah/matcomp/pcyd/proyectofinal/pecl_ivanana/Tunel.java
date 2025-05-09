package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Tunel {
    private final int id;
    private final Lock cerrojo = new ReentrantLock();
    private final Condition espera = cerrojo.newCondition(); // condición común
    private boolean ocupado = false;
    private final ApocalipsisLogger logger;

    public Tunel(int id, ApocalipsisLogger logger) {
        this.id = id;
        this.logger = logger;
    }

    // Método llamado antes de cruzar el túnel (entrada o regreso)
    public void entrar(Humano humano) {
        cerrojo.lock();
        try {
            while (ocupado) {
                espera.await();
            }
            ocupado = true;
            logger.log(humano.getName() + " ha entrado al túnel " + id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log("Interrupción: " + humano.getName() + " no pudo entrar al túnel " + id);
        } finally {
            cerrojo.unlock();
        }
    }

    // Método llamado después de cruzar el túnel (salida o llegada)
    public void salir(Humano humano) {
        cerrojo.lock();
        try {
            logger.log(humano.getName() + " ha salido del túnel " + id);
            ocupado = false;
            espera.signalAll();  // Despierta a los que esperan usar el túnel
        } finally {
            cerrojo.unlock();
        }
    }

    // Método auxiliar para liberar el túnel manualmente si es necesario (opcional)
    public void liberarTunel() {
        cerrojo.lock();
        try {
            ocupado = false;
            espera.signalAll();
            logger.log("Túnel " + id + " ha sido liberado manualmente.");
        } finally {
            cerrojo.unlock();
        }
    }

    public int getId() {
        return id;
    }
}

