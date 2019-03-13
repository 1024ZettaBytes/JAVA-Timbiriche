package dao;

import java.awt.Color;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Ariel AB
 */
public class Jugador {

    
    private int idJugador;
private String nickname;
private Color color;
private int puntos =0;
private ArrayList<Integer> lineas;
private ArrayList<Integer> cuadros;
    public Jugador(int idJugador, String nickname, Color color) {
        this.idJugador = idJugador;
        this.nickname = nickname;
        this.color = color;
        this.lineas = new ArrayList<>();
        this.cuadros = new ArrayList<>();
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public int hashCode(){
        int hash = 3;
        hash = 67 * hash + this.idJugador;
        return hash;
       
   }

    @Override
    public boolean equals(Object obj) {
         return this.hashCode() == obj.hashCode();
    }
public int getId(){
    return this.idJugador;
}
public String getNickName(){
    return this.nickname;
}
public void setColor(Color color){
    this.color = color;
}
public Color getColor(){
    return this.color;
}
public int getPuntos(){
    return this.puntos;
}
public void agregaPuntos(int cantidad){
    this.puntos+=cantidad;
}
public void agregarLinea(int linea){
    lineas.add(linea);
}
public void agregarCuadro(int cuadro){
    cuadros.add(cuadro);
}

    public ArrayList<Integer> getLineas() {
        return lineas;
    }

    public ArrayList<Integer> getCuadros() {
        return cuadros;
    }

    @Override
    public String toString() {
        return "Jugador: " + this.idJugador + "Nickname: " + this.nickname;
    }

    void quitarCuadro(Integer idCuadroDelinea) {
    cuadros.remove(idCuadroDelinea);
    puntos--;
    }

}
