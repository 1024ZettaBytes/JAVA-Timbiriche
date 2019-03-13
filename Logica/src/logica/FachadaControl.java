 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IFachadaControl;
import java.awt.Color;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Ariel AB
 */
public class FachadaControl implements IFachadaControl {
private ControlJuego control = new ControlJuego();
   

    @Override
    public void inicia(ArrayList<String[]> datosJugadores, int miID) {
            control.guardaJugadores(datosJugadores);
            control.setIDlocal(miID);
        
    }

    @Override
    public ArrayList<Integer> hacerJugada(int nLinea) {
       return control.hacerJugada(nLinea);
    }
@Override
    public String getNickJUgador(int num){  
        return control.getNickJugador(num);
    }

    @Override
    public Object hayGanador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardarJugada(int idJugador, int idlinea, int idCuadro) {
       control.guardarJugada(idJugador, idlinea, idCuadro);
    }

    @Override
    public void guardarJugada(int idJugador, int idlinea) {
      control.guardarJugada(idJugador, idlinea);
    }

    @Override
    public int siguienteTurno() {
       return control.quienSigue();
    }

    @Override
    public Color getColorJugador(int idJugador) {
        return control.consultaColor(idJugador);
    }

  

    @Override
    public int obtenerMiId() {
     return control.consultarMiId();
    }

    @Override
    public ArrayList<Object[]> infoJugadores() {
       return control.obtenerDatosJugadores();
    }

    @Override
    public void establecerColorNuevo(String nickname, Color color) {
        control.setColorNuevo(nickname, color);
    }

    @Override
    public ArrayList<Integer> consultaCuadros(String nickname) {
        return control.obtenerCuadros(nickname);
    }

    @Override
    public ArrayList<Integer> consultaLineas(String nickname) {
return control.obtenerLineas(nickname);
    }

    @Override
    public void jugadorDesconectado(int idJugador) {
control.desconectaJugador(idJugador);
    }

    @Override
    public void generaTurno() {
control.siguienteTurno();    }

    @Override
    public boolean isOver() {
return control.seTermino();
        }

    @Override
    public String quienGana() {
       return control.ganador();
    }

    

}
