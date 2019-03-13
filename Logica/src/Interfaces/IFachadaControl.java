package Interfaces;

import java.awt.Color;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Ariel AB
 */
public interface IFachadaControl {
 public void inicia(ArrayList<String[]> datosJugadores, int miID);
 

    public ArrayList<Integer> hacerJugada(int nLinea);

    public String getNickJUgador(int num);

    public Object hayGanador();

    public void guardarJugada(int idJugador, int idlinea, int idsCuadro);

    public void guardarJugada(int idJugador, int idlinea);
public int siguienteTurno();


    public Color getColorJugador(int idJugador);

    

    public int obtenerMiId();

    public ArrayList<Object[]> infoJugadores();

    public void establecerColorNuevo(String nickname, Color color);

    public ArrayList<Integer> consultaCuadros(String nickname);

    public ArrayList<Integer> consultaLineas(String nickname);

    public void jugadorDesconectado(int idJugador);

    public void generaTurno();

    public boolean isOver();

    public String quienGana();


}
