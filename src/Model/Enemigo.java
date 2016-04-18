package Model;
/**
 *
 * @author Godievski
 */
public class Enemigo extends Dibujable {
    /*PosX & PosY indica la esquina izquierda superior*/
    private int posX; 
    private int posY;
    
    public Enemigo(char elementoGrafico){
        super(elementoGrafico);
    }
    public Enemigo (int posX, int posY, char elementoGrafico){
        super(elementoGrafico);
        this.posX = posX;
        this.posY = posY;
    }
    public Enemigo (int posX, int posY, char elementoGrafico, int alto, int ancho){
        super(elementoGrafico, alto, ancho);
        this.posX = posX;
        this.posY = posY;
    }
    public int getPosX(){
        return posX;
    }
    public void setPosX(int value){
        posX = value;
    }
    public int getPosY(){
        return posY;
    }
    public void setPosY(int value){
        posY = value;
    }
}
