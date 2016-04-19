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
    public Celda(Dibujable dib) {
        obj = dib;
    }
    public Dibujable getObj(){
        return obj;
    }
    public void setObj(Dibujable value){
        this.obj = value;
    }
}