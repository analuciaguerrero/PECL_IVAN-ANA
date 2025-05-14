package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.concurrent.ThreadLocalRandom;

public class Zombie extends Thread {
    private final String[] id;
    private final ApocalipsisLogger logger;
    private final ControlPausa controlPausa;
    private final ZonaRiesgo[] zonas;
    private int victimas = 0;

    public Zombie(String[] id, ZonaRiesgo[] zonas, ControlPausa controlPausa, ApocalipsisLogger logger) {
        this.id = id;
        this.zonas = zonas;
        this.controlPausa = controlPausa;
        this.logger = logger;
    }

    public String getIdZombie() {
        return String.join("", id);
    }

    public int obtenerVictimas() {
        return victimas;
    }

    private void eliminarHumano(Humano objetivo, ZonaRiesgo zona) {
        logger.log("Humano " + objetivo.getIdHumanoNom() + " fue eliminado por el zombie " + getIdZombie()
                + " en la zona " + zona.getId() + ". Ahora es también un zombie.");
        objetivo.morir();
    }

    public void atacar(Humano victima, ZonaRiesgo zona) {
        try {
            controlPausa.verificarPausa();
            Thread.sleep(500 + ThreadLocalRandom.current().nextInt(1000));

            int resultado = ThreadLocalRandom.current().nextInt(3);
            if (resultado == 0) {
                String[] idHumano = victima.getIdHumano();
                eliminarHumano(victima, zona);

                String[] nuevoID = new String[]{"Z", idHumano[1], idHumano[2], idHumano[3], idHumano[4], idHumano[5]};
                new Zombie(nuevoID, zonas, controlPausa, logger).start();

                victimas++;
                victima.ejecutarDefensa();
            } else {
                logger.log("Humano " + victima.getIdHumanoNom() + " ha sido marcado por el zombie " + getIdZombie());
                victima.marcar(true);
                victima.ejecutarDefensa();
                victima.setEsperandoAtaque(false);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // buena práctica
            System.err.println("Zombie interrumpido durante ataque.");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                controlPausa.verificarPausa();

                int zonaSeleccionada = ThreadLocalRandom.current().nextInt(4);
                ZonaRiesgo zonaActual = zonas[zonaSeleccionada];
                logger.log("El zombie " + getIdZombie() + " ingresa a la zona de riesgo " + zonaSeleccionada);

                zonaActual.entrarZombie(this);

                Humano objetivo = zonaActual.seleccionarHumanoAleatorio(zonaActual);
                if (objetivo != null) {
                    objetivo.setEsperandoAtaque(true);
                    objetivo.interrupt();
                    atacar(objetivo, zonaActual);
                }

                Thread.sleep(2000 + ThreadLocalRandom.current().nextInt(1000));

                zonaActual.salirZombie(this);
                logger.log("El zombie " + getIdZombie() + " sale de la zona de riesgo " + zonaSeleccionada);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Zombie detenido.");
        }
    }
}
