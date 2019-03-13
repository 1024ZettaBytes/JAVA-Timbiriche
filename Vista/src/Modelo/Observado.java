/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


import Vistas.Timbiriche;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Eduardo Ramírez
 */
public class Observado {
   Timbiriche ventanaTimbiriche;
   public void cambiaTxt(String txt){
       ventanaTimbiriche = Timbiriche.obtieneInstancia();
       
   }
   public void Observado(){
       ventanaTimbiriche = Timbiriche.obtieneInstancia();
   }

    
    public void updateLog(String msg){
        ventanaTimbiriche = Timbiriche.obtieneInstancia();
        ventanaTimbiriche.logUpdated(msg);
    }
    

    public void setNumJugador(int miNum) {
       ventanaTimbiriche = Timbiriche.obtieneInstancia();
          ventanaTimbiriche.setNumJ(miNum);
    }

    public void agregaMovimiento(int numJ, int nLinea, Color color) {
        ventanaTimbiriche.agregaMovimiento(numJ, nLinea, color);
    }


    public void updateCuadro(int cerrado,int idJugador, String nickname, Color color) {
       ventanaTimbiriche = Timbiriche.obtieneInstancia();
          ventanaTimbiriche.actualizaCuadros(cerrado,idJugador, nickname, color);
    }

    public void actualizaTurno(int siguienteTurno) {
       ventanaTimbiriche = Timbiriche.obtieneInstancia();
       ventanaTimbiriche.setTurno(siguienteTurno);
    }

    public void comenzarPartida(int siguienteTurno, ArrayList<Object[]> jugadoresConectados) {
        actualizaTurno(siguienteTurno);
        ventanaTimbiriche.inicializaTablero();
        ventanaTimbiriche.inicializarMarcador(jugadoresConectados, false);
    }

    public void actualizarMarcador(ArrayList<Object[]> infoJugadores) {
 ventanaTimbiriche.inicializarMarcador(infoJugadores, true);
    }

    public void actualizarColoresTablero(ArrayList<Integer> lineas, ArrayList<Integer> cuadros, Color color) {
        
        ventanaTimbiriche = Timbiriche.obtieneInstancia();
        ventanaTimbiriche.actualizaColoresTablero(lineas, cuadros, color);
    }

    public void restableceJugador(int idJugador) {
        ventanaTimbiriche = Timbiriche.obtieneInstancia();
        ventanaTimbiriche.restablecerTablero(idJugador);
       }

    public void terminarDefault() {
ventanaTimbiriche.terminarPartidaDefault();    }

    

    public void mostrarGanador(String nickGanador) {
        ventanaTimbiriche.finPartida(nickGanador);
    }

   
}
