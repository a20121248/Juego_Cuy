package View;
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
            if (fila < 3) continue;
            System.out.print("| ");
            for(int columna = 0; columna < 16; columna++){
                Celda celda = mapa.getMapaAt(fila, columna);
                Objeto obj = celda.getObj();
                int suelo = celda.getTipoSuelo();
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
                        imprimir('T');
                        continue;
                    }

                if (obj != null){
                    imprimir('o');
                } else{
                    switch (suelo) {
                        case 1:
                            /*Suelo de Personaje 1*/
                            imprimir('S');
                            break;
                        case 2:
                            /*Suelo de Personaje 2*/
                            imprimir('N');
                            break;
                        case 3:
                            /*Meta*/
                            imprimir('F');
                            break;
                        case 4:
                            /*Accion A*/
                            imprimir('a');
                            break;
                        case 5:
                            /*Accion B*/
                            imprimir('b');
                            break;
                        case 6:
                            /*Duo*/
                            imprimir('D');
                            break;
                        default:
                            break;
                    }
                }
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
