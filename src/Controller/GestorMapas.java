package Controller;


import Model.*;
import java.io.*;
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
        
        setMapaGrafico();
        for (int n = 0; n < numNiveles; n++)
            this.mapas[n].CargarMapa(mapasGraf[n]);
    }
    
    public Mapa getMapa(int nivel){
        if (nivel >= 0 && nivel < 4)
            return mapas[nivel];
        else
            return null;
    }
    
    private void setMapaGrafico(){
//        FileReader arch;
//        for (int i = 0; i <= this.numNiveles; i++) {
//            arch = new FileReader("../Mapa0.txt");
//        }
        
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
            {'S', 'S', 'S', 'S', 't', 'S', 'S', 'S', 'S', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
            {'S', 'S', 'S', 'S', 't', 'S', 'S', 'S', 'S', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
            {'S', 'S', 'C', 'S', 't', 'S', 'S', 'S', 'S', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
            {'S', 'S', 'S', 'S', 't', 'S', 'S', 'S', 'D', 'L', 'L', 'L', 'o', 'o', 'o', 'F'},
            {'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'L', 'L', 'L', 'o', 'o', 'o', 'F'},
            {'S', 'N', 'N', 'm', 'N', 'N', 'N', 'N', 'D', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
            {'S', 'N', 'N', 'm', 'N', 'N', 'N', 'N', 'N', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
            {'S', 'N', 'N', 'm', 'N', 'h', 'h', 'N', 'N', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
            {'S', 'N', 'N', 'N', 'N', 'h', 'h', 'N', 'N', 'L', 'L', 'L', 'L', 'L', 'L', 'L'}
        };
        
        mapasGraf[0] = nivel1;
        mapasGraf[1] = nivel2;
    }
}
