package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.List;
import java.util.Random;

public class Zombie extends Thread {
    private String idZombie;
    private List<AreaRiesgo> zonasDeRiesgo;
    private int muertes = 0;
    private ApocalipsisLogger logger;
    private Random aleatorio = new Random();
    private static final int TIEMPO_MIN_ATAQUE = 500;
    private static final int TIEMPO_ADICIONAL_ATAQUE = 1000;
    private static final int TIEMPO_MIN_ESPERA = 2000;
    private static final int TIEMPO_ADICIONAL_ESPERA = 1000;
    private volatile boolean activo = true;

    public Zombie(String id, List<AreaRiesgo> zonasDeRiesgo, ApocalipsisLogger logger) {
        this.idZombie = id;
        this.zonasDeRiesgo = zonasDeRiesgo;
        this.logger = logger;
        setName("Zombie-" + id);
    }

    public String getZombieId() {
        return idZombie;
    }

    public int getMuertes() {
        return muertes;
    }

    public void detener() {
        activo = false;
        interrupt();  // Interrumpir el hilo si está en ejecución
    }

    private AreaRiesgo seleccionarZonaAleatoria() {
        int indice = aleatorio.nextInt(zonasDeRiesgo.size());
        return zonasDeRiesgo.get(indice);
    }

    private void esperarTiempoAleatorio(int base, int adicional) throws InterruptedException {
        int espera = base + (adicional > 0 ? aleatorio.nextInt(adicional + 1) : 0);
        Thread.sleep(espera);  // Pausar el hilo durante el tiempo calculado
    }

    // Metodo sincronizado para atacar a un humano
    private void atacarObjetivo(Humano humano) throws InterruptedException {
        synchronized (humano) {  // Protección contra otros hilos que puedan intentar modificar el humano al mismo tiempo
            if (!humano.isAlive()) return;  // Si el humano ya está muerto, no hace nada

            logger.log(getName() + " inicia ataque a " + humano.getName());
            humano.setSiendoAtacado(true);  // Marcar al humano como atacado

            int tiempoAtaque = TIEMPO_MIN_ATAQUE + aleatorio.nextInt(TIEMPO_ADICIONAL_ATAQUE + 1);
            esperarTiempoAleatorio(tiempoAtaque, 0);  // Esperar el tiempo de ataque

            if (humano.isAlive()) {
                double exito = aleatorio.nextDouble();
                if (exito > 0.66) {
                    humano.interrupt();  // Matar al humano interrumpiéndolo
                    muertes++;
                    logger.log(getName() + " eliminó a " + humano.getName());
                    logger.log(getName() + " ha eliminado " + muertes + " humanos en total.");
                } else {
                    humano.setHerido(true);
                    humano.setSiendoAtacado(false);  // El humano ha sido herido pero no muerto
                    logger.log(getName() + " solo hirió a " + humano.getName());
                }
            }
        }
    }

    @Override
    public void run() {
        while (activo && !isInterrupted()) {
            try {
                AreaRiesgo zonaActual = seleccionarZonaAleatoria();  // Seleccionar una zona aleatoria
                logger.log(getName() + " entra en zona de riesgo " + zonaActual.getNombre());

                zonaActual.añadirZombie(this);  // Registrar al zombi en la zona de riesgo

                Humano objetivo = zonaActual.obtenerObjetivoAleatorio().orElse(null);  // Seleccionar un humano, si existe

                if (objetivo != null) {
                    atacarObjetivo(objetivo);  // Atacar al humano si existe
                } else {
                    logger.log(getName() + " no encontró humanos en esta zona.");
                }

                esperarTiempoAleatorio(TIEMPO_MIN_ESPERA, TIEMPO_ADICIONAL_ESPERA);  // Esperar antes de moverse a otra zona

                zonaActual.eliminarZombie(this);  // Retirar al zombi de la zona de riesgo
                logger.log(getName() + " abandona la zona de riesgo.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Propagar la interrupción
                logger.log(getName() + " interrumpido durante la ejecución.");
            }
        }
    }
}
