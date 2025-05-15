package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControlPausa {
    // Logger para registrar eventos importantes
    private ApocalipsisLogger logger;

    // Indicador del estado de pausa del sistema
    private boolean enPausa = false;

    // Cerrojo que protege el acceso al estado de pausa
    private ReentrantLock lockPausa = new ReentrantLock();

    // Condición para que los hilos esperen hasta reanudación
    private Condition reanudarCondicion = lockPausa.newCondition();

    // Constructor que recibe el logger para uso interno
    public ControlPausa(ApocalipsisLogger log){
        this.logger = log;
    }

    // Método que los hilos llaman para verificar si deben esperar debido a una pausa
    public void verificarPausa(){
        lockPausa.lock();  // Se adquiere el cerrojo para acceso seguro
        try {
            if (enPausa) {  // Si el sistema está en pausa, el hilo debe esperar
                reanudarCondicion.await();  // El hilo se bloquea hasta señal de reanudación
            }
        } catch (InterruptedException e) {
            // Si se interrumpe, se lanza RuntimeException para no ignorar la interrupción
            throw new RuntimeException(e);
        } finally {
            lockPausa.unlock();  // Siempre liberar el cerrojo
        }
    }

    // Método para reanudar la ejecución, liberando todos los hilos en espera
    public void reanudarEjecucion(){
        logger.log("SE REANUDA LA EJECUCIÓN DEL SISTEMA");  // Registro en log
        enPausa = false;  // Cambiar el estado a no pausado
        lockPausa.lock();  // Se adquiere el cerrojo para señalizar
        try {
            reanudarCondicion.signalAll();  // Desbloquea todos los hilos que estaban esperando
        } finally {
            lockPausa.unlock();  // Liberar el cerrojo
        }
    }

    // Método para detener la ejecución (poner en pausa)
    public void detenerEjecucion() {
        logger.log("SE DETIENE LA EJECUCIÓN DEL SISTEMA");  // Registro en log
        enPausa = true;  // Cambiar el estado a pausado
    }
}
