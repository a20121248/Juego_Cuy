package View;

import java.util.Scanner;
import java.io.IOException;
import Controller.*;
import Model.*;
import java.util.List;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Juego {

    private Renderizador rend;
    private InterpreteComandos lector;
    private GestorMapas gestorMapa;
    private Personaje p1;
    private Personaje p2;
    private final Scanner scan;
    private int nivel;
    private boolean inicio_Nivel;
    final String[] txt_Historia;
    final String[] txt_Dialogo;

    public Juego() {
        rend = new Renderizador();
        lector = new InterpreteComandos();
        gestorMapa = new GestorMapas();
        scan = new Scanner(System.in);
        p1 = p2 = null;
        nivel = 3;
        inicio_Nivel = true;
        this.inicializarPersonajes(nivel);
        this.inicializarActividad(nivel);
        
        txt_Historia = new String[4];
        txt_Historia[0] = "- Kiru y Milo conversan.\nLe nace la pregunta a Kiru y deciden viajar.";
        txt_Historia[1] = "- Kiru y Milo viajan a Paracas en un auto.\nLlegan a la playa y empiezan a jugar.";
        txt_Historia[2] = "- Kiru y Milo se encuentran con Peli el Pelicano.\nPeli el pelicano no sabe de donde viene Kiru. Kiru y Milo deciden viajar a la sierra.";
        txt_Historia[3] = "- Kiru y Milo conversan con Dana la Llama.\nDana responde la pregunta de Kiru. Kiru se contenta y decide, con Milo, viajar por todos los Andes.";
        
        txt_Dialogo = new String [2];      
        txt_Dialogo[0] = "------------------------------------------------------------------------------\n"
                       + "                                INSTRUCCIONES\n"
                       + "------------------------------------------------------------------------------\n"
                       + "- Usa WASD para mover a Kiru y JKLI para mover a Milo.\n"
                       + "- Si ves un lugar para la accion o el duo... Parate sobre el!!\n"
                       + "  Podras realizar acciones especiales.\n"
                       + "- Solo podras pasar los niveles con la ayuda de las acciones especiales. Para\n"
                       + "  esto, tendras que presionar comandos que se mostraran en un cuadro de\n"
                       + "  dialogo como este.\n"
                       + "- Los comandos deben ser ejecutados en la secuencia correcta, sino\n"
                       + "  perderas puntos de vida.\n"
                       + "- Puedes ver los puntos de vida en la parte superior de la pantalla.\n"
                       + "- Para activar los terrenos con acciones especiales duo, tienen que estar sobre\n"
                       + "  ellos Kiru y Milo al mismo tiempo, en los de acciones especiales solo con uno\n"
                       + "  basta.\n";
        txt_Dialogo[1] = "- En tu aventura, a veces te toparas con animales malos.\n"
                       + "- Estos enemigos te bajaran puntos de vida. Si tus puntos de vida llegan a 0, se acabara el juego.\n"
                       + "- Si un enemigo afecta a un personaje, este no se podra mover. Tendras que usar a su amigo para ayudarlo.\n";        
    }

    public void start() throws IOException, InterruptedException {
        bienvenida();
        while (true) {
            if (menu())
                break;
            cleanWindow();
            System.out.println(txt_Dialogo[0]);
            System.out.print("\nPresiona ENTER para continuar...");
            scan.nextLine();
            do {
                if (inicio_Nivel) {
                    inicio_Nivel = false;
                    cleanWindow();
                    System.out.println(txt_Historia[nivel]);
                    System.out.print("\nPresiona ENTER para continuar...");
                    scan.nextLine();                    
                }
                renderizar();
                capturarAccion();
                actualizarInfo();
            } while (!finJuego());
            System.out.println("\n G A M E   O V E R !");
            System.out.print("Presione ENTER para volver al menu de bienvenida.");
            scan.nextLine();
        }
    }    
    
    private void confirmarFinJuego() {
        System.out.println("Desea salir? (1. Si, 2. No)");
        int salir = 0;
        String aux = scan.nextLine();
        try {
            salir = Integer.parseInt(aux);
        } catch (NumberFormatException ex) {
            //Regresará al menú principal
        }
        if (salir == 1) {
            System.exit(1);
        }
    }

    private void capturarAccion() throws IOException, InterruptedException {
        String accion;
        int posX1 = p1.getPosX();
        int posY1 = p1.getPosY();
        int posX2 = p2.getPosX();
        int posY2 = p2.getPosY();
        Mapa m = this.gestorMapa.getMapa(nivel);
        Dibujable obj1 = m.getMapaAt(posY1, posX1).getObj();
        Dibujable obj2 = m.getMapaAt(posY2, posX2).getObj();

        /**
         * ************ PRIMER CASO: ACCIÓN ESPECIAL *************
         */
        if (obj1 instanceof Terreno) {
            if (((Terreno) obj1).getTipo() == 3 && ((Terreno) obj1).getActivo()) {
                System.out.print("Escriba la accion (" + p1.getAccionEspecial(nivel) + "): ");
                accion = scan.nextLine();
                /*EJECUTA ACCION ESPECIAL SEGUN NIVEL*/
                boolean exito = lector.interpretaAccionEspecial(accion, gestorMapa.getMapa(nivel), p1, p2, nivel);
                if (exito) {
                    ejecutarAccionEspecial(1);
                }
                return;
            }
        }

        if (obj2 instanceof Terreno) {
            if (((Terreno) obj2).getTipo() == 3 && ((Terreno) obj2).getActivo()) {
                System.out.print("Escriba la accion (" + p2.getAccionEspecial(nivel) + "): ");
                accion = scan.nextLine();
                /*EJECUTA ACCION ESPECIAL SEGUN NIVEL*/
                boolean exito = lector.interpretaAccionEspecial(accion, gestorMapa.getMapa(nivel), p1, p2, nivel);
                if (exito) {
                    ejecutarAccionEspecial(2);
                }
                return;
            } else if (obj1 instanceof Terreno) {
                if (((Terreno) obj1).getTipo() == 4
                        && ((Terreno) obj2).getTipo() == 4
                        && ((Terreno) obj1).getActivo()
                        && ((Terreno) obj2).getActivo()) {
                    System.out.print("Escriba la accion duo (" + p2.getAccionDuo(nivel) + "): ");
                    accion = scan.nextLine();
                    /*EJECUTA ACCION DUO SEGUN NIVEL*/
                    boolean exito = lector.interpretaAccionEspecial(accion, gestorMapa.getMapa(nivel), p1, p2, nivel);
                    if (exito) {
                        ejecutarAccionEspecial(3);
                    }
                    return;
                }
            }
        }

        /**
         * ************ SEGUNDO CASO: MOVIMIENTO COMÚN *************
         */
        System.out.print("Escriba la accion mover (W-A-S-D / I-J-K-L): ");
        accion = scan.nextLine();

        //Verificar el tipo de comando
        if (accion.equalsIgnoreCase("SAVE")) { //GUARDAR PARTIDA
            //guardarPartida();
            System.out.println("Partida guardada correctamente!");
            System.out.println("(presione ENTER para continuar)");
            scan.nextLine();
        } else if (accion.equalsIgnoreCase("EXIT")) { //SALIR DEL JUEGO
            System.out.println();
            System.out.println("Realmente desea salir del juego? (1. Si, 2. No)");
            try {
                int opcion = Integer.parseInt(scan.nextLine());
                if (opcion == 1) {
                    this.p1.setVida(0);
                }
            } catch (NumberFormatException ex) {
                //No hace nada, regresa a seguir jugando
            }
        } else {
            lector.interpretaMovimiento(accion, p1, p2, gestorMapa, nivel);
        }
    }

    private void ejecutarAccionEspecial(int player) throws IOException, InterruptedException {
        /*PLAYER INDICA QUE JUGADOR MANDÓ LA ACCION*/
        if (nivel == 0) {
            if (player == 1) {
                for (int i = 0; i < 3; i++) {
                    p1.setPosX(p1.getPosX() + 1);
                    this.renderizar();
                    System.out.println("Presiona ENTER para continuar...");
                    scan.nextLine();
                }
                //ACTIVAR TERRENOS
                List listaTerrenoInactivos = gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < listaTerrenoInactivos.size(); i++) {
                    Terreno terreno = (Terreno) listaTerrenoInactivos.get(i);
                    terreno.setActivo(true);
                }
            } else if (player == 2) {
                //NOTHING
            } else if (player == 3) {
                p1.setPosY(p1.getPosY() + 1);
                p2.setPosY(p2.getPosY() - 1);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                for (int i = 0; i < 2; i++) {
                    p1.setPosX(p1.getPosX() + 1);
                    p2.setPosX(p2.getPosX() + 1);
                    this.renderizar();
                    System.out.println("Presiona ENTER para continuar...");
                    scan.nextLine();
                }
            }
        } else if (nivel == 1) {
            if (player == 1) {
                int xOrig = p1.getPosX();
                int yOrig = p1.getPosY();
                for (int i = 0; i < 2; i++) {
                    p1.setPosY(p1.getPosY() + 1);
                    this.renderizar();
                    System.out.println("Presiona ENTER para continuar...");
                    scan.nextLine();
                }
                p1.setPosY(yOrig + 4);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                //AQUI DESTRUYE ESAS COSAS
                Celda celda1 = gestorMapa.getMapa(nivel).getMapaAt(yOrig + 4, xOrig);
                Celda celda2 = gestorMapa.getMapa(nivel).getMapaAt(yOrig + 5, xOrig);
                celda1.setObj(new Terreno('N', 2));
                celda2.setObj(new Terreno('N', 2));
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                //VUELVE AL ORIGINAL
                p1.setPosX(xOrig);
                p1.setPosY(yOrig);
                //ACTIVAR TERRENOS
                List listaDuo = gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < listaDuo.size(); i++) {
                    Terreno terreno = (Terreno) listaDuo.get(i);
                    terreno.setActivo(true);
                }
            } else if (player == 2) {
                //RECORRE TERRITORIO
                int xOrig = p2.getPosX();
                int yOrig = p2.getPosY();
                p2.setPosX(xOrig - 1);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                /////
                p2.setPosX(xOrig - 2);
                //DESTRUYE LA ARENA
                Celda celda = gestorMapa.getMapa(nivel).getMapaAt(yOrig, xOrig - 1);
                celda.setObj(new Terreno('N', 2));
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
            } else if (player == 3) {
                //NOTHING
            }
        } else if (nivel == 2) {
            if (player == 1) {
                for (int i = 0; i < 3; i++) {
                    p1.setPosX(p1.getPosX() + 1);
                    this.renderizar();
                    System.out.println("Presiona ENTER para continuar...");
                    scan.nextLine();
                }
                //ACTIVAR TERRENOS
                List listaTerrenoInactivos = gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < listaTerrenoInactivos.size(); i++) {
                    Terreno terreno = (Terreno) listaTerrenoInactivos.get(i);
                    terreno.setActivo(true);
                }
            } else if (player == 2) {
                //NOTHING
            } else if (player == 3) {
                p1.setPosX(p1.getPosX() + 1);
                p2.setPosX(p2.getPosX() + 1);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                p1.setPosX(p1.getPosX() + 3);
                p1.setPosY(p1.getPosY() - 1);
                p2.setPosX(p2.getPosX() + 3);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                p1.setPosY(p1.getPosY() + 1);
                p2.setPosY(p2.getPosY() - 1);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
            }
        } else if (nivel == 3) {//NIVEL CON ENEMIGO
            if (player == 1) {
                //NADA
            } else if (player == 2) {
                //ANIMACION
                int xOrig = p2.getPosX();
                int yOrig = p2.getPosY();
                //1
                p2.setPosY(yOrig - 1);
                p2.setPosX(xOrig - 1);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                //2
                p2.setPosY(yOrig - 3);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                //3
                p2.setPosY(yOrig - 4);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
                //DESTRUYE ENEMIGO Y TRIGGERS
                Terreno t = new Terreno('S', 1);
                Mapa m = gestorMapa.getMapa(nivel);
                m.setMapaAt(4, 9, t);
                m.setMapaAt(3, 10, t);
                m.setMapaAt(4, 10, t);
                m.setMapaAt(5, 10, t);
                m.setMapaAt(6, 10, t);
                //4
                p2.setPosX(xOrig);
                p2.setPosY(yOrig);
                this.renderizar();
                System.out.println("Presiona ENTER para continuar...");
                scan.nextLine();
            } else if (player == 3) {
                //NOTHING
            }
        }
    }

    private void actualizarInfo() throws IOException, InterruptedException {
        /*Llego al final de la menta, pasa de nivel*/
        /*VERIFICA P1 y P2 EN META*/

        int posX1 = p1.getPosX();
        int posY1 = p1.getPosY();
        int posX2 = p2.getPosX();
        int posY2 = p2.getPosY();

        Celda celda1 = gestorMapa.getMapa(nivel).getMapaAt(posY1, posX1);
        Celda celda2 = gestorMapa.getMapa(nivel).getMapaAt(posY2, posX2);
        /*VERIFICA LLEGARON A LA META*/
        if (celda1.getObj() instanceof Terreno && celda2.getObj() instanceof Terreno) {
            if (((Terreno) celda1.getObj()).getTipo() == 6
                    && ((Terreno) celda2.getObj()).getTipo() == 6) {
                renderizar();
                nivel += 1;
                inicio_Nivel = true;
                System.out.print("Acabaste el nivel " + (nivel - 1));
                System.out.print(". Presiona ENTER para continuar...");
                scan.nextLine();
                inicializarPersonajes(nivel);
                inicializarActividad(nivel);
            }
        }
        
        /*VERIFICA SI EL PERSONAJE CAYÓ EN UN TRIGGER ENEMIGO*/
        if (celda1.getObj() instanceof Terreno || celda2.getObj() instanceof Terreno) {
            Terreno ter = (Terreno) celda1.getObj();
            if (ter.getActivo() && ter.getTipo() == 5) {
                //ACTIVE LA VISIBILIDAD DEL ENEMIGO Y QUE LO MUESTRE
                Enemigo e = this.gestorMapa.getEnemigo(nivel);
                e.setElementoGrafico('E');
                //DISMINUIR LA VIDA DEL JUGADOR 1
                this.p1.setVida(this.p1.getVida() - 1);
                //Activar el terreno de accion
                List l = this.gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < l.size(); i++) {
                    Terreno t = (Terreno) (l.get(i));
                    t.setActivo(true);
                }
            }
        }

    }

    private void renderizar() throws IOException, InterruptedException {
        cleanWindow();
        /*MOSTRAR MAPA*/
        System.out.print("Jugador 1: " + this.p1.getNombre());
        System.out.println("\tJugador 2: " + this.p2.getNombre());
        System.out.println("Puntos de vida: " + p1.getVida());
        System.out.println("Nivel: " + nivel);
        rend.mostrarMapa(gestorMapa, nivel, p1, p2);
    }

    private boolean finJuego() {
        /*TOPE NIVEL: cantidad de mapas*/
        /*Si ha muerto o terminó todos los niveles*/
        return (p1.getVida() <= 0) || (nivel == gestorMapa.getNumNiveles());
    }
    
    private void inicializarPersonajes(int nivel) {
        /*AQUI SE PUEDE REALIZAR LECTURA DE PERSONAJE Y ENEMIGO*/
        /*SUS DATOS, ETC*/
        if (p1 == null) {
            p1 = new Personaje('A');
        }
        if (p2 == null) {
            p2 = new Personaje('B');
        }
        if (p1.getVida() <= 0) {
            p1.setVida(10);
        }
        if (nivel == 0) {
            Cargar_Niveles_XML(0);
        } else if (nivel == 1) {
            Cargar_Niveles_XML(1);
        } else if (nivel == 2) {
            Cargar_Niveles_XML(2);
        } else if (nivel == 3) {
            Cargar_Niveles_XML(3);
        }

    }

    private void Cargar_Niveles_XML(int nivel) {
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
        Mapa mapa = gestorMapa.getMapa(nivel);
        /*PARCHE 1*/
        this.parcheActividadInicial(nivel);
        if (nivel == 0) {
            cargar_Actividad_XML(0);
        } else if (nivel == 1) {
            cargar_Actividad_XML(1);
//            Terreno terreno1 = ((Terreno) mapa.getMapaAt(9, 4).getObj());
//            terreno1.setActivo(false);
//            mapa.getListaTerrenoInactivo().add(terreno1);
        } else if (nivel == 2) {
            cargar_Actividad_XML(2);
//            Terreno terreno1 = ((Terreno) mapa.getMapaAt(6, 8).getObj());
//            Terreno terreno2 = ((Terreno) mapa.getMapaAt(8, 8).getObj());
//            terreno1.setActivo(false);
//            terreno2.setActivo(false);
//            mapa.getListaTerrenoInactivo().add(terreno1);
//            mapa.getListaTerrenoInactivo().add(terreno2);
        } else if (nivel == 3) {
            cargar_Actividad_XML(3);
//            Terreno terreno1 = ((Terreno) mapa.getMapaAt(9, 10).getObj());
//            terreno1.setActivo(false);
//            mapa.getListaTerrenoInactivo().add(terreno1);
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
            NodeList nList = doc.getElementsByTagName("Terreno");
            Node nNode = nList.item(nivel);
            Element eElement = (Element) nNode;
            
            int fila1 = Integer.parseInt(eElement.getElementsByTagName("fila1").item(0).getTextContent());
            int columna1 = Integer.parseInt(eElement.getElementsByTagName("columna1").item(0).getTextContent());
            
            int fila2 = Integer.parseInt(eElement.getElementsByTagName("fila2").item(0).getTextContent());
            int columna2 = Integer.parseInt(eElement.getElementsByTagName("columna2").item(0).getTextContent());
            
            Terreno terreno1 = ((Terreno) mapa.getMapaAt(fila1, columna1).getObj());
            terreno1.setActivo(false);
            mapa.getListaTerrenoInactivo().add(terreno1);
            
            if (nivel == 0 || nivel == 2) {
                Terreno terreno2 = ((Terreno) mapa.getMapaAt(fila2, columna2).getObj());
                terreno2.setActivo(false);                
                mapa.getListaTerrenoInactivo().add(terreno2);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    private void bienvenida() throws IOException, InterruptedException {
        cleanWindow();
        System.out.println("\n\n");
        System.out.println("        __   __             _              _                    _       ");
        System.out.println("        \\ \\ / /            | |            | |                  | |      ");
        System.out.println("         \\ V /           __| |  ___     __| |  ___   _ __    __| |  ___ ");
        System.out.println("          \\ /           / _` | / _ \\   / _` | / _ \\ | '_ \\  / _` | / _ \\");
        System.out.println("          | | _  _  _  | (_| ||  __/  | (_| || (_) || | | || (_| ||  __/");
        System.out.println("          \\_/(_)(_)(_)  \\__,_| \\___|   \\__,_| \\___/ |_| |_| \\__,_| \\___|");
        System.out.println("\n");
        System.out.println("                                                         ___  ");
        System.out.println("                                                        |__ \\ ");
        System.out.println("                       ___   ___   _   _    _   _   ___    ) |");
        System.out.println("                      / __| / _ \\ | | | |  | | | | / _ \\  / / ");
        System.out.println("                      \\__ \\| (_) || |_| |  | |_| || (_) ||_|  ");
        System.out.println("                      |___/ \\___/  \\__, |   \\__, | \\___/ (_)  ");
        System.out.println("                                    __/ |    __/ |            ");
        System.out.println("                                   |___/    |___/             ");
        System.out.print("\nPresiona ENTER para continuar...");
        scan.nextLine();
    }
    
    private boolean menu() throws IOException, InterruptedException {
        cleanWindow();
        do {
            System.out.println("1. Empezar el juego");
            System.out.println("2. Salir");
            //Leer opción
            int opc = 0;
            String aux = scan.nextLine();
            try {
                opc = Integer.parseInt(aux);
            } catch (NumberFormatException ex) {
                System.out.println("Error: Ingrese una opcion valida.");
                System.out.println();
                continue;
            }
            if (opc == 1) {
                /* Leer nombres de jugadores */
                cleanWindow();

                //Si ya se jugó una partida anterior ya no se piden los nombres
                if (this.p1.getNombre() == null || this.p2.getNombre() == null) {
                    System.out.println("Ingrese el nombre del primer jugador:");
                    this.p1.setNombre(scan.nextLine());
                    System.out.println("Ingrese el nombre del segundo jugador:");
                    this.p2.setNombre(scan.nextLine());
                }

                cleanWindow();

                if (nivel >= gestorMapa.getNumNiveles()) {
                    nivel = 0;
                    p1.setVida(10);
                }
                inicializarPersonajes(nivel);
                inicializarActividad(nivel);
                return false;
            } else if (opc == 2) {
                confirmarFinJuego();
            } else if (opc == 3) {
                //CARGAR ARCHIVO GUARDADO
                //inicializarPersonajes(nivel);
                //inicializarActividad(nivel);
                //return false;
            }
        } while (true);

    }    
    
    private void cleanWindow() throws IOException, InterruptedException {
        //Limpia la ventana (solo funciona en consola)
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
    
    private void parcheActividadInicial(int nivel){
        Mapa mapa = gestorMapa.getMapa(nivel);
        for (int i = 0; i < 12; i++)
            for(int j = 0; j < 16; j++){
                Celda celda = mapa.getMapaAt(i, j);
                Dibujable dib = celda.getObj();
                if (dib instanceof Terreno)
                    ((Terreno) dib).setActivo(true);
            }
    }
}
