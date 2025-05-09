package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Apocalipsis {

    public static void iniciarSimulacion() {
        ApocalipsisLogger logger = null;
        try {
            logger = new ApocalipsisLogger("logs/apocalipsis.txt");
        } catch (IOException e) {
            System.err.println("Error al inicializar el logger: " + e.getMessage());
            return;
        }

        Pause simulacionPausa = new Pause();

        List<AreaRiesgo> zonasDeRiesgo = new ArrayList<>();
        zonasDeRiesgo.add(new AreaRiesgo("Zona riesgo 1"));
        zonasDeRiesgo.add(new AreaRiesgo("Zona riesgo 2"));
        zonasDeRiesgo.add(new AreaRiesgo("Zona riesgo 3"));
        zonasDeRiesgo.add(new AreaRiesgo("Zona riesgo 4"));

        List<Tunel> tuneles = new ArrayList<>();
        tuneles.add(new Tunel(3));
        tuneles.add(new Tunel(4));
        tuneles.add(new Tunel(2));
        tuneles.add(new Tunel(5));

        Refugio refugio = new Refugio(10, logger);

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            String nombreHumano = "H" + String.format("%04d", i);
            Humano humano = new Humano(nombreHumano, zonasDeRiesgo, refugio, logger);
            humano.start();

            try {
                int tiempoEspera = 500 + random.nextInt(1500);
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 1; i++) {
            Zombie zombie = new Zombie("Z0000", zonasDeRiesgo, logger);
            zombie.start();
        }

        simulacionPausa.actualizarEstadoPausa(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        simulacionPausa.actualizarEstadoPausa(false);
    }
}


