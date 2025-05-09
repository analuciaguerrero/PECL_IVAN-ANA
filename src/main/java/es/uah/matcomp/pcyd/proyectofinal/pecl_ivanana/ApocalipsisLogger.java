package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase ApocalipsisLogger
 *
 * Funciona como un logger compartido para registrar eventos en un archivo
 * durante la simulación de la apocalipsis, garantizando que la escritura
 * en el archivo sea segura para los hilos concurrentes.
 *
 * Utiliza un mecanismo de sincronización para asegurar que los hilos no
 * escriban al mismo tiempo en el archivo y evitar la mezcla de mensajes.
 */
public class ApocalipsisLogger {
    private final PrintWriter escritor; // Para escribir en el archivo de log
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Constructor de ApocalipsisLogger
     *
     * @param nombreArchivo Nombre del archivo donde se almacenarán los logs.
     * @throws IOException Si ocurre algún error al abrir el archivo.
     */
    public ApocalipsisLogger(String nombreArchivo) throws IOException {
        // Inicialización del escritor con el archivo en modo de anexado (append)
        // y auto-flush activado para escribir inmediatamente después de cada mensaje.
        this.escritor = new PrintWriter(new FileWriter(nombreArchivo, true), true);
    }

    /**
     * Metodo para registrar un mensaje en el archivo de logs.
     * Este metodo está sincronizado para garantizar que solo un hilo pueda escribir
     * a la vez, evitando la mezcla de mensajes de múltiples hilos.
     *
     * @param mensaje El mensaje a registrar en el log.
     */
    public synchronized void log(String mensaje) {
        // Obtener la hora actual formateada como HH:mm:ss
        String tiempo = LocalDateTime.now().format(formatoHora);
        // Escribir el mensaje con la hora de registro
        escritor.println("[" + tiempo + "] " + mensaje);
    }

    /**
     * Metodo para cerrar el escritor de logs.
     * Es importante liberar los recursos al final de la ejecución.
     */
    public void cerrar() {
        if (escritor != null) {
            escritor.close();
        }
    }
}

