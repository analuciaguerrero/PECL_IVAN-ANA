package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase Interfaz que representa la interfaz gráfica de la simulación
 * de apocalipsis zombie. Permite pausar o reanudar la simulación,
 * mostrar el estado del refugio, túneles, zonas de riesgo, y zombies.
 * Utiliza un objeto Pause para controlar la pausa de los hilos.
 */
public class Interfaz extends JFrame {

    private final Pause pausa;

    // Componentes de la interfaz gráfica
    private JButton btnPausar;
    private JTextField refugioField, tunelesField1, tunelesField2, tunelesField3, tunelesField4;
    private JTextField riesgoField1, riesgoField2, riesgoField3;
    private JTextField zombisField1, zombisField2, zombisField3;
    private JTextArea rankingArea;
    private JButton detenerButton;

    /**
     * Constructor de la interfaz gráfica.
     *
     * @param pausa Objeto que maneja la pausa/reanudación de la simulación.
     */
    public Interfaz(Pause pausa) {
        this.pausa = pausa;

        // Configuración básica de la ventana
        configurarVentana();

        // Crear los componentes de la interfaz
        inicializarComponentes();

        // Configurar el botón de pausa
        configurarBotonPausar();

        // Configurar el botón de detener
        configurarBotonDetener();
    }

    private void configurarVentana() {
        setTitle("Simulación de Apocalipsis Zombie");
        setSize(450, 600);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Campos de texto no editables para mostrar información
        refugioField = crearCampoTextoDeshabilitado();
        tunelesField1 = crearCampoTextoDeshabilitado();
        tunelesField2 = crearCampoTextoDeshabilitado();
        tunelesField3 = crearCampoTextoDeshabilitado();
        tunelesField4 = crearCampoTextoDeshabilitado();
        riesgoField1 = crearCampoTextoDeshabilitado();
        riesgoField2 = crearCampoTextoDeshabilitado();
        riesgoField3 = crearCampoTextoDeshabilitado();
        zombisField1 = crearCampoTextoDeshabilitado();
        zombisField2 = crearCampoTextoDeshabilitado();
        zombisField3 = crearCampoTextoDeshabilitado();

        // Área de texto para mostrar el ranking de los zombis más letales
        rankingArea = new JTextArea(4, 20);
        rankingArea.setEditable(false);
        rankingArea.setLineWrap(true);
        rankingArea.setWrapStyleWord(true);

        JScrollPane scrollRanking = new JScrollPane(rankingArea);

        // Botón de detener ejecución
        detenerButton = new JButton("Detener ejecución");

        // Añadir los componentes al panel principal
        panelPrincipal.add(new JLabel("Número de humanos en el refugio"));
        panelPrincipal.add(refugioField);

        panelPrincipal.add(new JLabel("Número de humanos en los túneles"));
        JPanel panelTuneles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTuneles.add(tunelesField1);
        panelTuneles.add(tunelesField2);
        panelTuneles.add(tunelesField3);
        panelTuneles.add(tunelesField4);
        panelPrincipal.add(panelTuneles);

        panelPrincipal.add(new JLabel("Número de humanos en las zonas de riesgo"));
        JPanel panelRiesgo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRiesgo.add(riesgoField1);
        panelRiesgo.add(riesgoField2);
        panelRiesgo.add(riesgoField3);
        panelPrincipal.add(panelRiesgo);

        panelPrincipal.add(new JLabel("Número de zombis en las zonas de riesgo"));
        JPanel panelZombis = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelZombis.add(zombisField1);
        panelZombis.add(zombisField2);
        panelZombis.add(zombisField3);
        panelPrincipal.add(panelZombis);

        panelPrincipal.add(new JLabel("Zombis más letales"));
        panelPrincipal.add(scrollRanking);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(detenerButton);

        // Botón para pausar o reanudar la simulación
        btnPausar = new JButton("Pausar");
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnPausar);

        // Agregar el panel principal al JFrame
        add(panelPrincipal);
    }

    private JTextField crearCampoTextoDeshabilitado() {
        JTextField campoTexto = new JTextField(3);
        campoTexto.setEditable(false);
        return campoTexto;
    }

    private void configurarBotonPausar() {
        btnPausar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Pausar".equals(btnPausar.getText())) {
                    pausa.actualizarEstadoPausa(true);  // Pausar la simulación
                    btnPausar.setText("Reanudar");
                } else {
                    pausa.actualizarEstadoPausa(false);  // Reanudar la simulación
                    btnPausar.setText("Pausar");
                }
            }
        });
    }

    private void configurarBotonDetener() {
        detenerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí debes agregar la lógica para detener la simulación
                System.exit(0); // Ejemplo simple: Cerrar la aplicación
            }
        });
    }

    public void actualizarRefugio(int numHumanos) {
        refugioField.setText(String.valueOf(numHumanos));
    }

    public void actualizarTunnels(int[] numHumanosTuneles) {
        tunelesField1.setText(String.valueOf(numHumanosTuneles[0]));
        tunelesField2.setText(String.valueOf(numHumanosTuneles[1]));
        tunelesField3.setText(String.valueOf(numHumanosTuneles[2]));
        tunelesField4.setText(String.valueOf(numHumanosTuneles[3]));
    }

    public void actualizarZombisRiesgo(int[] numZombisRiesgo) {
        zombisField1.setText(String.valueOf(numZombisRiesgo[0]));
        zombisField2.setText(String.valueOf(numZombisRiesgo[1]));
        zombisField3.setText(String.valueOf(numZombisRiesgo[2]));
    }

    public void actualizarRanking(String ranking) {
        rankingArea.setText(ranking);
    }

    public static void main(String[] args) {
        Pause pausa = new Pause();
        Interfaz interfaz = new Interfaz(pausa);
        interfaz.setVisible(true);
    }
}

