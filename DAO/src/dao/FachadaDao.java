package dao;

import Interfaces.IFachadaDao;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Ariel AB
 */
public class FachadaDao implements IFachadaDao {

    private int nJugadores;
    private Linea[] listaLineas;
    private Cuadrado[] listaCuadros;
    private ArrayList<Jugador> jugadores;
    int tamTablero;
    int miIdJugador;
    int turno;
int colorContador=0;
    public FachadaDao() {
        this.jugadores = new ArrayList<>();
    }

    @Override
    public void construirTablero() {
        this.nJugadores = jugadores.size();
        if (this.nJugadores == 2) {
            tamTablero = 10;
        } else {
            tamTablero = 20;
        }
        // Se establecen las líneas
        listaLineas = new Linea[(((tamTablero - 1) * tamTablero) * 2) + 1];
        
        for (int x = 1; x < listaLineas.length; x++) {

            Linea linea = new Linea(x);
            listaLineas[x] = linea;
        }
        // Se establecen los cuadros
        listaCuadros = new Cuadrado[(tamTablero - 1) * (tamTablero - 1) + 1];
        for (int x = 1; x < listaCuadros.length; x++) {
            int lineaArriba = x;
            int lineaAbajo = x + tamTablero - 1;
            int lineaIzquierda;
            if (x <= (tamTablero-1)) {
                lineaIzquierda = (tamTablero * (tamTablero - 1)) + x;
            } else if (x % (tamTablero-1) == 0) {
                lineaIzquierda = (tamTablero * (tamTablero - 1)) + ((x / tamTablero) * tamTablero) + (tamTablero - 1);
            } else {
                lineaIzquierda = (tamTablero * (tamTablero - 1)) + ((x / tamTablero) * tamTablero) + (x / (tamTablero - 1)) + (x % tamTablero);
            }

            int lineaDerecha = lineaIzquierda + 1;

            Cuadrado cuadro = new Cuadrado(x);
            cuadro.setLineas(listaLineas[lineaArriba], listaLineas[lineaAbajo], listaLineas[lineaIzquierda], listaLineas[lineaDerecha]);

            listaCuadros[x] = cuadro;
            listaLineas[lineaArriba].setNuevoCuadro(x);
            listaLineas[lineaAbajo].setNuevoCuadro(x);
            listaLineas[lineaIzquierda].setNuevoCuadro(x);
            listaLineas[lineaDerecha].setNuevoCuadro(x);
          
        }

    }

    @Override
    public boolean agregarJugador(int id, String nickname) {
        Color color =Color.BLACK;
        switch(colorContador){
            case 0: color = Color.RED;
            break;
            case 1: color = Color.BLUE;
            break;
            case 2: color = Color.GREEN;
            break;
            case 3: color = Color.YELLOW;
            
        }
       boolean agregado=this.jugadores.add(new Jugador(id, nickname, color));
       colorContador++;
        return agregado;
    }

    @Override
    public ArrayList<Integer> ingresarJugada(int nLinea) {
ArrayList<Integer> cerrados = new ArrayList<>();
listaLineas[nLinea].setJugador(miIdJugador);
jugadores.get(jugadores.indexOf(new Jugador(miIdJugador, "", null))).agregarLinea(nLinea);
for(Integer idCuadro:listaLineas[nLinea].getCuadros()){
    
    listaCuadros[idCuadro].marcarLinea(nLinea);
    
if(listaCuadros[idCuadro].isCompleted()){
    
        cerrados.add(idCuadro);
        jugadores.get(jugadores.indexOf(new Jugador(miIdJugador, "", Color.yellow))).agregarCuadro(idCuadro);
        jugadores.get(jugadores.indexOf(new Jugador(miIdJugador, "", Color.yellow))).agregaPuntos(1);
    }
            
}
if(cerrados.size()==0)
    return null;

return cerrados;
        

    }

    @Override
    public String getNicknameJugador(int numJugador) {
        for (Jugador jug : jugadores) {
            if (jug.getId() == numJugador) {
               
                return jug.getNickName();
            }
        }
        return null;
    }

    @Override
    public void guardarJugada(int idJugador, int idLinea, int idCuadro) {
         listaLineas[idLinea].setJugador(idJugador);
         jugadores.get(jugadores.indexOf(new Jugador(idJugador, "", null))).agregarLinea(idLinea);
         for(Integer idCuadr:listaLineas[idLinea].getCuadros()){
             listaCuadros[idCuadr].marcarLinea(idLinea);
         }
         
         listaCuadros[idCuadro].setJugador(idJugador);
         jugadores.get(jugadores.indexOf(new Jugador(idJugador, "", null))).agregaPuntos(1);
         jugadores.get(jugadores.indexOf(new Jugador(idJugador, "", null))).agregarCuadro(idCuadro);
    }
@Override
    public void guardarJugada(int idJugador, int idLinea) {
         listaLineas[idLinea].setJugador(idJugador);
         jugadores.get(jugadores.indexOf(new Jugador(idJugador, "", null))).agregarLinea(idLinea);
         for(Integer idCuadro:listaLineas[idLinea].getCuadros()){
             listaCuadros[idCuadro].marcarLinea(idLinea);
             System.out.println("Lineas marcadas para el cuadro "+idCuadro+":"+listaCuadros[idCuadro].getlineasMarcadas());
         }
    }

    @Override
    public void estableceMiID(int miId) {
       this.miIdJugador = miId;
    }

    @Override
    public int obtieneTurno() {
        return this.turno;
    }

    @Override
    public void setPrimerTurno(int idJugador) {
       this.turno=idJugador;
    }
    @Override
    public void setSiguienteTurno() {
       int posicionActual = jugadores.indexOf(new Jugador(turno, null, null));
        
       if(posicionActual<jugadores.size()-1){
           posicionActual++;
       }
       else{
           posicionActual=0;
       }
        turno = jugadores.get(posicionActual).getId();
    }

    @Override
    public Color getColorJugador(int idJugador) {
        for (Jugador jug : jugadores) {
            if (jug.getId() == idJugador) {
               
                return jug.getColor();
            }
        }
        return null;
    }

    @Override
    public int getMiId() {
        return this.miIdJugador;
    }

    @Override
    public ArrayList<Object[]> getJugadores() {
      ArrayList<Object[]> lista = new ArrayList<>();
      for(Jugador jugador:jugadores){
          Object[] elemento = {jugador.getNickName(), jugador.getPuntos()+"", jugador.getColor()};
          lista.add(elemento);
      }
      return lista;
    }

    @Override
    public void actualizarColor(String nickname, Color color) {
        int index=0;
        for(Jugador jugador:jugadores){
            if(jugador.getNickName().equals(nickname)){
                jugadores.get(index).setColor(color);
                break;
            }
            index++;
        }
    }

    @Override
    public ArrayList<Integer> consultaCuadrosNickname(String nickname) {
       
        for (Jugador jugador : jugadores) {
            if(jugador.getNickName().equals(nickname)){
               return jugador.getCuadros();
            }
           
        }
        return null;
    }

    @Override
    public ArrayList<Integer> consultaLineasNickname(String nickname) {
         for (Jugador jugador : jugadores) {
            if(jugador.getNickName().equals(nickname)){
               return jugador.getLineas();
            }
           
        }
        return null;
    }

    @Override
    public void eliminaJugador(int idJugador) {
    
        // Obtiene las lineas marcadas por ese jugador
        ArrayList<Integer> lineasJugador= jugadores.get(jugadores.indexOf(new Jugador(idJugador, "", null))).getLineas();
        for (Integer idLinea : lineasJugador) {
         

           
                // Restablece la linea
            listaLineas[idLinea].restablecer();
            }
            
        
        // Se obtiene la lista de cuadros llenos por el jugador
        ArrayList<Integer> cuadrosJugador= jugadores.get(jugadores.indexOf(new Jugador(idJugador, "", null))).getCuadros();
        for (Integer idCuadro : cuadrosJugador) {
           
            listaCuadros[idCuadro].restablecer();
        }
         
//Elimina el jugador de la lista
        jugadores.remove(new Jugador(idJugador, "", null));
    }

    @Override
    public boolean cuadrosLlenos() {
        int nCuadrosCOmpletados=0;
        for (Jugador jugador : jugadores) {
            nCuadrosCOmpletados+=jugador.getCuadros().size();
        }
        return nCuadrosCOmpletados==listaCuadros.length-1;
    }

    @Override
    public String getMayorPuntaje() {
        String nickname=jugadores.get(0).getNickName();
        int index=0;
        for (Jugador jugador : jugadores) {
            if(jugador.getPuntos()>jugadores.get(index).getPuntos()){
                index=jugadores.indexOf(jugador);
                nickname = jugador.getNickName();
            }
        }
        return nickname;
}
   
    
}
