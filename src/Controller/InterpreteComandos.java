package Controller;
import Model.*;
import java.util.Scanner;

/**
 *
 * @author Godievski
 */
public class InterpreteComandos {
        
    private boolean movimientoValido(GestorMapas gm, int nivel, int posX, int posY, int dir){
        Mapa m = gm.getMapa(nivel);
        int newPosX = posX;
        int newPosY = posY;
        
        switch (dir) {
            case 1:
                newPosX = newPosX - 1; //Mover a la izquierda
                break;
            case 2:
                newPosX = newPosX + 1; //Mover a la derecha
                break;
            case 3:
                newPosY = newPosY - 1; //Mover hacia arriba
                break;
            case 4:
                newPosY = newPosY + 1; //Mover hacia abajo
                break;
            default:
                break;
        }
        if (!gm.validarPosicion(newPosX,newPosY)) return false; 
        Celda celda;
        celda = m.getMapaAt(newPosY, newPosX);
        if (celda == null){
            return false;
        }
        return (celda.getObj() == null);
    }
    
    public boolean ejecutarAccionEspecial(String accion,Mapa mapa, Personaje p1, Personaje p2){
        
        if ("QEQE".equals(accion)){
            /*Para p1*/
            Celda celda = mapa.getMapaAt( p1.getPosY(),p1.getPosX());
            ((Terreno) celda.getObj()).setTipo(1);
            return true;
        }
        else if("UOUO".equals(accion)){
            /*Para p2*/
            Celda celda = mapa.getMapaAt( p2.getPosY(),p2.getPosX());
            ((Terreno) celda.getObj()).setTipo(2);
            return true;
        } 
        else if ("XXX".equals(accion)) {
            /*Para p1 y  p2*/
            Celda celda1 = mapa.getMapaAt( p1.getPosY(),p1.getPosX());
            Celda celda2 = mapa.getMapaAt( p2.getPosY(),p2.getPosX());
            ((Terreno) celda1.getObj()).setTipo(1);
            ((Terreno) celda2.getObj()).setTipo(2);
            return true;
        }else{
            /*Pierde Vida*/
            p1.setVida(p1.getVida() - 1);
            p2.setVida(p2.getVida() - 1);
            return false;
        }
    }
    
    public void interpretaMovimiento(char c, Personaje p1, Personaje p2,
            GestorMapas gm,int nivel){
        int difX = 0;
        int difY = 0;
        int personaje = 0;
        
        if (c == 'W'){
            difY = -1; personaje = 1;
        } else if (c == 'A'){
            difX = -1;  personaje = 1;
        } else if (c == 'S'){
            difY = 1;   personaje = 1;
        } else if (c == 'D'){
            difX = 1;   personaje = 1;
        }
        if (personaje == 1){
            int xFinal = p1.getPosX() + difX;
            int yFinal = p1.getPosY() + difY;
            if (xFinal >= 0 && xFinal < 16 && yFinal >= 0 && yFinal < 12){
                Celda celda = gm.getMapa(nivel).getMapaAt(yFinal, xFinal);
                if (celda != null && celda.getObj() instanceof Terreno){
                    p1.setPosX(xFinal);
                    p1.setPosY(yFinal);
                }
            }
            return;
        }
        if (c == 'I'){
            difY = -1; personaje = 2;
        } else if (c == 'J'){
            difX = -1;  personaje = 2;
        } else if (c == 'K'){
            difY = 1;   personaje = 2;
        } else if (c == 'L'){
            difX = 1;   personaje = 2;
        }
        if (personaje == 2){
            int xFinal = p2.getPosX() + difX;
            int yFinal = p2.getPosY() + difY;
            if (xFinal >= 0 && xFinal < 16 && yFinal >= 0 && yFinal < 12){
                Celda celda = gm.getMapa(nivel).getMapaAt(yFinal, xFinal);
                if (celda != null && celda.getObj() instanceof Terreno){
                    p2.setPosX(xFinal);
                    p2.setPosY(yFinal);
                }
            }
        }
    }
    
    public void ejecutarMovimiento(char c, Personaje p1, Personaje p2, GestorMapas gm,int nivel,Enemigo enemigo){
        /*PERSONAJE 1*/
        /*Si el personaje está atrapado por un enemigo*/
            if((enemigo != null) &&  p1.getPosX() == enemigo.getPosX() 
                && p1.getPosY() == enemigo.getPosY() ){
                //nada
            }
            else{
                if ( c == 'W' || c == 'w'){
                    if (movimientoValido(gm, nivel,p1.getPosX(),p1.getPosY(),3)){
                        p1.Accion(3);
                    }
                } else if (c == 'A' || c == 'a'){
                    if (movimientoValido(gm, nivel,p1.getPosX(),p1.getPosY(),1)){
                        p1.Accion(1);
                    }
                } else if (c == 'S' || c == 's'){
                    if (movimientoValido(gm, nivel,p1.getPosX(),p1.getPosY(),4)){
                        p1.Accion(4);
                    }
                } else if (c == 'D' || c == 'd'){
                    if (movimientoValido(gm, nivel,p1.getPosX(),p1.getPosY(),2)){
                        p1.Accion(2);
                    }
                }
            }
            if((enemigo != null) && p2.getPosX() == enemigo.getPosX() 
                && p2.getPosY() == enemigo.getPosY()){
                //nada
            }
            else{
                /*PERSONAJE 2*/
                if ( c == 'I' || c == 'i'){
                    if (movimientoValido(gm, nivel,p2.getPosX(),p2.getPosY(),3)){
                        p2.Accion(3);
                    }  
                } else if (c == 'J' || c == 'j'){
                    if (movimientoValido(gm, nivel,p2.getPosX(),p2.getPosY(),1)){
                        p2.Accion(1);
                    }  
                } else if (c == 'K' || c == 'k'){
                    if (movimientoValido(gm, nivel,p2.getPosX(),p2.getPosY(),4)){
                        p2.Accion(4);
                    }    
                } else if (c == 'L' || c == 'l'){
                    if (movimientoValido(gm, nivel,p2.getPosX(),p2.getPosY(),2)){
                        p2.Accion(2);
                    }    
                }
            }   
    }
}
