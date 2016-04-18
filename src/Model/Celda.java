package Model;
/**
 *
 * @author Godievski
 */
public class Celda {
    private Dibujable obj;
    
    public Celda() {
        obj = null;
    }
    public Dibujable getObj(){
        return obj;
    }
    public void setObj(Dibujable value){
        this.obj = value;
    }
}