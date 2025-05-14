package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.concurrent.ThreadLocalRandom;

public class Zombie extends Thread{
    private String[] id;
    private ApocalipsisLogger logger;
    private ControlPausa controlPausa;
    private ZonaRiesgo[] zonas;
    private int victimas = 0;

    public Zombie(String[] id, ZonaRiesgo[] zonas, ControlPausa controlPausa, ApocalipsisLogger logger) {
        this.id = id;
        this.zonas = zonas;
        this.controlPausa = controlPausa;
        this.logger = logger;
    }

    public String getIdZombie() {
        String nom = "";
        for (int i = 0; i < 6; i++) {
            nom += id[i];
        }
        return nom;
    }

    public int obtenerVictimas() {
        return victimas;
    }

    public void eliminarHumano(Humano objetivo, ZonaRiesgo zona) {
        controlPausa.verificarPausa();
        logger.log("Humano " + objetivo.getIdHumanoNom() + " fue eliminado por el zombie " + getIdZombie() + " en la zona " + zona.getId() + ". Por lo que ahora es también un zombie.");
        objetivo.morir();
    }

    public void atacar(Humano victima, ZonaRiesgo zona) {
        try {
            controlPausa.verificarPausa();
            sleep(500 + (int) (Math.random()*1000));
            controlPausa.verificarPausa();
            int res = (int) (Math.random()*3);
            if (res == 0) {
                controlPausa.verificarPausa();
                String[] idHumano = victima.getIdHumano();
                controlPausa.verificarPausa();
                eliminarHumano(victima,zona);
                controlPausa.verificarPausa();
                String[] nuevoID = new String[]{"Z", idHumano[1], idHumano[2], idHumano[3], idHumano[4], idHumano[5]};
                new Zombie(nuevoID, zonas, controlPausa, logger).start();
                controlPausa.verificarPausa();
                victimas++;
                victima.ejecutarDefensa();
            }
            else {
                controlPausa.verificarPausa();
                logger.log("Humano " + victima.getIdHumanoNom() + " ha sido marcado por el zombie " + getIdZombie());
                victima.marcar(true);
                victima.ejecutarDefensa();
                controlPausa.verificarPausa();
                victima.setEsperandoAtaque(false);
            }
        } catch (InterruptedException ie) {
            System.out.println("Error en ataque.");
        }
    }


    public void run() {
        try {
            while (true) {
                controlPausa.verificarPausa(); // Comprobamos la pausa
                int zonaSeleccionada = (int) (4 * Math.random()); // Elegimos una zona de riesgo aleatoria
                logger.log("El zombie " + getIdZombie() + " ingresa a la zona de riesgo " + zonaSeleccionada);
                ZonaRiesgo zonaActual = zonas[zonaSeleccionada];
                controlPausa.verificarPausa();
                zonaActual.entrarZombie(this); // Metemos el zombie a la zona de riesgo
                controlPausa.verificarPausa();
                Humano objetivo = zonaActual.seleccionarHumanoAleatorio(zonaActual); // Elegimos un humano aleatorio
                controlPausa.verificarPausa();
                if(!(objetivo == null)){
                    controlPausa.verificarPausa();
                    objetivo.setEsperandoAtaque(true);
                    objetivo.interrupt();
                    controlPausa.verificarPausa();
                    atacar(objetivo, zonaActual);
                    controlPausa.verificarPausa();
                }
                sleep(2000 + (int) (Math.random()*1000)); // Simulamos el tiempo de espera en la zona de riesgo
                controlPausa.verificarPausa();
                logger.log("El zombie " + getIdZombie() + " va a la zona de riesgo " + zonaSeleccionada);
                zonaActual.salirZombie(this);
                controlPausa.verificarPausa();
            }
        } catch (InterruptedException ie) {
            System.out.println("Excepción detectada.");
        }
    }
}
