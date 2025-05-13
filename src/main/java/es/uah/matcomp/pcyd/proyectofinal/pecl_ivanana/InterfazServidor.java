
package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;


import javax.swing.*;

public class InterfazServidor extends javax.swing.JFrame {

    private Apocalipsis apocalipsis;

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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;

    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;

    private javax.swing.JPanel jPanelRefugio;
    private javax.swing.JPanel jPanelDescanso;

    private javax.swing.JPanel jPanelTuneles;
    private javax.swing.JPanel jPanelTunel0;
    private javax.swing.JPanel jPanelTunel1;
    private javax.swing.JPanel jPanelTunel2;
    private javax.swing.JPanel jPanelTunel3;
    private javax.swing.JPanel jPanelTunel4;
    private javax.swing.JPanel jPanelTunel5;
    private javax.swing.JPanel jPanelTunel6;
    private javax.swing.JPanel jPanelTunel7;

    private javax.swing.JPanel jPaneZonaRiesgo;

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;

    private javax.swing.JScrollPane jScrollPaneListaRegresando0;
    private javax.swing.JScrollPane jScrollPaneListaRegresando1;
    private javax.swing.JScrollPane jScrollPaneListaRegresando2;
    private javax.swing.JScrollPane jScrollPaneListaRegresando3;
    private javax.swing.JScrollPane jScrollPaneListaRegresando4;
    private javax.swing.JScrollPane jScrollPaneListaRegresando6;
    private javax.swing.JScrollPane jScrollPaneListaRegresando7;
    private javax.swing.JScrollPane jScrollPaneListaRegresando8;

    private javax.swing.JTextArea jTextAreaHumanosZonaComun;
    private javax.swing.JTextArea jTextAreaHumanosDescanso;
    private javax.swing.JTextArea jTextAreaHumanosComedor;
    private javax.swing.JTextField jTextFieldNumComida;

    private javax.swing.JList<String> jListZombis0;
    private javax.swing.JList<String> jListZombis1;
    private javax.swing.JList<String> jListZombis2;
    private javax.swing.JList<String> jListZombis3;

    private javax.swing.JList<String> jListPasar0;
    private javax.swing.JList<String> jListPasar1;
    private javax.swing.JList<String> jListPasar2;
    private javax.swing.JList<String> jListPasar3;

    private javax.swing.JList<String> jListPasando0;
    private javax.swing.JList<String> jListPasando1;
    private javax.swing.JList<String> jListPasando2;
    private javax.swing.JList<String> jListPasando3;

    private javax.swing.JList<String> jListHumanosZona0;
    private javax.swing.JList<String> jListHumanosZona1;
    private javax.swing.JList<String> jListHumanosZona2;
    private javax.swing.JList<String> jListHumanosZona3;

    private javax.swing.JList<String> jListRegresando0;
    private javax.swing.JList<String> jListRegresando1;
    private javax.swing.JList<String> jListRegresando2;
    private javax.swing.JList<String> jListRegresando3;

    public InterfazServidor(Apocalipsis apocalipsis) {
        setTitle("Apocalipsis Zombie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        this.apocalipsis = apocalipsis;
    }

    private void jTextFieldNumComidaActionPerformed(java.awt.event.ActionEvent evt) {}

    public void mostrarHumanosZonaComun() {

        jTextAreaHumanosZonaComun.setText("");
        for (Humano persona : apocalipsis.getZonaComun().getListaHumanosZonaComun()) {
            jTextAreaHumanosZonaComun.append(" " + persona.getIdHumanoNom());
        }
        jTextAreaHumanosZonaComun.setCaretPosition(jTextAreaHumanosZonaComun.getDocument().getLength());
    }

    public void mostrandoHumanosDescansando() {

        jTextAreaHumanosDescanso.setText("");
        for (Humano hu : apocalipsis.getZonaDescanso().getListaHumanosDescansando()) {
            jTextAreaHumanosDescanso.append(hu.getIdHumanoNom()+" ");
        }
        jTextAreaHumanosDescanso.setCaretPosition(jTextAreaHumanosDescanso.getDocument().getLength());
    }

    public void mostrarHumanosComedor() {

        jTextAreaHumanosComedor.setText("");
        for (Humano persona : apocalipsis.getComedor().getListaHumanosComedor()) {
            jTextAreaHumanosComedor.append(" " + persona.getIdHumanoNom());
        }
        jTextAreaHumanosComedor.setCaretPosition(jTextAreaHumanosComedor.getDocument().getLength());
    }

    public void actualizarCantidadComida() {
        jTextFieldNumComida.setText(apocalipsis.getComedor().getComida().toString());
    }

    public void refrescarListaEntradaTunel(int indice) {
        DefaultListModel<String> modelo = new DefaultListModel<>();

        switch (indice) {
            case 0:
                jListPasar0.removeAll();
                for (Humano persona : apocalipsis.getTunel()[0].getColaEspera()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListPasar0.setModel(modelo);
                break;
            case 1:
                jListPasar1.removeAll();
                for (Humano persona : apocalipsis.getTunel()[1].getColaEspera()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListPasar1.setModel(modelo);
                break;
            case 2:
                jListPasar2.removeAll();
                for (Humano persona : apocalipsis.getTunel()[2].getColaEspera()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListPasar2.setModel(modelo);
                break;
            case 3:
                jListPasar3.removeAll();
                for (Humano persona : apocalipsis.getTunel()[3].getColaEspera()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListPasar3.setModel(modelo);
                break;
        }
    }

    public void refrescarListaTransitando(int indice) {
        DefaultListModel<String> modelo = new DefaultListModel<>();

        switch (indice) {
            case 0:
                jListPasando0.removeAll();
                for (Humano persona : apocalipsis.getTunel()[0].getColaAtravesando()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListPasando0.setModel(modelo);
                break;
            case 1:
                jListPasando1.removeAll();
                for (Humano persona : apocalipsis.getTunel()[1].getColaAtravesando()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListPasando1.setModel(modelo);
                break;
            case 2:
                jListPasando2.removeAll();
                for (Humano persona : apocalipsis.getTunel()[2].getColaAtravesando()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListPasando2.setModel(modelo);
                break;
            case 3:
                jListPasando3.removeAll();
                for (Humano persona : apocalipsis.getTunel()[3].getColaAtravesando()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListPasando3.setModel(modelo);
                break;
        }
    }

    public void actualizarRetornoTunel(int indice) {
        DefaultListModel<String> modelo = new DefaultListModel<>();

        switch (indice) {
            case 0:
                jListRegresando0.removeAll();
                for (Humano persona : apocalipsis.getTunel()[0].getColaRegreso()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListRegresando0.setModel(modelo);
                break;
            case 1:
                jListRegresando1.removeAll();
                for (Humano persona : apocalipsis.getTunel()[1].getColaRegreso()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListRegresando1.setModel(modelo);
                break;
            case 2:
                jListRegresando2.removeAll();
                for (Humano persona : apocalipsis.getTunel()[2].getColaRegreso()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListRegresando2.setModel(modelo);
                break;
            case 3:
                jListRegresando3.removeAll();
                for (Humano persona : apocalipsis.getTunel()[3].getColaRegreso()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListRegresando3.setModel(modelo);
                break;
        }
    }

    public void mostrarZombisZonaPeligro(int indice) {
        DefaultListModel<String> modelo = new DefaultListModel<>();

        switch (indice) {
            case 0:
                jListZombis0.removeAll();
                for (Zombie z : apocalipsis.getZonaRiesgo()[0].getListaZombies()) {
                    modelo.addElement(z.getIdZombie());
                }
                jListZombis0.setModel(modelo);
                break;
            case 1:
                jListZombis1.removeAll();
                for (Zombie z : apocalipsis.getZonaRiesgo()[1].getListaZombies()) {
                    modelo.addElement(z.getIdZombie());
                }
                jListZombis1.setModel(modelo);
                break;
            case 2:
                jListZombis2.removeAll();
                for (Zombie z : apocalipsis.getZonaRiesgo()[2].getListaZombies()) {
                    modelo.addElement(z.getIdZombie());
                }
                jListZombis2.setModel(modelo);
                break;
            case 3:
                jListZombis3.removeAll();
                for (Zombie z : apocalipsis.getZonaRiesgo()[3].getListaZombies()) {
                    modelo.addElement(z.getIdZombie());
                }
                jListZombis3.setModel(modelo);
                break;
        }
    }


    public void mostrarHumanosZonaPeligro(int indice) {
        DefaultListModel<String> modelo = new DefaultListModel<>();

        switch (indice) {
            case 0:
                jListHumanosZona0.removeAll();
                for (Humano persona : apocalipsis.getZonaRiesgo()[0].getListaHumanos()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListHumanosZona0.setModel(modelo);
                break;
            case 1:
                jListHumanosZona1.removeAll();
                for (Humano persona : apocalipsis.getZonaRiesgo()[1].getListaHumanos()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListHumanosZona1.setModel(modelo);
                break;
            case 2:
                jListHumanosZona2.removeAll();
                for (Humano persona : apocalipsis.getZonaRiesgo()[2].getListaHumanos()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListHumanosZona2.setModel(modelo);
                break;
            case 3:
                jListHumanosZona3.removeAll();
                for (Humano persona : apocalipsis.getZonaRiesgo()[3].getListaHumanos()) {
                    modelo.addElement(persona.getIdHumanoNom());
                }
                jListHumanosZona3.setModel(modelo);
                break;
        }
    }
    /**
     * Este método se ejecuta desde el constructor para configurar la interfaz.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelRefugio = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelDescanso = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaHumanosDescanso = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaHumanosComedor = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldNumComida = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaHumanosZonaComun = new javax.swing.JTextArea();
        jPanelTuneles = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanelTunel0 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPaneListaRegresando0 = new javax.swing.JScrollPane();
        jListRegresando0 = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListPasando0 = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        jListPasar0 = new javax.swing.JList<>();
        jPanelTunel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPaneListaRegresando6 = new javax.swing.JScrollPane();
        jListRegresando1 = new javax.swing.JList<>();
        jScrollPane21 = new javax.swing.JScrollPane();
        jListPasando1 = new javax.swing.JList<>();
        jScrollPane22 = new javax.swing.JScrollPane();
        jListPasar1 = new javax.swing.JList<>();
        jPanelTunel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        jListPasar2 = new javax.swing.JList<>();
        jScrollPane24 = new javax.swing.JScrollPane();
        jListPasando2 = new javax.swing.JList<>();
        jScrollPaneListaRegresando7 = new javax.swing.JScrollPane();
        jListRegresando2 = new javax.swing.JList<>();
        jPanelTunel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jListPasar3 = new javax.swing.JList<>();
        jScrollPane26 = new javax.swing.JScrollPane();
        jListPasando3 = new javax.swing.JList<>();
        jScrollPaneListaRegresando8 = new javax.swing.JScrollPane();
        jListRegresando3 = new javax.swing.JList<>();
        jPaneZonaRiesgo = new javax.swing.JPanel();
        jPanelTunel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPaneListaRegresando1 = new javax.swing.JScrollPane();
        jListHumanosZona0 = new javax.swing.JList<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        jListZombis0 = new javax.swing.JList<>();
        jPanelTunel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPaneListaRegresando2 = new javax.swing.JScrollPane();
        jListHumanosZona1 = new javax.swing.JList<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        jListZombis1 = new javax.swing.JList<>();
        jPanelTunel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPaneListaRegresando3 = new javax.swing.JScrollPane();
        jListHumanosZona3 = new javax.swing.JList<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        jListZombis3 = new javax.swing.JList<>();
        jPanelTunel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPaneListaRegresando4 = new javax.swing.JScrollPane();
        jListHumanosZona2 = new javax.swing.JList<>();
        jScrollPane11 = new javax.swing.JScrollPane();
        jListZombis2 = new javax.swing.JList<>();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelRefugio.setBackground(new java.awt.Color(102, 255, 255));
        jPanelRefugio.setPreferredSize(new java.awt.Dimension(200, 400));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REFUGIO");

        jPanelDescanso.setBackground(new java.awt.Color(102, 102, 102));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Descansando");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jTextAreaHumanosDescanso.setColumns(20);
        jTextAreaHumanosDescanso.setRows(5);
        jScrollPane1.setViewportView(jTextAreaHumanosDescanso);

        javax.swing.GroupLayout jPanelDescansoLayout = new javax.swing.GroupLayout(jPanelDescanso);
        jPanelDescanso.setLayout(jPanelDescansoLayout);
        jPanelDescansoLayout.setHorizontalGroup(
            jPanelDescansoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDescansoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDescansoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelDescansoLayout.setVerticalGroup(
            jPanelDescansoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDescansoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setForeground(new java.awt.Color(153, 255, 255));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Comedor");

        jTextAreaHumanosComedor.setColumns(20);
        jTextAreaHumanosComedor.setRows(5);
        jScrollPane2.setViewportView(jTextAreaHumanosComedor);

        jLabel4.setText("Comida");

        jTextFieldNumComida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNumComidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextFieldNumComida, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 1, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNumComida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Zona común");

        jTextAreaHumanosZonaComun.setColumns(20);
        jTextAreaHumanosZonaComun.setRows(5);
        jScrollPane3.setViewportView(jTextAreaHumanosZonaComun);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelRefugioLayout = new javax.swing.GroupLayout(jPanelRefugio);
        jPanelRefugio.setLayout(jPanelRefugioLayout);
        jPanelRefugioLayout.setHorizontalGroup(
            jPanelRefugioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRefugioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRefugioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRefugioLayout.createSequentialGroup()
                        .addGroup(jPanelRefugioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelDescanso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 29, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelRefugioLayout.setVerticalGroup(
            jPanelRefugioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRefugioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelDescanso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanelTuneles.setBackground(new java.awt.Color(255, 153, 0));
        jPanelTuneles.setPreferredSize(new java.awt.Dimension(200, 400));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("TUNELES");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("TUNEL 0");

        jScrollPaneListaRegresando0.setViewportView(jListRegresando0);

        jListPasando0.setVisibleRowCount(3);
        jScrollPane5.setViewportView(jListPasando0);

        jListPasar0.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(jListPasar0);

        javax.swing.GroupLayout jPanelTunel0Layout = new javax.swing.GroupLayout(jPanelTunel0);
        jPanelTunel0.setLayout(jPanelTunel0Layout);
        jPanelTunel0Layout.setHorizontalGroup(
            jPanelTunel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel0Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTunel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelTunel0Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneListaRegresando0, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTunel0Layout.setVerticalGroup(
            jPanelTunel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTunel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(jScrollPaneListaRegresando0, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("TUNEL 1");

        jScrollPaneListaRegresando6.setViewportView(jListRegresando1);

        jListPasando1.setVisibleRowCount(3);
        jScrollPane21.setViewportView(jListPasando1);

        jListPasar1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane22.setViewportView(jListPasar1);

        javax.swing.GroupLayout jPanelTunel1Layout = new javax.swing.GroupLayout(jPanelTunel1);
        jPanelTunel1.setLayout(jPanelTunel1Layout);
        jPanelTunel1Layout.setHorizontalGroup(
            jPanelTunel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTunel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTunel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneListaRegresando6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTunel1Layout.setVerticalGroup(
            jPanelTunel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTunel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneListaRegresando6, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("TUNEL 2");

        jListPasar2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane23.setViewportView(jListPasar2);

        jListPasando2.setVisibleRowCount(3);
        jScrollPane24.setViewportView(jListPasando2);

        jScrollPaneListaRegresando7.setViewportView(jListRegresando2);

        javax.swing.GroupLayout jPanelTunel2Layout = new javax.swing.GroupLayout(jPanelTunel2);
        jPanelTunel2.setLayout(jPanelTunel2Layout);
        jPanelTunel2Layout.setHorizontalGroup(
            jPanelTunel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTunel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelTunel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneListaRegresando7, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTunel2Layout.setVerticalGroup(
            jPanelTunel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTunel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane24)
                    .addComponent(jScrollPaneListaRegresando7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("TUNEL 3");

        jListPasar3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane25.setViewportView(jListPasar3);

        jListPasando3.setVisibleRowCount(3);
        jScrollPane26.setViewportView(jListPasando3);

        jScrollPaneListaRegresando8.setViewportView(jListRegresando3);

        javax.swing.GroupLayout jPanelTunel3Layout = new javax.swing.GroupLayout(jPanelTunel3);
        jPanelTunel3.setLayout(jPanelTunel3Layout);
        jPanelTunel3Layout.setHorizontalGroup(
            jPanelTunel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTunel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelTunel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneListaRegresando8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTunel3Layout.setVerticalGroup(
            jPanelTunel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTunel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTunel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane26)
                    .addComponent(jScrollPaneListaRegresando8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelTunelesLayout = new javax.swing.GroupLayout(jPanelTuneles);
        jPanelTuneles.setLayout(jPanelTunelesLayout);
        jPanelTunelesLayout.setHorizontalGroup(
            jPanelTunelesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunelesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTunelesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(jPanelTunel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTunel0, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTunel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTunel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanelTunelesLayout.setVerticalGroup(
            jPanelTunelesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunelesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelTunel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelTunel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelTunel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelTunel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPaneZonaRiesgo.setBackground(new java.awt.Color(51, 255, 51));
        jPaneZonaRiesgo.setPreferredSize(new java.awt.Dimension(200, 400));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("ZONA 0");

        jScrollPaneListaRegresando1.setViewportView(jListHumanosZona0);

        jListZombis0.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane8.setViewportView(jListZombis0);

        javax.swing.GroupLayout jPanelTunel4Layout = new javax.swing.GroupLayout(jPanelTunel4);
        jPanelTunel4.setLayout(jPanelTunel4Layout);
        jPanelTunel4Layout.setHorizontalGroup(
            jPanelTunel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanelTunel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPaneListaRegresando1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanelTunel4Layout.setVerticalGroup(
            jPanelTunel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTunel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPaneListaRegresando1, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("ZONA 1");

        jScrollPaneListaRegresando2.setViewportView(jListHumanosZona1);

        jListZombis1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane9.setViewportView(jListZombis1);

        javax.swing.GroupLayout jPanelTunel5Layout = new javax.swing.GroupLayout(jPanelTunel5);
        jPanelTunel5.setLayout(jPanelTunel5Layout);
        jPanelTunel5Layout.setHorizontalGroup(
            jPanelTunel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanelTunel5Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPaneListaRegresando2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanelTunel5Layout.setVerticalGroup(
            jPanelTunel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTunel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPaneListaRegresando2, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ZONA 3");

        jScrollPaneListaRegresando3.setViewportView(jListHumanosZona3);

        jListZombis3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane10.setViewportView(jListZombis3);

        javax.swing.GroupLayout jPanelTunel6Layout = new javax.swing.GroupLayout(jPanelTunel6);
        jPanelTunel6.setLayout(jPanelTunel6Layout);
        jPanelTunel6Layout.setHorizontalGroup(
            jPanelTunel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanelTunel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPaneListaRegresando3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanelTunel6Layout.setVerticalGroup(
            jPanelTunel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTunel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPaneListaRegresando3, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("ZONA 2");

        jScrollPaneListaRegresando4.setViewportView(jListHumanosZona2);

        jListZombis2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane11.setViewportView(jListZombis2);

        javax.swing.GroupLayout jPanelTunel7Layout = new javax.swing.GroupLayout(jPanelTunel7);
        jPanelTunel7.setLayout(jPanelTunel7Layout);
        jPanelTunel7Layout.setHorizontalGroup(
            jPanelTunel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanelTunel7Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPaneListaRegresando4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanelTunel7Layout.setVerticalGroup(
            jPanelTunel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTunel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTunel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPaneListaRegresando4, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("ZONAS DE RIESGO");

        javax.swing.GroupLayout jPaneZonaRiesgoLayout = new javax.swing.GroupLayout(jPaneZonaRiesgo);
        jPaneZonaRiesgo.setLayout(jPaneZonaRiesgoLayout);
        jPaneZonaRiesgoLayout.setHorizontalGroup(
            jPaneZonaRiesgoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneZonaRiesgoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPaneZonaRiesgoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTunel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTunel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTunel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTunel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPaneZonaRiesgoLayout.setVerticalGroup(
            jPaneZonaRiesgoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneZonaRiesgoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelTunel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelTunel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jPanelTunel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelTunel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelRefugio, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanelTuneles, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPaneZonaRiesgo, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelRefugio, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
            .addComponent(jPaneZonaRiesgo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
            .addComponent(jPanelTuneles, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
}
