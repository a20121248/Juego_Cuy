package Controller;


import Model.*;
/**
 *
 * @author Godievski
 */
public class GestorMapas {
    private Mapa[] mapas;
    private char[][][] mapasGraf;
    static int numNiveles = 2;
    
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
    
    public Mapa getMapa(int nivel){
        if (nivel >= 0 && nivel < 4)
            return mapas[nivel];
        else
            return null;
    }

    public void updateGrafToNormal(int nivel){
        this.mapas[nivel].CargarMapa(mapasGraf[nivel]);
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
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'C', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
            {'F', 'N', 'N', 'L', 'N', 'N', 'N', 'N', 'N', 'i', 'i', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'L', 'C', 'N', 'N', 'N', 'N', 'i', 'N', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'L', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'g', 'g', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'g', 'g', 'N', 'N', 'N'}
        };
        
        char[][] nivel2 = {
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'o', 'o', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'F', 'S', 'S', 'S', 'D', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
            {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
            {'F', 'N', 'N', 'L', 'D', 'N', 'N', 'N', 'N', 'i', 'i', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'L', 'N', 'N', 'N', 'N', 'N', 'i', 'N', 'N', 'N', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'L', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'g', 'g', 'N', 'N', 'N'},
            {'F', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'g', 'g', 'N', 'N', 'N'}
        };
        
        mapasGraf[0] = nivel1;
        mapasGraf[1] = nivel2;
    }
}
