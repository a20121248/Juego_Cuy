package Model;

import java.awt.image.BufferedImage;

public abstract class Dibujable {
    private BufferedImage img;
    protected char elementoGrafico;
    protected int alto;
    protected int ancho;

    public Dibujable(BufferedImage img, char elementoGrafico) {
        this.img = img;
        this.elementoGrafico = elementoGrafico;
        alto = ancho = 1;
    }

    public BufferedImage getImg() {
        return img;
    }
    
    public void setImg(BufferedImage img) {
        this.img = img;
    }
    
    public Dibujable(BufferedImage img, char elementoGrafico, int alto, int ancho) {
        this.img = img;
        this.elementoGrafico = elementoGrafico;
        this.alto = alto;
        this.ancho = ancho;
    }

    public void setElementoGrafico(char value) {
        this.elementoGrafico = value;
    }

    public char getElementoGrafico() {
        return this.elementoGrafico;
    }

    public void setAlto(int value) {
        this.alto = value;
    }

    public int getAlto() {
        return this.alto;
    }

    public void setAncho(int value) {
        this.ancho = value;
    }

    public int getAncho() {
        return this.ancho;
    }
}
