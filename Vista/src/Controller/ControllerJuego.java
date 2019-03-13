/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Conexion.Conexion;
import Interfaces.IFachadaControl;
import Modelo.Observado;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ControlJuego;
import logica.FachadaControl;

/**
 *
 * @author Eduardo Ramírez
 */
public class ControllerJuego {
    Conexion conexion;
    int nJugadores;
    Observado modelo;
    private int nConectados;
    IFachadaControl fachadaControl;
    boolean partidaIniciada=false;
    
   // IFachadaControl controlJuego;
    private static ControllerJuego instanciaUnica = new ControllerJuego();

    public static ControllerJuego obtieneInstancia() {
        return instanciaUnica;
    }
    private ControllerJuego() {
        modelo = new Observado();
        conexion = new Conexion();
        fachadaControl = new FachadaControl();
        
        
    }
    public void crearPartida(int nJugadores, String nickname){
        
        this.nJugadores = nJugadores;
        conexion.crearServerPartida(nJugadores, nickname);
        
    }
public void unirsePartida(String IP, int port, String nickname){
   conexion.conectarse(IP, port, nickname);
}


 
    

    

    public void upModeloLog(String msg) {
       modelo.updateLog(msg);
    }
   

   
    

    public void jugadorDesconectado(int idJugador) {
        if(partidaIniciada){
            if(fachadaControl.siguienteTurno()==idJugador){
                fachadaControl.generaTurno();
            }
            // Se eliminan todos los registros del jugador (Lineas, cuadros) y el propio jugador
            fachadaControl.jugadorDesconectado(idJugador);
            modelo.actualizaTurno(idJugador);
            modelo.actualizarMarcador(fachadaControl.infoJugadores());
            modelo.restableceJugador(idJugador);
            nConectados--;
            if(nConectados==1){
                modelo.terminarDefault();
            }
        }else{
        String nicknameDesconectado = fachadaControl.getNickJUgador(idJugador);
        
modelo.updateLog("El jugador "+nicknameDesconectado+" se desconectó");
        }
    }

    public void iniciarPartida(ArrayList<String[]> datosJugadores, int miNum, String nickname) {
        this.nConectados = datosJugadores.size();
       modelo.setNumJugador(miNum);
     
       fachadaControl.inicia(datosJugadores, miNum);
       modelo.comenzarPartida(fachadaControl.siguienteTurno(),fachadaControl.infoJugadores() );
       this.partidaIniciada=true;
      
    }
public void hacerJugada(int nLinea){
    int miId =fachadaControl.obtenerMiId();
    String nuevaOrden =nLinea+",";
    
    ArrayList<Integer> resultado =fachadaControl.hacerJugada(nLinea);
    modelo.agregaMovimiento(miId, nLinea, fachadaControl.getColorJugador(miId));
    modelo.actualizaTurno(fachadaControl.siguienteTurno());
    if(resultado!=null){
        nuevaOrden+="CUADROS:";
        for (Integer idCuadro : resultado) {
            System.out.println("Formaste el cuadro "+idCuadro);
            modelo.updateCuadro(idCuadro, miId, fachadaControl.getNickJUgador(miId), fachadaControl.getColorJugador(miId));
            nuevaOrden+=idCuadro+",";
            modelo.actualizarMarcador(fachadaControl.infoJugadores());
            
        }
                    modelo.actualizarColoresTablero(fachadaControl.consultaLineas(fachadaControl.getNickJUgador(miId)), fachadaControl.consultaCuadros(fachadaControl.getNickJUgador(miId)), fachadaControl.getColorJugador(miId));
if(fachadaControl.isOver()){
    String nickGanador = fachadaControl.quienGana();
    modelo.mostrarGanador(nickGanador);
}
    }
    
        
    System.out.println("Orden para enviar: "+nuevaOrden);
    conexion.enviarOrden(nuevaOrden);
    // Comprobrobar si gané
//    if(fachadaControl.hayGanador()!=null){
//        
//    }
}
    public void procesarOrden(String orden, int idJugador) {
        int idlinea = Integer.valueOf(orden.split(",")[0]);
        
        if(orden.contains("CUADROS")){
            String sub = orden.substring(orden.lastIndexOf(":")+1);
        String[] idsCuadros = sub.split(",");
        modelo.agregaMovimiento(idJugador, idlinea, fachadaControl.getColorJugador(idJugador));
        
            for (String idsCuadro : idsCuadros) {
                    fachadaControl.guardarJugada(idJugador, idlinea, Integer.valueOf(idsCuadro));
                
                modelo.updateCuadro(Integer.valueOf(idsCuadro),idJugador, fachadaControl.getNickJUgador(idJugador), fachadaControl.getColorJugador(idJugador));
            }
            modelo.actualizarMarcador(fachadaControl.infoJugadores());
                    modelo.actualizarColoresTablero(fachadaControl.consultaLineas(fachadaControl.getNickJUgador(idJugador)), fachadaControl.consultaCuadros(fachadaControl.getNickJUgador(idJugador)), fachadaControl.getColorJugador(idJugador));
        }
        else{
            fachadaControl.guardarJugada(idJugador, idlinea);
            int sigue =fachadaControl.siguienteTurno();
            
            modelo.actualizaTurno(sigue);
            modelo.agregaMovimiento(idJugador, idlinea, fachadaControl.getColorJugador(idJugador));
        }
    }

    public void abandonarPartida() {
       conexion.enviarOrden("DESCONECTARSE");
    }

    public void actualizarColor(String nickname, Color color) {
      fachadaControl.establecerColorNuevo(nickname, color);
      
      modelo.actualizarMarcador(fachadaControl.infoJugadores());
      ArrayList<Integer> lineas = fachadaControl.consultaLineas(nickname);
      ArrayList<Integer> cuadros;
      if(lineas.size()>0)
       cuadros = fachadaControl.consultaCuadros(nickname);
      else
          cuadros=null;
        System.out.println("Lineas a actualizar: "+lineas.toString());
        System.out.println("Cuadros a actualizar: "+cuadros.toString());
      modelo.actualizarColoresTablero(lineas, cuadros, color);
    }
    public void terminarPartida(){
        conexion.terminarConexiones();
    }
}
