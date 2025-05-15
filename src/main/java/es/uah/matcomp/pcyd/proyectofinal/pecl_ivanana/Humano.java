package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

// Esta clase representa un humano en la simulación. Hereda de Thread.
public class Humano extends Thread {

    // Identificador del humano (formado por 5 letras)
    private String[] id;

    // Referencias a zonas del entorno
    private ControlPausa controlPausa;
    private ZonaComun zonaComun;
    private ZonaDescanso zonaDescanso;
    private Comedor comedor;
    private Tunel[] tunel;

    // Estado del humano respecto a un posible ataque
    private AtomicBoolean enEsperaAtaque = new AtomicBoolean(false);
    private CyclicBarrier sincronizadorAtaque = new CyclicBarrier(2); // Barrera para coordinar con el zombi

    private boolean estaMarcado = false; // Si fue atacado
    private boolean muerto = false;      // Si ha muerto

    // Constructor del humano. Recibe todas las zonas que va a usar
    public Humano(String[] id, Comedor comedor, Tunel[] tunel, ZonaComun zonaComun, ZonaDescanso zonaDescanso, ControlPausa controlPausa) {
        this.id = id;
        this.comedor = comedor;
        this.tunel = tunel;
        this.zonaComun = zonaComun;
        this.zonaDescanso = zonaDescanso;
        this.controlPausa = controlPausa;
    }

    // Devuelve el identificador como array
    public String[] getIdHumano() {
        return id;
    }

    // Devuelve el identificador como string concatenado
    public String getIdHumanoNom() {
        String nom = "";
        for (int i = 0; i < 5; i++) {
            nom += id[i];
        }
        return nom;
    }

    // Permite cambiar el ID
    public void setIdHumano(String[] id) {
        this.id = id;
    }

    // Devuelve los túneles disponibles
    public Tunel[] getTunel() {
        return tunel;
    }

    // ¿Está esperando un ataque?
    public boolean estaEsperandoAtaque() {
        return enEsperaAtaque.get();
    }

    // Cambia el estado de espera por ataque
    public void setEsperandoAtaque(boolean estado) {
        this.enEsperaAtaque.set(estado);
    }

    // Espera en la barrera al zombi para defenderse
    public void ejecutarDefensa(){
        try {
            sincronizadorAtaque.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    // ¿Está marcado por ataque?
    public boolean estaMarcado() {
        return estaMarcado;
    }

    // Cambia el estado de "marcado"
    public void marcar(boolean marcado) {
        estaMarcado = marcado;
    }

    // ¿Está muerto?
    public boolean isMuerto(){
        return muerto;
    }

    // Mata al humano
    public void morir(){
        muerto = true;
    }

    // Verifica si hay que pausar
    public void verificarPausa(){
        controlPausa.verificarPausa();
    }

    // Método principal: lo que hace el humano mientras esté vivo
    public void run() {
        while (!muerto) {
            controlPausa.verificarPausa();

            zonaComun.entrarZonaComun(this); // Entra a la zona común
            controlPausa.verificarPausa();

            zonaComun.prepararse(this); // Se prepara para salir
            controlPausa.verificarPausa();

            zonaComun.explorarExterior(this); // Sale al exterior y vuelve

            if (!muerto) {
                controlPausa.verificarPausa();

                comedor.entregarComida(this); // Pide comida
                controlPausa.verificarPausa();

                zonaDescanso.descansar(this); // Descansa después de salir
                controlPausa.verificarPausa();

                comedor.comer(this); // Come
                controlPausa.verificarPausa();

                if (estaMarcado) {
                    zonaDescanso.descansarHumano(this); // Descanso extra si fue atacado
                    controlPausa.verificarPausa();
                }
            }
        }

        System.out.println("Finalizó el humano " + getIdHumanoNom());
    }
}
