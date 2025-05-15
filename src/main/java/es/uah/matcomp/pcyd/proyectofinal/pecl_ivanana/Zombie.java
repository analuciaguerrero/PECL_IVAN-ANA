package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;


// Clase que representa un zombie como hilo independiente
public class Zombie extends Thread {
    // Identificador del zombie (compuesto por 5 partes)
    private String[] id;

    // Logger para registrar acciones
    private ApocalipsisLogger logger;

    // Objeto que gestiona pausas del sistema
    private ControlPausa controlPausa;

    // Zonas de riesgo donde puede moverse el zombie
    private ZonaRiesgo[] zonas;

    // Contador de humanos eliminados por este zombie
    private int victimas = 0;

    // Constructor del zombie
    public Zombie(String[] id, ZonaRiesgo[] zonas, ControlPausa controlPausa, ApocalipsisLogger logger) {
        this.id = id;
        this.zonas = zonas;
        this.controlPausa = controlPausa;
        this.logger = logger;
    }

    // Devuelve el ID del zombie como String concatenado
    public String getIdZombie() {
        String nom = "";
        for (int i = 0; i < 5; i++) {
            nom += id[i];
        }
        return nom;
    }

    // Devuelve la cantidad de víctimas que ha generado este zombie
    public int obtenerVictimas() {
        return victimas;
    }

    // Elimina a un humano y lo transforma en zombie
    public void eliminarHumano(Humano objetivo, ZonaRiesgo zona) {
        controlPausa.verificarPausa();
        logger.log("Humano " + objetivo.getIdHumanoNom() + " fue eliminado por el zombie " + getIdZombie() +
                " en la zona " + zona.getId() + ". Por lo que ahora es también un zombie.");
        objetivo.morir();  // Marca al humano como muerto
    }

    // Lógica del ataque del zombie a un humano
    public void atacar(Humano victima, ZonaRiesgo zona) {
        try {
            controlPausa.verificarPausa();

            // Simula tiempo de ataque
            sleep(500 + (int) (Math.random() * 1000));

            controlPausa.verificarPausa();

            // Resultado del ataque (0 = éxito)
            int res = (int) (Math.random() * 3);

            if (res == 0) {
                // Si el ataque tiene éxito
                controlPausa.verificarPausa();
                String[] idHumano = victima.getIdHumano();
                controlPausa.verificarPausa();
                eliminarHumano(victima, zona);

                // Se crea un nuevo zombie a partir del humano eliminado
                String[] nuevoID = new String[]{"Z", idHumano[1], idHumano[2], idHumano[3], idHumano[4]};
                new Zombie(nuevoID, zonas, controlPausa, logger).start();

                controlPausa.verificarPausa();
                victimas++;  // Se incrementa el contador de víctimas

                // Se ejecuta posible defensa del humano (aunque ya ha sido eliminado)
                victima.ejecutarDefensa();
            } else {
                // Si el ataque no es exitoso, solo se marca al humano
                controlPausa.verificarPausa();
                logger.log("Humano " + victima.getIdHumanoNom() + " ha sido marcado por el zombie " + getIdZombie());
                victima.marcar(true);
                victima.ejecutarDefensa();
                controlPausa.verificarPausa();
                victima.setEsperandoAtaque(false);
            }

        } catch (InterruptedException ie) {
            System.out.println("Error en ataque.");
        }
    }

    // Método principal del hilo zombie
    public void run() {
        try {
            while (true) {
                // Verifica si el sistema está pausado
                controlPausa.verificarPausa();

                // Selección aleatoria de zona de riesgo
                int zonaSeleccionada = (int) (4 * Math.random());
                logger.log("El zombie " + getIdZombie() + " ingresa a la zona de riesgo " + zonaSeleccionada);

                ZonaRiesgo zonaActual = zonas[zonaSeleccionada];

                controlPausa.verificarPausa();

                // El zombie entra en la zona seleccionada
                zonaActual.entrarZombie(this);

                controlPausa.verificarPausa();

                // Intenta seleccionar un humano aleatorio dentro de la zona
                Humano objetivo = zonaActual.seleccionarHumanoAleatorio(zonaActual);

                controlPausa.verificarPausa();

                if (objetivo != null) {
                    // Si hay un humano, se prepara para el ataque
                    controlPausa.verificarPausa();
                    objetivo.setEsperandoAtaque(true);
                    objetivo.interrupt(); // Despierta al humano si estaba esperando
                    controlPausa.verificarPausa();
                    atacar(objetivo, zonaActual);
                    controlPausa.verificarPausa();
                }

                // Simula el tiempo de espera antes de salir de la zona
                sleep(2000 + (int) (Math.random() * 1000));

                controlPausa.verificarPausa();

                logger.log("El zombie " + getIdZombie() + " va a la zona de riesgo " + zonaSeleccionada);

                // Sale de la zona
                zonaActual.salirZombie(this);

                controlPausa.verificarPausa();
            }
        } catch (InterruptedException ie) {
            System.out.println("Excepción detectada.");
        }
    }
}
