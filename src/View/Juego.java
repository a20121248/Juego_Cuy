/*
 * To change this license header, choo  se License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.Scanner;
import java.io.IOException;
import Controller.*;
import Model.*;
import java.util.List;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class Juego {
    private Renderizador rend;
    private InterpreteComandos lector;
    private GestorMapas gestorMapa;
    private Personaje p1;
    private Personaje p2;
    private final Scanner scan;
    private int nivel;
    
    public Juego(){
        rend = new Renderizador();
        lector = new InterpreteComandos();
        gestorMapa = new GestorMapas();
        scan = new Scanner(System.in);
        p1 = p2 = null;
        nivel = 3;
        this.inicializarPersonajes(nivel);
        this.inicializarActividad(nivel);
    }
    
    public boolean bienvenida() throws IOException, InterruptedException{
        cleanWindow();
        System.out.println("Bievenido al juego");
        System.out.println("(Presiona ENTER para continuar...)");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        cleanWindow();
        do {            
            System.out.println("1. Empezar el juego");
            System.out.println("2. Salir");
            //Leer opción
            int opc = 0;
            String aux = scan.nextLine();
            try {
                opc = Integer.parseInt(aux);
            } catch (NumberFormatException ex) {
                System.out.println("Error: Ingrese una opcion valida.");
                System.out.println();
                continue;
            }            
            if (opc == 1) {
                /* Leer nombres de jugadores */
                cleanWindow();
                
                //Si ya se jugó una partida anterior ya no se piden los nombres
                
                if (this.p1.getNombre() == null || this.p2.getNombre() == null) {
                    System.out.println("Ingrese el nombre del primer jugador:");
                    this.p1.setNombre(scan.nextLine());
                    System.out.println("Ingrese el nombre del segundo jugador:");
                    this.p2.setNombre(scan.nextLine());
                }
                
                cleanWindow();              
                
                if (nivel >= gestorMapa.getNumNiveles()) {
                    nivel = 0;
                    p1.setVida(10);
                }
                inicializarPersonajes(nivel);
                inicializarActividad(nivel);
                return false;
            }
            else if (opc == 2){
                confirmarFinJuego();
            } else if (opc == 3) {
                //CARGAR ARCHIVO GUARDADO
                //inicializarPersonajes(nivel);
                //inicializarActividad(nivel);
                //return false;
            }
        } while (true);
        
    }
    private void confirmarFinJuego(){
        System.out.println("Desea salir? (1. Si, 2. No)");
        int salir = 0;
        String aux = scan.nextLine();
        try {
            salir = Integer.parseInt(aux);
        } catch (NumberFormatException ex){
            //Regresará al menú principal
        }
        if (salir == 1){
            System.exit(1);
        }
    }
    private void capturarAccion() throws IOException, InterruptedException{
        String accion;
        int posX1 = p1.getPosX();   int posY1 = p1.getPosY();
        int posX2 = p2.getPosX();   int posY2 = p2.getPosY();
        Mapa m = this.gestorMapa.getMapa(nivel);
        Dibujable obj1 = m.getMapaAt(posY1, posX1).getObj();
        Dibujable obj2  = m.getMapaAt(posY2, posX2).getObj();
        
        /************** PRIMER CASO: ACCIÓN ESPECIAL **************/
        
        if (obj1 instanceof Terreno )
            if (((Terreno) obj1).getTipo() == 3 && ((Terreno) obj1).getActivo()){
                System.out.print("Escriba la accion (" + p1.getAccionEspecial(nivel) +"): ");
                accion = scan.nextLine();
                /*EJECUTA ACCION ESPECIAL SEGUN NIVEL*/
                boolean exito = lector.interpretaAccionEspecial(accion,gestorMapa.getMapa(nivel), p1, p2,nivel);
                if (exito)
                    ejecutarAccionEspecial(1);
                return;
            }
        
        if (obj2 instanceof Terreno)
            if (((Terreno) obj2).getTipo() == 3 && ((Terreno) obj2).getActivo()){
                System.out.print("Escriba la accion ("+ p2.getAccionEspecial(nivel)+ "): ");
                accion = scan.nextLine();
                /*EJECUTA ACCION ESPECIAL SEGUN NIVEL*/
                boolean exito = lector.interpretaAccionEspecial(accion,gestorMapa.getMapa(nivel), p1, p2,nivel);
                if (exito)
                    ejecutarAccionEspecial(2);
                return;
            } else if (obj1 instanceof Terreno){
                if (((Terreno) obj1).getTipo() == 4 && 
                    ((Terreno) obj2).getTipo() == 4 &&
                    ((Terreno) obj1).getActivo()    &&
                    ((Terreno) obj2).getActivo()){
                    System.out.print("Escriba la accion duo (" + p2.getAccionDuo(nivel) + "): ");
                    accion = scan.nextLine();
                    /*EJECUTA ACCION DUO SEGUN NIVEL*/
                    boolean exito = lector.interpretaAccionEspecial(accion,gestorMapa.getMapa(nivel), p1, p2,nivel);
                    if (exito)
                        ejecutarAccionEspecial(3);
                    return;
                }
            }
        
        /************** SEGUNDO CASO: MOVIMIENTO COMÚN **************/
        
        System.out.print("Escriba la accion mover (W-A-S-D / I-J-K-L): ");
        accion = scan.nextLine();
        
        //Verificar el tipo de comando
        
        if (accion.equalsIgnoreCase("SAVE")) { //GUARDAR PARTIDA
            //guardarPartida();
            System.out.println("Partida guardada correctamente!");
            System.out.println("(presione ENTER para continuar)");
            scan.nextLine();
        } 
        
        else if (accion.equalsIgnoreCase("EXIT")) { //SALIR DEL JUEGO
            System.out.println();
            System.out.println("Realmente desea salir del juego? (1. Si, 2. No)");
            try {
                int opcion = Integer.parseInt(scan.nextLine());
                if (opcion == 1) this.p1.setVida(0);
            } catch (NumberFormatException ex) {
                //No hace nada, regresa a seguir jugando
            }
        }
        
        else lector.interpretaMovimiento(accion, p1, p2, gestorMapa, nivel);
    }
    
    private void ejecutarAccionEspecial(int player)throws IOException, InterruptedException{
        /*PLAYER INDICA QUE JUGADOR MANDÓ LA ACCION*/
        if (nivel == 0){
            if (player == 1){
                for (int i = 0; i < 3; i++){
                    p1.setPosX(p1.getPosX() + 1);
                    this.renderizar();
                    System.out.println("Presiona ENTER para continuar...");
                    scan.nextLine();
                }
                //ACTIVAR TERRENOS
                List listaTerrenoInactivos = gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < listaTerrenoInactivos.size(); i++){
                    Terreno terreno = (Terreno) listaTerrenoInactivos.get(i);
                    terreno.setActivo(true);
                }
            }
            else if(player == 2){
                //NOTHING
            } else if (player == 3){
                p1.setPosY(p1.getPosY()+1);
                p2.setPosY(p2.getPosY()-1);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                for (int i = 0; i < 2; i++){
                    p1.setPosX(p1.getPosX()+1);
                    p2.setPosX(p2.getPosX()+1);
                    this.renderizar();
                    System.out.println("Presiona ENTER para continuar...");
                    scan.nextLine();
                }
            }
        }
        else if (nivel == 1){
            if (player == 1){
                int xOrig = p1.getPosX(); int yOrig = p1.getPosY();
                for (int i = 0; i < 2; i++){
                    p1.setPosY(p1.getPosY() + 1);
                    this.renderizar();
                    System.out.println("Presiona ENTER para continuar...");
                    scan.nextLine();
                }
                p1.setPosY(yOrig + 4);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                //AQUI DESTRUYE ESAS COSAS
                Celda celda1 = gestorMapa.getMapa(nivel).getMapaAt(yOrig + 4, xOrig);
                Celda celda2 = gestorMapa.getMapa(nivel).getMapaAt(yOrig + 5, xOrig);
                celda1.setObj(new Terreno('N',2));
                celda2.setObj(new Terreno('N',2));
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                //VUELVE AL ORIGINAL
                p1.setPosX(xOrig);  p1.setPosY(yOrig);
                //ACTIVAR TERRENOS
                List listaDuo = gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < listaDuo.size(); i++){
                    Terreno terreno = (Terreno) listaDuo.get(i);
                    terreno.setActivo(true);
                }
            }
            else if(player == 2){
                //RECORRE TERRITORIO
                int xOrig = p2.getPosX(); int yOrig = p2.getPosY();
                p2.setPosX(xOrig - 1);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                /////
                p2.setPosX(xOrig - 2);
                //DESTRUYE LA ARENA
                Celda celda = gestorMapa.getMapa(nivel).getMapaAt(yOrig, xOrig - 1);
                celda.setObj(new Terreno('N',2));
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
            } else if (player == 3){
                //NOTHING
            }
        } else if (nivel == 2){
            if (player == 1){
                for (int i = 0; i < 3; i++){
                    p1.setPosX(p1.getPosX() + 1);
                    this.renderizar();
                    System.out.println("Presiona ENTER para continuar...");
                    scan.nextLine();
                }
                //ACTIVAR TERRENOS
                List listaTerrenoInactivos = gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < listaTerrenoInactivos.size(); i++){
                    Terreno terreno = (Terreno) listaTerrenoInactivos.get(i);
                    terreno.setActivo(true);
                }
            } else if (player == 2){
                //NOTHING
            } else if (player == 3){
                p1.setPosX(p1.getPosX()+1);
                p2.setPosX(p2.getPosX()+1);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                p1.setPosX(p1.getPosX()+3);
                p1.setPosY(p1.getPosY()-1);
                p2.setPosX(p2.getPosX()+3);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                p1.setPosY(p1.getPosY()+1);
                p2.setPosY(p2.getPosY()-1);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
            }
        } else if (nivel == 3){//NIVEL CON ENEMIGO
            if (player == 1){
                //NADA
            } else if (player == 2){
                //ANIMARLO
                Terreno t = new Terreno('S', 1);
                Mapa m = gestorMapa.getMapa(nivel);
                m.setMapaAt(4, 9, t);
                m.setMapaAt(3, 10, t);
                m.setMapaAt(4, 10, t);
                m.setMapaAt(5, 10, t);
                m.setMapaAt(6, 10, t);
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                //DESACTIVA TRIGGER 1
            } else if (player == 3){
                //NOTHING
            }
        }
    }
    
    private void actualizarInfo()throws IOException, InterruptedException{
        /*Llegó al final de la menta, pasa de nivel*/
        /*VERIFICA P1 y P2 EN META*/
        
        int posX1 = p1.getPosX(); int posY1 = p1.getPosY();
        int posX2 = p2.getPosX(); int posY2 = p2.getPosY();
        
        Celda celda1 = gestorMapa.getMapa(nivel).getMapaAt(posY1, posX1);
        Celda celda2 = gestorMapa.getMapa(nivel).getMapaAt(posY2, posX2);
        if (celda1.getObj() instanceof Terreno && celda2.getObj() instanceof Terreno){
            if (((Terreno)celda1.getObj()).getTipo() == 6 && 
                ((Terreno)celda2.getObj()).getTipo() == 6){
                renderizar();
                nivel += 1;
                System.out.print("Acabaste el nivel " + (nivel - 1));
                System.out.print(". Presiona ENTER para continuar...");
                scan.nextLine();
                inicializarPersonajes(nivel);
                inicializarActividad(nivel);
            }
        }
        if (celda1.getObj() instanceof Terreno || celda2.getObj() instanceof Terreno){
            Terreno ter = (Terreno) celda1.getObj();
            if (ter.getActivo() && ter.getTipo() == 5){
                //ACTIVE LA VISIBILIDAD DEL ENEMIGO Y QUE LO MUESTRE
                Enemigo e = this.gestorMapa.getEnemigo(nivel);
                e.setElementoGrafico('E');
                //DISMINUIR LA VIDA DEL JUGADOR 1
                this.p1.setVida(this.p1.getVida() - 1);
                //Activar el terreno de accion
                List l = this.gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < l.size(); i++) {
                    Terreno t = (Terreno)(l.get(i));
                    t.setActivo(true);
                }
            }
            
        }
    
    }
    private void renderizar() throws IOException, InterruptedException{
        cleanWindow();
        /*MOSTRAR MAPA*/
        System.out.print("Jugador 1: " + this.p1.getNombre());
        System.out.println("\tJugador 2: " + this.p2.getNombre());
        System.out.println("Puntos de vida: " + p1.getVida());
        System.out.println("Nivel: " + nivel);
        rend.mostrarMapa(gestorMapa,nivel,p1,p2);
    }
    
    private boolean finJuego(){        
        /*TOPE NIVEL: cantidad de mapas*/
        /*Si ha muerto o terminó todos los niveles*/
        return (p1.getVida() <= 0) || (nivel == gestorMapa.getNumNiveles());
    }
    
    private void inicializarPersonajes(int nivel){
        /*AQUI SE PUEDE REALIZAR LECTURA DE PERSONAJE Y ENEMIGO*/
        /*SUS DATOS, ETC*/
        if (p1 == null)
            p1 = new Personaje('A');
        if (p2 == null)
            p2 = new Personaje('B');
        if (p1.getVida() <= 0)
            p1.setVida(10);
        if (nivel == 0)
            CargaDatosXML(0);
        else if (nivel == 1)
            CargaDatosXML(1);
        else if (nivel == 2)
            CargaDatosXML(2);
        else if (nivel == 3)
            CargaDatosXML(3);
        
    }
    
    private void CargaDatosXML(int nivel) {
        try {	
         File inputFile = new File("niveles.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         NodeList nList = doc.getElementsByTagName("Nivel");
         Node nNode = nList.item(nivel);
         Element eElement = (Element) nNode;
         p1.setPosY(Integer.parseInt(eElement.
                           getElementsByTagName("p1PosY").item(0).getTextContent()));
         p1.setPosX(Integer.parseInt(eElement.
                           getElementsByTagName("p1PosX").item(0).getTextContent()));
         p1.setAccionEspecial(eElement.
                           getElementsByTagName("p1AccEsp").item(0).getTextContent(), nivel);
         p2.setPosY(Integer.parseInt(eElement.
                           getElementsByTagName("p2PosY").item(0).getTextContent()));
         p2.setPosX(Integer.parseInt(eElement.
                           getElementsByTagName("p2PosX").item(0).getTextContent())); 
         p2.setAccionEspecial(eElement.
                           getElementsByTagName("p2AccEsp").item(0).getTextContent(), nivel);
         p2.setAccionDuo(eElement.
                           getElementsByTagName("AccDuo").item(0).getTextContent(), nivel);
      } catch (Exception e) {
         e.printStackTrace();
      }
    }    
    
    
    private void inicializarActividad(int nivel){
        Mapa mapa = gestorMapa.getMapa(nivel);
        if (nivel == 0){
            Terreno terreno1 = ((Terreno) mapa.getMapaAt(5, 13).getObj());
            Terreno terreno2 = ((Terreno) mapa.getMapaAt(8, 13).getObj());
            terreno1.setActivo(false);
            terreno2.setActivo(false);
            mapa.getListaTerrenoInactivo().add(terreno1);
            mapa.getListaTerrenoInactivo().add(terreno2);
        } else if (nivel == 1){
            Terreno terreno1 = ((Terreno) mapa.getMapaAt(9, 4).getObj());
            terreno1.setActivo(false);
            mapa.getListaTerrenoInactivo().add(terreno1);
        } else if (nivel == 2){
            Terreno terreno1 = ((Terreno) mapa.getMapaAt(6, 8).getObj());
            Terreno terreno2 = ((Terreno) mapa.getMapaAt(8, 8).getObj());
            terreno1.setActivo(false);
            terreno2.setActivo(false);
            mapa.getListaTerrenoInactivo().add(terreno1);
            mapa.getListaTerrenoInactivo().add(terreno2);
        } else if (nivel == 3){
            Terreno terreno1 = ((Terreno) mapa.getMapaAt(9, 10).getObj());
            terreno1.setActivo(false);
            mapa.getListaTerrenoInactivo().add(terreno1);
        }
    }
    public void start() throws IOException, InterruptedException{
        while(true){
            if (bienvenida()) break;
            do {
                renderizar();
                capturarAccion();
                actualizarInfo();
            } while(!finJuego());
            System.out.println("\n G A M E   O V E R !");
            System.out.print("Presione ENTER para volver al menu de bienvenida.");
            scan.nextLine();
        }
    }
    
    private void cleanWindow() throws IOException, InterruptedException {
        //Limpia la ventana (solo funciona en consola)
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}

    
    