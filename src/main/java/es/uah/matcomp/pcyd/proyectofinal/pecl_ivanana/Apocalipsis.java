package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Apocalipsis {
    private List<AreaRiesgo> zonas;
    private Refugio refugio;
    private ApocalipsisLogger logger;

    public Apocalipsis(TextField[] zonasText, TextField cantidadComida, TextField[] zombiesText) throws IOException {
        this.logger = ApocalipsisLogger.getInstancia();
        this.zonas = new ArrayList<>();

        // Crear zonas de riesgo
        for (int i = 0; i < 4; i++) {
            zonas.add(new AreaRiesgo("Zona-" + i));
        }

        // Crear refugio
        this.refugio = new Refugio(6, logger); // Capacidad fija de 6
    }

    public List<AreaRiesgo> getZonas() {
        return zonas;  // Devuelve la lista completa de zonas
    }

    public Refugio getRefugio() {
        return refugio;
    }

    public ApocalipsisLogger getLogger() {
        return logger;
    }
}
