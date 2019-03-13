
package Vistas;

import javax.swing.JButton;

/**
 *
 * @author eduardo
 */
public class Cuadro extends JButton{
   private int idJugador;
   public void setIdJugador(int idJugador){
       this.idJugador = idJugador;
   }
   public void restablece(){
       setText("");
       idJugador=0;
       
   }
   public int getJugador(){
       return this.idJugador;
   }
}
