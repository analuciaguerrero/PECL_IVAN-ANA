
package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import javax.swing.DefaultListModel;
import es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana.ejecucion.ClienteController;

public class InterfazCliente extends javax.swing.JFrame {
    private ClienteController clienteController;

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;

    // REFUGIO
    private javax.swing.JTextField jTextFieldHumanosRefugio;
    private javax.swing.JTextField jTextFieldHumanosZComun;
    private javax.swing.JTextField jTextFieldHumanosZDescanso;
    private javax.swing.JTextField jTextFieldHumanosComedor;

    // ZOMBIES EN ZONA DE RIESGO
    private javax.swing.JTextField jTextFieldZombisZR1;
    private javax.swing.JTextField jTextFieldZombisZR2;
    private javax.swing.JTextField jTextFieldZombisZR3;
    private javax.swing.JTextField jTextFieldZombisZR4;

    // TUNEL
    private javax.swing.JTextField jTextFieldTunel1;
    private javax.swing.JTextField jTextFieldTunel2;
    private javax.swing.JTextField jTextFieldTunel3;
    private javax.swing.JTextField jTextFieldTunel4;

    // HUMANOS EN ZONA DE RIESGO
    private javax.swing.JTextField jTextFieldHumanosZR1;
    private javax.swing.JTextField jTextFieldHumanosZR2;
    private javax.swing.JTextField jTextFieldHumanosZR3;
    private javax.swing.JTextField jTextFieldHumanosZR4;

    private javax.swing.JToggleButton jToggleButtonParar;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;

    public InterfazCliente(ClienteController clienteController) {
        initComponents();
        this.clienteController = clienteController;
    }

    // MÉTODOS PARA IMPLEMENTAR LA INTERFAZ CLIENTE

    private void jTextFieldHumanosRefugioActionPerformed(java.awt.event.ActionEvent evt) {
    }
    private void jTextFieldHumanosZComunActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldHumanosZDescansoActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldHumanosComedorActionPerformed(java.awt.event.ActionEvent evt) {}

    private void jTextFieldZombisZR1ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldZombisZR2ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldZombisZR3ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldZombisZR4ActionPerformed(java.awt.event.ActionEvent evt) {}

    private void jTextFieldHumanosZR1ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldHumanosZR2ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldHumanosZR3ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldHumanosZR4ActionPerformed(java.awt.event.ActionEvent evt) {}

    private void jTextFieldTunel1ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldTunel2ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldTunel3ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jTextFieldTunel4ActionPerformed(java.awt.event.ActionEvent evt) {}

    private void jToggleButtonPararActionPerformed(java.awt.event.ActionEvent evt) {
        if (clienteController.isDetener()){
            jToggleButtonParar.setText("Detener ejecución");
        }else{
            jToggleButtonParar.setText("Reanudar Apocalipsis");
        }
        clienteController.alternarEstadoDetener();
        clienteController.activarCambio();
    }

    public void cargarDatos(){

        // REFUGIO
        jTextFieldHumanosRefugio.setText(""+(clienteController.getZonaComun()+clienteController.getComedor()+clienteController.getZonaDescanso()));
        jTextFieldHumanosZComun.setText(""+clienteController.getZonaComun());
        jTextFieldHumanosZDescanso.setText(""+clienteController.getZonaDescanso());
        jTextFieldHumanosComedor.setText(""+clienteController.getComedor());

        // ZOMBIES EN ZONA DE RIESGO
        jTextFieldZombisZR1.setText(""+clienteController.getZombieZonaRiesgo1());
        jTextFieldZombisZR2.setText(""+clienteController.getZombieZonaRiesgo2());
        jTextFieldZombisZR3.setText(""+clienteController.getZombieZonaRiesgo3());
        jTextFieldZombisZR4.setText(""+clienteController.getZombieZonaRiesgo4());

        // HUMANOS EN ZONA DE RIESGO
        jTextFieldHumanosZR1.setText(""+clienteController.getZonaRiesgo1());
        jTextFieldHumanosZR2.setText(""+clienteController.getZonaRiesgo2());
        jTextFieldHumanosZR3.setText(""+clienteController.getZonaRiesgo3());
        jTextFieldHumanosZR4.setText(""+clienteController.getZonaRiesgo4());

        // TUNEL
        jTextFieldTunel1.setText(""+(clienteController.getEnColaTunel1()+clienteController.getCruzandoTunel1()+clienteController.getRegresandoTunel1()));
        jTextFieldTunel2.setText(""+(clienteController.getEnColaTunel2()+clienteController.getCruzandoTunel2()+clienteController.getRegresandoTunel2()));
        jTextFieldTunel3.setText(""+(clienteController.getEnColaTunel3()+clienteController.getCruzandoTunel3()+clienteController.getRegresandoTunel3()));
        jTextFieldTunel4.setText(""+(clienteController.getEnColaTunel4()+clienteController.getCruzandoTunel4()+clienteController.getRegresandoTunel4()));

        // TOP ZOMBIES
        DefaultListModel<String> top = new DefaultListModel<>();
        jList1.removeAll();
        for (int i = 0; i<clienteController.getTopZombies().size(); i++) {
            top.addElement(clienteController.getTopZombies().get(i));
        }
        jList1.setModel(top);
    }

    public void limpiarCampos(){
        jTextFieldHumanosRefugio.removeAll();
        jTextFieldTunel1.removeAll();
        jTextFieldTunel2.removeAll();
        jTextFieldTunel3.removeAll();
        jTextFieldTunel4.removeAll();
        jTextFieldHumanosZR1.removeAll();
        jTextFieldHumanosZR2.removeAll();
        jTextFieldHumanosZR3.removeAll();
        jTextFieldHumanosZR4.removeAll();
        jTextFieldZombisZR1.removeAll();
        jTextFieldZombisZR2.removeAll();
        jTextFieldZombisZR3.removeAll();
        jTextFieldZombisZR4.removeAll();
        jList1.removeAll();
    }
    
    public void refrescarInterfaz(){
        limpiarCampos();
        cargarDatos();
    }

    /**
     * Este método se ejecuta desde el constructor para configurar la interfaz.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jToggleButtonParar = new javax.swing.JToggleButton();
        jTextFieldTunel1 = new javax.swing.JTextField();
        jTextFieldHumanosRefugio = new javax.swing.JTextField();
        jTextFieldHumanosZR1 = new javax.swing.JTextField();
        jTextFieldHumanosZR2 = new javax.swing.JTextField();
        jTextFieldHumanosZR3 = new javax.swing.JTextField();
        jTextFieldHumanosZR4 = new javax.swing.JTextField();
        jTextFieldZombisZR1 = new javax.swing.JTextField();
        jTextFieldZombisZR2 = new javax.swing.JTextField();
        jTextFieldZombisZR3 = new javax.swing.JTextField();
        jTextFieldZombisZR4 = new javax.swing.JTextField();
        jTextFieldTunel2 = new javax.swing.JTextField();
        jTextFieldTunel3 = new javax.swing.JTextField();
        jTextFieldTunel4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldHumanosZComun = new javax.swing.JTextField();
        jTextFieldHumanosZDescanso = new javax.swing.JTextField();
        jTextFieldHumanosComedor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Número de humanos en el refugio");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Número de humanos en las zonas de riesgo");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Número de zombis en las zonas de riesgo");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Número de humanos en los túneles");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Zombis más letales");

        jList1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jScrollPane1.setViewportView(jList1);

        jToggleButtonParar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jToggleButtonParar.setText("Detener ejecución");
        jToggleButtonParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonPararActionPerformed(evt);
            }
        });

        jTextFieldTunel1.setEditable(false);
        jTextFieldTunel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTunel1ActionPerformed(evt);
            }
        });

        jTextFieldHumanosRefugio.setEditable(false);
        jTextFieldHumanosRefugio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHumanosRefugioActionPerformed(evt);
            }
        });

        jTextFieldHumanosZR1.setEditable(false);
        jTextFieldHumanosZR1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHumanosZR1ActionPerformed(evt);
            }
        });

        jTextFieldHumanosZR2.setEditable(false);
        jTextFieldHumanosZR2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHumanosZR2ActionPerformed(evt);
            }
        });

        jTextFieldHumanosZR3.setEditable(false);
        jTextFieldHumanosZR3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHumanosZR3ActionPerformed(evt);
            }
        });

        jTextFieldHumanosZR4.setEditable(false);
        jTextFieldHumanosZR4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHumanosZR4ActionPerformed(evt);
            }
        });

        jTextFieldZombisZR1.setEditable(false);
        jTextFieldZombisZR1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldZombisZR1ActionPerformed(evt);
            }
        });

        jTextFieldZombisZR2.setEditable(false);
        jTextFieldZombisZR2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldZombisZR2ActionPerformed(evt);
            }
        });

        jTextFieldZombisZR3.setEditable(false);
        jTextFieldZombisZR3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldZombisZR3ActionPerformed(evt);
            }
        });

        jTextFieldZombisZR4.setEditable(false);
        jTextFieldZombisZR4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldZombisZR4ActionPerformed(evt);
            }
        });

        jTextFieldTunel2.setEditable(false);
        jTextFieldTunel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTunel2ActionPerformed(evt);
            }
        });

        jTextFieldTunel3.setEditable(false);
        jTextFieldTunel3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTunel3ActionPerformed(evt);
            }
        });

        jTextFieldTunel4.setEditable(false);
        jTextFieldTunel4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTunel4ActionPerformed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("En A");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("En B");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("En C");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("En D");

        jTextFieldHumanosZComun.setEditable(false);
        jTextFieldHumanosZComun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHumanosZComunActionPerformed(evt);
            }
        });

        jTextFieldHumanosZDescanso.setEditable(false);
        jTextFieldHumanosZDescanso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHumanosZDescansoActionPerformed(evt);
            }
        });

        jTextFieldHumanosComedor.setEditable(false);
        jTextFieldHumanosComedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHumanosComedorActionPerformed(evt);
            }
        });

        jLabel10.setText("Común");

        jLabel11.setText("Descanso");

        jLabel12.setText("Comedor");

        jLabel13.setText("Suma");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(101, 101, 101)
                            .addComponent(jToggleButtonParar, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextFieldZombisZR1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldZombisZR2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldZombisZR3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldZombisZR4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextFieldHumanosZR1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldHumanosZR2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldHumanosZR3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldHumanosZR4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextFieldTunel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldTunel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldTunel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldTunel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextFieldHumanosZComun, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldHumanosZDescanso, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldHumanosComedor, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldHumanosRefugio, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel13)
                            .addGap(7, 7, 7))))
                .addGap(26, 70, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHumanosRefugio, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHumanosZComun, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHumanosZDescanso, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHumanosComedor, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTunel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTunel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTunel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTunel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHumanosZR1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHumanosZR2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHumanosZR3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHumanosZR4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldZombisZR1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldZombisZR2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldZombisZR3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldZombisZR4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButtonParar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
}
