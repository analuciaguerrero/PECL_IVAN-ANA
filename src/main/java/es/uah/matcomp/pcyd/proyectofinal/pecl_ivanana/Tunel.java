package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion.ServidorController;

import javax.swing.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Tunel {
    private int id;
    private ApocalipsisLogger logger;
    private InterfazServidor interfaz;
    private ZonaRiesgo zonaRiesgo;
    private CyclicBarrier barreraEsperar = new CyclicBarrier(3);
    private ReentrantLock lockPaso = new ReentrantLock();
    private Condition condicionPaso = lockPaso.newCondition();
    private Semaphore semEspera = new Semaphore(3, true);
    private Semaphore semPaso = new Semaphore(1, true);
    private LinkedBlockingQueue<Humano> colaEspera = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Humano> colaAtravesando = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Humano> colaRegreso = new LinkedBlockingQueue<>();

    public Tunel(int id, ZonaRiesgo zonaRiesgo, ApocalipsisLogger logger, InterfazServidor interfaz) {
        this.id = id;
        this.zonaRiesgo = zonaRiesgo;
        this.logger = logger;
        this.interfaz = interfaz;
    }

    public int getId() {
        return id;
    }

    public LinkedBlockingQueue<Humano> getColaEspera() {
        return colaEspera;
    }

    public LinkedBlockingQueue<Humano> getColaAtravesando() {
        return colaAtravesando;
    }

    public LinkedBlockingQueue<Humano> getColaRegreso() {
        return colaRegreso;
    }

    public void salirAlExterior(Humano h) {
        try {
            h.verificarPausa();
            semEspera.acquire(); //Comprueba si puede entrar a la zona de espera
            colaEspera.add(h); // Añadimos el humano a la cola de espera
            SwingUtilities.invokeLater(() -> interfaz.refrescarListaEntradaTunel(getId()));

            if (barreraEsperar.getNumberWaiting() + 1 == 3) {
                logger.log("Humano " + h.getIdHumanoNom() + " ha llegado al tunel " + id + " y ya son 3, así que están preparados para adentrarse en el mundo exterior.");
            } else {
                logger.log("Humano " + h.getIdHumanoNom() + " está esperando en el tunel " + id + " para salir. Humanos esperando: " + (1 + barreraEsperar.getNumberWaiting()));
            }
            h.verificarPausa();
            barreraEsperar.await(); // Tenemos que esperar a que sean 3
            h.verificarPausa();

            lockPaso.lock();
            try {

                while (!colaRegreso.isEmpty()) {
                    logger.log("Humano " + h.getIdHumanoNom() + " esperando entrada al Refugio de otro humano por el túnel " + id);
                    condicionPaso.await();
                }
            } finally {
                lockPaso.unlock();
            }

            h.verificarPausa();
            semPaso.acquire(); // Tenemos que comprobar que pasan de 1 en 1
            logger.log("Humano " + h.getIdHumanoNom() + " está atravesando el tunel " + id);
            semEspera.release(); // Liberamos uno de la cola de espera del túnel
            colaEspera.remove(h); // Eliminamos de la cola de espera
            SwingUtilities.invokeLater(() -> interfaz.refrescarListaEntradaTunel(getId()));
            colaAtravesando.add(h); // Añadimos en la cola atravesando el túnel
            SwingUtilities.invokeLater(() -> interfaz.refrescarListaTransitando(getId()));
            Thread.sleep(1000); // Simulación del tiempo que tarda en pasar
            colaAtravesando.remove(h); // Eliminamos de la cola atravesando el túnel
            SwingUtilities.invokeLater(() -> interfaz.refrescarListaTransitando(getId()));
            logger.log("Humano " + h.getIdHumanoNom() + " terminó de cruzar el tunel " + id);
            semPaso.release(); // Permitimos que pase el siguiente
            h.verificarPausa();
            logger.log("Humano " + h.getIdHumanoNom() + " entró en la zona de riesgo " + id);
            zonaRiesgo.entrarHumano(h);
            SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosZonaPeligro(getId()));
            Thread.sleep((long) (Math.random() * 3000 + 2000));
            h.verificarPausa();

        } catch (InterruptedException | BrokenBarrierException e) {
            if (h.estaEsperandoAtaque()) {
                System.out.println(h.getIdHumanoNom() + " puede morir ya que está siendo atacado.");
                h.ejecutarDefensa();
            }
        }
    }

    public void regresarDelExterior(Humano h) {
        try {
            h.verificarPausa();
            zonaRiesgo.salirHumano(h); // Eliminamos de la zona de riesgo
            logger.log("Humano " + h.getIdHumanoNom() + " deja la zona de riesgo " + id + " y regresa al refugio");

            if (h.estaMarcado()) {
                logger.log("Retorna el humano " + h.getIdHumanoNom() + " marcado y sin comida.");
            } else {
                logger.log("Retorna el humano " + h.getIdHumanoNom() + " con comida.");
            }
            colaRegreso.add(h); // Añadimos el humano a la cola de regreso
            SwingUtilities.invokeLater(() -> interfaz.actualizarRetornoTunel(getId()));

            logger.log("Humano " + h.getIdHumanoNom() + " esperando entrada por túnel " + id);

            semPaso.acquire();
            colaRegreso.remove(h); // Eliminamos el humano de la cola de regreso
            SwingUtilities.invokeLater(() -> interfaz.actualizarRetornoTunel(getId()));
            colaAtravesando.add(h); // Añadimos el humano a la cola de atravesar el túnel
            SwingUtilities.invokeLater(() -> interfaz.actualizarRetornoTunel(getId()));
            logger.log("Humano " + h.getIdHumanoNom() + " regresando por tunel " + id);
            Thread.sleep(1000); // Simula el tiempo que tarda en cruzar
            colaAtravesando.remove(h); // Eliminamos el humano de la cola atravesar el túnel cuando haya finalizado el tiempo
            SwingUtilities.invokeLater(() -> interfaz.actualizarRetornoTunel(getId()));
            logger.log("Humano " + h.getIdHumanoNom() + " está pasando por el tunel " + id);
            semPaso.release(); // Lo liberamos para que se permita pasar al siguiente
            lockPaso.lock();
            try {
                condicionPaso.signalAll();
            } finally {
                lockPaso.unlock();
            }

            h.verificarPausa();
        } catch (InterruptedException e) {
            System.out.println("El humano ha muerto en el intento.");
        }
    }
}
