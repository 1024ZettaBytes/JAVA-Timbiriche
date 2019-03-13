
package Conexion;

import Controller.ControllerJuego;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ariel AB
 */
public class Conexion {

    ArrayList<Socket> socketsJugadores;
    ControllerJuego control;
    int nJugadores;
    ObjectOutputStream salida;
    ObjectInputStream entrada;
    ArrayList<String[]> datosJugadores;
    int nextID;
    int miNum;
    String miNickname;

    public Conexion() {
        control = ControllerJuego.obtieneInstancia();
        datosJugadores = new ArrayList<>();
        socketsJugadores = new ArrayList<>();
    }

    public void crearServerPartida(int nJugadores, String nickname) {
        //Se agrega a sí mismo a la lista de datos
        this.nJugadores = nJugadores;
        miNum = 1;
        miNickname = nickname;
        String[] yo = {miNum + "", this.nJugadores + "",miNickname};
        nextID = 2;
        datosJugadores.add(yo);

        control = ControllerJuego.obtieneInstancia();

        // Se crea el servidor
        Thread servidor = new Thread() {

            @Override
            public void run() {

                ServerSocket listener = null;
                Socket conectado = null;

                try {
                    control = ControllerJuego.obtieneInstancia();
                    listener = new ServerSocket(6666);

                    while (datosJugadores.size() < nJugadores) {
                        if (conectado == null) {
                            control.upModeloLog("Hola " + nickname + ", iniciaste la partida.");
                             control.upModeloLog("Esperando conexiones...");
                        }
                        System.out.println("Esperando conexión...");
                        conectado = listener.accept();
                        socketsJugadores.add(conectado);
                        // Se recibe el nickname del jugador conectado y actualiza el modelo
                        entrada = new ObjectInputStream(conectado.getInputStream());
                        String nicknameConectado = (String) entrada.readObject();

                        // Se agregan los datos del jugador a la lista
                        String[] datosJugador = {nextID + "", conectado.getInetAddress().getHostAddress(), nicknameConectado};

                        datosJugadores.add(datosJugador);
                        //Se envía la lista de datos
                        Thread.sleep(500);
                        salida = new ObjectOutputStream(conectado.getOutputStream());
                        salida.writeObject(datosJugadores);
                        control.upModeloLog("El jugador '" + nicknameConectado + "' se unió a la partida.");
                       
                        // Se inicia el hilo para escuchar las órdenes del jugador conectado
                        escuchaOrdenes(conectado, nextID, nicknameConectado);
                        nextID++;
                    }
                    // Depués de que todos se conectaron 

                    control.upModeloLog("Todos los jugadores se conectaron.");
                    control.iniciarPartida(datosJugadores, miNum, miNickname);
                    datosJugadores.forEach((datos) -> {
                        System.out.println(Arrays.toString(datos));
                    });
                } catch (IOException e) {
                     control.upModeloLog("No se pudo inicializar la partida, cierre la aplicación e intente nuevamente.");
                } catch (InterruptedException ex) {

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };

        servidor.start();

    }

    public void conectarse(String IP, int puerto, String nickname) {
        miNickname = nickname;
        datosJugadores = new ArrayList<>();
        control = ControllerJuego.obtieneInstancia();
        try {

            // Se conecta al servidor
            Socket server = new Socket(IP, puerto);
            socketsJugadores.add(server);
            // Envia su nickname
            Thread.sleep(500);
            salida = new ObjectOutputStream(server.getOutputStream());
            salida.writeObject(nickname);
            // Recibe la lista de datos de los jugadores en la partida
            System.out.println("Esperando para recibir la lista de datos...");
            entrada = new ObjectInputStream(server.getInputStream());

            datosJugadores = (ArrayList<String[]>) entrada.readObject();

            nJugadores = Integer.valueOf(datosJugadores.get(0)[1]);
            String nicknameServer = datosJugadores.get(0)[2];
            miNum = Integer.valueOf(datosJugadores.get(1)[0]);
            nextID = miNum + 1;
            if (nJugadores > 2) {
                if (datosJugadores.size() > 2) {

                    //Se conecta a los demás
                    List<String[]> restoJugadores = datosJugadores.subList(1, datosJugadores.size() - 1);
                    for (String[] datoJugador : restoJugadores) {

                        Socket jugador = new Socket(datoJugador[1], 6666);
                        socketsJugadores.add(jugador);
                        // Se envía el nickname
                        salida = new ObjectOutputStream(server.getOutputStream());
                        salida.writeObject(miNickname);
                        // Crea hilo para escuchar órdenes del jugador a al que se conectó.
                        escuchaOrdenes(jugador, Integer.valueOf(datoJugador[0]), datoJugador[2]);

                    }
                }
                // Crea hilo para escuchar órdenes del server.
                System.out.println("ID del server: "+datosJugadores.get(0)[0]);
                escuchaOrdenes(server, Integer.valueOf(datosJugadores.get(0)[0]), nicknameServer);

                control.upModeloLog("Te uniste a la partida de " + nicknameServer + ".");
                if (datosJugadores.size() < nJugadores) {
                    control.upModeloLog("Esperando por los demás jugadores...");
                    crearServerEscucha(puerto);
                } else {
                    control.iniciarPartida(datosJugadores, miNum, miNickname);
                    control.upModeloLog("Todos los jugadores conectados");
                    
                }
            } else {
                control.upModeloLog("Te uniste a la partida de " + nicknameServer + ".");
                // Crea hilo para escuchar órdenes del server.
                System.out.println("ID del server: "+datosJugadores.get(0)[0]);
                escuchaOrdenes(server, Integer.valueOf(datosJugadores.get(0)[0]), nicknameServer);
                // Depués de que todos se conectaron 
                control.iniciarPartida(datosJugadores, miNum, miNickname);

                control.upModeloLog("Todos los jugadores conectados.");
                
                datosJugadores.forEach((datos) -> {
                    System.out.println(Arrays.toString(datos));
                });
            }

        } catch (ClassNotFoundException ex) {

        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearServerEscucha(int puerto) {

        // Se crea el servidor
        Thread servidorEscucha = new Thread() {

            @Override
            public void run() {

                ServerSocket listener = null;
                Socket conectado = null;

                try {
                    control = ControllerJuego.obtieneInstancia();
                    listener = new ServerSocket(puerto);
                    while (datosJugadores.size() < nJugadores) {
                        if (conectado == null) {
                            System.out.println("Server para escuchar conexiones iniciado...");
                        }
                        // Espera a que se conecte
                        conectado = listener.accept();
                        socketsJugadores.add(conectado);
                        // Se recibe el nickname del jugador conectado
                        entrada = new ObjectInputStream(conectado.getInputStream());
                        String nicknameConectado = (String) entrada.readObject();
                        String[] datosJugador = {nextID + "", conectado.getInetAddress().getHostName(), nicknameConectado};
                        // Agrega los datos del jugador a la lista
                        datosJugadores.add(datosJugador);
                        // Se actualiza el modelo cuando un jugador se conectó.
                        control.upModeloLog("El jugador '" + nicknameConectado + "' se unió a la partida.");
                        escuchaOrdenes(conectado, nextID, nicknameConectado);
                        nextID++;
                    }
                    // Depués de que todos se conectaron 
                    control.upModeloLog("Todos los jugadores conectados.");

                    control.iniciarPartida(datosJugadores, miNum, miNickname);
                    datosJugadores.forEach((datos) -> {
                        System.out.println(Arrays.toString(datos));
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    // System.out.println("No se pudo inicializar el server.");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        servidorEscucha.start();
    }

    public void enviarOrden(String orden) {
        ArrayList<Socket> copiaSockets = new ArrayList<>(socketsJugadores);
        for (Socket jugador : copiaSockets) {
            
            try {
                ObjectOutputStream salidaJugador = new ObjectOutputStream(jugador.getOutputStream());
                salidaJugador.writeObject(orden);
                if(orden.contains("DESCONECTARSE")){
                    socketsJugadores.removeAll(socketsJugadores);
                }
                System.out.println("Se envió movimiento.");
            } catch (IOException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void escuchaOrdenes(Socket jugador, int idJugador, String nickname) {
        Thread hiloEscucha = new Thread() {
            ObjectInputStream entradaJugador;

            @Override
            public void run() {

                System.out.println("Se inicia el hilo para escuchar al jugador conectado");
                try {

                    while (socketsJugadores.contains(jugador)) {
                        
                        entradaJugador = new ObjectInputStream(jugador.getInputStream());
                        String orden = (String) entrada.readObject();
                        control = ControllerJuego.obtieneInstancia();

                        if (orden.equals("DESCONECTARSE")) {
                            if(socketsJugadores.get(0).equals(jugador)){
                                 control.upModeloLog("El servidor se desconectó, por favor, cierre la aplicación y abrala de nuevo.");
                                
                            }
                            jugador.close();
                            socketsJugadores.remove(jugador);
                            for (String[] datosJugador : datosJugadores) {
                                if (Integer.valueOf(datosJugador[0]) == idJugador) {
                                    datosJugadores.remove(datosJugador);
                                    break;
                                }
                            }
                            control.jugadorDesconectado(idJugador);
                            break;
                        } else {
                            System.out.println("Orden recibida:"+orden);
                            control.procesarOrden(orden, idJugador);
                        }
                    }
                    System.out.println("El jugador se desconectó.");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    if(socketsJugadores.contains(jugador)){
                    try {
                        jugador.close();
                    } catch (IOException ex1) {
                        // Esto nunca sucede, pero se necesita este try-catch para poder usar el método close()
                    }
                    socketsJugadores.remove(jugador);
                    for (String[] datosJugador : datosJugadores) {
                        if (Integer.valueOf(datosJugador[0]) == idJugador) {
                            datosJugadores.remove(datosJugador);
                            break;
                        }
                    }
                    control.jugadorDesconectado(idJugador);
                }
                }
                
            }

        };
        hiloEscucha.start();
    }

    public void terminarConexiones() {
socketsJugadores.removeAll(socketsJugadores);
    }

}
