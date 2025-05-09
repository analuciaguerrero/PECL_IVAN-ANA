package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextField;

public class ApocalipsisLogger {
    private static ApocalipsisLogger instancia; // <-- Singleton
    private final PrintWriter escritorArchivo;
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
    private TextField[] zonasTxtField;
    private TextField cantidadComida;
    private TextField[] zombiesTxtField;

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

        if (zonasTxtField != null) {
            for (TextField zona : zonasTxtField) {
                zona.appendText(linea + "\n");
            }
        }
    }

    public void setInterfaz(TextField[] zonas, TextField cantidadComida, TextField[] zombies) {
        this.zonasTxtField = zonas;
        this.cantidadComida = cantidadComida;
        this.zombiesTxtField = zombies;
    }

    public void cerrar() {
        if (escritorArchivo != null) {
            escritorArchivo.close();
        }
    }
}
