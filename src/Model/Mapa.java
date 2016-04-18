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
    public void CargarMapa(char[][] mapa){
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                this.mapa[i][j] = charToCelda(mapa[i][j]);
    }
    private Celda charToCelda(char c){
        Celda celda = new Celda();
        if (c == 'S')
            celda.setObj(new Terreno(c,1));
        else if (c == 'N')
            celda.setObj(new Terreno(c,2));
        else if (c == 'C')
            celda.setObj(new Terreno(c,3));
        else if (c == 'D')
            celda.setObj(new Terreno(c,4));
        else if (c == 'F')
            celda.setObj(new Terreno(c,6));
        else if (c == 'g' || c == 'h' || c == 't' || c == 'm' 
                || c == 'L' || c == 'p' || c == 'i' || c == 'd'
                || c == 'j' || c == 'a')
            celda.setObj(new Objeto(c,1));
        else if (c == 'o')
            celda.setObj(new Objeto(c,2));
        return celda;
    }
}
