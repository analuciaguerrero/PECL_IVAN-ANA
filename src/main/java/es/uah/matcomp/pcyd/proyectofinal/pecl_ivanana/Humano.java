package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.List;
import java.util.Random;

public class Humano extends Thread {

    private final String nombre;
    private final List<AreaRiesgo> zonas;
    private final Refugio refugio;
    private final ApocalipsisLogger logger;
    private final Random random = new Random();

    private volatile boolean vivo = true;
    private volatile boolean atacado = false;
    private volatile boolean herido = false;

    private volatile boolean paused = false;  // Control de pausa
    private static final int TIEMPO_ZONA_RIESGO_MIN = 3000;
    private static final int TIEMPO_ZONA_RIESGO_EXTRA = 2000;
    private static final int TIEMPO_REFUGIO = 2500;

    public Humano(String nombre, List<AreaRiesgo> zonas, Refugio refugio, ApocalipsisLogger logger) {
        this.nombre = nombre;
        this.zonas = zonas;
        this.refugio = refugio;
        this.logger = logger;
        setName(nombre);
    }
    public String getNombre() {
        return nombre;
    }

    public boolean estaSiendoAtacado() {
        return atacado;
    }

    public void setSiendoAtacado(boolean valor) {
        atacado = valor;
    }

    public boolean estaHerido() {
        return herido;
    }

    public void setHerido(boolean valor) {
        herido = valor;
    }

    public boolean estaVivo() {
        return vivo;
    }

    private void transformarseEnZombie(AreaRiesgo zonaActual) {
        vivo = false;
        zonaActual.eliminarHumano(this);
        logger.log(nombre + " ha muerto y se ha transformado en zombi.");

        String nuevoIdZombie = nombre.replace("H", "Z");
        Zombie nuevoZombi = new Zombie(nuevoIdZombie, zonas, logger);
        nuevoZombi.start();
    }

    private void esperar(long milisegundos) throws InterruptedException {
        long restante = milisegundos;
        while (restante > 0) {
            long paso = Math.min(100, restante);
            Thread.sleep(paso);
            restante -= paso;
        }
    }

    public int getZona() {
        return zonas.isEmpty() ? 0 : zonas.get(0).getId();  // devolvemos zona 0 si vacío
    }

    // Método para pausar el hilo
    public synchronized void pausar() throws InterruptedException {
        while (paused) {
            wait();  // El hilo se "duerme" hasta que se reanude
        }
    }
    // Método para reanudar el hilo
    public synchronized void reanudar() {
        paused = false;
        notify();  // Despierta al hilo
    }
    @Override
    public void run() {
        try {
            while (vivo && !Thread.currentThread().isInterrupted()) {
                pausar();  // pausa si corresponde

                int indiceZona = random.nextInt(zonas.size());
                AreaRiesgo zona = zonas.get(indiceZona);

                refugio.cruzarTunel(this);
                zona.añadirHumano(this); // <-- ya registra en log dentro de AreaRiesgo

                esperar(TIEMPO_ZONA_RIESGO_MIN + random.nextInt(TIEMPO_ZONA_RIESGO_EXTRA));

                while (atacado && vivo) {
                    esperar(100);
                    if (Thread.interrupted()) {
                        transformarseEnZombie(zona);
                        return;
                    }
                }

                zona.eliminarHumano(this); // <-- también registra en log

                refugio.volverPorTunel(this);
                if (refugio.entrarRefugio(this)) {
                    refugio.usarZonaDescanso(this);
                    if (herido) {
                        logger.log(nombre + " está recuperándose de sus heridas.");
                        refugio.usarZonaDescanso(this);
                        herido = false;
                    }
                    refugio.salirRefugio(this);
                } else {
                    logger.log(nombre + " no pudo entrar al refugio. Esperará fuera.");
                }
            }
        } catch (InterruptedException e) {
            vivo = false;
            logger.log(nombre + " ha sido interrumpido durante su ejecución.");
            Thread.currentThread().interrupt();
        }
    }

}
