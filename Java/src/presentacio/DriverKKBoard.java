package presentacio;

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
        KKBoard b = new KKBoard(4);

        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);

        ArrayList<Cell> C = new ArrayList<Cell>(4*4);


        C.add(b.getCell(0, 0));
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
        b.createRegion(C, KKRegion.OperationType.SUBTRACTION, 3);

        b.solve();
        for (int i=0;i<4;i++){
            for (Cell c: b.getRows().get(i).getCells())out.print(c.getValue());
            out.println(' ');
        }
        for (boolean bo: b.getCell(0,0).getPossibilities())out.print (bo);


    }
}
