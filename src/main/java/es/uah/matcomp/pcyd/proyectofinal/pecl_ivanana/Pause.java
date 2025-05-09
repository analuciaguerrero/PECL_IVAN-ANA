package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

public class Pause {
    private boolean estaPausado = false;

    // -------------------------------------------------------------------------
    // 🟠 Metodo sincronizado para cambiar el estado de pausa
    // Cambia el estado de la simulación entre "pausado" y "en ejecución"
    public synchronized void actualizarEstadoPausa(boolean estadoPausa) {
        this.estaPausado = estadoPausa;

        if (!estadoPausa) {
            // Si la simulación se reanuda, despertamos todos los hilos en espera
            System.out.println("Simulación reanudada.");
            notifyAll(); // Desbloquea los hilos que están en espera
        } else {
            // Si la simulación se pausa, podríamos imprimir un mensaje para trazar la pausa
            System.out.println("Simulación pausada.");
        }
    }

    // -------------------------------------------------------------------------
    // 🟣 Metodo sincronizado para verificar el estado de pausa
    // Los hilos deben llamar a este metodo para saber si deben esperar
    public synchronized void verificarPausa() {
        while (estaPausado) {
            try {
                wait(); // El hilo se bloquea mientras la simulación está pausada
            } catch (InterruptedException e) {
                // Si un hilo es interrumpido mientras está esperando, restauramos su estado de interrupción
                Thread.currentThread().interrupt();
            }
        }
    }
}


