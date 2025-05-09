package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Refugio {

    private final Lock accesoRefugioLock = new ReentrantLock();  // Para controlar entrada/salida
    private final Set<Humano> humanosDentro = new HashSet<>();
    private final ApocalipsisLogger logger;
    private final int capacidadMaxima;
    private final Tunel[] tuneles = new Tunel[4];  // Túneles hacia zonas 0,1,2,3

    // Zonas internas
    private final Object zonaComun = new Object();
    private final Object zonaDescanso = new Object();
    private final Object comedor = new Object();

    public Refugio(int capacidad, ApocalipsisLogger logger) {
        this.capacidadMaxima = capacidad;
        this.logger = logger;
        for (int i = 0; i < tuneles.length; i++) {
            tuneles[i] = new Tunel(i, logger);  // Inicializamos los túneles
        }
    }

    public boolean entrarRefugio(Humano humano) {
        accesoRefugioLock.lock();
        try {
            if (humanosDentro.size() >= capacidadMaxima) {
                logger.log("Refugio lleno: " + humano.getName() + " no pudo entrar.");
                return false;
            }
            humanosDentro.add(humano);
            logger.log(humano.getName() + " ha entrado al refugio. [" + humanosDentro.size() + "/" + capacidadMaxima + "]");
            return true;
        } finally {
            accesoRefugioLock.unlock();
        }
    }

    public void salirRefugio(Humano humano) {
        accesoRefugioLock.lock();
        try {
            if (humanosDentro.remove(humano)) {
                logger.log(humano.getName() + " ha salido del refugio. [" + humanosDentro.size() + "/" + capacidadMaxima + "]");
            }
        } finally {
            accesoRefugioLock.unlock();
        }
    }

    public void usarZonaDescanso(Humano humano) throws InterruptedException {
        synchronized (zonaDescanso) {
            logger.log(humano.getName() + " está descansando en la zona de descanso.");
            Thread.sleep(1000 + (int)(Math.random() * 2000));  // Simula descanso
        }
    }

    public void usarZonaComun(Humano humano) throws InterruptedException {
        synchronized (zonaComun) {
            logger.log(humano.getName() + " está en la zona común.");
            Thread.sleep(500 + (int)(Math.random() * 1000));  // Simula uso
        }
    }

    public void usarComedor(Humano humano) throws InterruptedException {
        synchronized (comedor) {
            logger.log(humano.getName() + " está comiendo en el comedor.");
            Thread.sleep(800 + (int)(Math.random() * 1500));  // Simula comida
        }
    }

    // Humano sale del refugio a través del túnel hacia su zona
    public void cruzarTunel(Humano humano) throws InterruptedException {
        int zona = humano.getZona();  // Suponiendo que el humano tiene una zona asignada (0-3)
        Tunel tunel = tuneles[zona];

        salirRefugio(humano);  // Sale del refugio primero

        tunel.entrar(humano);
        logger.log(humano.getName() + " está cruzando el túnel hacia zona " + zona);
        Thread.sleep(1000);  // Simula cruce del túnel
        tunel.salir(humano);
        tunel.liberarTunel();
        logger.log(humano.getName() + " ha salido del túnel hacia zona " + zona);
    }

    // Humano regresa desde la zona insegura al refugio por el túnel
    public void volverPorTunel(Humano humano) throws InterruptedException {
        int zona = humano.getZona();
        Tunel tunel = tuneles[zona];

        tunel.entrar(humano);
        logger.log(humano.getName() + " está regresando al refugio por el túnel desde zona " + zona);
        Thread.sleep(1000);  // Simula cruce del túnel
        tunel.salir(humano);
        tunel.liberarTunel();
        logger.log(humano.getName() + " ha llegado al refugio desde zona " + zona);

        boolean pudoEntrar = entrarRefugio(humano);  // Intenta entrar al refugio
        if (!pudoEntrar) {
            logger.log(humano.getName() + " se queda fuera: el refugio sigue lleno tras cruzar el túnel.");
            // Aquí podrías implementar lógica para esperar afuera, buscar otro refugio, etc.
        }
    }
    public boolean estaDentro(Humano humano) {
        accesoRefugioLock.lock();
        try {
            return humanosDentro.contains(humano);
        } finally {
            accesoRefugioLock.unlock();
        }
    }
}

