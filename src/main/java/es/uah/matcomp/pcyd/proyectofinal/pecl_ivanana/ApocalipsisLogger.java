package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class ApocalipsisLogger {

    // Semáforo para controlar el acceso exclusivo al archivo de log
    private Semaphore accesoArchivo = new Semaphore(1, true);

    // Ruta del archivo donde se guardarán los mensajes de log
    private String ARCHIVO_LOG = "logs/apocalipsis.txt";

    // Método para preparar el archivo de log antes de iniciar la escritura
    public void prepararArchivo() {
        try {
            File archivo = new File(ARCHIVO_LOG);
            // Si el archivo ya existe, se elimina para comenzar con uno nuevo
            if (archivo.exists()) {
                archivo.delete();
            }
            // Se crea un nuevo archivo vacío para el log
            archivo.createNewFile();
        } catch (IOException e) {
            System.out.println("No se pudo preparar el archivo de registro.");
        }
    }

    // Método para añadir un mensaje al archivo de log con una marca de tiempo
    public void log(String mensaje){
        try {
            // Adquirimos el semáforo para asegurar acceso exclusivo
            accesoArchivo.acquire();

            // Abrimos el archivo en modo append (agregar al final)
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_LOG, true))) {
                // Obtenemos la fecha y hora actual formateada
                String marcaTiempo = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss").format(new Date());
                // Escribimos la marca de tiempo y el mensaje
                escritor.write(marcaTiempo + " - " + mensaje);
                escritor.newLine(); // Nueva línea para la siguiente entrada
            } catch (IOException e) {
                System.out.println("Ocurrió un error al escribir en el archivo.");
            }
        } catch (InterruptedException e) {
            System.out.println("Se interrumpió el acceso al archivo de registro.");
        } finally {
            // Liberamos el semáforo para permitir que otros hilos puedan escribir
            accesoArchivo.release();
        }
    }
}
