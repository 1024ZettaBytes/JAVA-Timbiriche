/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controller.ControllerJuego;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Eduardo Ramírez
 */
public class TimbiricheRun {
    public static void main(String []args) throws InterruptedException, UnknownHostException{
        System.out.println(InetAddress.getLocalHost().getHostAddress());
       ControllerJuego control = ControllerJuego.obtieneInstancia();
       // Se crea la ventana de configuración inicial y se emuestra.
        Inicial inicial = new Inicial();
        inicial.setVisible(true);
        while(inicial.isDisplayable()){
            Thread.sleep(500);
        }
        
        // Si se seleccionó "Siguiente"
        if(inicial.wasSiguienteSelected()){
            if(inicial.tipoPartida){
                Timbiriche ventanaJuego = Timbiriche.obtieneInstancia();
            ventanaJuego.setVisible(true);  
                control.crearPartida(inicial.nJugadores, inicial.nickname);
            }
            else{
                control.unirsePartida(inicial.ip, inicial.puerto, inicial.nickname);
                Timbiriche ventanaJuego = Timbiriche.obtieneInstancia();
            ventanaJuego.setVisible(true);
            }
            
        }
        
//        controller.iniciarServidor();
//        System.out.println("Así es");
        
      
       
      
        
        
    }
}
