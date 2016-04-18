package View;
import Controller.GestorMapas;
import Model.*;
/**
 *
 * @author Godievski
 */
public class Renderizador {
    
    
    private void imprimir(char c){
        System.out.print(c);
        System.out.print(' ');
    }
    
    private void imprimirLinea(){
        for(int i = 0; i <= 34; i++)
            System.out.print('-');
        System.out.println();
    }
    
    public void mostrarMapa(GestorMapas gm,int nivel,
        Personaje p1, Personaje p2, Enemigo enemigo){
        Mapa mapa = gm.getMapa(nivel);
        imprimirLinea();
        for(int fila = 0; fila < 12; fila++){
            System.out.print("| ");
            for(int columna = 0; columna < 16; columna++){
                Celda celda = mapa.getMapaAt(fila, columna);
                if (celda == null){
                    imprimir(' ');
                    continue;
                }
                Dibujable obj = celda.getObj();
                if (obj == null){
                    imprimir(' ');
                    continue;
                }
                if (p1.getPosX() == columna && p1.getPosY() == fila){
                    imprimir('A');
                    continue;
                }
                if (p2.getPosX() == columna && p2.getPosY() == fila){
                    imprimir('B');
                    continue;
                }
                /*IMPRIMIR POSICION DEL ENEMIGO*/
                /*Falta desarrollar condicion de que sea mas grande*/
                if (enemigo != null)
                    if (enemigo.getPosX() == columna && enemigo.getPosY() == fila){
                        imprimir('E');
                        continue;
                    }
                
                imprimir(obj.getElementoGrafico());
            }
            System.out.println('|');
            if (fila != 11){
                System.out.print('|');
                for(int i = 0; i < 33; i++) System.out.print(" ");
                System.out.println('|');
            }
        }
        imprimirLinea();
    }
 
}
