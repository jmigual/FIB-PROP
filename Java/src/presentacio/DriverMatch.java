package presentacio;

import dades.KKDB;
import dades.PlayersAdmin;
import dades.Table;
import domini.Basic.Cell;
import domini.Basic.Match;
import domini.KKBoard;

import java.io.PrintStream;
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
        Match m;
        DriverMatch dm;
        Table<Match> taula = db.getMatches();

        //LOGIN USER
        out.print("Nom de l'usuari actual: ");
        String user = in.next();

        boolean finish = false;

        //Donar valor a m
        while (!finish) {
            out.println("1) Carregar un match inacabat de la DB");
            out.println("2) Començar nou Match amb un Board de la DB");
            out.println("3) Sortir");

            switch (in.nextInt()) {
                case 1:
                    if (taula.size() > 0) {
                        out.println("Hi ha aquests: ");

                        String s;
                        KKBoard b;
                        String p;

                        for (int i = 0; i < taula.size(); i++) {
                            s = "No acabat";
                            if (taula.get(i).hasFinished()) s = "Acabat";

                            b = taula.get(i).getBoard();
                            p = taula.get(i).getPlayer();

                            out.println(Integer.toString(i) + " Board " + /*b.getName +*/  " Player " + p + "  " + s);
                        }
                        out.println("Quin match vols reanudar? ");

                        int n = in.nextInt();

                        if (n > -1 && n < taula.size()) {
                            if (!taula.get(n).hasFinished() && taula.get(n).getPlayer().equals(user)) {
                                m = taula.get(n);
                                dm = new DriverMatch(m);
                                dm.run();

                            } else out.println("Aquest Match ja ha acabat o no es teu");
                        }

                    } else out.println("No hi ha cap Match guardat");

                    break;

                //Comen�ar nou match
                case 2:
                    Table<KKBoard> taulaB = db.getBoards();
                    PlayersAdmin pa = db.getPlayersAdmin();

                    if (taulaB.size() > 0) {
                        out.println("Hi ha aquests taulers: ");
                        for (int i = 0; i < taulaB.size(); i++) {
                            out.println(Integer.toString(i) + /*getName*/ ": de tamany " + Integer.toString(taulaB.get(i).getSize()));
                        }
                        out.println("Quin tauler vols? (si no vols cap posa -1)");

                        int aux = in.nextInt();

                        if (aux > -1 && aux < taulaB.size()) {
                            m = new Match(taulaB.get(aux), user);
                            taula.add(m);

                            dm = new DriverMatch(m);
                            dm.run();
                        }
                    } else out.println("No hi ha taulers a la Base de Dades");

                    break;
                case 3:
                    finish = true;
                    break;
            }
        }
        db.save();
    }

    public void run() {

        // KKBoard sol = m.getBoard().getSolution();
        PrintStream out = System.out;
        DriverKKBoardPrinter KKp = new DriverKKBoardPrinter(_match.getBoard(), out);
        Scanner in = new Scanner(System.in);

        boolean finish = false;

        while (!finish) {
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
                    out.println("Escriu la fila i columna de la cel�la a modificar seguit del valor desitjat:");
                    _match.makeMove(in.nextInt(), in.nextInt(), in.nextInt());
                    break;
                case 2:
                    if (!_match.back()) out.println("No hi ha moviments a revertir!");
                    break;
                case 3:
                    if (!_match.forward()) out.println("No hi ha moviments a refer!");
                    break;
                case 4:
                    out.println("1. Afegir anotacio");
                    out.println("2. Eliminar anotacio");
                    out.println("3. Veure anotacions d'una cel·la");

                    switch (in.nextInt()) {
                        case 1:
                            out.println("Introdueix fila, columna de cel·la i numero de l'anotacio");
                            _match.addAnnotation(in.nextInt(), in.nextInt(), in.nextInt(), true);
                            break;
                        case 2:
                            out.println("Introdueix fila, columna de cel·la i numero de l'anotacio");
                            _match.addAnnotation(in.nextInt(), in.nextInt(), in.nextInt(), false);
                            break;
                        case 3:
                            out.println("Introdueix fila, columna de cel·la");
                            Cell cell = _match.getBoard().getCell(in.nextInt(), in.nextInt());

                            for (int it = 0; it < _match.getBoard().getSize(); ++it)
                                out.print(cell.getAnnotation(it + 1) + " ");
                            out.println("");
                            break;
                    }
                    break;
                case 5:
                    //MENU HINTS
                case 6:
                    finish = true;
                    break;
            }
        }
    }
}
