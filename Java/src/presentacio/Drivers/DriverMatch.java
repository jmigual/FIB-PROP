package presentacio.Drivers;

import dades.KKDB;
import dades.Table;
import domini.Basic.Cell;
import domini.Basic.Match;
import domini.KKBoard;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by Marc on 10/11/2015.
 */
public class DriverMatch {
    Match _match;

    public DriverMatch(Match match) {
        _match = match;
    }

    public static void main(String[] args) {
        KKDB db = new KKDB();
        db.load();
        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);
        Match m = null;
        DriverMatch dm;
        Table<Match> taula = db.getMatches();

        //LOGIN USER
        String user;
        if (args.length ==0) {
            out.print("Nom de l'usuari actual: ");
            user = in.next();
        }
        else user = args[0];

        boolean finish = false;

        //Donar valor a m
        while (!finish) {
            out.println("1) Carregar una partida inacabada de la DB");
            out.println("2) Comensar nova partida amb un taulell de la DB");
            out.println("3) Enrere");

            switch (in.nextInt()) {
                case 1:
                    if (taula.size() > 0) {
                        out.println("Hi ha aquestes partides: ");

                        String s;
                        KKBoard b;
                        String p;

                        for (int i = 0; i < taula.size(); i++) {
                                s = "No acabat";
                                if (taula.get(i).hasFinished()) s = "Acabat  Score : " + taula.get(i).getScore();
                                b = taula.get(i).getBoard();
                                p = taula.get(i).getPlayer().getUserName();

                                out.println(Integer.toString(i) + ": Board " + b.getName() + " Player " + p + "  " + s);
                        }
                        out.println("Quin match vols reanudar? ");

                        int n = in.nextInt();

                        if (n > -1 && n < taula.size()) {
                            if (!taula.get(n).hasFinished() && taula.get(n).getPlayer().getUserName().equals(user)) {
                                m = taula.get(n);
                                finish = true;
                            }
                            else out.println("Aquest Match ja ha acabat o no es teu");
                        }

                    } else out.println("No hi ha cap Match guardat");

                    break;

                //Comen�ar nou match
                case 2:
                    Table<KKBoard> taulaB = db.getBoards();

                    if (taulaB.size() > 0) {
                        out.println("Hi ha aquests taulers: ");
                        for (int i = 0; i < taulaB.size(); i++) {
                            out.println(Integer.toString(i) + ": " + taulaB.get(i).getName() + " de tamany " +
                                    Integer.toString(taulaB.get(i).getSize())+ " fet per " + taulaB.get(i).getCreator());
                        }
                        out.println("Quin tauler vols? (si no vols cap posa -1)");

                        int aux = in.nextInt();

                        if (aux > -1 && aux < taulaB.size()) {
                            m = new Match(taulaB.get(aux), user);
                            taula.add(m);
                            finish = true;
                        }
                    } else out.println("No hi ha taulers a la Base de Dades");

                    break;

                case 3:
                    return;
            }
        }

        dm = new DriverMatch(m);
        db.save();
        dm.run();
        db.save();
    }

    public void run() {
        PrintStream out = System.out;
        DriverKKBoardPrinter KKp = new DriverKKBoardPrinter(_match.getBoard(), out);
        Scanner in = new Scanner(System.in);

        boolean finish = false;

        while (!finish) {
            out.println("SCORE: " + _match.getScore());
            KKp.printBoard(_match.getBoard());

            out.println("");
            out.println("Escull:");
            out.println("1. Fer un moviment");
            out.println("2. Tirar enrere l'ultim moviment");
            out.println("3. Refer l'ultim moviment tirat enrere");
            out.println("4. Editar anotacions");
            out.println("5. Demanar un Ajut (Hint)");
            out.println("6. Guardar i sortir");

            switch (in.nextInt()) {
                case 1:
                    out.println("Escriu la fila i columna de la cella a modificar seguit del valor desitjat:");
                    if (!_match.makeMove(in.nextInt(), in.nextInt(), in.nextInt())) out.println("No pots posar aquest valor aqui!");
                    else if (_match.checkFinish()) {
                        out.println("FELICITATS! Has completat aquest taulell amb una puntuacio de " + _match.getScore());
                        finish = true;
                    }
                    break;
                case 2:
                    if (!_match.back()) out.println("No hi ha moviments a revertir!");
                    break;
                case 3:
                    if (!_match.forward()) out.println("No hi ha moviments a refer!");
                    break;
                case 4:
                    //MENU ANNOTATIONS
                    out.println("1. Afegir anotacio");
                    out.println("2. Eliminar anotacio");
                    out.println("3. Veure anotacions d'una cel·la");

                    switch (in.nextInt()) {
                        case 1:
                            out.println("Introdueix fila, columna de cel·la i numero de l'anotacio");
                            _match.addAnnotation(in.nextInt()-1, in.nextInt()-1, in.nextInt(), true);
                            break;
                        case 2:
                            out.println("Introdueix fila, columna de cel·la i numero de l'anotacio");
                            _match.addAnnotation(in.nextInt()-1, in.nextInt()-1, in.nextInt(), false);
                            break;
                        case 3:
                            out.println("Introdueix fila, columna de cel·la");
                            Cell cell = _match.getBoard().getCell(in.nextInt()-1, in.nextInt()-1);

                            for (int it = 0; it < _match.getBoard().getSize(); ++it)
                                out.print(cell.getAnnotation(it + 1) + " ");
                            out.println("");
                            break;
                    }
                    break;
                case 5:
                    //MENU HINTS
                    out.println();
                    out.println("1. Corregir errors");
                    out.println("2. Mostrar numero aleatori");
                    out.println("3. Sortir");

                    ArrayList<Cell> cells;

                    switch (in.nextInt()) {
                        case 1:
                            cells = _match.hint(0);
                            int c, f;
                            out.print("Caselles erronies : ");
                            for (int i = 0; i<cells.size(); ++i) {
                                f = cells.get(i).getRow().getPos()+1;
                                c = cells.get(i).getColumn().getPos()+1;
                                out.print("(" + f + "," + c + ") ");
                            }
                            out.println();
                            break;

                        case 2:
                            cells = _match.hint(1);
                            if (cells == null) out.println ("No queden espais lliures!");
                            else {
                                f = cells.get(0).getRow().getPos() + 1;
                                c = cells.get(0).getColumn().getPos() + 1;
                                out.println("Casella mostrada: (" + f + "," + c + ") ");
                                if (_match.checkFinish()) {
                                    out.println("FELICITATS! Has completat aquest taulell amb una puntuacio de " + _match.getScore());
                                    finish = true;
                                }
                            }
                            break;
                    }
                    break;
                case 6:
                    finish = true;
                    break;
            }
        }
    }
}
