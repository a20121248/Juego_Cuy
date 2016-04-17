package Model;
/**
 *
 * @author Godievski
 */
public class Mapa {
    private Celda[][] mapa;
    static int filas = 12;
    static int columnas = 16;
    
    public Mapa() {
        mapa = new Celda[filas][columnas];
        for (int f = 0; f < filas; f++)
            for(int c = 0; c < columnas; c++)
                mapa[f][c] = new Celda();
    }
    public void setMapaAt(int fila, int columna, Objeto obj){
        if (fila < 0 || fila >= 12) return;
        if (columna < 0 || columna >= 16) return;
        mapa[fila][columna].setObj(obj);
    }
    public void setMapaAt(int fila, int columna, int tipo){
        if (fila < 0 || fila >= 12) return;
        if (columna < 0 || columna >= 16) return;
        mapa[fila][columna].setTipoSuelo(tipo);
    }
    public Celda getMapaAt(int fila, int columna){
        if (fila < 0 || fila >= 12) return null;
        if (columna < 0 || columna >= 16) return null;
        return mapa[fila][columna];
    }
}
