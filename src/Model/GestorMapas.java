package Model;

/**
 *
 * @author Godievski
 */
public class GestorMapas {
    private Mapa[] mapas;
    private char[][][] mapasGraf;
    static int numNiveles = 4;
    
    public GestorMapas() {
        mapas = new Mapa[numNiveles];
        for (int niveles = 0; niveles < numNiveles; niveles++)
            mapas[niveles] = new Mapa();
        mapasGraf = new char[numNiveles][12][16];
        /*NIVEL 1*/
        setMapaGrafico();
        for (int n = 0; n < numNiveles; n++)
            updateGrafToNormal(n);
    }
 
    public boolean validarPosicion(int posX, int posY){
        return !(posX < 0 || posY < 3 || posX >= 16 || posY >= 12);
    }
    
    public Mapa getMapa(int nivel){
        if (nivel >= 0 && nivel < 4)
            return mapas[nivel];
        else
            return null;
    }
    public char getMapaGraf(int nivel,int fila, int col){
        if (nivel >= 0 && nivel < numNiveles){
            return mapasGraf[nivel][fila][col];
        }
        return ' ';
    }
    public void updateGrafToNormal(int nivel){
        for(int f = 0; f < 12; f++)
            for(int c = 0; c < 16; c++){
            switch (mapasGraf[nivel][f][c]) {
                case 'F':
                    mapas[nivel].setMapaAt(f, c, 3);
                    break;
                case 'S':
                    mapas[nivel].setMapaAt(f, c, 1);
                    break;
                case 'o':
                    Objeto obj = new Objeto(1);
                    mapas[nivel].setMapaAt(f, c, obj);
                    break;
                case 'N':
                    mapas[nivel].setMapaAt(f, c, 2);
                    break;
                case 'b':
                    //casilla de accion de B
                    mapas[nivel].setMapaAt(f, c, 5);
                    break;
                case 'a':
                    //casilla de accion de A
                    mapas[nivel].setMapaAt(f, c, 4);
                    break;
                default:
                    break;
            }
            }
    }
    public boolean personajeEnAccion(Personaje p,int nivel){
        for (int f = 0; f < 12; f++)
            for (int c = 0; c < 16; c++){
                if (mapasGraf[nivel][f][c] == 'a'){
                    //Personaje 1
                    return (p.getPosX() == c && p.getPosY() == f);
                }
                if (mapasGraf[nivel][f][c] == 'b'){
                    //Personaje 2
                    return (p.getPosX() == c && p.getPosY() == f);
                }  
            }
        return false;
    }
    
    private void setMapaGrafico(){
        char[][] nivel1 = {
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'N', 'N', 'b', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N'}
        };
        
        char[][] nivel2 = {
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'N', 'N', 'b', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N'}
        };
        
        char[][] nivel3 = {
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'N', 'N', 'b', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N'}
        };
        
        char[][] nivel4 = {
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'o', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'N', 'N', 'b', 'N', 'N', 'N', 'N', 'N', 'o', 'o', 'N', 'N', 'N'}
        };
        
        mapasGraf[0] = nivel1;
        mapasGraf[1] = nivel2;
        mapasGraf[2] = nivel3;
        mapasGraf[3] = nivel4;
    }
}
