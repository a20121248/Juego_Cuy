/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.Scanner;
import java.io.IOException;
import Controller.*;
import Model.*;


public class Juego {
    private final Renderizador rend;
    private final InterpreteComandos lector;
    private final GestorMapas gestorMapa;
    private final Personaje p1;
    private final Personaje p2;
    //private Enemigo enemigo;
    private final Scanner scan;
    private int nivel;
    private String nombre1, nombre2;
    
    public Juego(){
        rend = new Renderizador();
        lector = new InterpreteComandos();
        gestorMapa = new GestorMapas();
        scan = new Scanner(System.in);
        /*NIVEL 1*/
        p1 = new Personaje('A');
        nombre1 = "Player 1";
        nombre2 = "Player 2";
        p2 = new Personaje('B');
        //enemigo = new Enemigo('E');
        nivel = 0;
        this.inicializarPersonajes(nivel);
    }
    
    public boolean bienvenida() throws IOException, InterruptedException{
        cleanWindow();
        System.out.println("Bievenido al juego");
        System.out.println("(Presiona ENTER para continuar...)");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        do{
            cleanWindow();
            System.out.println("1. Empezar el juego");
            System.out.println("2. Salir");
            int opc = 0;
            try{
                opc =  scan.nextInt();
            } catch (java.util.InputMismatchException ex){
                scan.nextLine();
            }
            if (opc == 1) {
                /*NIVEL 1*/
                /*COMPLETAR: LEER NOMBRES*/
                nivel = 0;
                inicializarPersonajes(nivel);
                scan.nextLine();
                return false;
            }
            else if (opc == 2){
                confirmarFinJuego();
            }
        } while(true);
        
    }
    private void confirmarFinJuego(){
        System.out.println("Desea salir? (1. Si, 2. No)");
        int salir = 0;
        try{
            salir = scan.nextInt();
        } catch (java.util.InputMismatchException ex){
            scan.nextLine();
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
        if (obj1 instanceof Terreno )
            if (((Terreno) obj1).getTipo() == 3 && ((Terreno) obj1).getActivo()){
                System.out.print("Escriba la accion(" + p1.getAccionEspecial(nivel) +"): ");
                accion = scan.nextLine();
                /*EJECUTA ACCION ESPECIAL SEGUN NIVEL*/
                boolean exito = lector.interpretaAccionEspecial(accion,gestorMapa.getMapa(nivel), p1, p2,nivel);
                if (exito)
                    ejecutarAccionEspecial(1);
                return;
            }
        
        if (obj2 instanceof Terreno)
            if (((Terreno) obj2).getTipo() == 3 && ((Terreno) obj2).getActivo()){
                System.out.print("Escriba la accion("+ p2.getAccionEspecial(nivel)+ "): ");
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
                    System.out.print("Escriba la accion duo(" + p2.getAccionDuo(nivel) + "): ");
                    accion = scan.nextLine();
                    /*EJECUTA ACCION DUO SEGUN NIVEL*/
                    boolean exito = lector.interpretaAccionEspecial(accion,gestorMapa.getMapa(nivel), p1, p2,nivel);
                    if (exito)
                        ejecutarAccionEspecial(3);
                    return;
                }
            }
        System.out.print("Escriba la accion mover(W-A-S-D / I-J-K-L): ");
        accion = scan.nextLine();
        lector.interpretaMovimiento(accion, p1,p2,gestorMapa,nivel);
    }
    
    private void ejecutarAccionEspecial(int player)throws IOException, InterruptedException{
        /*PLAYER INDICA QUE JUGADOR MANDÓ LA ACCION*/
        if (nivel == 0){
            if (player == 1){
                //RECORRE TERRITORIO
                int xOrig = p1.getPosX(); int yOrig = p1.getPosY();
                p1.setPosY(yOrig + 1);
                this.renderizar();
                System.out.println("\nPresiona ENTER para continuar...");
                scan.nextLine();
                /////
                p1.setPosY(yOrig + 2);
                this.renderizar();
                System.out.println("\nPresiona ENTER para continuar...");
                scan.nextLine();
                /////
                p1.setPosY(yOrig + 4);
                this.renderizar();
                System.out.println("\nPresiona ENTER para continuar...");
                scan.nextLine();
                //AQUI DESTRUYE ESAS COSAS
                Celda celda1 = gestorMapa.getMapa(nivel).getMapaAt(yOrig + 4, xOrig);
                Celda celda2 = gestorMapa.getMapa(nivel).getMapaAt(yOrig + 5, xOrig);
                celda1.setObj(new Terreno('N',2));
                celda2.setObj(new Terreno('N',2));
                this.renderizar();
                System.out.println("\nPresiona ENTER para continuar...");
                scan.nextLine();
                //VUELVE AL ORIGINAL
                p1.setPosX(xOrig);  p1.setPosY(yOrig);
            }
            else if(player == 2){
                //RECORRE TERRITORIO
                int xOrig = p2.getPosX(); int yOrig = p2.getPosY();
                p2.setPosX(xOrig - 1);
                this.renderizar();
                System.out.println("\nPresiona ENTER para continuar...");
                scan.nextLine();
                /////
                p2.setPosX(xOrig - 2);
                //DESTRUYE LA ARENA
                Celda celda = gestorMapa.getMapa(nivel).getMapaAt(yOrig, xOrig - 1);
                celda.setObj(new Terreno('N',2));
                this.renderizar();
                System.out.println("\nPresiona ENTER para continuar...");
                scan.nextLine();
            } else if (player == 3){
                //NOTHING
            }
        }
        //COMPLETAR EL OTRO NIVEL
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
                System.out.print("Acabaste el nivel ");
                System.out.print(nivel);
                System.out.print(". Presiona ENTER para continuar...");
                scan.nextLine();
                inicializarPersonajes(nivel);
            }
        }
    }
    private void renderizar() throws IOException, InterruptedException{
        cleanWindow();
        /*MOSTRAR MAPA*/
        System.out.print("Jugador 1: " + this.nombre1);
        System.out.println("\tJugador 2: " + this.nombre2);
        System.out.println("Puntos de vida: " + p1.getVida());
        System.out.println("Nivel: " + (nivel + 1));
        rend.mostrarMapa(gestorMapa,nivel,p1,p2);
    }
    
    private boolean finJuego(){        
        /*TOPE NIVEL: 2 (cantidad de mapas)*/
        /*Si ha muerto o terminó todos los niveles*/
        return (p1.getVida() <= 0) || (nivel == 2);
    }
    
    private void inicializarPersonajes(int nivel){
        /*AQUI SE PUEDE REALIZAR LECTURA DE PERSONAJE Y ENEMIGO*/
        /*SUS DATOS, ETC*/
        if (nivel == 0){
            p1.setPosY(5);  p1.setPosX(15); p1.setVida(10);
            p1.setAccionEspecial("SDQEQE", nivel);
            p2.setPosY(9);  p2.setPosX(15); p2.setVida(10);
            p2.setAccionEspecial("JJUOJ", nivel);
            p2.setAccionDuo("", nivel);
        }
        if (nivel == 1){
            p1.setPosY(5);  p1.setPosX(0); p1.setVida(10);
            p1.setAccionEspecial("SDEWD", nivel);
            p2.setPosY(10);  p2.setPosX(0); p2.setVida(10);
            p2.setAccionEspecial("", nivel);
            p1.setAccionDuo("SIQEUOKLSD", nivel);
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
    
    private void cleanWindow() throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); //Limpia ventana
    }
}

    
    