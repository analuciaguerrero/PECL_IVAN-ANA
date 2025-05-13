package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class ApocalipsisLogger {

    private Semaphore accesoArchivo = new Semaphore(1, true);
    private String ARCHIVO_LOG = "logs/apocalipsis.txt";

    public void prepararArchivo() {
        try {
            File archivo = new File(ARCHIVO_LOG);
            // si el archivo ya existe, lo borramos
            if (archivo.exists()) {
                archivo.delete();
            }
            // crear nuevo archivo
            archivo.createNewFile();
        } catch (IOException e) {
            System.out.println("No se pudo preparar el archivo de registro.");
        }
    }

    public void log(String mensaje){
        try {
            accesoArchivo.acquire();
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_LOG, true))) {
                String marcaTiempo = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss").format(new Date());
                escritor.write(marcaTiempo + " - " + mensaje);
                escritor.newLine();
            } catch (IOException e) {
                System.out.println("Ocurrió un error al escribir en el archivo.");
            }
        } catch (InterruptedException e) {
            System.out.println("Se interrumpió el acceso al archivo de registro.");
        } finally {
            accesoArchivo.release();
        }
    }
}
