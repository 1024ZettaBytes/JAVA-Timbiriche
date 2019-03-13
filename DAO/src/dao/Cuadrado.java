/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 *
 * @author eduardo
 */
public class Cuadrado {
    private int idJugador;
    ArrayList<Linea> mislineas;
    private int idCuadrado;
    private ArrayList<Integer> lineasMarcadas;
    public Cuadrado(int idCuadrado){
        mislineas = new ArrayList<>();
        this.idCuadrado = idCuadrado;
        idJugador = 0;
        lineasMarcadas = new ArrayList<>();
    }
    public void marcarLinea(int numLinea){
        lineasMarcadas.add(numLinea);
        
    }
    public int getIdCuadrado(){
        return this.idCuadrado;
    }
    public boolean isCompleted(){
        return lineasMarcadas.size()==4;
    }
public void setJugador(int idJugador){
    this.idJugador = idJugador;
}
public int getJugador(){
    return this.idJugador;
}
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idCuadrado;
        return hash;
    }
    @Override
    public boolean equals(Object obj){
        return this.hashCode()==obj.hashCode();
    }

    void setLineas(Linea lineaArriba, Linea lineaAbajo, Linea lineaIzquierda, Linea lineaDerecha) {
        mislineas.add(lineaArriba);
        mislineas.add(lineaAbajo);
        mislineas.add(lineaIzquierda);
        mislineas.add(lineaDerecha);
    }
    @Override
    public String toString(){
        return "Cuadro: "+this.idCuadrado+", Arriba:"+mislineas.get(0)+", Abajo:"+mislineas.get(1)+", Izquierda: "+mislineas.get(2)+", Derecha:"+mislineas.get(3);
    }

    ArrayList<Integer> getlineasMarcadas() {
      return this.lineasMarcadas;
    }
    public void restablecer(){
        this.idJugador=0;
    }

    void desmarcarLinea(Integer idLinea) {
lineasMarcadas.remove(idLinea);
        }
}
