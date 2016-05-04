package View;
import Controller.GestorMapas;
import Model.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 *
 * @author Godievski
 */
public class Renderizador {
    public static final int MAX_SIZE = 64;
    
    public void dibujarMapa(Graphics graphics, Mapa mapa) throws IOException{
        /*ELIMINAR LA LINEA DE ABAGO Y DESCOMENTAR LA OTRA*/
        for (int f = 0; f < Mapa.NUM_FILAS; f++){
            for (int c = 0; c < Mapa.NUM_COLUMNAS; c++){
                Celda celda = mapa.getMapaAt(f, c);
                if (celda == null) continue;
                Dibujable dib = celda.getObj();
                if (dib == null) continue;
                //Obtenemos la imagen y la dibujamos
                BufferedImage img = dib.getImagen();
                graphics.drawImage(img, c*MAX_SIZE, f*MAX_SIZE, MAX_SIZE, MAX_SIZE, null);
            }
        }
    }
    
    public void dibujarJugadores(Graphics graphics,Personaje p1, Personaje p2) throws IOException{
        //BufferedImage imgP1 = p1.getImg();
        //BufferedImage imgP2 = p2.getImg();
        BufferedImage imgP1 = ImageIO.read(new File("sprite_cuy.gif"));
        BufferedImage imgP2 = ImageIO.read(new File("sprite_cuy.gif"));
        graphics.drawImage(imgP1, p1.getPosX()*MAX_SIZE, p1.getPosY()*MAX_SIZE,MAX_SIZE,MAX_SIZE,null);
        graphics.drawImage(imgP2, p2.getPosX()*MAX_SIZE, p2.getPosY()*MAX_SIZE,MAX_SIZE,MAX_SIZE,null);
    }
    
    /* Muestra los datos (nombres, vida, nivel) */
    public void pnlTexto_mostrarDatos(Graphics graphics, Personaje p1, Personaje p2) throws IOException {
        String vida = "Vida : " + p1.getVida();
        String nombre1  = "Nombre Jugador 1: " + p1.getNombre();
        String nombre2  = "Nombre Jugador 2: " + p2.getNombre();
        
        String accion = p1.getAccionEspecial(0);
        
        graphics.drawString(vida, 15, 50);
        
//        graphics.drawString(accion, 15, 60);
        

    }
    
    public void pnlTexto_mostrarLista(Graphics graphics, List<String> lstString, int inicial, int incremento) {

        int x = 15, y = inicial;
        int linea_length = 0;
        String linea_acumulador = "";
        for (int i = 0; i < lstString.size(); ++i) {
            String[] lstPalabras = lstString.get(i).split(" ");
            linea_length = 0;
            linea_acumulador = "";
            for (int j = 0; j < lstPalabras.length; ++j) {
                linea_acumulador += (" " + lstPalabras[j]);
                linea_length += (1 + lstPalabras[j].length());
                
                boolean cond1 = linea_length >= 25;
                boolean cond2 = (j == lstPalabras.length-1);
                if (cond1 || cond2) {
                    graphics.drawString(linea_acumulador, x, y);
                    linea_length = 0;
                    linea_acumulador = "";
                    y += incremento/2;
                }
            }
            y += incremento;
        }
    }      
    
}
