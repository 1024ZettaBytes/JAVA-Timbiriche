/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controller.ControllerJuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Eduardo Ramírez
 */
public class Timbiriche extends javax.swing.JFrame {

    /**
     * Creates new form Timbiriche
     */
    ControllerJuego controller = ControllerJuego.obtieneInstancia();
    JLabel imagenPunto;
    Boton[] lineas;
    Cuadro[] cuadros;
    JLabel[] marcadorJugadores;
    JLabel[] marcadorPuntos;
    
    JColorChooser colorChooser = new JColorChooser();
    private int numJugador;
    private static Timbiriche instanciaUnica = new Timbiriche();
private int turno;
    public static Timbiriche obtieneInstancia() {

        return instanciaUnica;
    }

    private Timbiriche() {

        initComponents();
        setLocationRelativeTo(null);
       

    }

    public void inicializaTablero() {
        int n = 10;
        lineas = new Boton[181];
        ImageIcon icon = new ImageIcon("dot.jpg");
        int tamImagen = 15;
        int anchoBtn = tamImagen;
        int largoBtn = 60;

        int coorx = 0, coory = 0;
        for (int y = 0; y < n; y++) {

            coorx = 0;
            for (int x = 1; x < n; x++) {

                imagenPunto = new JLabel(icon);

                imagenPunto.setBounds(coorx, coory, tamImagen, tamImagen);
                panelTablero.add(imagenPunto);
                coorx += tamImagen;
                Boton btn = new Boton();

                btn.setBounds(coorx, coory, largoBtn, anchoBtn);
                lineas[btn.getId()] = btn;
                panelTablero.add(lineas[btn.getId()]);
                coorx += largoBtn;

            }
            imagenPunto = new JLabel(icon);

            imagenPunto.setBounds(coorx, coory, tamImagen, tamImagen);
            panelTablero.add(imagenPunto);
            coory += (largoBtn + tamImagen);

        }

        coorx = 0;
        coory = tamImagen;
        for (int y = 1; y < n; y++) {
            coorx = 0;
            for (int x = 0; x < n; x++) {
                Boton btnvertical = new Boton();

                btnvertical.setBounds(coorx, coory, anchoBtn, largoBtn);
                lineas[btnvertical.getId()] = btnvertical;
                
                panelTablero.add(lineas[btnvertical.getId()]);

                coorx += (largoBtn + tamImagen);
            }
            coory += (largoBtn + tamImagen);
        }

// Crea los botones para los cuadros interiores
        int contador = 1;
        cuadros = new Cuadro[((n - 1) * (n - 1)) + 1];

        coory = tamImagen;
        for (int y = 1; y < n; y++) {
            coorx = tamImagen;
            for (int x = 1; x < n; x++) {
                Cuadro cuadro = new Cuadro();
                cuadro.setBounds(coorx, coory, largoBtn, largoBtn);
               
                cuadro.setOpaque(false);
                cuadro.setContentAreaFilled(false);
                cuadro.setBorderPainted(false);
                cuadros[contador] = cuadro;
                panelTablero.add(cuadros[contador]);
                coorx += tamImagen + largoBtn;
                contador++;
            }
            coory += tamImagen + largoBtn;
        }

        panelTablero.revalidate();
        panelTablero.repaint();
        panelTablero.setVisible(true);
        
        //txtLog.setBounds(910, 520, 400, 180);
    }
public void inicializarMarcador(ArrayList<Object[]> jugadoresConectados, boolean update){
    if(update){
        for(int i=0;i<marcadorJugadores.length;i++){
            panelMarcador.remove(marcadorJugadores[i]);
            panelMarcador.remove(marcadorPuntos[i]);
            
        }
    }
    
    marcadorJugadores = new JLabel[jugadoresConectados.size()];
    marcadorPuntos = new JLabel[jugadoresConectados.size()];
    for(int x=0;x<jugadoresConectados.size();x++){
        Color color = (Color)jugadoresConectados.get(x)[2];
        marcadorJugadores[x] = new JLabel();
        marcadorJugadores[x].setBounds(labelTituloJugador.getBounds().x,
                labelTituloJugador.getBounds().y+(20*(x+1))+(labelTituloJugador.getBounds().height*(x+1)),
                labelTituloJugador.getBounds().width,
               labelTituloJugador.getBounds().height);
        marcadorJugadores[x].setFont(new Font(labelTituloJugador.getFont().getName(), Font.PLAIN, 22));
      marcadorJugadores[x].setHorizontalAlignment(SwingConstants.CENTER);
        marcadorJugadores[x].setText((String)jugadoresConectados.get(x)[0]);
        marcadorJugadores[x].setForeground(color);
        marcadorPuntos[x]= new JLabel();
        marcadorPuntos[x].setBounds(labelTituloPuntos.getBounds().x,
                labelTituloPuntos.getBounds().y+(20*(x+1))+(labelTituloPuntos.getBounds().height*(x+1)),
                labelTituloPuntos.getBounds().width,
                labelTituloPuntos.getBounds().height);
        marcadorPuntos[x].setFont(new Font(labelTituloPuntos.getFont().getName(), Font.PLAIN, 22));
      marcadorPuntos[x].setHorizontalAlignment(SwingConstants.CENTER);
        marcadorPuntos[x].setText((String)jugadoresConectados.get(x)[1]);
        
        marcadorJugadores[x].addMouseListener(new MouseAdapter() {
@Override
public void mouseClicked(MouseEvent e){
   
    colorChooser.setColor(((JLabel)e.getSource()).getForeground());
    if(JOptionPane.showConfirmDialog(null, colorChooser, "Seleccione el color", JOptionPane.OK_CANCEL_OPTION)==0){
       
        for(int y=0;y<marcadorJugadores.length;y++){
            if(marcadorJugadores[y].getText().equals(((JLabel)e.getSource()).getText())){
                
                controller.actualizarColor(marcadorJugadores[y].getText(), colorChooser.getColor());
                break;
            }
        }
       
    }
}
        });
        panelMarcador.add(marcadorJugadores[x]);
        panelMarcador.add(marcadorPuntos[x]);
        panelMarcador.revalidate();
        panelMarcador.repaint();
    }
}
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTablero = new javax.swing.JPanel();
        btnAbandonar = new javax.swing.JButton();
        panelMarcador = new javax.swing.JPanel();
        labelTituloJugador = new javax.swing.JLabel();
        labelTituloPuntos = new javax.swing.JLabel();
        labelMarcador = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(2040, 1080));

        panelTablero.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelTablero.setMaximumSize(new java.awt.Dimension(690, 690));

        javax.swing.GroupLayout panelTableroLayout = new javax.swing.GroupLayout(panelTablero);
        panelTablero.setLayout(panelTableroLayout);
        panelTableroLayout.setHorizontalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 688, Short.MAX_VALUE)
        );
        panelTableroLayout.setVerticalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 688, Short.MAX_VALUE)
        );

        btnAbandonar.setText("Abandonar partida");
        btnAbandonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbandonarActionPerformed(evt);
            }
        });

        panelMarcador.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelTituloJugador.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        labelTituloJugador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTituloJugador.setText("JUGADOR");

        labelTituloPuntos.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        labelTituloPuntos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTituloPuntos.setText("PUNTOS");

        javax.swing.GroupLayout panelMarcadorLayout = new javax.swing.GroupLayout(panelMarcador);
        panelMarcador.setLayout(panelMarcadorLayout);
        panelMarcadorLayout.setHorizontalGroup(
            panelMarcadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMarcadorLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(labelTituloJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(labelTituloPuntos, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );
        panelMarcadorLayout.setVerticalGroup(
            panelMarcadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMarcadorLayout.createSequentialGroup()
                .addGroup(panelMarcadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTituloJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTituloPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(208, Short.MAX_VALUE))
        );

        labelMarcador.setFont(new java.awt.Font("Dialog", 2, 36)); // NOI18N
        labelMarcador.setText("MARCADOR");

        txtLog.setColumns(20);
        txtLog.setRows(5);
        jScrollPane1.setViewportView(txtLog);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(labelMarcador))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelMarcador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAbandonar, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(labelMarcador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelMarcador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAbandonar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbandonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbandonarActionPerformed
       
        controller.abandonarPartida();
        dispose();
        
    }//GEN-LAST:event_btnAbandonarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbandonar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelMarcador;
    private javax.swing.JLabel labelTituloJugador;
    private javax.swing.JLabel labelTituloPuntos;
    private javax.swing.JPanel panelMarcador;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables

    public void agregaMovimiento(int numJ, int nLinea, Color color) {
       lineas[nLinea].actualiza(numJ, color);
    }

    public void logUpdated(String msg) {
        txtLog.append(msg + "\n");
    }

    public void setTitulo(String nombre) {
        setTitle(nombre);
    }

    public void setNumJ(int miNum) {
        this.numJugador=miNum;
    }
public int getNumJ(){
    return this.numJugador;
}

    public void actualizaCuadros(int cerrado, int idJugador, String nickname, Color color) {
cuadros[cerrado].setIdJugador(idJugador);
// Set color del jugador
        cuadros[cerrado].setForeground(color);
       cuadros[cerrado].setText(String.valueOf(nickname.charAt(0)));
       cuadros[cerrado].setOpaque(true);
    }
public void actualizaColoresTablero(ArrayList<Integer> lineasJugador, ArrayList<Integer> cuadrosJugador, Color color){
    for (int linea:lineasJugador){
        lineas[linea].setBackground(color);
    }
    if(cuadrosJugador!=null){
        for (int cuadro:cuadrosJugador){
        cuadros[cuadro].setForeground(color);
    }
    }
}
    
    public void makeMove(int idLinea){
        controller.hacerJugada(idLinea);
    }

    public void setTurno(int siguienteTurno) {
       this.turno = siguienteTurno;
    }
    public boolean isMyTurn(){
        return this.numJugador==this.turno;
    }

    public void restablecerTablero(int idJugador) {
        for(int x=1;x<lineas.length;x++){
            
            if(lineas[x].getJugador()==idJugador)
                lineas[x].restablece();
        }
        for(int x=1;x<cuadros.length;x++){
            if(cuadros[x].getJugador()==idJugador)
                cuadros[x].restablece();
        }
    }

    public void terminarPartidaDefault() {
        JOptionPane.showMessageDialog(this, "Todos los jugadores abandonaron la partida.\n!Tú ganas¡");
   dispose();
   controller.terminarPartida();
    }

    public void finPartida(String nickGanador) {
                JOptionPane.showMessageDialog(this, "El ganador es el jugador "+nickGanador+".");
dispose();
controller.terminarPartida();
    }

    
}
