package View;

import java.io.IOException;
import Controller.*;
import Model.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Juego {
    public static String nombre = "";

    private Renderizador rend;
    private InterpreteComandos lector;
    private GestorMapas gestorMapa;
    private Personaje p1, p2;
    private int nivel;
    final String[] txt_Historia, txt_Dialogo;
    Ventana ventana;
    PanelGrafico pnlGrafico;
    PanelTexto pnlTexto;

    public static final int MENU_JUEGO = 0;
    public static final int NOMBRE_PLAYER1 = 1;
    public static final int NOMBRE_PLAYER2 = 2;
    public static final int CAPTURAR_MOVIMIENTO = 3;

    public static int eventFlag = MENU_JUEGO; // INDICA EN QUE SECCION ESTOY

    public Juego(Ventana ventana, PanelGrafico pnlGrafico, PanelTexto pnlTexto) {

        p1 = p2 = null;
        nivel = 0;
        rend = new Renderizador(pnlGrafico, pnlTexto);
        lector = new InterpreteComandos();
        gestorMapa = new GestorMapas();

        this.inicializarPersonajes(nivel);
        this.inicializarActividad(nivel);
        this.pnlGrafico = pnlGrafico;
        this.pnlTexto = pnlTexto;
        this.ventana = ventana;

        txt_Historia = new String[4];
        txt_Historia[0] = "- Kiru y Milo conversan.\nLe nace la pregunta a Kiru y deciden viajar.";
        txt_Historia[1] = "- Kiru y Milo viajan a Paracas en un auto.\nLlegan a la playa y empiezan a jugar.";
        txt_Historia[2] = "- Kiru y Milo se encuentran con Peli el Pelicano.\nPeli el pelicano no sabe de donde viene Kiru. Kiru y Milo deciden viajar a la sierra.";
        txt_Historia[3] = "- Kiru y Milo conversan con Dana la Llama.\nDana responde la pregunta de Kiru. Kiru se contenta y decide, con Milo, viajar por todos los Andes.";

        txt_Dialogo = new String[2];
        txt_Dialogo[0] = "- Usa WASD para mover a Kiru y JKLI para mover a Milo.\n"
                + "- Si ves un lugar para la accion o el duo... Parate sobre el!! Podras realizar acciones especiales.\n"
                + "- Solo podras pasar los niveles con la ayuda de las acciones especiales. Para esto, tendras que presionar comandos que se mostraran en un cuadro de dialogo como este.\n"
                + "- Los comandos deben ser ejecutados en la secuencia correcta, sino perderas puntos de vida.\n"
                + "- Puedes ver los puntos de vida en la parte superior de la pantalla.\n"
                + "- Para activar los terrenos con acciones especiales duo, tienen que estar sobre ellos Kiru y Milo al mismo tiempo, en los de acciones especiales solo con uno basta.\n";
        txt_Dialogo[1] = "- En tu aventura, a veces te toparas con animales malos.\n"
                + "- Estos enemigos te bajaran puntos de vida. Si tus puntos de vida llegan a 0, se acabara el juego.\n"
                + "- Si un enemigo afecta a un personaje, este no se podra mover. Tendras que usar a su amigo para ayudarlo.\n";
    }

    public void start() {
        pnlTexto.textos.clear();
        pnlTexto.addTexto("\n");
        pnlTexto.addTexto("1.- Iniciar juego");
        pnlTexto.addTexto("2.- Cargar partida");
        pnlTexto.addTexto("3.- Salir");
        pnlTexto.repaint();
//        rend.mostrarMapa_Consola(gestorMapa, nivel, p1, p2);                
    }

    private void evt_MenuJuego(KeyEvent evt) {
        char opcion = evt.getKeyChar();
        if (opcion == '1') {
//            rend.mostrarMapa(gestorMapa, nivel, p1, p2);
            pnlTexto.textos.clear();
            pnlTexto.addTexto("\n");
            pnlTexto.addTexto("Ingrese el nombre del jugador 1.");
            pnlTexto.addTexto("\n");
            pnlTexto.repaint();
            eventFlag = NOMBRE_PLAYER1;
        } else if (opcion == '2') {
//            opcionCargarJuego();
        } else if (opcion == '3') {
            ventanaConfirmacionSalir();
        }
    }

    private void evt_IngresarNombre(KeyEvent evt, int player) {
        char c = evt.getKeyChar();
        if (Character.isAlphabetic(c)) {
            pnlTexto.anadirUltimo(c);
            nombre += c;
            pnlTexto.repaint();
        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (eventFlag == NOMBRE_PLAYER1) {
                p1.setNombre(nombre);
                nombre = "";

                pnlTexto.addTexto("\n");
                pnlTexto.addTexto("Ingrese el nombre del jugador 2.");
                pnlTexto.addTexto("\n");
                pnlTexto.repaint();

                eventFlag = NOMBRE_PLAYER2;
            } else if (eventFlag == NOMBRE_PLAYER2) {
                p2.setNombre(nombre);
                
                pnlTexto.inicializarTexto(p1.getNombre());
                pnlTexto.repaint();
                rend.mostrarMapa(gestorMapa, nivel, p1, p2);
                eventFlag = CAPTURAR_MOVIMIENTO;
            }
        }
    }

    public void procesarEvento(KeyEvent evt) {
        // Permite salir en Todo Momento
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            ventanaConfirmacionSalir();
            return;
        }

        if (eventFlag == MENU_JUEGO) {
            evt_MenuJuego(evt);
        } else if (eventFlag == NOMBRE_PLAYER1) {
            evt_IngresarNombre(evt, 1);
        } else if (eventFlag == NOMBRE_PLAYER2) {
            evt_IngresarNombre(evt, 2);
        }
    }

    private void ventanaConfirmacionSalir() {
        Object[] options = {"Sí", "No"};

        int n = JOptionPane.showOptionDialog(ventana, "¿Seguro que desea salir?", "Mensaje de confirmacion", JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, null, options, options[0]);
        if (n == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private BufferedImage crear_Img(String ruta) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    private void inicializarPersonajes(int nivel) {
        if (nivel < 0 || nivel >= gestorMapa.getNumNiveles()) {
            return;
        }
        String ruta = "";
        if (p1 == null) {
            ruta = "imagenes/sprite_cuy.gif";
            p1 = new Personaje(crear_Img(ruta), 'A');
        }
        if (p2 == null) {
            ruta = "imagenes/sprite_perro.gif";
            p2 = new Personaje(crear_Img(ruta), 'B');
        }

        if (p1.getVida() <= 0) {
            p1.setVida(10);
        }
        cargar_Niveles_XML(nivel);
    }

    private void cargar_Niveles_XML(int nivel) {
        try {
            File inputFile = new File("niveles.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Nivel");
            Node nNode = nList.item(nivel);
            Element eElement = (Element) nNode;

            p1.setPosY(Integer.parseInt(eElement.getElementsByTagName("p1PosY").item(0).getTextContent()));
            p1.setPosX(Integer.parseInt(eElement.getElementsByTagName("p1PosX").item(0).getTextContent()));
            p1.setAccionEspecial(eElement.getElementsByTagName("p1AccEsp").item(0).getTextContent(), nivel);

            p2.setPosY(Integer.parseInt(eElement.getElementsByTagName("p2PosY").item(0).getTextContent()));
            p2.setPosX(Integer.parseInt(eElement.getElementsByTagName("p2PosX").item(0).getTextContent()));
            p2.setAccionEspecial(eElement.getElementsByTagName("p2AccEsp").item(0).getTextContent(), nivel);

            p1.setAccionDuo(eElement.getElementsByTagName("AccDuo").item(0).getTextContent(), nivel);
            p2.setAccionDuo(eElement.getElementsByTagName("AccDuo").item(0).getTextContent(), nivel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inicializarActividad(int nivel) {
        if (nivel < 0 || nivel >= gestorMapa.getNumNiveles());
        Mapa mapa = gestorMapa.getMapa(nivel);
//        /*PARCHE 1*/
        this.parcheActividadInicial(nivel);
        this.cargar_Actividad_XML(nivel);
    }
//

    private void parcheActividadInicial(int nivel) {
        Mapa mapa = gestorMapa.getMapa(nivel);
        if (mapa == null) {
            return;
        }
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 16; ++j) {
                Celda celda = mapa.getMapaAt(i, j);
                Dibujable dib = celda.getObj();
                if (dib instanceof Terreno) {
                    ((Terreno) dib).setActivo(true);
                }
            }
        }
    }

    private void cargar_Actividad_XML(int nivel) {
        Mapa mapa = gestorMapa.getMapa(nivel);
        try {
            File inputFile = new File("terreno.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Nivel");
            Node nNode = nList.item(nivel);
            Element eElement = (Element) nNode;

            NodeList filas = eElement.getElementsByTagName("fila");
            NodeList columnas = eElement.getElementsByTagName("columna");

            for (int i = 0; i < filas.getLength(); ++i) {
                int fila = Integer.parseInt(filas.item(i).getTextContent());
                int col = Integer.parseInt(columnas.item(i).getTextContent());
                Terreno terreno = ((Terreno) mapa.getMapaAt(fila, col).getObj());
                terreno.setActivo(false);
                mapa.addListaTerrenoInactivo(terreno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
