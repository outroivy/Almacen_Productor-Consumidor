package Leon_Mont;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Pantalla {

    private JFrame ventana;
    protected static JTextArea almacen1, almacen2;
    protected static JTable hilosL, hilosM, hilosLT, hilosMT;
    private String[] columnas, columnasT;
    private Object[][] data, dataT;
    private JScrollPane scrollA1, scrollA2, scrollHL, scrollHM, scrollTA1, scrollTA2;
    protected static JProgressBar progresoA1, progresoA2;
    private JButton btn_play, btn_replay, btn_terminar, btn_agregarL, btn_agregarM, btn_quitarL, btn_quitarM;
    private JMenuBar menu;
    private JMenu jmenu_hilos, jmenu_pausa, jmenu_reiniciar, jmenu_terminar, jmenu_leon, jmenu_monterrey;
    private JMenuItem jmenu_agregarL, jmenu_quitarL, jmenu_agregarM, jmenu_quitarM;
    private Border greenline, blueline;
    private int numHilosL, numHilosM;
    private Controlador c;
    private controladorM m;
    protected static JLabel totalL, totalM;
    private JLabel fondo;
    private Productor[] ProductoresL, ProductoresM;
    private Consumidor[] ConsumidoresL, ConsumidoresM;
    private boolean pausa;
    private Color c_transparente, c_verde, c_azul;
    private Font fuente, fuente_bold;

    public Pantalla() {
        ventana = new JFrame("Productor-Consumidor. León-Monterrey");
        menu = new JMenuBar();
        jmenu_hilos = new JMenu("Hilos");
        jmenu_leon = new JMenu("León");
        jmenu_monterrey = new JMenu("Monterrey");
        jmenu_agregarL = new JMenuItem("Agregar");
        jmenu_quitarL = new JMenuItem("Eliminar");
        jmenu_agregarM = new JMenuItem("Agregar");
        jmenu_quitarM = new JMenuItem("Eliminar");
        jmenu_pausa = new JMenu("Pausa");
        jmenu_reiniciar = new JMenu("Reiniciar");
        jmenu_terminar = new JMenu("Terminar");
        almacen1 = new JTextArea();
        almacen2 = new JTextArea();
        columnas = new String[]{"Camión", "P", "C"};
        columnasT = new String[]{"Total", "P", "C"};
        data = new Object[][]{{null, null, null}, {null, null, null}, {null, null, null},
        {null, null, null}, {null, null, null}, {null, null, null},
        {null, null, null}, {null, null, null}, {null, null, null},
        {null, null, null}};
        dataT = new Object[][]{{null, null, null}};
        hilosL = new JTable(data, columnas);
        hilosM = new JTable(data, columnas);
        hilosLT = new JTable(dataT, columnasT);
        hilosMT = new JTable(dataT, columnasT);
        scrollA1 = new JScrollPane(almacen1);
        scrollA2 = new JScrollPane(almacen2);
        scrollHL = new JScrollPane(hilosL);
        scrollHM = new JScrollPane(hilosM);
        scrollTA1 = new JScrollPane(hilosLT);
        scrollTA2 = new JScrollPane(hilosMT);
        progresoA1 = new JProgressBar();
        progresoA2 = new JProgressBar();
        btn_play = new JButton();
        btn_replay = new JButton();
        btn_terminar = new JButton();
        btn_agregarL = new JButton();
        btn_agregarM = new JButton();
        btn_quitarL = new JButton();
        btn_quitarM = new JButton();
        numHilosL = 0;
        numHilosM = 0;
        totalL = new JLabel("Total. León: ");
        totalM = new JLabel("Total. Monterrey: ");
        fondo = new JLabel();
        ProductoresL = new Productor[10];
        ProductoresM = new Productor[10];
        ConsumidoresL = new Consumidor[10];
        ConsumidoresM = new Consumidor[10];
        pausa = true;
        c_transparente = new Color(0, 0, 0, 0);
        c_verde = new Color(127, 220, 187);
        c_azul = new Color(48, 60, 78);
        greenline = BorderFactory.createLineBorder(c_verde);
        blueline = BorderFactory.createLineBorder(c_azul);
        fuente = new Font("Poppins", Font.PLAIN, 11);
        fuente_bold = new Font("Poppins", Font.BOLD, 11);
    }

    public void modificar() {
        ventana.setLayout(null);
        fondo.setIcon(new ImageIcon("src/imgs/fondo.png"));
        fondo.setBounds(0, 0, 996, 560);
        ventana.setSize(996, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setJMenuBar(menu);
        progresoA1.setBounds(288, 410, 200, 20);
        progresoA1.setStringPainted(true);
        progresoA1.setFont(fuente);
        progresoA1.setForeground(c_verde);
        progresoA2.setBounds(508, 410, 200, 20);
        progresoA2.setStringPainted(true);
        progresoA2.setFont(fuente);
        progresoA2.setForeground(c_azul);
        scrollA1.setBounds(288, 70, 200, 300);
        almacen1.setEditable(false);
        almacen1.setFont(fuente);
        scrollA1.setBorder(greenline);
        scrollA2.setBounds(508, 70, 200, 300);
        almacen2.setEditable(false);
        almacen2.setFont(fuente);
        scrollA2.setBorder(blueline);
        hilosL.getColumnModel().getColumn(0).setPreferredWidth(100);
        hilosL.getColumnModel().getColumn(1).setPreferredWidth(80);
        hilosL.getColumnModel().getColumn(2).setPreferredWidth(80);
        hilosL.setOpaque(false);
        hilosL.setFont(fuente);
        hilosL.getTableHeader().setBackground(c_verde);
        hilosL.getTableHeader().setFont(fuente);
        hilosLT.getColumnModel().getColumn(0).setPreferredWidth(100);
        hilosLT.getColumnModel().getColumn(1).setPreferredWidth(80);
        hilosLT.getColumnModel().getColumn(2).setPreferredWidth(80);
        hilosLT.setOpaque(false);
        hilosLT.setFont(fuente);
        hilosLT.getTableHeader().setBackground(c_verde);
        hilosLT.getTableHeader().setFont(fuente);
        scrollHL.setBounds(48, 70, 220, 185);
        scrollTA1.setBounds(48, 370, 220, 41);
        hilosM.getColumnModel().getColumn(0).setPreferredWidth(100);
        hilosM.getColumnModel().getColumn(1).setPreferredWidth(80);
        hilosM.getColumnModel().getColumn(2).setPreferredWidth(80);
        hilosM.setOpaque(false);
        hilosM.getTableHeader().setBackground(c_azul);
        hilosM.getTableHeader().setForeground(Color.white);
        hilosM.getTableHeader().setFont(fuente);
        hilosM.setFont(fuente);
        hilosMT.getColumnModel().getColumn(0).setPreferredWidth(100);
        hilosMT.getColumnModel().getColumn(1).setPreferredWidth(80);
        hilosMT.getColumnModel().getColumn(2).setPreferredWidth(80);
        hilosMT.setOpaque(false);
        hilosMT.getTableHeader().setBackground(c_azul);
        hilosMT.getTableHeader().setForeground(Color.white);
        hilosMT.getTableHeader().setFont(fuente);
        hilosMT.setFont(fuente);
        scrollHM.setBounds(730, 70, 220, 185);
        scrollTA2.setBounds(730, 370, 220, 41);
        btn_play.setBounds(378, 450, 60, 60);
        btn_play.setIcon(new ImageIcon("src/imgs/pause.png"));
        btn_play.setOpaque(false);
        btn_play.setBackground(c_transparente);
        btn_replay.setBounds(468, 450, 60, 60);
        btn_replay.setIcon(new ImageIcon("src/imgs/reset.png"));
        btn_replay.setOpaque(false);
        btn_replay.setBackground(c_transparente);
        btn_terminar.setBounds(555, 450, 60, 60);
        btn_terminar.setIcon(new ImageIcon("src/imgs/finish.png"));
        btn_terminar.setOpaque(false);
        btn_terminar.setBackground(c_transparente);
        btn_agregarL.setBounds(100, 290, 40, 40);
        btn_agregarL.setIcon(new ImageIcon("src/imgs/plus.png"));
        btn_agregarL.setOpaque(false);
        btn_agregarL.setBackground(c_transparente);
        btn_quitarL.setBounds(170, 290, 40, 40);
        btn_quitarL.setIcon(new ImageIcon("src/imgs/minus.png"));
        btn_quitarL.setOpaque(false);
        btn_quitarL.setBackground(c_transparente);
        btn_agregarM.setBounds(782, 290, 40, 40);
        btn_agregarM.setIcon(new ImageIcon("src/imgs/plus1.png"));
        btn_agregarM.setOpaque(false);
        btn_agregarM.setBackground(c_transparente);
        btn_quitarM.setBounds(852, 290, 40, 40);
        btn_quitarM.setIcon(new ImageIcon("src/imgs/minus1.png"));
        btn_quitarM.setOpaque(false);
        btn_quitarM.setBackground(c_transparente);
        totalL.setBounds(300, 380, 200, 20);
        totalL.setFont(fuente_bold);
        totalM.setBounds(520, 380, 200, 20);
        totalM.setFont(fuente_bold);
    }

    public void armar() {
        jmenu_hilos.add(jmenu_leon);
        jmenu_hilos.add(jmenu_monterrey);
        jmenu_leon.add(jmenu_agregarL);
        jmenu_leon.add(jmenu_quitarL);
        jmenu_monterrey.add(jmenu_agregarM);
        jmenu_monterrey.add(jmenu_quitarM);
        menu.add(jmenu_hilos);
        menu.add(jmenu_pausa);
        menu.add(jmenu_reiniciar);
        menu.add(jmenu_terminar);
        ventana.add(progresoA1);
        ventana.add(progresoA2);
        ventana.add(scrollA1);
        ventana.add(scrollA2);
        ventana.add(scrollHL);
        ventana.add(scrollTA1);
        ventana.add(scrollHM);
        ventana.add(scrollTA2);
        ventana.add(progresoA1);
        ventana.add(btn_play);
        ventana.add(btn_replay);
        ventana.add(btn_terminar);
        ventana.add(btn_agregarL);
        ventana.add(btn_agregarM);
        ventana.add(btn_quitarL);
        ventana.add(btn_quitarM);
        ventana.add(totalL);
        ventana.add(totalM);
        ventana.add(fondo);
    }

    public void ejecutar() {
        ventana.setVisible(true);
    }

    public void escucha() {
        c = new Controlador();
        m = new controladorM();
        btn_agregarL.addActionListener(c);
        btn_quitarL.addActionListener(c);
        btn_agregarM.addActionListener(c);
        btn_quitarM.addActionListener(c);
        btn_play.addActionListener(c);
        btn_replay.addActionListener(c);
        btn_terminar.addActionListener(c);
        jmenu_agregarL.addActionListener(c);
        jmenu_agregarM.addActionListener(c);
        jmenu_quitarL.addActionListener(c);
        jmenu_quitarM.addActionListener(c);
        jmenu_pausa.addMenuListener(m);
        jmenu_reiniciar.addMenuListener(m);
        jmenu_terminar.addMenuListener(m);
    }

    class Controlador implements ActionListener {

        ExecutorService aplicacion = Executors.newCachedThreadPool();
        Almacen almacenLeon = new Almacen("León");
        Almacen almacenMonterrey = new Almacen("Monterrey");

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == btn_agregarL) {
                agregarL();
            }
            if (ae.getSource() == btn_quitarL) {
                quitarL();
            }
            if (ae.getSource() == btn_agregarM) {
                agregarM();
            }
            if (ae.getSource() == btn_quitarM) {
                quitarM();
            }
            if (ae.getSource() == btn_play) {
                pausa();
            }
            if (ae.getSource() == btn_replay) {
                reiniciar();
            }
            if (ae.getSource() == btn_terminar) {
                terminar();
            }
            if (ae.getSource() == jmenu_agregarL) {
                agregarL();
            }
            if (ae.getSource() == jmenu_quitarL) {
                quitarL();
            }
            if (ae.getSource() == jmenu_agregarM) {
                agregarM();
            }
            if (ae.getSource() == jmenu_quitarM) {
                quitarM();
            }
            if (ae.getSource() == jmenu_pausa) {
                pausa();
            }
            if (ae.getSource() == jmenu_reiniciar) {
                reiniciar();
            }
            if (ae.getSource() == jmenu_terminar) {
                terminar();
            }

        }

        public void quitarL() {
            ProductoresL[numHilosL - 1].matar();
            ProductoresL[numHilosL - 1].setStateDead(true);
            ProductoresL[numHilosL - 1] = null;
            ConsumidoresL[numHilosL - 1].matar();
            ConsumidoresL[numHilosL - 1].setStateDead(true);
            ConsumidoresL[numHilosL - 1] = null;
            hilosL.setValueAt("", numHilosL - 1, 1);
            hilosL.setValueAt("", numHilosL - 1, 2);
            hilosL.setValueAt("", numHilosL - 1, 0);
            numHilosL--;
        }

        public void agregarL() {
            if (numHilosL < 10 && numHilosL >= 0) {
                ProductoresL[numHilosL] = (new Productor(almacenLeon, 200, "León - " + (numHilosL + 1), numHilosL));
                aplicacion.execute(ProductoresL[numHilosL]);
                ConsumidoresL[numHilosL] = (new Consumidor(almacenMonterrey, 150, "León - " + (numHilosL + 1), numHilosL));
                aplicacion.execute(ConsumidoresL[numHilosL]);
                hilosL.setValueAt("Camión " + (numHilosL + 1), numHilosL, 0);
                numHilosL++;
            }
        }

        public void quitarM() {
            ProductoresM[numHilosM - 1].matar();
            ProductoresM[numHilosM - 1].setStateDead(true);
            ProductoresM[numHilosM - 1] = null;
            ConsumidoresM[numHilosM - 1].matar();
            ConsumidoresM[numHilosM - 1].setStateDead(true);
            ConsumidoresM[numHilosM - 1] = null;
            hilosM.setValueAt("", numHilosM - 1, 1);
            hilosM.setValueAt("", numHilosM - 1, 2);
            hilosM.setValueAt("", numHilosM - 1, 0);
            numHilosM--;
        }

        public void agregarM() {
            if (numHilosM < 10 && numHilosM >= 0) {
                ProductoresM[numHilosM] = (new Productor(almacenMonterrey, 200, "Monterrey - " + (numHilosM + 1), numHilosM));
                aplicacion.execute(ProductoresM[(numHilosM)]);
                ConsumidoresM[numHilosM] = (new Consumidor(almacenLeon, 150, "Monterrey - " + (numHilosM + 1), numHilosM));
                aplicacion.execute(ConsumidoresM[(numHilosM)]);
                hilosM.setValueAt("Camión " + (numHilosM + 1), numHilosM, 0);
                numHilosM++;
            }
        }

        public void pausa() {
            if (pausa) {
                pausa = false;
                for (int i = 0; i < numHilosL; i++) {
                    if (!ProductoresL[i].isSuspended()) {
                        ProductoresL[i].parar();
                    }
                    if (!ConsumidoresL[i].isSuspended()) {
                        ConsumidoresL[i].parar();
                    }
                }
                for (int i = 0; i < numHilosM; i++) {
                    if (!ProductoresM[i].isSuspended()) {
                        ProductoresM[i].parar();
                    }
                    if (!ConsumidoresM[i].isSuspended()) {
                        ConsumidoresM[i].parar();
                    }
                }
            } else {
                pausa = true;
                for (int i = 0; i < numHilosL; i++) {
                    if (ProductoresL[i].isSuspended()) {
                        ProductoresL[i].reanudar();
                    }
                    if (ConsumidoresL[i].isSuspended()) {
                        ConsumidoresL[i].reanudar();
                    }
                }
                for (int i = 0; i < numHilosM; i++) {
                    if (ProductoresM[i].isSuspended()) {
                        ProductoresM[i].reanudar();
                    }
                    if (ConsumidoresM[i].isSuspended()) {
                        ConsumidoresM[i].reanudar();
                    }
                }
            }
        }

        public void reiniciar() {
            for (int i = 0; i < numHilosL; i++) {
                ProductoresL[i].matar();
                ConsumidoresL[i].matar();
                ProductoresL[i] = null;
                ConsumidoresL[i] = null;
            }
            for (int i = 0; i < numHilosM; i++) {
                ConsumidoresM[i].matar();
                ProductoresM[i].matar();
                ProductoresM[i] = null;
                ConsumidoresM[i] = null;
            }
            try {
                progresoA1.setValue(0);
                progresoA2.setValue(0);
                almacen1.setText("");
                almacen2.setText("");
                totalL.setText("Total. León: ");
                totalM.setText("Total. Monterrey: ");
                for (int i = 0; i < numHilosM; i++) {
                    hilosM.setValueAt("", i, 1);
                    hilosM.setValueAt("", i, 2);
                    hilosM.setValueAt("", i, 0);
                }
                for (int i = 0; i < numHilosL; i++) {
                    hilosL.setValueAt("", i, 1);
                    hilosL.setValueAt("", i, 2);
                    hilosL.setValueAt("", i, 0);
                }
                Thread.sleep(3000);
                hilosMT.setValueAt("", 0, 1);
                hilosLT.setValueAt("", 0, 1);
                hilosMT.setValueAt("", 0, 2);
                hilosLT.setValueAt("", 0, 2);
                numHilosL = numHilosM = 0;
                almacenLeon.setConsumidoL(0); almacenLeon.setConsumidoM(0); almacenLeon.setProducidoL(0); almacenLeon.setProducidoM(0);
                almacenMonterrey.setConsumidoL(0); almacenLeon.setConsumidoM(0); almacenLeon.setProducidoL(0); almacenLeon.setProducidoM(0);
            } catch (InterruptedException ex) {
            }
        }

        public void terminar() {
            for (int i = 0; i < numHilosL; i++) {
                ProductoresL[i].matar();
                ConsumidoresL[i].matar();
            }
            for (int i = 0; i < numHilosM; i++) {
                ProductoresM[i].matar();
                ConsumidoresM[i].matar();
            }
            try {
                Thread.sleep(3000);
                JOptionPane.showMessageDialog(null, "Cerrando programa...");
            } catch (InterruptedException ex) {
            }
            System.exit(0);

        }
        
        
    }
    
    class controladorM implements MenuListener {
        Controlador c = new Controlador();

        @Override
        public void menuSelected(MenuEvent e) {
            if(e.getSource() == jmenu_pausa){
                c.pausa();
            }if(e.getSource() == jmenu_reiniciar){
                c.reiniciar();
            }if(e.getSource() == jmenu_terminar){
                c.terminar();
            }
        }

        @Override
        public void menuDeselected(MenuEvent e) {
        }

        @Override
        public void menuCanceled(MenuEvent e) {
        }
    }

}
