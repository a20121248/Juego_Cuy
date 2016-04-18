package Controller;
import Model.*;

/**
 *
 * @author Godievski
 */
public class InterpreteComandos {
        
    public boolean ejecutarAccionEspecial(String accion,Mapa mapa, Personaje p1, Personaje p2){
        if (accion == null || accion.length() <= 0 ) return false;
        accion = accion.toUpperCase();
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
    
    public void interpretaMovimiento(String accion, Personaje p1, Personaje p2,
            GestorMapas gm,int nivel){
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
            int xFinal = p1.getPosX() + difX;
            int yFinal = p1.getPosY() + difY;
            if (xFinal >= 0 && xFinal < 16 && yFinal >= 0 && yFinal < 12){
                Celda celda = gm.getMapa(nivel).getMapaAt(yFinal, xFinal);
                if (celda != null && celda.getObj() instanceof Terreno)
                    p1.Mover(xFinal, yFinal);
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
                if (celda != null && celda.getObj() instanceof Terreno)
                    p2.Mover(xFinal, yFinal);
            }
        }
    }
}
