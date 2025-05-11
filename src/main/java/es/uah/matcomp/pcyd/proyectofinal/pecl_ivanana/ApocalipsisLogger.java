package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Platform;
import javafx.scene.text.Text;

public class ApocalipsisLogger {
    private static ApocalipsisLogger instancia;
    private final PrintWriter escritorArchivo;
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Text[] zonasTxtField;
    private Text cantidadComida;
    private Text[] zombiesTxtField;
    private Text eventosGenerales;

    private ApocalipsisLogger() throws IOException {
        File carpeta = new File("logs");
        if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw new IOException("No se pudo crear la carpeta de logs.");
        }

        File archivoLog = new File(carpeta, "apocalipsis.txt");
        try {
            this.escritorArchivo = new PrintWriter(new FileWriter(archivoLog, true), true);
        } catch (IOException e) {
            throw new IOException("Error al abrir el archivo de log.", e);
        }
    }

    public static synchronized ApocalipsisLogger getInstancia() throws IOException {
        if (instancia == null) {
            instancia = new ApocalipsisLogger();
        }
        return instancia;
    }

    public synchronized void log(String mensaje) {
        String tiempo = LocalDateTime.now().format(formatoHora);
        String linea = "[" + tiempo + "] " + mensaje;

        escritorArchivo.println(linea);
        System.out.println(linea);

        if (eventosGenerales != null) {
            Platform.runLater(() -> {
                String textoActual = eventosGenerales.getText();
                eventosGenerales.setText(textoActual + linea + "\n");
            });
        }
    }

    public void setInterfaz(Text[] zonas, Text cantidadComida, Text[] zombies, Text eventos) {
        this.zonasTxtField = zonas;
        this.cantidadComida = cantidadComida;
        this.zombiesTxtField = zombies;
        this.eventosGenerales = eventos;
    }

    public void cerrar() {
        if (escritorArchivo != null) {
            escritorArchivo.close();
        }
    }
}
