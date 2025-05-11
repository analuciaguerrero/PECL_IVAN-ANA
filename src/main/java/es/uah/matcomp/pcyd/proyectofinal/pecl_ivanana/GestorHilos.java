package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GestorHilos {

    private final List<Thread> hilosActivos;
    private final Text vista;

    public GestorHilos(Text vista) {
        this.hilosActivos = Collections.synchronizedList(new LinkedList<>());
        this.vista = vista;
    }

    public void añadir(Thread hilo) {
        synchronized (hilosActivos) {
            hilosActivos.add(hilo);
            actualizarVista();
        }
    }

    public void eliminar(Thread hilo) {
        synchronized (hilosActivos) {
            hilosActivos.remove(hilo);
            actualizarVista();
        }
    }

    private void actualizarVista() {
        if (vista == null) return;

        StringBuilder estado = new StringBuilder();
        synchronized (hilosActivos) {
            for (Thread t : hilosActivos) {
                estado.append(t.getName()).append(" ");
            }
        }

        String textoFinal = estado.toString().trim();

        Platform.runLater(() -> {
            if (vista.getScene() != null) {
                vista.setText(textoFinal);
            }
        });
    }

    public int cantidad() {
        return hilosActivos.size();
    }

    public Thread obtener(int pos) {
        synchronized (hilosActivos) {
            return hilosActivos.get(pos);
        }
    }
}
