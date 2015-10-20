package presentacio;

import domini.Cell;
import domini.CellLine.Column;
import domini.Region;
import domini.CellLine.Row;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by Joan on 19/10/2015.
 */
public class DriverCell {
    private static Cell cell;
    private static Row row;
    private static Column column;
    private static Region region;

    public static void main(String[] args) {
        row = new Row(9);
        column = new Column(9);
        region = new Region();
        cell = new Cell(9, region, column, row);

        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);

        boolean keepAsking = true;

        while (keepAsking) {
            out.println();
            out.println("What do you wish to do?");
            out.println("1: Enter a new value");
            out.println("2: Get current value");
            out.println("3: Enter a possibility");
            out.println("4: Get a possibility");
            out.println("5: Enter an annotation");
            out.println("6: Get an annotation");
            if (in.hasNextInt()) {
                int modifiedValue;
                switch (in.nextInt()) {
                    case 1:
                        out.println("Enter the value");
                        cell.setValue(in.nextInt());
                        break;
                    case 2:
                        if (cell.getValue() == 0) out.println("There is no value assigned to the cell");
                        else out.println("The value is " + Integer.toString(cell.getValue()));
                        break;
                    case 3:
                        out.println("For which value do you wish to modify the possibility?");
                        modifiedValue = in.nextInt();
                        out.print("Is it possible that there is a " + Integer.toString(modifiedValue) + "?");
                        cell.setPossibility(modifiedValue, in.nextBoolean());
                        break;
                    case 4:
                        out.println("Which value do you want to get the possibility of?");
                        int consultedValue = in.nextInt();
                        if (cell.getPossibility(consultedValue))
                            out.println("It's possible for " + Integer.toString(consultedValue) + " to be in the cell");
                        else
                            out.println("It's impossible for " + Integer.toString(consultedValue) + " to be in the cell");
                        break;
                    case 5:
                        out.print("For which value do you wish to change the annotation: ");
                        modifiedValue = in.nextInt();
                        out.print("Do you want to switch it or set it (switch/set): ");
                        if (in.next().contains("switch")) cell.switchAnnotation(modifiedValue);
                        else {
                            out.print("To what do you want to set?: ");
                            cell.setAnnotation(modifiedValue, in.nextBoolean());
                        }
                        break;
                    case 6:
                        out.println("Which value do you want to get the possibility of?");
                        modifiedValue = in.nextInt();
                        out.println("The annotation for " + Integer.toString(modifiedValue) + " is " + Boolean.toString(cell.getAnnotation(modifiedValue)));
                        break;
                }
            } else keepAsking = false;
        }

    }
}
