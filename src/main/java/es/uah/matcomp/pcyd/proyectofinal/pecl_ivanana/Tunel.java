package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javax.swing.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Tunel {
    // Identificador del túnel
    private int id;
    // Logger para registrar eventos
    private ApocalipsisLogger logger;
    // Interfaz gráfica para actualizar visualizaciones
    private InterfazServidor interfaz;
    // Zona de riesgo asociada al túnel
    private ZonaRiesgo zonaRiesgo;

    // Barrera para esperar a que 3 humanos estén listos para salir juntos
    private CyclicBarrier barreraEsperar = new CyclicBarrier(3);

    // Lock para controlar acceso exclusivo y condición asociada
    private ReentrantLock lockPaso = new ReentrantLock();
    private Condition condicionPaso = lockPaso.newCondition();

    // Semáforo para limitar a 3 humanos en zona de espera (justicia con orden)
    private Semaphore semEspera = new Semaphore(3, true);

    // Semáforo para permitir que solo un humano pase el túnel a la vez
    private Semaphore semPaso = new Semaphore(1, true);

    // Colas para controlar humanos en diferentes estados del túnel
    private LinkedBlockingQueue<Humano> colaEspera = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Humano> colaAtravesando = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Humano> colaRegreso = new LinkedBlockingQueue<>();

    // Constructor
    public Tunel(int id, ZonaRiesgo zonaRiesgo, ApocalipsisLogger logger, InterfazServidor interfaz) {
        this.id = id;
        this.zonaRiesgo = zonaRiesgo;
        this.logger = logger;
        this.interfaz = interfaz;
    }

    // Getters para atributos principales
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

    // Método para que un humano salga al exterior atravesando el túnel
    public void salirAlExterior(Humano h) {
        try {
            // Verifica si la simulación está pausada para este humano
            h.verificarPausa();

            // Adquiere permiso para entrar a la zona de espera (máximo 3)
            semEspera.acquire();

            // Añade humano a la cola de espera
            colaEspera.add(h);

            // Actualiza interfaz gráfica para la lista de entrada al túnel
            SwingUtilities.invokeLater(() -> interfaz.refrescarListaEntradaTunel(getId()));

            // Registro en log dependiendo de si ya hay 3 humanos esperando
            if (barreraEsperar.getNumberWaiting() + 1 == 3) {
                logger.log("Humano " + h.getIdHumanoNom() + " ha llegado al tunel " + id +
                        " y ya son 3, así que están preparados para adentrarse en el mundo exterior.");
            } else {
                logger.log("Humano " + h.getIdHumanoNom() + " está esperando en el tunel " + id +
                        " para salir. Humanos esperando: " + (1 + barreraEsperar.getNumberWaiting()));
            }

            // Pausa si corresponde
            h.verificarPausa();

            // Espera en la barrera hasta que sean 3 humanos listos para salir juntos
            barreraEsperar.await();

            // Pausa si corresponde
            h.verificarPausa();

            // Control de paso exclusivo usando lock y condición para evitar paso simultáneo
            lockPaso.lock();
            try {
                // Espera si hay humanos en la cola de regreso (prioridad a los que regresan)
                while (!colaRegreso.isEmpty()) {
                    logger.log("Humano " + h.getIdHumanoNom() + " esperando entrada al Refugio de otro humano por el túnel " + id);
                    condicionPaso.await();
                }
            } finally {
                lockPaso.unlock();
            }

            // Pausa si corresponde
            h.verificarPausa();

            // Semáforo para pasar de uno en uno
            semPaso.acquire();

            // Registra que el humano está atravesando el túnel
            logger.log("Humano " + h.getIdHumanoNom() + " está atravesando el tunel " + id);

            // Libera un permiso del semáforo de espera para que otro humano pueda entrar a zona de espera
            semEspera.release();

            // Remueve al humano de la cola de espera
            colaEspera.remove(h);

            // Actualiza la interfaz para reflejar cambios en cola de espera
            SwingUtilities.invokeLater(() -> interfaz.refrescarListaEntradaTunel(getId()));

            // Añade humano a la cola de los que atraviesan el túnel
            colaAtravesando.add(h);

            // Actualiza la interfaz para reflejar cambio en humanos transitando
            SwingUtilities.invokeLater(() -> interfaz.refrescarListaTransitando(getId()));

            // Simula el tiempo que tarda el humano en atravesar el túnel (1 segundo)
            Thread.sleep(1000);

            // Remueve humano de la cola de atravesando tras cruzar
            colaAtravesando.remove(h);

            // Actualiza la interfaz de nuevo
            SwingUtilities.invokeLater(() -> interfaz.refrescarListaTransitando(getId()));

            // Registra que terminó de cruzar el túnel
            logger.log("Humano " + h.getIdHumanoNom() + " terminó de cruzar el tunel " + id);

            // Libera el semáforo para que el siguiente pueda pasar
            semPaso.release();

            // Pausa si corresponde
            h.verificarPausa();

            // Añade humano a la zona de riesgo (exterior)
            logger.log("Humano " + h.getIdHumanoNom() + " entró en la zona de riesgo " + id);
            zonaRiesgo.entrarHumano(h);

            // Actualiza interfaz para humanos en zona de peligro
            SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosZonaPeligro(getId()));

            // Simula que el humano está un tiempo aleatorio en la zona de riesgo (2 a 5 segundos)
            Thread.sleep((long) (Math.random() * 3000 + 2000));

            // Pausa si corresponde
            h.verificarPausa();

        } catch (InterruptedException | BrokenBarrierException e) {
            // En caso de interrupción (ej. ataque) si humano está siendo atacado, ejecuta defensa
            if (h.estaEsperandoAtaque()) {
                System.out.println(h.getIdHumanoNom() + " puede morir ya que está siendo atacado.");
                h.ejecutarDefensa();
            }
        }
    }

    // Método para que un humano regrese del exterior atravesando el túnel
    public void regresarDelExterior(Humano h) {
        try {
            // Verifica si la simulación está pausada para este humano
            h.verificarPausa();

            // Elimina humano de la zona de riesgo
            zonaRiesgo.salirHumano(h);

            // Log que humano deja zona de riesgo y regresa al refugio
            logger.log("Humano " + h.getIdHumanoNom() + " deja la zona de riesgo " + id + " y regresa al refugio");

            // Log dependiendo si humano está marcado o no
            if (h.estaMarcado()) {
                logger.log("Retorna el humano " + h.getIdHumanoNom() + " marcado y sin comida.");
            } else {
                logger.log("Retorna el humano " + h.getIdHumanoNom() + " con comida.");
            }

            // Añade humano a la cola de regreso para atravesar túnel
            colaRegreso.add(h);

            // Actualiza interfaz para reflejar humanos retornando
            SwingUtilities.invokeLater(() -> interfaz.actualizarRetornoTunel(getId()));

            // Log que humano espera para entrar por el túnel
            logger.log("Humano " + h.getIdHumanoNom() + " esperando entrada por túnel " + id);

            // Adquiere semáforo para pasar uno a uno
            semPaso.acquire();

            // Remueve humano de la cola de regreso
            colaRegreso.remove(h);

            // Actualiza interfaz
            SwingUtilities.invokeLater(() -> interfaz.actualizarRetornoTunel(getId()));

            // Añade humano a la cola de atravesar túnel
            colaAtravesando.add(h);

            // Actualiza interfaz
            SwingUtilities.invokeLater(() -> interfaz.actualizarRetornoTunel(getId()));

            // Log que humano está regresando por el túnel
            logger.log("Humano " + h.getIdHumanoNom() + " regresando por tunel " + id);

            // Simula el tiempo que tarda en cruzar el túnel (1 segundo)
            Thread.sleep(1000);

            // Remueve humano de la cola atravesando al finalizar el cruce
            colaAtravesando.remove(h);

            // Actualiza interfaz
            SwingUtilities.invokeLater(() -> interfaz.actualizarRetornoTunel(getId()));

            // Log que humano está pasando por el túnel
            logger.log("Humano " + h.getIdHumanoNom() + " está pasando por el tunel " + id);

            // Libera semáforo para que el siguiente pueda pasar
            semPaso.release();

            // Notifica a posibles humanos esperando para pasar (prioridad al regreso)
            lockPaso.lock();
            try {
                condicionPaso.signalAll();
            } finally {
                lockPaso.unlock();
            }

            // Pausa si corresponde
            h.verificarPausa();

        } catch (InterruptedException e) {
            System.out.println("El humano ha muerto en el intento.");
        }
    }
}
