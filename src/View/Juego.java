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
    private Enemigo enemigo;
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
        enemigo = new Enemigo('E');
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
    private void capturarAccion(){
        /*Si el personaje está en casilla accion*/
        String accion;
        /*
        if(gestorMapa.personajeEnAccion(p1,nivel)){
            System.out.print("Escriba la accion(QEQE): ");
            accion = scan.nextLine();
            if (lector.ejecutarAccionEspecial(accion, p1, p2))
                enemigo = null; //mata al enemigo
        }
        if(gestorMapa.personajeEnAccion(p2,nivel)){
            System.out.print("Escriba la accion(UOUO): ");
            accion = scan.nextLine();
            if (lector.ejecutarAccionEspecial(accion, p1, p2))
                enemigo = null; //mata al enemigo
        }
        System.out.print("Escriba la accion mover(W - A - S - D / I - J - K - L): ");
        accion = scan.nextLine();
        for (int i = 0; i < accion.length(); i++){
            char c = accion.charAt(i);
            lector.ejecutarMovimiento(c, p1,p2,gestorMapa,nivel,enemigo);
        }*/
        int posX1 = p1.getPosX();   int posY1 = p1.getPosY();
        int posX2 = p2.getPosX();   int posY2 = p2.getPosY();
        Mapa m = this.gestorMapa.getMapa(nivel);
        Dibujable obj1 = m.getMapaAt(posY1, posX1).getObj();
        Dibujable obj2  = m.getMapaAt(posY2, posX2).getObj();
        if (obj1 instanceof Terreno )
            if (((Terreno) obj1).getTipo() == 3){
                System.out.print("Escriba la accion(QEQE): ");
                accion = scan.nextLine();
                accion = accion.toUpperCase();
                /*EJECUTA ACCION ESPECIAL SEGUN NIVEL*/
                boolean x = lector.ejecutarAccionEspecial(accion, p1, p2);
                return;
            }
        
        if (obj2 instanceof Terreno)
            if (((Terreno) obj2).getTipo() == 3){
                System.out.print("Escriba la accion(UOUO): ");
                accion = scan.nextLine();
                /*EJECUTA ACCION ESPECIAL SEGUN NIVEL*/
                boolean x = lector.ejecutarAccionEspecial(accion, p1, p2);
                return;
            } else if (obj1 instanceof Terreno){
                if (((Terreno) obj1).getTipo() == 4 && 
                    ((Terreno) obj2).getTipo() == 4){
                    System.out.print("Escriba la accion duo(XXX): ");
                    accion = scan.nextLine();
                    accion = accion.toUpperCase();
                    /*EJECUTA ACCION DUO SEGUN NIVEL*/
                    boolean x = lector.ejecutarAccionEspecial(accion, p1, p2);
                    return;
                }
            }
        System.out.print("Escriba la accion mover(W-A-S-D / I-J-K-L): ");
        accion = scan.nextLine();
        accion = accion.toUpperCase();
        char c = ' ';
        if (accion.length() > 0){
            c = accion.charAt(0);
            lector.interpretaMovimiento(c, p1,p2,gestorMapa,nivel,enemigo);
        }
    }
    
    private void actualizarInfo()throws IOException, InterruptedException{
        /*Llegó al final de la menta, pasa de nivel*/
        /*VERIFICA P1 y P2 EN META*/
        boolean cond1 = false;
        boolean cond2 = false;
        for (int f = 0; f < 12; f++){
            for(int c = 0; c < 16; c++){
                char tipo = gestorMapa.getMapaGraf(nivel, f, c);
                if (tipo == 'F'){
                    cond1 = p1.verificaMeta(c,f);
                    if (cond1) break;
                }
            }
            if (cond1) break;
        }
            
        for (int f = 0; f < 12; f++){
            for(int c = 0; c < 16; c++){
                char tipo = gestorMapa.getMapaGraf(nivel, f, c);
                if (tipo == 'F'){
                    cond2 = p2.verificaMeta(c,f);
                    if (cond2) break;
                }
            }
            if (cond2) break;
        }
        if (cond1 && cond2) {
            renderizar();
            nivel += 1;
            System.out.print("Acabaste el nivel ");
            System.out.print(nivel);
            System.out.print(". Presiona ENTER para continuar...");
            scan.nextLine();
            inicializarPersonajes(nivel);
        }
        
    }
    private void renderizar() throws IOException, InterruptedException{
        cleanWindow();
        /*MOSTRAR MAPA*/
        System.out.print("Jugador 1: ");
        System.out.print(this.nombre1);
        System.out.print("\tJugador 2: ");
        System.out.println(this.nombre2);
        System.out.print("Puntos de vida: ");
        System.out.println(p1.getVida());
        System.out.print("Nivel: ");
        System.out.println(nivel + 1);
        rend.mostrarMapa(gestorMapa,nivel,p1,p2,enemigo);
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
            p2.setPosY(9);  p2.setPosX(15); p2.setVida(10);
            if (enemigo == null)
                enemigo = new Enemigo(3,6,'E');
            else{
                enemigo.setPosX(3); enemigo.setPosY(6);
                
            }
        }
        if (nivel == 1){
            p1.setPosY(5);  p1.setPosX(15); p1.setVida(10); 
            p2.setPosY(9);  p2.setPosX(15); p2.setVida(10);
            if (enemigo == null)
                enemigo = new Enemigo(6,3,'E');
            else{
                enemigo.setPosX(6); enemigo.setPosY(3);
                
            }
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

    
    