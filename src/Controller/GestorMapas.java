package Controller;

import Model.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class GestorMapas {

    private Mapa[] mapas;
    static int numNiveles = 4;

    public GestorMapas() {
        mapas = new Mapa[numNiveles];
        for (int niveles = 0; niveles < numNiveles; ++niveles) {
            mapas[niveles] = new Mapa();
        }

        for (int n = 0; n < numNiveles; ++n) {
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

    private void LeerArchivoMapa(int nivel, String ruta) throws IOException, FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea;
        for (int fila = 0; (linea = br.readLine()) != null; ++fila) {
            char[] aux = linea.toCharArray();
            for (int col = 0; col < linea.length(); ++col) {
                Dibujable dib = charToDibujable(aux[col], nivel);
                if (dib instanceof Enemigo) {
                    ((Enemigo) dib).setPosX(col);
                    ((Enemigo) dib).setPosY(fila);
                }
                mapas[nivel].setMapaAt(fila, col, dib);
            }
        }
    }

    private BufferedImage crearImg(String ruta) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    private Dibujable charToDibujable(char c, int nivel) {
        String path = "", file = "";
        if (nivel == 0) {
            path = "imagenes/MapaTutorial/";
        } else if (nivel == 1) {
            path = "imagenes/MapaNivel1/";
        } else if (nivel == 2) {
            path = "imagenes/MapaNivel2/";
        }

        Dibujable dib = null;
        if (c == 'S') {//Terreno de A
            file = "piso1.gif";
            dib = new Terreno(crearImg(path + file), c, 1);
        } else if (c == 'N') { //Terreno de B
            file = "piso2.gif";
            dib = new Terreno(crearImg(path + file), c, 2);
        } else if (c == 'C') { //Accion especial
            file = "imagenes/sprite_azul.png";
            dib = new Terreno(crearImg(file), c, 3);
        } else if (c == 'D') { //Accion duo
            file = "imagenes/sprite_rojo.png";
            dib = new Terreno(crearImg(file), c, 4);
        } else if (c == 'T') {//Trigger enemigo de A
            file = "imagenes/o.png";
            dib = new Terreno(crearImg(file), 'S', 5);
        } else if (c == 'F') {//Terreno final
            file = "imagenes/F.png";
            dib = new Terreno(crearImg(file), c, 6);
        } else if (c == 'Y') {//Trigger enemigo de B
            file = "imagenes/o.png";
            dib = new Terreno(crearImg(file), 'N', 7);
        } else if (c == 'E') {
            file = "imagenes/o.png";
            Enemigo e = new Enemigo(crearImg(file), 'S');
            e.setTipo(1);
            dib = e;
        } else if (c == 'R') {
            file = "imagenes/o.png";
            Enemigo e = new Enemigo(crearImg(file), 'N');
            e.setTipo(2);
            dib = e;
        } else if (c == 'o') {
            file = "imagenes/o.png";
            dib = new Objeto(crearImg(file), c, 2);
        } else if (c == 'g' || c == 'h' || c == 't' || c == 'm'
                || c == 'L' || c == 'p' || c == 'i' || c == 'd'
                || c == 'j' || c == 'a') {
            if (c == 'g') {
                file = "g.gif";
            } else if (c == 'h') {
                file = "h.gif";
            } else if (c == 't') {
                file = "t.gif";
            } else if (c == 'm') {
                file = "m.gif";
            } else if (c == 'L') {
                file = "L.gif";
            } else if (c == 'p') {
                file = "o.gif"; // no se ke es
            } else if (c == 'i') {
                file = "i.gif";
            } else if (c == 'd') {
                file = "d.gif";
            } else if (c == 'j') {
                file = "j.gif";
            } else if (c == 'a') {
                file = "a.gif";
            }
            dib = new Objeto(crearImg(path + file), c, 1);
        } else {
            file = "imagenes/e.png";
            dib = new Objeto(crearImg(file), c, 1);
        }

        return dib;
    }

    public Mapa getMapa(int nivel) {
        if (nivel >= 0 && nivel < numNiveles) {
            return mapas[nivel];
        } else {
            return null;
        }
    }

    public int getNumNiveles() {
        return this.numNiveles;
    }

    public Enemigo getEnemigo(int nivel) {
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 16; ++j) {
                Dibujable obj = mapas[nivel].getMapaAt(i, j).getObj();
                if (obj instanceof Enemigo) {
                    return (Enemigo) obj;
                }
            }
        }
        return null;
    }
}
