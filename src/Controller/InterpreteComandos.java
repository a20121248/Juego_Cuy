package Controller;
import Model.*;
import java.util.List;

/**
 *
 * @author Godievski
 */
public class InterpreteComandos {
        
    public boolean interpretaAccionEspecial(String accion,Mapa mapa, Personaje p1, Personaje p2, int nivel){
        if (accion == null || accion.length() <= 0 ) return false;
        accion = accion.toUpperCase();
        
        if (p1.getAccionEspecial(nivel).equals(accion)){
            /*PARA P1*/
            Celda celda = mapa.getMapaAt( p1.getPosY(),p1.getPosX());
            ((Terreno) celda.getObj()).setActivo(false);
            return true;
        } 
        else if (p2.getAccionEspecial(nivel).equals(accion)){
            /*PARA P2*/
            Celda celda = mapa.getMapaAt( p2.getPosY(),p2.getPosX());
            ((Terreno) celda.getObj()).setActivo(false);
            return true;
        }
        else if (p2.getAccionDuo(nivel).equals(accion)) {
            /*Para p1 y  p2*/
            //DESACTIVA TERRENO DUO
            Celda celda1 = mapa.getMapaAt( p1.getPosY(),p1.getPosX());
            Celda celda2 = mapa.getMapaAt( p2.getPosY(),p2.getPosX());
            ((Terreno) celda1.getObj()).setActivo(false);
            ((Terreno) celda2.getObj()).setActivo(false);
            return true;
        }else{
            /*Pierde Vida*/
            p1.setVida(p1.getVida() - 2);
            return false;
        }
    }
    
    public void interpretaMovimiento(String accion, Personaje p1, Personaje p2,
            GestorMapas gm, int nivel){
        int difX = 0;
        int difY = 0;
        int personaje = 0;
        if (accion == null || accion.length() <= 0 ) return;
        accion = accion.toUpperCase();
        char c = accion.charAt(0);
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
            Celda celda1 = gm.getMapa(nivel).getMapaAt(p1.getPosY(), p1.getPosX());
            //PARA QUE NO SE MUEVA CUANDO ESTA EN UN TRIGGER ACTIVO
            if (celda1.getObj() instanceof Terreno){
                Terreno ter = (Terreno) celda1.getObj();
                if (ter.getActivo() && ter.getTipo()==5){
                return;
                }
            }
            int xFinal = p1.getPosX() + difX;
            int yFinal = p1.getPosY() + difY;
            if (xFinal >= 0 && xFinal < 16 && yFinal >= 0 && yFinal < 12){
                Celda celda = gm.getMapa(nivel).getMapaAt(yFinal, xFinal);
                if (celda != null && celda.getObj() instanceof Terreno){
                    Terreno terreno = (Terreno)celda.getObj();
                    int t = terreno.getTipo();
                    if (t != 2 && t > 0 && t < 8){
                        p1.Mover(xFinal, yFinal);
                    }
                    return;
                }
                //Cuando se encuntra sobre objeto ayuda
                if (celda != null && celda1 != null && 
                        celda.getObj() instanceof Objeto &&
                        celda1.getObj() instanceof Objeto){
                    Objeto obj = (Objeto) celda.getObj();
                    Objeto obj1 = (Objeto) celda1.getObj();
                    if(obj.getTipo() == 2 && obj1.getTipo()== 2){
                        p1.Mover(xFinal, yFinal);
                    }
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
            Celda celda2 = gm.getMapa(nivel).getMapaAt(p2.getPosY(), p2.getPosX());
            //PARA QUE NO SE MUEVA CUANDO ESTA EN UN TRIGGER ACTIVO
            if (celda2.getObj() instanceof Terreno){
                Terreno ter = (Terreno) celda2.getObj();
                if (ter.getActivo() && ter.getTipo()==5){
                    return;
                }
            }
            int xFinal = p2.getPosX() + difX;
            int yFinal = p2.getPosY() + difY;
            if (xFinal >= 0 && xFinal < 16 && yFinal >= 0 && yFinal < 12){
                Celda celda = gm.getMapa(nivel).getMapaAt(yFinal, xFinal);
                if (celda != null && celda.getObj() instanceof Terreno){
                    Terreno terreno = (Terreno)celda.getObj();
                    int t = terreno.getTipo();
                    if (t != 1 && t > 0 && t < 8){
                        p2.Mover(xFinal, yFinal);
                    }
                    return;
                }
                //Cuando se encuntra sobre objeto ayuda
                if (celda != null && celda2 != null && 
                        celda.getObj() instanceof Objeto &&
                        celda2.getObj() instanceof Objeto){
                    Objeto obj = (Objeto) celda.getObj();
                    Objeto obj2 = (Objeto) celda2.getObj();
                    if(obj.getTipo() == 2 && obj2.getTipo()== 2){
                        p2.Mover(xFinal, yFinal);
                    }
                }
            }
        }
    }
}
