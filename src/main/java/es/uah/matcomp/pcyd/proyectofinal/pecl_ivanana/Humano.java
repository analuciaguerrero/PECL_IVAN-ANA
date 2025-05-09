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

    @Override
    public void run() {
        try {
            while (vivo && !Thread.currentThread().isInterrupted()) {

                // 1. Seleccionar zona de riesgo aleatoria
                int indiceZona = random.nextInt(zonas.size());
                AreaRiesgo zona = zonas.get(indiceZona);

                // 2. Salir del refugio hacia zona peligrosa
                refugio.cruzarTunel(this);
                zona.añadirHumano(this);
                logger.log(nombre + " entra en la zona de riesgo: " + zona.getNombre());

                // 3. Permanecer en la zona durante un tiempo
                esperar(TIEMPO_ZONA_RIESGO_MIN + random.nextInt(TIEMPO_ZONA_RIESGO_EXTRA));

                // 4. Si está siendo atacado, esperar a resultado (herido o muerto)
                while (atacado && vivo) {
                    esperar(100);
                    if (Thread.interrupted()) {
                        transformarseEnZombie(zona);
                        return;
                    }
                }

                // 5. Salir de la zona
                zona.eliminarHumano(this);
                logger.log(nombre + " ha salido de " + zona.getNombre());

                // 6. Volver al refugio
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
