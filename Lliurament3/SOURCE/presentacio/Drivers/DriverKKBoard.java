package presentacio.Drivers;

import dades.KKDB;
import dades.Table;
import domini.Basic.Cell;
import domini.Basic.Driver;
import domini.KKBoard;
import domini.KKRegion.KKRegion;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ets19 on 5/11/2015.
 */
public class DriverKKBoard implements Driver {

    private KKBoard b;

    public DriverKKBoard(KKBoard b) {
        this.b = b;
    }

    public static void main(String[] args) {
        KKDB db = new KKDB();
        db.load();
        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);
        KKBoard b;
        Table<KKBoard> taula = db.getBoards();
        if (taula.size() != 0) out.print("Vols carregar un tauler de la DB? (y/n): ");
        if (taula.size() != 0 && "y".equals(in.next())) {
            out.println("Hi han aquestes: ");
            for (int i = 0; i < taula.size(); i++) {
                out.println(Integer.toString(i) + ": " + taula.get(i).getName() + " de tamany " + Integer.toString(taula.get(i).getSize()));
            }
            out.println("Quina taula vols? ");
            b = taula.get(in.nextInt());

        } else {
            out.print("Introdueix el tamany: ");
            b = new KKBoard(in.nextInt());

            out.print("Introdueix el nom del taulell: ");
            b.setName(in.next());

            ArrayList<Cell> C = new ArrayList<>(b.getSize() * b.getSize());

            out.print("Introdueix el nombre de regions: ");
            int nregions = in.nextInt();
            for (int i = 0; i < nregions; i++) {
                C.clear();
                out.print("Introdueix la operacio (*,+,/,-): ");
                char op = in.next().charAt(0);
                out.print("Introdueix el resultat: ");
                int opValue = in.nextInt();
                out.print("Introdueix el tamany de la regio: ");
                int sizeRegion = in.nextInt();
                for (int j = 0; j < sizeRegion; j++) {
                    out.print("Introdueix la posicio de la cella: ");
                    int x = in.nextInt();
                    int y = in.nextInt();
                    C.add(b.getCell(x, y));
                }
                KKRegion.OperationType KKop = KKRegion.OperationType.NONE;
                switch (op) {
                    case '+':
                        KKop = KKRegion.OperationType.ADDITION;
                        break;
                    case '-':
                        KKop = KKRegion.OperationType.SUBTRACTION;
                        break;
                    case '*':
                        KKop = KKRegion.OperationType.PRODUCT;
                        break;
                    case '/':
                        KKop = KKRegion.OperationType.DIVISION;
                        break;
                    case ' ':
                        KKop = KKRegion.OperationType.NONE;
                        break;
                }
                b.createRegion(C, KKop, opValue);
            }

            out.print("Vols guardar el tauler a la DB? (y/n): ");
            if ("y".equals(in.next())) {
                Table<KKBoard> aux = db.getBoards();
                aux.add(b);
                db.save();
            }
        }

        /*
            2
            2
            +
            3
            2
            0 0
            0 1
            -
            1
            2
            1 0
            1 1

         */
        DriverKKBoard driver = new DriverKKBoard(b);
        driver.run();
    }

    public void run() {
        PrintStream out = System.out;
        DriverKKBoardPrinter KKp = new DriverKKBoardPrinter(b, out);
        Scanner in = new Scanner(System.in);

        boolean keepAsking = true;
        while (keepAsking) {
            out.println();
            out.println("Què vols fer?");
            out.println("1) Solucionar el tauler");
            out.println("2) Imprimir el tauler");
            out.println("3) Tornar");
            out.println("4) Solucionar pas a pas");
            if (in.hasNextInt()) {
                switch (in.nextInt()) {
                    case 1:
                        b.solve();
                        break;
                    case 2:
                        KKp.printBoard();
                        break;
                    case 3:
                        keepAsking = false;
                        break;
                    case 4:
                        System.out.println ("No longer works without KKPrinter");
                        break;
                }
            } else keepAsking = false;
        }
    }
}

