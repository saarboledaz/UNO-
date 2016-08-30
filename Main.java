
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    

    static Scanner entrada = new Scanner(System.in);
    static Random rnd = new Random();
    static ArrayList<Integer> deck = new ArrayList<>(); /* Arreglo dinámico con los ids de las 108 cartas */
    static ArrayList<Integer> deckS = new ArrayList<>(); /*Arreglo dinámico con los ids de las cartas ya jugadas */
    static ArrayList<Integer> deckPlayer = new ArrayList<>(); /*Arreglo dinámico con los ids de las cartas del jugador */
    static ArrayList<Integer> pc1 = new ArrayList<>(); /*Arreglos dinámicos con los ids de las cartas de las pcs */
    static ArrayList<Integer> pc2 = new ArrayList<>();
    static ArrayList<Integer> pc3 = new ArrayList<>();
    static String Cartas[] = new String[108]; /* Arreglo estàtico con todas las cartas del juego  */
    static int Op = 0, P1 = 1, P2 = 2, P3 = 3, Se = 1;
    static int reverse[] = {0, 1, 2, 3};
    static boolean cambio = false, juega = true;    
    static char c[] = {'T','R', 'G', 'Y', 'B'};
    static String ce[] = {"boyamarica","\033[31mRojo", "\033[32mVerde", "\033[33mAmarillo", "\033[34mAzul"};
    static int UCarta;
    static int NDraw = -1; /* Cartas a robar */
    static String Win = "nadie";/*Cambiar por un booleano? */
    static String nick;

    public static void main(String[] args) throws InterruptedException {      
        inst(); /* Imprime las instrucciones del juego */  
        System.out.println("Ingrese su nick:");
        nick = entrada.next(); /* Pide el nick */
        System.out.println("");
        Cartas = llenar(Cartas); /* Llena el arreglo estático 'Cartas', con las cartas del juego con su número o acciòn y su color*/
        deck = llenar(deck); /* LLena el arreglo dinámico 'deck' con las IDs de las 108 cartas del juego */
        UCarta = draw(); /* Toma una carta del mazo y la pone como la ultima carta jugada */
        int turnos = 1; /* Turno número 1 */
        deckPlayer = repartir(deckPlayer); /* Llena el arreglo dinàmico 'deckPlayer' con 7 IDs que corresponden a 7 cartas iniciales */
        pc1 = repartir(pc1); /* Llena el arreglo dinámico 'pc1' con 7 IDs de las cartas de la IA */ 
        pc2 = repartir(pc2); 
        pc3 = repartir(pc3);
        System.out.println("---------------Inicio-----------------");
        System.out.println("Primera Carta: " + ccarta(UCarta)); /* Imprime la primera carta jugada */
        System.out.println("");

        while ("nadie".equals(Win)) {
            for (int i = 0; i < 4; i++) {

                if (i == Op) {
                    /* Si es el turno de el jugador */
                    if (NDraw != -1) {
                        //Si el jugador se ve afectado por una carta de acción(Más 2, Más 4, Salta turno)
                        
                        if (NDraw == 0) {
                            // Si esta carta es Salta turno, el jugador no tiene que robar cartas
                            NDraw = -1;
                        } else {
                            // Si el jugador tiene que robar cartas debido a una carta de acción
                            System.out.println("---------------Ronda " + turnos + "-----------------");
                            info(UCarta);//Imprime la información importante para el jugador//
                            System.out.println("");
                            turnos++;//Aumenta el turno
                            deckPlayer = draw(deckPlayer, NDraw, nick);//Extrae de la baraja NDraw cartas, siendo NDraw el número de cartas que el jugador tiene que robar
                            NDraw = -1; //NDraw vuelve a su valor inicial
                            new Scanner(System.in).nextLine();//Espera la confirmación del jugador para seguir el juego
                        }
                    } else {
                        //Si el jugador no se ve afectado por una carta de acción
                        System.out.println("---------------Ronda " + turnos + "-----------------");
                        info(UCarta);//Imprime la información importante para el jugador//
                        System.out.println("");
                        turnos++;//Aumenta el turno
                        JU(deckPlayer, UCarta, nick);
                        if (!"nadie".equals(Win)) {
                            break;
                        }
                        if (cambio == true) {
                            break;
                        }
                    }
                }
                if (i == P1) {
                    if (NDraw != -1) {
                        if (NDraw == 0) {
                            NDraw = -1;
                        } else {
                            pc1 = draw(pc1, NDraw, "PC1");
                            NDraw = -1;
                        }
                    } else {
                        IA(pc1, UCarta, "PC1");
                        if (!"nadie".equals(Win)) {
                            break;
                        }
                        if (cambio == true) {
                            break;
                        }
                    }
                }
                if (i == P2) {
                    if (NDraw != -1) {
                        if (NDraw == 0) {
                            NDraw = -1;
                        } else {
                            pc2 = draw(pc2, NDraw, "PC2");
                            NDraw = -1;
                        }
                    } else {
                        IA(pc2, UCarta, "PC2");
                        if (!"nadie".equals(Win)) {
                            break;
                        }
                        if (cambio == true) {
                            break;
                        }
                    }
                }
                if (i == P3) {
                    if (NDraw != -1) {
                        if (NDraw == 0) {
                            NDraw = -1;
                        } else {
                            pc3 = draw(pc3, NDraw, "PC3");
                            NDraw = -1;
                        }
                    } else {
                        IA(pc3, UCarta, "PC3");
                        if (!"nadie".equals(Win)) {
                            break;
                        }
                        if (cambio == true) {
                            break;
                        }
                    }
                }
            }
            if (juega) {
                Op = reverse[0];
                P1 = reverse[1];
                P2 = reverse[2];
                P3 = reverse[3];
                cambio = false;

            } else {
                juega = true;
                cambio = false;

            }

            new Scanner(System.in).nextLine();

        }
        if (Win == nick) {
            for (int i = 0; i < 6; i++) {
                System.out.println("\033[30m!Felicidades " + nick + " has ganado¡");
                System.out.println("\033[31m!Felicidades " + nick + " has ganado¡");
                System.out.println("\033[32m!Felicidades " + nick + " has ganado¡");
                System.out.println("\033[33m!Felicidades " + nick + " has ganado¡");
                System.out.println("\033[34m!Felicidades " + nick + " has ganado¡");
                System.out.println("\033[35m!Felicidades " + nick + " has ganado¡");
                System.out.println("\033[36m!Felicidades " + nick + " has ganado¡");
                System.out.println("\033[37m!Felicidades " + nick + " has ganado¡");
            }
        } else {
            System.out.println(Win + ": !Uno¡");
            System.out.println("Gana " + Win);
        }
    }

    static void inst() {
        /* Instrucciones */
        System.out.println("-Para jugar una carta solo debe poner por consola \n"
                + "el indice que le coresponde a cada carta \n"
                + "iniciando de 0 y terminado en el n-1");
        System.out.println("");
        System.out.println("-Si juega una carta que pueda cambiar el color de juego \n"
                + "solo tendra que poner el indice de la carta y despues el color (rojo,verde,azul,amarillo)");
        System.out.println("");
        System.out.println("-Si no tiene cartas que le sirvan para jugar solo digite un 1 \n"
                + "con esto robara una carta automaticamente");
        System.out.println("");
        System.out.println("+Si trata de jugar una carta que no corresponde a las condiciones \n"
                + "para ser jugada sera penalizado robando una carta");
        System.out.println("");
    }

    static void print(String lista[]) {
        System.out.print("[");
        for (int i = 0; i < lista.length; i++) {
            System.out.print(lista[i]);
            if (i < lista.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    static void print(short lista[]) {
        System.out.print("[");
        for (int i = 0; i < lista.length; i++) {
            System.out.print(lista[i]);
            if (i < lista.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    static void print(ArrayList lista) {
        System.out.print("[");
        for (int i = 0; i < lista.size(); i++) {
            if ((int) lista.get(i) != -1) {
                System.out.print(lista.get(i));
            }
            if (i < lista.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    static String ccarta(int x) {
        /* recibe la ID de una carta y devuelve su traducciòn 'Color Número' */
        String cartaS = Cartas[x];
        char color = cartaS.charAt(0);
        char numero = cartaS.charAt(1);
        String carta = traductor(color, numero);
        return carta;
    }

    static ArrayList llenar(ArrayList lista) {
        /* Llena el arreglo dinàmico ingresado con los ID de las 108 cartas */
        for (int i = 0; i < 108; i++) {
            lista.add(i);
        }

        return lista;
    }

    static void quecartas(ArrayList player) {
        int carta;
        String casiCarta;
        char color, numero;
        System.out.print("[");
        for (int i = 0; i < player.size(); i++) {
            carta = (int) player.get(i);
            casiCarta = (Cartas[carta]);
            color = casiCarta.charAt(0);
            numero = casiCarta.charAt(1);
            casiCarta = traductor(color, numero);
            System.out.print(casiCarta);

            if (i < player.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    static void quecartasI(ArrayList player) {
        int carta;
        String casiCarta;
        char color, numero;
        for (int i = 0; i < player.size(); i++) {
            carta = (int) player.get(i);
            casiCarta = (Cartas[carta]);
            color = casiCarta.charAt(0);
            numero = casiCarta.charAt(1);
            casiCarta = traductor(color, numero);
            System.out.println("\033[30m" + "[" + i + "] = " + casiCarta);

        }

    }

    static void comp(int x) {
        /*Comprueba el número de cartas que hay en el mazo, si no hay cartas, llena el mazo con las cartas ya usadas */
        int m = 0, borrados = 0;
        for (int i = 0; i < 108; i++) {
            if (deck.get(i) == -1) {
                m++;
            }
        }
        if ((108 - m) < x) {
            System.out.println("La baraja se quedo sin cartas ahora se reutilizaran las cartas ya usadas");
            for (int i = 0; i < 108; i++) {
                if (deck.get(i) == -1) {
                    deck.remove(i);
                    borrados++;
                }
                if (borrados == deckS.size()) {
                    break;
                }
            }
            for (int i = 0; i < deckS.size(); i++) {
                deck.add(deckS.get(i));
            }
        }
    }

    static ArrayList repartir(ArrayList player) {
        /* Genera IDs de forma aleatoria hasta encontrar uno que aùn se encuentre en el mazo,
        si lo encuentra lo añade a la baraja del jugador y cambia su valor en el mazo por un '-1',
        hace este procedimiento un total de 7 veces(#cartas iniciales) y retorna un arreglo dinàmico lleno
        */
        boolean sr = true;
        int x;
        for (int i = 0; i < 7; i++) {
            sr = true;
            while (sr) {
                x = ((int) (rnd.nextDouble() * 108));
                if (deck.contains(x)) {
                    player.add(x);
                    deck.set(x, -1);
                    sr = false;
                }
            }
        }
        return player;
    }

    static String[] llenar(String lista[]) {
        /* Llena el arreglo estàtico ingresado con las 108 cartas del juego */
        int ai = 0;
        int ind = 0;
        String colores[] = {
            "R", "Y", "B", "G"
        };
        String especiales[] = {
            "Skip", "Reverse", "DMas", "CC", "4Mas"
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                lista[ai] = (colores[i] + ind);
                ai++;
                if (j >= 1) {
                    lista[ai] = (colores[i] + ind);
                    ai++;
                }
                ind++;
            }
            ind = 0;
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 || i == 1 || i == 2) {
                    lista[ai] = (colores[j] + especiales[i]);
                    ai++;
                    lista[ai] = (colores[j] + especiales[i]);
                    ai++;
                } else {
                    lista[ai] = (especiales[i]);
                    ai++;
                }

            }
        }
        return lista;
    }

    static int draw() {
        /* Primero comprueba si hay cartas en el mazo, 
        luego busca una carta cualquiera del mazo, la borra de este y la devuelve */
        comp(1);
        int carta, x = -1;
        boolean sr = true;
        while (sr) {
            carta = ((int) (rnd.nextDouble() * 100));
            if (deck.contains(carta)) {
                deck.set(carta, -1);
                return carta;

            }
            System.out.println(carta);
        }
        return x;
    }

    static ArrayList draw(ArrayList mano, int u, String p) {
        /* Recibe un arreglo dinámico que corresponde a una mano, un entero que corresponde al número
        de cartas a robar y un string que es el nick de el jugador
        */
        comp(u);
        System.out.println("\033[30m" + p + " tiene que robar " + u + " cartas y pierde turno");
        boolean sr;
        int x;
        for (int i = 0; i < u; i++) {
            sr = true;
            while (sr) {
                x = ((int) (rnd.nextDouble() * 108));
                if (deck.contains(x)) {
                    mano.add(x);
                    deck.set(x, -1);
                    sr = false;
                }
            }
        }
        System.out.println(p + " Roba " + u + " cartas");
        new Scanner(System.in).nextLine();
        NDraw = 0;
        if (p == nick) {
            infoCarta(UCarta);
            System.out.println("Mano: ");
            quecartasI(deckPlayer);
            //System.out.println("");
        }
        return mano;
    }

    static ArrayList draw(ArrayList mano, String p) {
        comp(1);
        boolean sr;
        int x;
        sr = true;
        while (sr) {
            x = ((int) (rnd.nextDouble() * 108));
            if (deck.contains(x)) {
                mano.add(x);
                deck.set(x, -1);
                sr = false;
            }
        }

        NDraw = 0;
        if (p == nick) {
            System.out.print("Mano: ");
            quecartasI(deckPlayer);
            System.out.println("");
        }
        return mano;
    }

    static int traductorColor(String color) {
        int c = 0;
        color = color.toLowerCase();
        switch (color) {
            case "rojo":
                c = -1;
                break;
            case "verde":
                c = -2;
                break;
            case "azul":
                c = -4;
                break;
            case "amarillo":
                c = -3;
                break;
        }
        return c;
    }

    static int traductorN(String n) {
        int x = -1;
        if (n == nick) {
            x = 0;
        }
        switch (n) {
            case "PC1":
                x = 1;
                break;
            case "PC2":
                x = 2;
                break;
            case "PC3":
                x = 3;
                break;
        }
        return x;
    }

    static String traductor(char a, char b) {
        /*Recibe dos carácteres, que pueden corresponder a: el color(r,g,y,b) y el número(0,9),
        o a la primera letra de la acción('Skip','Reverse','CC','2Mas','4mas')
        */
        String respuesta, color = "", numero = "", colorConsola = "";
        switch (a) {
            case 'R':
                color = "Rojo";
                colorConsola = "\033[31m";
                break;
            case 'Y':
                color = "Amarillo";
                colorConsola = "\033[33m";
                break;
            case 'B':
                color = "Azul";
                colorConsola = "\033[34m";
                break;
            case 'G':
                color = "Verde";
                colorConsola = "\033[32m";
                break;
            case 'C':
                color = "Cambio de color";
                break;
            case '4':
                color = "Mas 4";
                break;
        }
        switch (b) {
            case '0':
                numero = "0 ";
                break;
            case '1':
                numero = "1 ";
                break;
            case '2':
                numero = "2 ";
                break;
            case '3':
                numero = "3 ";
                break;
            case '4':
                numero = "4 ";
                break;
            case '5':
                numero = "5 ";
                break;
            case '6':
                numero = "6 ";
                break;
            case '7':
                numero = "7 ";
                break;
            case '8':
                numero = "8 ";
                break;
            case '9':
                numero = "9 ";
                break;
            case 'S':
                numero = "Saltar ";
                break;
            case 'R':
                numero = "Devolver ";
                break;
            case 'D':
                numero = "Mas 2 ";
                break;
            case 'Z':
                numero = "";
                break;

        }
        respuesta = (colorConsola + numero + color);
        return respuesta;
    }

    static ArrayList IA(ArrayList mano, int ultimo, String player) throws InterruptedException {
        System.out.println("Turno de " + player);
        Thread.sleep(100);
        char color = ' ', numero = ' ', cmano, nmano;
        boolean d = true;
        String carta, ucarta;
        int idCarta;
        if (ultimo < 0) {
            color = c[Math.abs(ultimo)];
            numero = 'Z';
        } else {
            String ultima = Cartas[ultimo];
            color = ultima.charAt(0);
            numero = ultima.charAt(1);
        }
        while (d) {
            for (int i = 0; i < mano.size(); i++) {
                idCarta = (int) mano.get(i);
                cmano = (Cartas[idCarta].charAt(0));
                nmano = (Cartas[idCarta].charAt(1));
                if (cmano == 'C' || cmano == '4') {
                    if (cmano == '4') {
                        NDraw = 4;
                    }

                    carta = traductor(cmano, nmano);
                    ucarta = traductor(color, numero);
                    System.out.println("\033[30m" + player + " juega la carta '" + carta + "\033[30m" + "' sobre la carta '" + ucarta + "\033[30m" + "'");
                    deckS.add(idCarta);
                    mano.remove(mano.indexOf(idCarta));
                    if (mano.size() == 1) {
                        Win = player;
                    }
                    int x = ((int) ((rnd.nextDouble() * 3) + 1));
                    System.out.println(x);
                    UCarta = -x;
                    int temp = x-1;
                    System.out.println("\033[30m" + player + " Cambia el color a " + ce[Math.abs(x)]);
                    new Scanner(System.in).nextLine();
                    return mano;

                } else if (color == cmano) {
                    if (nmano == 'D') {
                        NDraw = 2;
                    }

                    carta = traductor(cmano, nmano);
                    ucarta = traductor(color, numero);
                    System.out.println("\033[30m" + player + " juega la carta '" + carta + "\033[30m" + "' sobre la carta '" + ucarta + "\033[30m" + "'");
                    deckS.add(idCarta);
                    mano.remove(mano.indexOf(idCarta));
                    if (mano.size() == 1) {
                        Win = player;
                    }
                    UCarta = idCarta;

                    if (nmano == 'R') {
                        int orden = traductorN(player);
                        if (Se == 1) {
                            switch (orden) {
                                case 0:
                                    reverse[0] = 3;
                                    reverse[1] = 2;
                                    reverse[2] = 1;
                                    reverse[3] = 0;
                                    break;
                                case 1:
                                    reverse[0] = 0;
                                    reverse[1] = 3;
                                    reverse[2] = 2;
                                    reverse[3] = 1;
                                    break;
                                case 2:
                                    reverse[0] = 1;
                                    reverse[1] = 0;
                                    reverse[2] = 3;
                                    reverse[3] = 2;
                                    break;
                                case 3:
                                    reverse[0] = 2;
                                    reverse[1] = 1;
                                    reverse[2] = 0;
                                    reverse[3] = 3;
                                    break;
                            }
                            cambio = true;
                            Se = -1;
                            System.out.println("\033[30m" + player + " cambio la direccion del juego");

                        } else {
                            switch (orden) {
                                case 0:
                                    reverse[0] = 3;
                                    reverse[1] = 0;
                                    reverse[2] = 1;
                                    reverse[3] = 2;
                                    break;
                                case 1:
                                    reverse[0] = 2;
                                    reverse[1] = 3;
                                    reverse[2] = 0;
                                    reverse[3] = 1;
                                    break;
                                case 2:
                                    reverse[0] = 1;
                                    reverse[1] = 2;
                                    reverse[2] = 3;
                                    reverse[3] = 0;
                                    break;
                                case 3:
                                    reverse[0] = 0;
                                    reverse[1] = 1;
                                    reverse[2] = 2;
                                    reverse[3] = 3;
                                    break;
                            }
                            cambio = true;
                            Se = 1;
                            System.out.println("\033[30m" + player + " cambio la direccion del juego");
                        }

                    }

                    if (nmano == 'S') {
                        if (Se == 1) {
                            int orden = traductorN(player);
                            switch (orden) {
                                case 0:
                                    NDraw = 0;
                                    System.out.println("\033[30m" + "PC1 pierde turno");
                                    break;
                                case 1:
                                    NDraw = 0;
                                    System.out.println("\033[30m" + "PC2 pierde turno");
                                    break;
                                case 2:
                                    NDraw = 0;
                                    System.out.println("\033[30m" + "PC3 pierde turno");
                                    break;
                                case 3:
                                    NDraw = 0;
                                    System.out.println("\033[30m" + nick + " pierde turno");
                                    break;
                            }
                            System.out.println("");
                        } else {
                            int orden = traductorN(player);
                            switch (orden) {
                                case 0:
                                    NDraw = 0;
                                    System.out.println("\033[30m" + "PC3 pierde turno");
                                    break;
                                case 1:
                                    NDraw = 0;
                                    System.out.println("\033[30m" + nick + " pierde turno");
                                    break;
                                case 2:
                                    NDraw = 0;
                                    System.out.println("\033[30m" + "PC1 pierde turno");
                                    break;
                                case 3:
                                    NDraw = 0;
                                    System.out.println("\033[30m" + "Pc2 pierde turno");
                                    break;
                            }
                        }
                    }
                    new Scanner(System.in).nextLine();
                    return mano;

                } else if (numero == nmano) {
                    if (nmano == 'D') {
                        NDraw = 2;
                    }

                    carta = traductor(cmano, nmano);
                    ucarta = traductor(color, numero);
                    System.out.println("\033[30m" + player + " juega la carta '" + carta + "\033[30m" + "' sobre la carta '" + ucarta + "\033[30m" + "'");
                    deckS.add(idCarta);
                    mano.remove(mano.indexOf(idCarta));
                    if (mano.size() == 1) {
                        Win = player;
                    }
                    UCarta = idCarta;
                    if (nmano == 'S') {
                        int orden = traductorN(player);
                        switch (orden) {
                            case 0:
                                NDraw = 0;
                                System.out.println("\033[30m" + "PC1 pierde turno");
                                break;
                            case 1:
                                NDraw = 0;
                                System.out.println("\033[30m" + "PC2 pierde turno");
                                break;
                            case 2:
                                NDraw = 0;
                                System.out.println("\033[30m" + "PC3 pierde turno");
                                break;
                            case 3:
                                NDraw = 0;
                                System.out.println("\033[30m" + nick + " pierde turno");
                                break;
                        }
                        System.out.println("");
                    }

                    if (nmano == 'R') {
                        int orden = traductorN(player);
                        if (Se == 1) {
                            switch (orden) {
                                case 0:
                                    reverse[0] = 3;
                                    reverse[1] = 2;
                                    reverse[2] = 1;
                                    reverse[3] = 0;
                                    break;
                                case 1:
                                    reverse[0] = 0;
                                    reverse[1] = 3;
                                    reverse[2] = 2;
                                    reverse[3] = 1;
                                    break;
                                case 2:
                                    reverse[0] = 1;
                                    reverse[1] = 0;
                                    reverse[2] = 3;
                                    reverse[3] = 2;
                                    break;
                                case 3:
                                    reverse[0] = 2;
                                    reverse[1] = 1;
                                    reverse[2] = 0;
                                    reverse[3] = 3;
                                    break;
                            }
                            cambio = true;
                            Se = -1;
                            System.out.println("\033[30m" + player + " cambio la direccion del juego");

                        } else {
                            switch (orden) {
                                case 0:
                                    reverse[0] = 3;
                                    reverse[1] = 0;
                                    reverse[2] = 1;
                                    reverse[3] = 2;
                                    break;
                                case 1:
                                    reverse[0] = 2;
                                    reverse[1] = 3;
                                    reverse[2] = 0;
                                    reverse[3] = 1;
                                    break;
                                case 2:
                                    reverse[0] = 1;
                                    reverse[1] = 2;
                                    reverse[2] = 3;
                                    reverse[3] = 0;
                                    break;
                                case 3:
                                    reverse[0] = 0;
                                    reverse[1] = 1;
                                    reverse[2] = 2;
                                    reverse[3] = 3;
                                    break;
                            }
                            cambio = true;
                            Se = 1;
                            System.out.println("\033[30m" + player + " cambio la direccion del juego");
                        }

                    }

                    new Scanner(System.in).nextLine();
                    return mano;
                }
            }
            System.out.println("\033[30m" + player + " roba carta");
            mano = draw(mano, player);
        }
        return mano;

    }

    static void infoCarta(int ultimo) {
        char color = ' ', numero = ' ';
        String carta;
        if (ultimo < 0) {
            color = c[Math.abs(ultimo)];
            numero = ' ';
        } else {
            String ultima = Cartas[ultimo];
            color = ultima.charAt(0);
            numero = ultima.charAt(1);
        }
        System.out.println("\033[30m" + "Ultima carta jugada: " + traductor(color, numero));
        System.out.println("");
    }

    static void info(int ultimo) {
        /* Recibe la ID de la última carta jugada, si este ID es negativo o igual a 0
        es porque la carta actual solo tiene color, siendo 0, -1, -2, -3 rojo, verde,
        amarillo y azul respectivamente. Si el ID es positivo, busca la carta en el arreglo estático global
        'Cartas', y obtiene su color y valor, finalmente imprime toda la información acerca del juego
        */
        char color = ' ', numero = ' ';
        if (ultimo < 0) {
            color = c[Math.abs(ultimo)];
            numero = ' ';
        } else {
            String ultima = Cartas[ultimo];
            color = ultima.charAt(0);
            numero = ultima.charAt(1);
        }
        System.out.println("\033[30m" + "PC1 tiene " + pc1.size() + " cartas");
        System.out.println("\033[30m" + "PC2 tiene " + pc2.size() + " cartas");
        System.out.println("\033[30m" + "PC3 tiene " + pc3.size() + " cartas");
        System.out.println("\033[30m" + "Tienes " + deckPlayer.size() + " cartas");
        System.out.println("");
        System.out.println("\033[30m" + "Ultima carta jugada: " + traductor(color, numero));
        System.out.println("");
        System.out.print("\033[30m" + "Mano:");
        System.out.println("");
        quecartasI(deckPlayer);
    }
    //////////////////////////////////////////////////////////////////////

    static ArrayList JU(ArrayList mano, int ultimo, String player) {
        /*Recibe la baraja del jugador, la ID de la última carta y el nick del jugador
        */
        
        char color = ' ', numero = ' ', cmano, nmano;
        boolean Cvalida = true;
        String carta, ucarta;
        int idCarta = -2, temp;
        if (ultimo < 0) {
            //Si la id de la ultima carta jugada es negativa o igual a cero, es porque esta carta solo tiene color
            color = c[Math.abs(ultimo)];//-1,-2,-3,-4 para rojo, verde, amarillo y azul respectivamente
            numero = ' ';//Solo tiene color, no tiene número
        } else {
            String ultima = Cartas[ultimo];//Busca la carta en el arreglo estático global 'Cartas'
            color = ultima.charAt(0);
            numero = ultima.charAt(1);
        }
        ////////////////////////
        while (true) {
            Cvalida = true;
            while (Cvalida) {
                temp = entrada.nextInt();// Índice de la carta que el usuario va a elegir
                if (temp < mano.size() && temp >= 0) { // Si el índice elegido está en la baraja de el jugador
                    idCarta = (int) mano.get(temp);// Busca el ID de la carta con el índice escogido
                    Cvalida = false;
                } else if (temp == -1) { // Si el número ingresado es -1, el jugado roba una carta
                    System.out.println("\033[30m" + player + " roba carta");
                    System.out.println("");
                    mano = draw(mano, player); //'Player' roba una carta cualquiera para su 'mano'
                    

                } else {
                    //Si el índice ingresado no está en la baraja
                    info(UCarta);
                    System.out.println("");
                    System.out.println("Estas tratando de jugar una carta que no tienes");
                    System.out.println("Indice jugado: " + temp + ".... Maximo incide permitido: " + (mano.size() - 1));
                    System.out.println("Si quieres robar carta el indice es: -1");
                }
            }

            cmano = (Cartas[idCarta].charAt(0));//Decodifica la carta en color, número o acción
            nmano = (Cartas[idCarta].charAt(1));
            if (cmano == 'C' || cmano == '4') { //Si la carta es 'CC'(Cambio Color) o '4Más'(Coge cuatro cartas y cambia color)
                if (cmano == '4') {//Si es '4mas' el número de cartas a robar para el próximo jugador será 4
                    NDraw = 4;
                }

                carta = traductor(cmano, nmano);//Traduce cual carta es la que eligió el jugador
                ucarta = traductor(color, numero);//Traduce cual carta es la ultima en el mazo(la que está en juego)
                System.out.println("\033[30m" + player + " juega la carta '" + carta + "\033[30m" + "' sobre la carta '" + ucarta + "\033[30m" + "'");
                deckS.add(idCarta); //Añade el ID de la carta jugada a la baraja de carta jugadas
                mano.remove(mano.indexOf(idCarta));//Remueve la carta de la baraja del jugador
                if (mano.size() == 1) {//Si el jugador gana
                    Win = player;
                }
                int x = (traductorColor(entrada.next()));
                UCarta = x;
                System.out.println("\033[30m" + player + " Cambia el color a " + ce[Math.abs(UCarta)]);
                new Scanner(System.in).nextLine();
                return mano;

            } else if (color == cmano) {
                if (nmano == 'D') {
                    NDraw = 2;
                }

                carta = traductor(cmano, nmano);
                ucarta = traductor(color, numero);
                System.out.println("\033[30m" + player + " juega la carta '" + carta + "\033[30m" + "' sobre la carta '" + ucarta + "\033[30m" + "'");
                deckS.add(idCarta);
                mano.remove(mano.indexOf(idCarta));
                if (mano.size() == 1) {
                    Win = player;
                }
                UCarta = idCarta;

                if (nmano == 'R') {
                    int orden = traductorN(player);
                    if (Se == 1) {
                        switch (orden) {
                            case 0:
                                reverse[0] = 3;
                                reverse[1] = 2;
                                reverse[2] = 1;
                                reverse[3] = 0;
                                break;
                            case 1:
                                reverse[0] = 0;
                                reverse[1] = 3;
                                reverse[2] = 2;
                                reverse[3] = 1;
                                break;
                            case 2:
                                reverse[0] = 1;
                                reverse[1] = 0;
                                reverse[2] = 3;
                                reverse[3] = 2;
                                break;
                            case 3:
                                reverse[0] = 2;
                                reverse[1] = 1;
                                reverse[2] = 0;
                                reverse[3] = 3;
                                break;
                        }
                        cambio = true;
                        Se = -1;
                        System.out.println("\033[30m" + player + " cambio la direccion del juego");

                    } else {
                        switch (orden) {
                            case 0:
                                reverse[0] = 3;
                                reverse[1] = 0;
                                reverse[2] = 1;
                                reverse[3] = 2;
                                break;
                            case 1:
                                reverse[0] = 2;
                                reverse[1] = 3;
                                reverse[2] = 0;
                                reverse[3] = 1;
                                break;
                            case 2:
                                reverse[0] = 1;
                                reverse[1] = 2;
                                reverse[2] = 3;
                                reverse[3] = 0;
                                break;
                            case 3:
                                reverse[0] = 0;
                                reverse[1] = 1;
                                reverse[2] = 2;
                                reverse[3] = 3;
                                break;
                        }
                        cambio = true;
                        Se = 1;
                        System.out.println("\033[30m" + player + " cambio la direccion del juego");
                    }

                }

                if (nmano == 'S') {
                    if (Se == 1) {
                        int orden = traductorN(player);
                        switch (orden) {
                            case 0:
                                NDraw = 0;
                                System.out.println("\033[30m" + "PC1 pierde turno");
                                break;
                            case 1:
                                NDraw = 0;
                                System.out.println("\033[30m" + "PC2 pierde turno");
                                break;
                            case 2:
                                NDraw = 0;
                                System.out.println("\033[30m" + "PC3 pierde turno");
                                break;
                            case 3:
                                NDraw = 0;
                                System.out.println("\033[30m" + nick + " pierde turno");
                                break;
                        }
                        System.out.println("");
                    } else {
                        int orden = traductorN(player);
                        switch (orden) {
                            case 0:
                                NDraw = 0;
                                System.out.println("\033[30m" + "PC3 pierde turno");
                                break;
                            case 1:
                                NDraw = 0;
                                System.out.println("\033[30m" + nick + " pierde turno");
                                break;
                            case 2:
                                NDraw = 0;
                                System.out.println("\033[30m" + "PC1 pierde turno");
                                break;
                            case 3:
                                NDraw = 0;
                                System.out.println("\033[30m" + "PC2 pierde turno");
                                break;
                        }
                    }
                }

                new Scanner(System.in).nextLine();
                return mano;

            } else if (numero == nmano) {
                if (nmano == 'D') {
                    NDraw = 2;
                }

                carta = traductor(cmano, nmano);
                ucarta = traductor(color, numero);
                System.out.println("\033[30m" + player + " juega la carta '" + carta + "\033[30m" + "' sobre la carta '" + ucarta + "\033[30m" + "'");
                deckS.add(idCarta);
                mano.remove(mano.indexOf(idCarta));
                if (mano.size() == 1) {
                    Win = player;
                }
                UCarta = idCarta;
                if (nmano == 'S') {
                    int orden = traductorN(player);
                    switch (orden) {
                        case 0:
                            if (reverse[1] == 0) {
                                juega = false;
                            }
                            ;
                            P1 = -1;
                            System.out.println("\033[30m" + "PC1 pierde turno");
                            break;
                        case 1:
                            if (reverse[2] == 0) {
                                juega = false;
                            }
                            ;
                            P2 = -1;
                            System.out.println("\033[30m" + "PC2 pierde turno");
                            break;
                        case 2:
                            if (reverse[3] == 0) {
                                juega = false;
                            }
                            ;
                            P3 = -1;
                            System.out.println("\033[30m" + "PC3 pierde turno");
                            break;
                        case 3:
                            if (reverse[0] == 0) {
                                juega = false;
                            }
                            ;
                            Op = -1;
                            System.out.println("\033[30m" + nick + " pierde turno");
                            break;
                    }
                    System.out.println("");
                }

                if (nmano == 'R') {
                    int orden = traductorN(player);
                    if (Se == 1) {
                        switch (orden) {
                            case 0:
                                reverse[0] = 3;
                                reverse[1] = 2;
                                reverse[2] = 1;
                                reverse[3] = 0;
                                break;
                            case 1:
                                reverse[0] = 0;
                                reverse[1] = 3;
                                reverse[2] = 2;
                                reverse[3] = 1;
                                break;
                            case 2:
                                reverse[0] = 1;
                                reverse[1] = 0;
                                reverse[2] = 3;
                                reverse[3] = 2;
                                break;
                            case 3:
                                reverse[0] = 2;
                                reverse[1] = 1;
                                reverse[2] = 0;
                                reverse[3] = 3;
                                break;
                        }
                        cambio = true;
                        Se = -1;
                        System.out.println("\033[30m" + player + " cambio la direccion del juego");

                    } else {
                        switch (orden) {
                            case 0:
                                reverse[0] = 3;
                                reverse[1] = 0;
                                reverse[2] = 1;
                                reverse[3] = 2;
                                break;
                            case 1:
                                reverse[0] = 2;
                                reverse[1] = 3;
                                reverse[2] = 0;
                                reverse[3] = 1;
                                break;
                            case 2:
                                reverse[0] = 1;
                                reverse[1] = 2;
                                reverse[2] = 3;
                                reverse[3] = 0;
                                break;
                            case 3:
                                reverse[0] = 0;
                                reverse[1] = 1;
                                reverse[2] = 2;
                                reverse[3] = 3;
                                break;
                        }
                        cambio = true;
                        Se = 1;
                        System.out.println("\033[30m" + player + " cambio la direccion del juego");
                    }

                }

                new Scanner(System.in).nextLine();
                return mano;
            }

            System.out.println("\033[30m" + player + " roba carta por intentar jugar una carta no valida");
            System.out.println("");
            mano = draw(mano, player);
        }

        //return mano;
    }
}