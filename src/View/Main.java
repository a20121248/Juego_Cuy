package View;
import java.io.IOException;
/**
 *
 * @author Godievski
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Juego j = new Juego();

        j.start();
        System.out.println("Salu2");
    }
    
}
