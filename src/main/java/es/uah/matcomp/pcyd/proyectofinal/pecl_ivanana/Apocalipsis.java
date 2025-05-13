package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Apocalipsis {
    private ApocalipsisLogger logger = new ApocalipsisLogger();
    private ControlPausa controlPausa = new ControlPausa(logger);
    private InterfazServidor interfaz = new InterfazServidor(this);
    private String[] idZ = new String[6]; // ID Zombie
    private String[] idH = new String[6]; // ID Humano
    private ZonaRiesgo[] zonas = new ZonaRiesgo[4];
    private ZonaComun zonaComun;
    private ZonaDescanso zonaDescanso;
    private Comedor comedor = new Comedor(logger, interfaz);
    private Tunel[] tunel = new Tunel[4];
    private Clasificacion clasificacion = new Clasificacion(zonas);
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Apocalipsis(){
        interfaz.setVisible(true);
    }
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

    public void reanudar(){
        controlPausa.reanudarEjecucion();
    }

    public void detener(){
        controlPausa.detenerEjecucion();
    }

    public void ejecutarEnSegundoPlano() {
        executor.submit(this::inicializar);
    }

    private void inicializar() {

        logger.prepararArchivo();

        for (int i = 0; i < 4; i++) {
            zonas[i] = new ZonaRiesgo(i, logger, interfaz);
        }

        for (int i = 0; i < 4; i++) {
            tunel[i] = new Tunel(i, zonas[i], logger, interfaz);
        }
        zonaComun = new ZonaComun(tunel, logger, interfaz);
        zonaDescanso = new ZonaDescanso(logger, interfaz);

        // Creamos zombies
        idZ[0] = "Z";
        for (int i = 1; i <= 5; i++) {
            idZ[i] = "0";
        }
        new Zombie(idZ, zonas, controlPausa, logger).start();

        // Creamos humanos
        int recuento = 1;
        for (int t = 0; t < 10; t++) {
            for (int k = 0; k < 10; k++) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        String numero = String.format("%05d", recuento);
                        idH[0] = "H";
                        idH[1] = String.valueOf(numero.charAt(0));
                        idH[2] = String.valueOf(numero.charAt(1));
                        idH[3] = String.valueOf(numero.charAt(2));
                        idH[4] = String.valueOf(numero.charAt(3));
                        idH[5] = String.valueOf(numero.charAt(4));
                        System.out.println(String.join("", idH));
                        new Humano(idH.clone(), comedor, tunel, zonaComun, zonaDescanso, controlPausa).start();
                        recuento++;
                        controlPausa.verificarPausa();
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
