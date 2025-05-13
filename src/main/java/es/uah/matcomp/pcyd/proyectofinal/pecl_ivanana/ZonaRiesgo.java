package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ZonaRiesgo {
    private int id;
    private ApocalipsisLogger logger;
    private InterfazServidor interfaz;
    private LinkedBlockingQueue<Zombie> listaZombies = new LinkedBlockingQueue<>();
    private ArrayList<Humano> listaHumanos = new ArrayList<>();

    public ZonaRiesgo(int idZona, ApocalipsisLogger logger, InterfazServidor ventanaUI) {
        this.id = idZona;
        this.logger = logger;
        this.interfaz = ventanaUI;
    }

    public int getId() {
        return id;
    }

    public LinkedBlockingQueue<Zombie> getListaZombies() {
        return listaZombies;
    }

    public ArrayList<Humano> getListaHumanos() {
        return listaHumanos;
    }

    public void entrarZombie(Zombie z) {
        listaZombies.add(z);
        SwingUtilities.invokeLater(() -> interfaz.mostrarZombisZonaPeligro(id));
    }

    public void entrarHumano(Humano h) {
        listaHumanos.add(h);
        SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosZonaPeligro(id));
    }

    public void salirZombie(Zombie z) {
        listaZombies.remove(z);
        SwingUtilities.invokeLater(() -> interfaz.mostrarZombisZonaPeligro(id));
    }

    public void salirHumano(Humano h) {
        listaHumanos.remove(h);
        SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosZonaPeligro(id));
    }

    public synchronized Humano seleccionarHumanoAleatorio(ZonaRiesgo zona) {
        if (zona.getListaHumanos().isEmpty()) {
            return null;
        } else {
            int totalHumanos = zona.getListaHumanos().size();
            int indice = (int) (totalHumanos * Math.random());
            Humano objetivo = zona.getListaHumanos().get(indice);
            zona.getListaHumanos().remove(objetivo);
            SwingUtilities.invokeLater(() -> interfaz.mostrarHumanosZonaPeligro(id));
            return objetivo;
        }
    }
}
