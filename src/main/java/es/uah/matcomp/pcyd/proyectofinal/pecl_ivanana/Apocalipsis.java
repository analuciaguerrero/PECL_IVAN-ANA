package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.List;

public class Apocalipsis {

    private final List<AreaRiesgo> zonas;
    private final Refugio refugio;
    private final ApocalipsisLogger logger;
    public Apocalipsis(
            ApocalipsisLogger logger,
            GestorHilos zonaComun,
            GestorHilos descanso,
            GestorHilos comedor,
            GestorHilos[] entradas,
            GestorHilos[] tuneles,
            GestorHilos[] salidas,
            AreaRiesgo[] zonasRiesgo,
            GestorHilos[] zonasZombies
    ) {
        this.logger = logger;
        this.zonas = List.of(zonasRiesgo); // ✅ Usamos directamente las zonas que ya tienen visualizador
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

    public void actualizarEstadoZonas() {
        for (AreaRiesgo zona : zonas) {
            int numHumanos = zona.getNumHumanos();
            int numZombies = zona.getNumZombies();
            logger.log("Estado actual - " + zona.getNombre() + ": " +
                    numHumanos + " humanos, " + numZombies + " zombies.");
        }

        logger.log("Refugio: " + refugio.getOcupacionActual() + " / " + refugio.getCapacidad());
    }
}
