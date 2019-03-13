/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author eduardo
 */
class Linea {

    private int idJugador;
    private int idLinea;
    private ArrayList<Integer> misCuadros;

    public Linea(int idLinea) {

        this.misCuadros = new ArrayList<>();
        this.idLinea = idLinea;
    }

    public void setNuevoCuadro(int idCuadro) {
        misCuadros.add(idCuadro);

    }

    public void setJugador(int idJugador) {

        this.idJugador = idJugador;
    }

    public ArrayList<Integer> getCuadros() {

        return this.misCuadros;
    }

    public int getIdLinea() {
        return this.idLinea;
    }

    @Override
    public String toString() {
        return this.idLinea + "";
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.idLinea;
        return hash;
    }

    void restablecer() {
this.idJugador=0;  
    
       }

    int getIdJugador() {
        return this.idJugador;
    }

}
