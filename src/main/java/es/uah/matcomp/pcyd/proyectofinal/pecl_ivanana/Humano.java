package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Humano extends Thread {
    private String[] id;
    private ControlPausa controlPausa;
    private ZonaComun zonaComun;
    private ZonaDescanso zonaDescanso;
    private Comedor comedor;
    private Tunel[] tunel;
    private AtomicBoolean enEsperaAtaque = new AtomicBoolean(false);
    private CyclicBarrier sincronizadorAtaque = new CyclicBarrier(2);
    private boolean estaMarcado = false;
    private boolean muerto = false;

    public Humano(String[] id, Comedor comedor, Tunel[] tunel, ZonaComun zonaComun, ZonaDescanso zonaDescanso, ControlPausa controlPausa) {
        this.id = id;
        this.comedor = comedor;
        this.tunel = tunel;
        this.zonaComun = zonaComun;
        this.zonaDescanso = zonaDescanso;
        this.controlPausa = controlPausa;
    }

    public String[] getIdHumano() {
        return id;
    }

    public String getIdHumanoNom() {
        String nom = "";
        for (int i = 0; i < 6; i++) {
            nom += id[i];
        }
        return nom;
    }

    public void setIdHumano(String[] id) {
        this.id = id;
    }

    public Tunel[] getTunel() {
        return tunel;
    }

    public boolean estaEsperandoAtaque() {
        return enEsperaAtaque.get();
    }

    public void setEsperandoAtaque(boolean estado) {
        this.enEsperaAtaque.set(estado);
    }

    public void ejecutarDefensa(){
        try {
            sincronizadorAtaque.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean estaMarcado() {
        return estaMarcado;
    }

    public void marcar(boolean marcado) {
        estaMarcado = marcado;
    }

    public boolean isMuerto(){
        return muerto;
    }

    public void morir(){
        muerto = true;
    }

    public void verificarPausa(){
        controlPausa.verificarPausa();
    }

    public void run() {

        while (!muerto) {
            controlPausa.verificarPausa();
            zonaComun.entrarZonaComun(this);
            controlPausa.verificarPausa();
            zonaComun.prepararse(this);
            controlPausa.verificarPausa();
            zonaComun.explorarExterior(this);
            if(!muerto){
                controlPausa.verificarPausa();
                comedor.entregarComida(this);
                controlPausa.verificarPausa();
                zonaDescanso.descansar(this);
                controlPausa.verificarPausa();
                comedor.comer(this);
                controlPausa.verificarPausa();
                if (estaMarcado) {
                    zonaDescanso.descansarHumano(this);
                    controlPausa.verificarPausa();
                }
            }
        }
        System.out.println("Finalizó el humano " + getIdHumanoNom());
    }
}
