package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControlPausa {
    private ApocalipsisLogger logger;
    private boolean enPausa = false;
    private ReentrantLock lockPausa = new ReentrantLock();
    private Condition reanudarCondicion = lockPausa.newCondition();

    public ControlPausa(ApocalipsisLogger log){
        this.logger = log;
    }

    public void verificarPausa(){
        lockPausa.lock();
        try {
            if (enPausa) {
                reanudarCondicion.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lockPausa.unlock();
        }
    }

    public void reanudarEjecucion(){
        logger.log("SE REANUDA LA EJECUCIÓN DEL SISTEMA");
        enPausa = false;
        lockPausa.lock();
        try {
            reanudarCondicion.signalAll();
        } finally {
            lockPausa.unlock();
        }
    }

    public void detenerEjecucion() {
        logger.log("SE DETIENE LA EJECUCIÓN DEL SISTEMA");
        enPausa = true;
    }
}
