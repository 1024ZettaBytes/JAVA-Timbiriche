/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IFachadaControl;
import Interfaces.IFachadaDao;
import dao.FachadaDao;
import java.awt.Color;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Ariel AB
 */


public class ControlJuego{
IFachadaDao dao = new FachadaDao();
    void guardaJugadores(ArrayList<String[]> datosJugadores) {
        for (String[] datosJugador : datosJugadores) {
            dao.agregarJugador(Integer.valueOf(datosJugador[0]), datosJugador[2]);
        }
        
        dao.construirTablero();
        dao.setPrimerTurno(Integer.valueOf(datosJugadores.get(0)[0]));
    }

    public ArrayList<Integer> hacerJugada(int nLinea) {
        ArrayList<Integer> resultado = dao.ingresarJugada(nLinea);
        if(resultado==null)
        dao.setSiguienteTurno();
       return resultado;
    }

    String getNickJugador(int num) {
       return dao.getNicknameJugador(num);
    }

    void guardarJugada(int idJugador, int idlinea, int idCuadro) {
        dao.guardarJugada(idJugador, idlinea, idCuadro);
    }

    void guardarJugada(int idJugador, int idlinea) {
        dao.guardarJugada(idJugador, idlinea);
        dao.setSiguienteTurno();
    }

    void setIDlocal(int miID) {
       dao.estableceMiID(miID);
    }

    int quienSigue() {
      return dao.obtieneTurno();
    }

    Color consultaColor(int idJugador) {
       return dao.getColorJugador(idJugador);
    }

  
    int consultarMiId() {
       return dao.getMiId();
    }

    ArrayList<Object[]> obtenerDatosJugadores() {
        return dao.getJugadores();
    }

    void setColorNuevo(String nickname, Color color) {
        dao.actualizarColor(nickname, color);
    }

    ArrayList<Integer> obtenerCuadros(String nickname) {
      return dao.consultaCuadrosNickname(nickname);
    }

    ArrayList<Integer> obtenerLineas(String nickname) {
      return dao.consultaLineasNickname(nickname);
    }

    void desconectaJugador(int idJugador) {
        dao.eliminaJugador(idJugador);
    }

    void siguienteTurno() {
dao.setSiguienteTurno();    }

    boolean seTermino() {
        return dao.cuadrosLlenos();
    }

    String ganador() {
       return dao.getMayorPuntaje();
    }
    
}
