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
                logger.log(humano.getName() + " está esperando para entrar al túnel " + id);
                espera.await();  // El humano espera hasta que el túnel esté libre
            }
            ocupado = true;
            logger.log(humano.getName() + " ha entrado al túnel " + id);
        } catch (InterruptedException e) {
            // En caso de que se interrumpa mientras espera, se maneja adecuadamente
            Thread.currentThread().interrupt();  // Restablecer el estado de interrupción
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
            if (ocupado) {
                logger.log("El túnel " + id + " está ocupado y será liberado manualmente.");
                ocupado = false;
                espera.signalAll();  // Libera a todos los hilos que esperan
            } else {
                logger.log("El túnel " + id + " ya está libre.");
            }
        } finally {
            cerrojo.unlock();
        }
    }


    public int getId() {
        return id;
    }
}

