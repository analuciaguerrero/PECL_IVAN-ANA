package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.ArrayList;
import java.util.List;

public class Apocalipsis {
    private final List<AreaRiesgo> zonas;
    private final Refugio refugio;
    private final ApocalipsisLogger logger;

    public Apocalipsis(ApocalipsisLogger logger) {
        this.logger = logger;
        this.zonas = new ArrayList<>();

        // Crear zonas de riesgo
        for (int i = 0; i < 4; i++) {
            zonas.add(new AreaRiesgo("Zona-" + i, logger)); // ✅ Asegúrate que cada zona tenga acceso al logger
        }

        // Crear refugio con capacidad fija de 6
        this.refugio = new Refugio(6, logger);
    }

    public List<AreaRiesgo> getZonas() {
        return zonas;
    }

    public AreaRiesgo getZona(int index) {
        if (index >= 0 && index < zonas.size()) {
            return zonas.get(index);
        } else {
            throw new IndexOutOfBoundsException("Índice de zona inválido: " + index);
        }
    }

    public Refugio getRefugio() {
        return refugio;
    }

    public ApocalipsisLogger getLogger() {
        return logger;
    }

    // ✅ Método para actualizar visualmente los contadores de humanos/zombies si lo deseas
    public void actualizarEstadoZonas() {
        for (int i = 0; i < zonas.size(); i++) {
            AreaRiesgo zona = zonas.get(i);
            int numHumanos = zona.getNumHumanos();
            int numZombies = zona.getNumZombies();

            logger.log("Estado actual - " + zona.getNombre() + ": " +
                    numHumanos + " humanos, " + numZombies + " zombies.");
        }

        logger.log("Refugio: " + refugio.getOcupacionActual() + " / " + refugio.getCapacidad());
    }
}
