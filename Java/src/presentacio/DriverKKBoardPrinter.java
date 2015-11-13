package presentacio;

import dades.KKDB;
import dades.Table;
import domini.Basic.Cell;
import domini.Basic.Driver;
import domini.KKBoard;
import domini.KKRegion.KKRegion;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Allows to print a KKBoard and view some info
 */
public class DriverKKBoardPrinter implements Driver {

    private PrintStream out = System.out;

    private KKBoard board;


    public DriverKKBoardPrinter() {
    }

    public DriverKKBoardPrinter(KKBoard board) {
        this.board = board;
    }

    public DriverKKBoardPrinter(KKBoard board, PrintStream out) {
        this.board = board;
        this.out = out;
    }

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
            int index = in.nextInt();
            if (index < 0 || index >= boards.size()) {
                out.println("El valor introduit no és correcte");
                return;
            }
            DriverKKBoardPrinter driver = new DriverKKBoardPrinter(boards.get(index), out);
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

        int sizeV = 2 * size + 1;
        int sizeH = 3 * size + 1;
        char[][] outC = new char[sizeV][sizeH];

        // Fill all with white lines
        for (int i = 0; i < sizeV; ++i) Arrays.fill(outC[i], ' ');

        // Create borders
        outC[0][0] = outC[0][sizeH - 1] = outC[sizeV - 1][0] = outC[sizeV - 1][sizeH - 1] = '+';
        for (int i = 1; i < sizeV - 1; ++i) {
            outC[i][0] = '|';
            outC[i][sizeH - 1] = '|';
        }
        for (int i = 1; i < sizeH - 1; ++i) {
            outC[0][i] = '-';
            outC[sizeV - 1][i] = '-';
        }

        // Fill with numbers and region separators
        for (int i = 0; i < size; ++i) {
            // Horizontal lines separators
            if (i != 0) {
                for (int j = 0; j < size; ++j) {
                    if (board.getCell(i - 1, j).getRegion() != board.getCell(i, j).getRegion()) {
                        outC[2 * i][3 * j + 1] = outC[2 * i][3 * j + 2] = '-';
                    }
                }
            }
            // Vertical line separators and numbers
            for (int j = 0; j < size; ++j) {
                Cell c = board.getCell(i, j);
                if (j != 0 && board.getCell(i, j - 1).getRegion() != c.getRegion()) {
                    outC[2 * i + 1][3 * j] = '|';
                    if (outC[2 * i + 2][3 * j] == ' ') outC[2 * i + 2][3 * j] = '|';
                }

                // Numbers check first and second digits
                outC[2 * i + 1][3 * j + 1] = c.getValue() < 10 ? ' ' : (char) ((c.getValue() / 10) + '0');
                outC[2 * i + 1][3 * j + 2] = c.getValue() == 0 ? ' ' : (char) (c.getValue() % 10 + '0');
            }
        }

        // Replace some separators with '+' and '-'
        for (int i = 1; i < sizeV - 1; ++i) {
            for (int j = 1; j < sizeH - 1; ++j) {
                if (outC[i][j] == ' ' && outC[i][j - 1] == '-' && outC[i][j + 1] == '-') outC[i][j] = '-';
                if ((outC[i - 1][j] == '|' || outC[i + 1][j] == '|') &&
                        (outC[i][j - 1] == '-' || outC[i][j + 1] == '-')) {
                    outC[i][j] = '+';
                }
            }
        }

        char[][] outC2 = new char[sizeV][sizeH];
        for (int i = 0; i < sizeV; ++i) {
            System.arraycopy(outC[i], 0, outC2[i], 0, sizeH);
        }

        // Print regions
        ArrayList<KKRegion> regions = board.get_kkregions();

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Cell c = board.getCell(i, j);
                KKRegion reg = (KKRegion) c.getRegion();
                int number = regions.indexOf(reg);

                outC2[2 * i + 1][3 * j + 1] = number < 10 ? ' ' : (char) ((number / 10) + '0');
                outC2[2 * i + 1][3 * j + 2] = (char) ((number % 10) + '0');
            }
        }

        // Print the final matrix with all the region data
        for (int i = 0; i < sizeV; ++i) {
            for (int j = 0; j < sizeH; ++j) out.print(outC[i][j]);
            out.print("  ");
            for (int j = 0; j < sizeH; ++j) out.print(outC2[i][j]);
            out.println();
        }

        out.println();
        out.println("Valors de les regions");
        ArrayList<String> printRegions = new ArrayList<>(regions.size());
        int maxSize = 0;
        for (int i = 0; i < regions.size(); ++i) {
            KKRegion r = regions.get(i);
            String op = r.getOperation().toString();
            String opV = Integer.toString(r.getOperationValue());
            String temp = (i < 10 ? " " : "") + Integer.toString(i) + ": " + op + " => " + opV;
            printRegions.add(temp);
            if (temp.length() > maxSize) maxSize = temp.length();
        }

        boolean odd = regions.size() % 2 != 0;
        int midSize = regions.size() / 2;
        for (int i = 0; i < midSize; ++i) {
            String spaces = "";
            int sizeS = printRegions.get(i).length();
            for (int j = 0; j < maxSize - sizeS; ++j) spaces += " ";
            out.println(printRegions.get(i) + spaces + " | " + printRegions.get(odd ? i + midSize + 1 : i + midSize));
        }
        if (odd) out.println(printRegions.get(midSize));
    }

    public static void printRegion(KKRegion region, PrintStream out) {
        out.println("L'operació d'aquesta regió és " + region.getOperation().toString());
        out.println("El valor d'aquesta operació és " + Integer.toString(region.getOperationValue()));

        for (int i = 0; i < region.size(); ++i) {
            Cell c = region.getCell(i);
            out.println("Cel·la " + Integer.toString(i) + " Valor: " + Integer.toString(c.getValue()));
        }
    }

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
                    break;
                }
                case 3: {
                    return;
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
