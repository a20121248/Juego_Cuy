package Controller;


import Model.*;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Godievski
 */
public class GestorMapas {
    private Mapa[] mapas;
    static int numNiveles = 4;
    
    public GestorMapas() {
        mapas = new Mapa[numNiveles];
        for (int niveles = 0; niveles < numNiveles; niveles++)
            mapas[niveles] = new Mapa();
        
        for (int n = 0; n < numNiveles; n++) {
            try {
                LeerArchivoMapa(n, "Mapa" + n + ".txt");                
            } catch (FileNotFoundException ex) {
                System.out.println("No se encontraron los mapas del juego.");
                Scanner sc = new Scanner(System.in);
                sc.nextLine();
                System.exit(1);
            } catch (IOException ex) {
                //Se terminó de leer el mapa
            }
        }       
        
    }
    
    private void LeerArchivoMapa(int nivel, String ruta) throws IOException,
            FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea; int fila = 0;
        while ((linea = br.readLine()) != null) {
            char[] aux = linea.toCharArray();
            for (int col = 0; col < linea.length(); col++){
                Dibujable dib = charToDibujable(aux[col]);
                mapas[nivel].setMapaAt(fila, col, dib);
            }
            fila++;
        }
    }
    
    private Dibujable charToDibujable(char c){
        Dibujable dib = null;
        if (c == 'S') //Terreno de A
            dib = new Terreno(c,1);
        else if (c == 'N') //Terreno de B
            dib = new Terreno(c,2);
        else if (c == 'C') //Accion especial
            dib = new Terreno(c,3);
        else if (c == 'D') //Accion duo
            dib = new Terreno(c,4);
        else if (c == 'T') //Trigger enemigo
            dib = new Terreno(c,5);
        else if (c == 'F') //Terreno final
            dib = new Terreno(c,6);
        else if (c == 'g' || c == 'h' || c == 't' || c == 'm' 
                || c == 'L' || c == 'p' || c == 'i' || c == 'd'
                || c == 'j' || c == 'a')
            dib = new Objeto(c,1);
        else if (c == 'o')
            dib = new Objeto(c,2);
        return dib;
    }
    
    public Mapa getMapa(int nivel){
        if (nivel >= 0 && nivel < numNiveles)
            return mapas[nivel];
        else
            return null;
    }
}
