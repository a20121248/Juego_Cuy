package Model;
/**
 *
 * @author Godievski
 */
public class Enemigo {
    /*PosX & PosY indica la esquina izquierda superior*/
    private int posX; 
    private int posY;
    private int anchura;
    private int altura;
    
    public Enemigo(){
        this.anchura = this.altura = 1;
    }
    
    public Enemigo (int posX, int posY, int anchura, int altura){
        this.posX = posX;
        this.posY = posY;
        this.anchura = anchura;
        this.altura = altura;
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
    public int getAnchura()
    {
        return this.anchura;
    }
    public void setAnchura(int value){
        this.anchura = value;
    }
    public int getAltura()
    {
        return this.altura;
    }
    public void setAltura(int value)
    {
        this.altura = value;
    }
}
