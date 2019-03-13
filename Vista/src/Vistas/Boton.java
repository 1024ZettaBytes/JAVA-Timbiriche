/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author eduardo
 */
public class Boton extends JButton implements ActionListener {
    String mensaje;
    Timbiriche vista;
    private int idJugador;
    private static int identificador = 1;
    private int id;

    public Boton() {
        super();
        addActionListener(this);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        this.id = identificador++;
        this.idJugador=0;
        
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.vista = Timbiriche.obtieneInstancia();
        if (vista.isMyTurn()) {
            setOpaque(true);
            this.idJugador = vista.getNumJ();
            vista.makeMove(this.id);
        } else {
            JOptionPane.showMessageDialog(this, "No es tu turno");
        }
    }

    public void actualiza(int idJugador, Color color) {
        this.idJugador = idJugador;
        vista = Timbiriche.obtieneInstancia();
        setBackground(color);
        setOpaque(true);
    }
    public void restablece(){
        System.out.println(this.id);
        setOpaque(false);
        repaint();
    }
public int getJugador(){
    return this.idJugador;
}
}
