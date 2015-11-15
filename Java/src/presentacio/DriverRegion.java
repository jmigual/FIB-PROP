package presentacio;

import domini.Basic.Cell;
import domini.Basic.Column;
import domini.Basic.Region;
import domini.Basic.Row;
import domini.KKRegion.KKRegionAddition;
import domini.KKRegion.KKRegionDivision;
import domini.KKRegion.KKRegionProduct;
import domini.KKRegion.KKRegionSubtraction;

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

        out.println("Quin tipus de regio vols crear?");
        out.println("1.Columna/Fila");
        out.println("2.Regio de producte");
        out.println("3.Regio de suma");
        out.println("4.Regio de resta");
        out.println("5.Regio de divisio");
        int regType = in.nextInt();

        Region region = null;
        Row row = null;
        Column column = null;

        if (regType == 1) {
            out.print("Introdueix el tamany de la columna/fila");
            size = in.nextInt();
            maxCellValue = size;
            row = new Row(size, 0);
            region = new Column(size, 0);
            column = new Column(size, 0);
        } else {
            out.print("Introdueix el tamany de la regio: ");
            size = in.nextInt();
            region = new Column(size, 0);
            out.print("Introdueix el valor maxim de les celles: ");
            maxCellValue = in.nextInt();
            row = new Row(maxCellValue, 0);
            column = new Column(maxCellValue, 0);
            out.print("Introdueix el valor resultant de la operacio : ");
            result = in.nextInt();
            switch (regType) {
                case 2:
                    region = new KKRegionProduct(size, maxCellValue, result);
                    break;
                case 3:
                    region = new KKRegionAddition(size, maxCellValue, result);
                    break;
                case 4:
                    region = new KKRegionSubtraction(size, maxCellValue, result);
                    break;
                case 5:
                    region = new KKRegionDivision(size, maxCellValue, result);
                    break;


            }
        }


        for (int i = 0; i < size; i++) {
            region.addCell(i, new Cell(maxCellValue, region, column, row));
        }

        boolean keepAsking = true;
        while (keepAsking) {
            out.println();
            out.println("Que vols fer?");
            out.println("1: Introduir un nou valor");
            out.println("2: Obtenir els valor de les cel·les");
            out.println("3: Posar una possibilitat de la regio");
            out.println("4: Obtenir totes les possibilitats de la regio");
            out.println("5: Calcular les possibilitats de la regio");
            out.println("6: Veure si de moment es correcte");
            out.println("7: Posar una possibilitat d'una cel.la");
            out.println("8: Veure totes les possibilitats d'una cel.la");
            out.println("9: Recalcular les possibilitats de totes les cel·les");
            if (in.hasNextInt()) {
                int modifiedCell, modifiedValue;
                switch (in.nextInt()) {
                    case 1:
                        out.print("A quina cel.la vols introduir el nou valor?");
                        modifiedCell = in.nextInt();
                        out.print("Quin valor li vols donar a la cel.la " + modifiedCell + "? ");
                        region.getCell(modifiedCell).setValue(in.nextInt());
                        break;
                    case 2:
                        for (int i = 0; i < size; i++) out.print(Integer.toString(region.getCell(i).getValue()) + " ");
                        out.println();
                        break;
                    case 3:
                        out.println("De quin valor vols canviar la possibilitat?");
                        modifiedValue = in.nextInt();
                        out.print("Es possible que hi hagi un " + Integer.toString(modifiedValue) + "? (y/n)");
                        region.setPossibility(modifiedValue, "y".equals(in.next()));
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
                        out.print("De quina cel.la vols modificar les possibilitats");
                        modifiedCell = in.nextInt();
                        out.println("De quin valor vols canviar la possibilitat?");
                        modifiedValue = in.nextInt();
                        out.print("Es possible que hi hagi un " + Integer.toString(modifiedValue) + "? (y/n)");
                        region.getCells().get(modifiedCell).setPossibility(modifiedValue, "y".equals(in.next()));
                        break;
                    case 8:
                        out.print("De quina cel.la vols veure les possibilitats? ");
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
