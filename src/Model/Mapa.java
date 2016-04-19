package Model;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Godievski
 */
public class Mapa {
    private Celda[][] mapa;
    static int filas = 12;
    static int columnas = 16;
    List<Dibujable> listaDuo;
    
    public Mapa() {
        mapa = new Celda[filas][columnas];
        for (int f = 0; f < filas; f++)
            for(int c = 0; c < columnas; c++)
                mapa[f][c] = new Celda();
        listaDuo = new ArrayList();
    }
    public void setMapaAt(int fila, int columna, Dibujable obj){
        if (fila < 0 || fila >= 12) return;
        if (columna < 0 || columna >= 16) return;
        mapa[fila][columna].setObj(obj);
    }

    public Celda getMapaAt(int fila, int columna){
        if (fila < 0 || fila >= 12) return null;
        if (columna < 0 || columna >= 16) return null;
        return mapa[fila][columna];
    }
    public List<Dibujable> getListDuo(){
        return this.listaDuo;
    }
//    public void CargarMapa(char[][] mapa){
//        for (int i = 0; i < filas; i++)
//            for (int j = 0; j < columnas; j++){
//                
//                this.mapa[i][j] = charToCelda(mapa[i][j]);
//                
//                if ((this.mapa[i][j].getObj() instanceof Terreno) &&
//                     ((Terreno) this.mapa[i][j].getObj()).getTipo() == 4)
//                    listaDuo.add((Terreno) this.mapa[i][j].getObj());
//            }
//    }
//    
}
