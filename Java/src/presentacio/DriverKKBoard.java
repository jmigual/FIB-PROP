package presentacio;

import dades.KKDB;
import dades.Table;
import domini.Basic.Cell;
import domini.KKBoard;
import domini.KKRegion.KKRegion;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ets19 on 5/11/2015.
 */
public class DriverKKBoard {

    public static void main(String[] args) {
        KKDB db = new KKDB();
        db.load();
        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);
        KKBoard b;
        Table<KKBoard> taula = db.getBoards();
        if (taula.size()!=0)out.print("Vols carregar un tauler de la DB? (y/n): ");
        if (taula.size()!=0 && in.next().equals("y")){
            out.println("Hi han aquestes: ");
            for (int i=0; i<taula.size(); i++){
                out.println(Integer.toString(i) + ": de tamany" + Integer.toString(taula.get(i).getSize()));
            }
            out.println("Quina taula vols? ");
            b= taula.get(in.nextInt());

        }
        else {
            out.print("Introdueix el tamany: ");
            b = new KKBoard(in.nextInt());


            ArrayList<Cell> C = new ArrayList<Cell>(b.getSize() * b.getSize());

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
            if (in.next().equals("y")){
                Table<KKBoard> aux = db.getBoards();
                aux.add(b);
                db.setBoards(aux);
            }
        }
      /*  C.add(b.getCell(0, 0));
        C.add(b.getCell(0, 1));
        b.createRegion(C, KKRegion.OperationType.ADDITION, 7);
        C.clear();
        C.add(b.getCell(0, 2));
        C.add(b.getCell(0, 3));
        b.createRegion(C, KKRegion.OperationType.DIVISION, 2);
        C.clear();
        C.add(b.getCell(1, 0));
        C.add(b.getCell(1, 1));
        b.createRegion(C, KKRegion.OperationType.PRODUCT, 4);
        C.clear();
        C.add(b.getCell(1, 2));
        C.add(b.getCell(2, 2));
        b.createRegion(C, KKRegion.OperationType.PRODUCT, 12);
        C.clear();
        C.add(b.getCell(1, 3));
        C.add(b.getCell(2, 3));
        b.createRegion(C, KKRegion.OperationType.SUBTRACTION, 1);
        C.clear();
        C.add(b.getCell(2, 0));
        C.add(b.getCell(3, 0));
        b.createRegion(C, KKRegion.OperationType.PRODUCT, 6);
        C.clear();
        C.add(b.getCell(2, 1));
        C.add(b.getCell(3, 1));
        b.createRegion(C, KKRegion.OperationType.DIVISION, 2);
        C.clear();
        C.add(b.getCell(3, 2));
        C.add(b.getCell(3, 3));
        b.createRegion(C, KKRegion.OperationType.SUBTRACTION, 3);*/

        //b.solve();

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
        boolean keepAsking = true;
        while (keepAsking) {
            out.println();
            out.println("What do you wish to do?");
            out.println("1: Solve the board");
            out.println("2: Print the board");
            if (in.hasNextInt()) {
                switch (in.nextInt()) {
                    case 1:
                        b.solve();
                        break;
                    case 2:
                        for (int i = 0; i < b.getSize(); i++) {
                            for (Cell c : b.getRows().get(i).getCells()) out.print(c.getValue());
                            out.println(' ');
                        }
                        break;

                }
            } else keepAsking = false;
        }
    }
}

