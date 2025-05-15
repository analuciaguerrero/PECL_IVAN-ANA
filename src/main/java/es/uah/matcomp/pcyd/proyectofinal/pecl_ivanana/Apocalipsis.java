package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Apocalipsis {
    // Logger para registrar eventos en archivo
    private ApocalipsisLogger logger = new ApocalipsisLogger();

    // Controlador de pausa para detener y reanudar la simulación
    private ControlPausa controlPausa = new ControlPausa(logger);

    // Interfaz gráfica del servidor
    private InterfazServidor interfaz = new InterfazServidor(this);

    // Identificadores para zombis y humanos
    private String[] idZ = new String[5]; // ID Zombie
    private String[] idH = new String[5]; // ID Humano

    // Zonas del entorno
    private ZonaRiesgo[] zonas = new ZonaRiesgo[4];
    private ZonaComun zonaComun;
    private ZonaDescanso zonaDescanso;

    // Comedor y túneles
    private Comedor comedor = new Comedor(logger, interfaz);
    private Tunel[] tunel = new Tunel[4];

    // Sistema de clasificación de humanos
    private Clasificacion clasificacion = new Clasificacion(zonas);

    // Executor para ejecutar en segundo plano
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    // Constructor: muestra la interfaz gráfica
    public Apocalipsis(){
        interfaz.setVisible(true);
    }

    // Getters para acceder a las zonas desde otras clases
    public ZonaRiesgo[] getZonaRiesgo() {
        return zonas;
    }

    public ZonaComun getZonaComun() {
        return zonaComun;
    }

    public ZonaDescanso getZonaDescanso() {
        return zonaDescanso;
    }

    public Comedor getComedor() {
        return comedor;
    }

    public Tunel[] getTunel() {
        return tunel;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    // Método para reanudar ejecución desde la interfaz
    public void reanudar(){
        controlPausa.reanudarEjecucion();
    }

    // Método para pausar ejecución desde la interfaz
    public void detener(){
        controlPausa.detenerEjecucion();
    }

    // Lanza la inicialización de la simulación en un hilo separado
    public void ejecutarEnSegundoPlano() {
        executor.submit(this::inicializar);
    }

    // Inicializa todas las zonas y crea los hilos de humanos y zombis
    private void inicializar() {
        // Preparar archivo de log
        logger.prepararArchivo();

        // Crear zonas de riesgo
        for (int i = 0; i < 4; i++) {
            zonas[i] = new ZonaRiesgo(i, logger, interfaz);
        }

        // Crear túneles asociados a cada zona
        for (int i = 0; i < 4; i++) {
            tunel[i] = new Tunel(i, zonas[i], logger, interfaz);
        }

        // Crear zona común y zona de descanso
        zonaComun = new ZonaComun(tunel, logger, interfaz);
        zonaDescanso = new ZonaDescanso(logger, interfaz);

        // Crear zombi (solo uno con ID "Z0000")
        idZ[0] = "Z";
        for (int i = 1; i <= 4; i++) {
            idZ[i] = "0";
        }
        new Zombie(idZ, zonas, controlPausa, logger).start();

        // Generación progresiva de humanos
        int recuento = 1;
        for (int t = 0; t < 10; t++) {
            for (int k = 0; k < 10; k++) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        // Formato de ID: HXXXX (H0001, H0002, ...)
                        String numero = String.format("%04d", recuento);
                        idH[0] = "H";
                        idH[1] = String.valueOf(numero.charAt(0));
                        idH[2] = String.valueOf(numero.charAt(1));
                        idH[3] = String.valueOf(numero.charAt(2));
                        idH[4] = String.valueOf(numero.charAt(3));

                        // Mostrar por consola
                        System.out.println(String.join("", idH));

                        // Crear y lanzar hilo Humano
                        new Humano(idH.clone(), comedor, tunel, zonaComun, zonaDescanso, controlPausa).start();

                        recuento++;

                        // Comprobar si se ha pausado la simulación
                        controlPausa.verificarPausa();

                        // Espera aleatoria entre 0.5s y 2s
                        try {
                            Thread.sleep(500 + (int) (Math.random() * 1500));
                        } catch (InterruptedException e) {
                            System.out.println("Error generando humano");
                        }
                    }
                }
            }
        }
    }
}
