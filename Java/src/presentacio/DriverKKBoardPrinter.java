package presentacio;

import dades.KKDB;
import dades.Table;
import domini.Basic.Cell;
import domini.Basic.Driver;
import domini.KKBoard;
import domini.KKRegion.KKRegion;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Allows to print a KKBoard and view some info
 */
public class DriverKKBoardPrinter implements Driver {

    private PrintStream out = System.out;

    private KKBoard board;


    public static void main(String args[]) {
        PrintStream out = System.out;
        KKDB db = new KKDB();
        db.load();


        Table<KKBoard> boards = db.getBoards();

        if (boards.size() == 0) {
            out.println("No hi ha taulers per mostrar");
            return;
        }

        out.println("Hi ha " + Integer.toString(boards.size()) + " taulers selecciona el número que vols visualitzar");
        for (int i = 0; i < boards.size(); ++i) {
            out.println(Integer.toString(i) + ") Tamany: " + Integer.toString(boards.get(i).getSize()));
        }

        Scanner in = new Scanner(System.in);

        if (in.hasNextInt()) {
            DriverKKBoardPrinter driver = new DriverKKBoardPrinter(boards.get(in.nextInt()), out);
            driver.run();
        }
    }

    /**
     * Prints the KKBoard to the selected PrintStream
     *
     * @param board KKBoard to be printed
     * @param out   PrintStream where the KKBoard can be printed
     */
    public static void printBoard(KKBoard board, PrintStream out) {
        int size = board.getSize();
        Integer sizeI = size;
        out.println("Tauler de tamany " + sizeI.toString() + "x" + sizeI.toString());

        int sizeV = 2*size + 1;
        int sizeH = 3*size + 1;
        char[][] outC = new char[sizeV][sizeH];

        for (int i = 0; i < sizeV; ++i) {
            outC[i][0] = '|';
            outC[i][sizeH - 1] = '|';
        }
        for (int i = 1; i < sizeH - 1; ++i) {
            outC[0][i] = '-';
            outC[sizeV][i] = '-';
        }

        // Print the final matrix with all the data
        for (int i = 0; i < sizeV; ++i) {
            for (int j = 0; j < sizeH; ++j) {
                out.print(outC[i][j]);
            }
            out.println();
        }
    }

    public static void printRegion(KKRegion region, PrintStream out) {
        out.println("L'operació d'aquesta regió és " + region.getOperation().toString());
        out.println("El valor d'aquesta operació és " + Integer.toString(region.getOperationValue()));

        for (int i = 0; i < region.size(); ++i) {
            Cell c = region.getCell(i);
            out.println("Cel·la " + Integer.toString(i) + " Valor: " + Integer.toString(c.getValue()));
        }
    }

    public DriverKKBoardPrinter() {}

    public DriverKKBoardPrinter(KKBoard board) { this.board = board; }

    public DriverKKBoardPrinter(KKBoard board, PrintStream out) { this.board = board; this.out = out; }

    public void setStream(PrintStream stream) {
        this.out = stream;
    }

    public void setBoard(KKBoard b) {
        this.board = b;
    }

    public void run() {
        if (board == null) {
            out.println("No hi ha cap tauler a mostrar");
            return;
        }

        Scanner in = new Scanner(System.in);

        while (true) {
            out.println("Selecciona una opció:");
            out.println("1) Veure el tauler");
            out.println("2) Veure informació de la regió");
            out.println("3) Tornar");

            if (!in.hasNextInt()) break;

            switch (in.nextInt()) {
                case 1: {
                    printBoard();
                    break;
                }
                case 2: {
                    out.println("Escriu les coordenades d'una cel·la re la regió (fila, columna):");

                    // Get coordinates
                    int i, j;
                    i = in.nextInt();
                    j = in.nextInt();

                    // Print the region
                    printRegion((KKRegion) board.getCell(i, j).getRegion());
                }

            }
        }
    }

    public void printBoard() {
        printBoard(this.board, this.out);
    }

    public void printBoard(KKBoard board) {
        printBoard(board, this.out);
    }

    public void printRegion(KKRegion region) {
        printRegion(region, this.out);
    }
}
