package presentacio;

import domini.Basic.Cell;
import domini.Basic.Column;
import domini.Basic.Region;
import domini.Basic.Row;
import domini.KKRegion.KKRegionAddition;
import domini.KKRegion.KKRegionProduct;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by Inigo on 21/10/2015.
 */
public class DriverRegion {

    public static void main(String[] args) {

        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);



        int size, maxCellValue, result;
        out.print("Introdueix el tamany de la regio: ");
        size = in.nextInt();
        out.print("Introdueix el valor maxim de les celles: ");
        maxCellValue = in.nextInt();
        out.print("Introdueix el valor resultant de la suma : ");
        result = in.nextInt();

        Row row = new Row(maxCellValue, 0);
        Region region = new KKRegionProduct(size,maxCellValue,result);
        Column column = new Column(maxCellValue, 0);

        for (int i = 0; i < size; i++) {
            region.setCell(i, new Cell(size, region, column, row));
        }

        boolean keepAsking = true;
        while (keepAsking) {
            out.println();
            out.println("What do you wish to do?");
            out.println("1: Enter a new cell value");
            out.println("2: Get current cell values");
            out.println("3: Enter a possibility");
            out.println("4: Get all possibilities");
            out.println("5: Calculate possibilities");
            if (in.hasNextInt()) {
                int modifiedCell;
                switch (in.nextInt()) {
                    case 1:
                        out.print("Which cell do you want to set the value of? ");
                        modifiedCell = in.nextInt();
                        out.print("What value do you want to give to cell " + modifiedCell + "? ");
                        region.getCell(modifiedCell).setValue(in.nextInt());
                        break;
                    case 2:
                        for (int i=0; i<size; i++)out.print (Integer.toString(region.getCell(i).getValue())+" ");
                        out.println();
                        break;
                    case 3:
                        out.println("For which value do you wish to modify the possibility?");
                        int modifiedValue = in.nextInt();
                        out.print("Is it possible that there is a " + Integer.toString(modifiedValue) + "?");
                        region.setPossibility(modifiedValue, in.nextBoolean());
                        break;
                    case 4:
                        for (int i=0; i<maxCellValue; i++)out.print (Boolean.toString(region.getPossibility(i+1))+" ");
                        out.println();
                        break;
                    case 5:
                        region.calculatePossibilities();
                        for (int i=0; i<maxCellValue; i++)out.print (Boolean.toString(region.getPossibility(i+1))+" ");
                        out.println();
                        break;

                }
            } else keepAsking = false;
        }
    }
}
