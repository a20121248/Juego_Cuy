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
    
    public boolean ejecutarAccionEspecial(String accion, Personaje p1, Personaje p2){
        if ("QEQE".equals(accion)){
            /*Para p1*/
            return true;
        }
        else if("UOUO".equals(accion)){
            /*Para p2*/
            return true;
        } else{
            /*Pierde Vida*/
            p1.setVida(p1.getVida() - 1);
            p2.setVida(p2.getVida() - 1);
            return false;
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
