/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Ariel AB
 */
public interface IFachadaDao {

    
    public void  construirTablero();

   public boolean agregarJugador(int id, String nickname);
    

    
    public String getNicknameJugador(int numJugador);
     public ArrayList<Integer> ingresarJugada(int nLinea);
    public void guardarJugada(int idJugador, int idLinea, int idCuadro);
    public void guardarJugada(int idJugador, int idLinea);
    public void estableceMiID(int miId);
    public int obtieneTurno();
    public void setPrimerTurno(int idJugador);
    public void setSiguienteTurno();

    public Color getColorJugador(int idJugador);


    public int getMiId();

    public ArrayList<Object[]> getJugadores();

    public void actualizarColor(String nickname, Color color);

    public ArrayList<Integer> consultaCuadrosNickname(String nickname);

    public ArrayList<Integer> consultaLineasNickname(String nickname);

    public void eliminaJugador(int idJugador);

    public boolean cuadrosLlenos();

  

    public String getMayorPuntaje();
    
}
