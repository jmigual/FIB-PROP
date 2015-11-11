package presentacio;

import domini.Basic.Cell;
import domini.Basic.Column;
import domini.Basic.Region;
import domini.Basic.Row;

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
        /*out.print("Introdueix el valor maxim de les celles: ");
        maxCellValue = in.nextInt();
        out.print("Introdueix el valor resultant de la suma : ");
        result = in.nextInt();*/
        maxCellValue = size;
        Row row = new Row(maxCellValue, 0);
        //Region region = new KKRegionProduct(size, maxCellValue, result);
        Region region = new Column(size, 0);
        Column column = new Column(maxCellValue, 0);

        for (int i = 0; i < size; i++) {
            region.addCell(i, new Cell(maxCellValue, region, column, row));
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
            out.println("6: It is correct?");
            out.println("7: Set Individual possibility");
            out.println("8: Get Individual possibilities");
            out.println("9: Calculate Individual possibilities");
            if (in.hasNextInt()) {
                int modifiedCell, modifiedValue;
                switch (in.nextInt()) {
                    case 1:
                        out.print("Which cell do you want to set the value of? ");
                        modifiedCell = in.nextInt();
                        out.print("What value do you want to give to cell " + modifiedCell + "? ");
                        region.getCell(modifiedCell).setValue(in.nextInt());
                        break;
                    case 2:
                        for (int i = 0; i < size; i++) out.print(Integer.toString(region.getCell(i).getValue()) + " ");
                        out.println();
                        break;
                    case 3:
                        out.println("For which value do you wish to modify the possibility?");
                        modifiedValue = in.nextInt();
                        out.print("Is it possible that there is a " + Integer.toString(modifiedValue) + "?");
                        region.setPossibility(modifiedValue, in.nextBoolean());
                        break;
                    case 4:
                        for (int i = 0; i < maxCellValue; i++)
                            out.print(Boolean.toString(region.getPossibility(i + 1)) + " ");
                        out.println();
                        break;
                    case 5:
                        region.calculatePossibilities();
                        for (int i = 0; i < maxCellValue; i++)
                            out.print(Boolean.toString(region.getPossibility(i + 1)) + " ");
                        region.getCells().forEach(Cell::calculatePossibilities);
                        out.println();
                        break;
                    case 6:
                        out.println(Boolean.toString(region.isCorrect()));
                        break;
                    case 7:
                        out.print("Which cell do you want to set the possibility of? ");
                        modifiedCell = in.nextInt();
                        out.println("For which value do you wish to modify the possibility?");
                        modifiedValue = in.nextInt();
                        out.print("Is it possible that there is a " + Integer.toString(modifiedValue) + "?");
                        region.getCells().get(modifiedCell).setPossibility(modifiedValue, in.nextBoolean());
                        break;
                    case 8:
                        out.print("Which cell do you want to get the possibilities of? ");
                        modifiedCell = in.nextInt();
                        for (int i = 0; i < maxCellValue; i++)
                            out.print(Boolean.toString(region.getCells().get(modifiedCell).getPossibility(i + 1)) + " ");
                        break;
                    case 9:
                        region.calculateIndividualPossibilities();
                        break;
                }
            } else keepAsking = false;
        }
    }
}
