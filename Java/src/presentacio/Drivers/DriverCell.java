package presentacio.Drivers;

import domini.Basic.Cell;
import domini.Basic.Column;
import domini.Basic.Region;
import domini.Basic.Row;
import domini.KKRegion.KKRegionAddition;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by Joan on 19/10/2015.
 */
public class DriverCell {

    public static void main(String[] args) {
        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);
        out.print("Quin es el valor maxim de la cel.la?");
        int size= in.nextInt();
        Row row = new Row(size, 0);
        Column column = new Column(size, 0);
        Region region = new KKRegionAddition(2, size, 12);
        Cell cell = new Cell(size, region, column, row);


        boolean keepAsking = true;

        while (keepAsking) {
            out.println();
            out.println("Que vols fer?");
            out.println("1: Introduir un nou valor");
            out.println("2: Veure el valor actual");
            out.println("3: Introduir una possibilitat");
            out.println("4: Obtenir una possibilitat");
            out.println("5: Introduir una anotacio");
            out.println("6: Obtenir una anotacio");
            if (in.hasNextInt()) {
                int modifiedValue;
                switch (in.nextInt()) {
                    case 1:
                        out.println("Quin es el valor?");
                        cell.setValue(in.nextInt());
                        break;
                    case 2:
                        if (cell.getValue() == 0) out.println("No hi ha cap valor assignat a la cel.la");
                        else out.println("El valor es " + Integer.toString(cell.getValue()));
                        break;
                    case 3:
                        out.println("De quin valor vols canviar la possibilitat?");
                        modifiedValue = in.nextInt();
                        out.print("Es possible que hi hagi un " + Integer.toString(modifiedValue) + "? (y/n)");
                        cell.setPossibility(modifiedValue, "y".equals(in.next()));
                        break;
                    case 4:
                        out.println("De quin valor vols obtenir la possibilitat?");
                        int consultedValue = in.nextInt();
                        if (cell.getPossibility(consultedValue))
                            out.println("Es possible que hi hagi un " + Integer.toString(consultedValue) + " en la cel.la");
                        else
                            out.println("Es impossible que hi hagi un " + Integer.toString(consultedValue) + " en la cel.la");
                        break;
                    case 5:
                        out.print("De quin valor vols canviar l'annotacio: ");
                        modifiedValue = in.nextInt();
                        out.print("Vols alternar el valor o introduir-ne un de nou?: (alt/intr)");
                        if (in.next().contains("alt")) cell.switchAnnotation(modifiedValue);
                        else {
                            out.print("Quin valor li vols donar a l'annotacio?: (cert/fals)");
                            cell.setAnnotation(modifiedValue, "cert".equals(in.next()));
                        }
                        break;
                    case 6:
                        out.println("De quin valor vols consultar l'annotacio?");
                        modifiedValue = in.nextInt();
                        out.println("L'annotacio de la cel.la " + Integer.toString(modifiedValue) + " es " + Boolean.toString(cell.getAnnotation(modifiedValue)));
                        break;
                }
            } else keepAsking = false;
        }

    }
}
