package domini.KKRegion;

import domini.Basic.Cell;
import domini.Basic.Region;

import java.util.ArrayList;

/**
 * Region from a Ken-Ken board
 */
public abstract class KKRegion extends Region {

    protected OperationType opType = OperationType.NONE;
    protected int operationValue;

    public KKRegion(int size, int maxCellValue, int value) {
        super(size, maxCellValue);
        operationValue = value;
    }

    public KKRegion(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue);
        operationValue = value;
    }

    public KKRegion(Cell[] cells, int maxCellValue, int value) {
        super(cells.length, maxCellValue);
        for (int i = 0; i < cells.length; i++) {
            this.cells.set(i, cells[i]);
        }
        operationValue = value;
    }

    public OperationType getOperation() {
        return opType;
    }

    public enum OperationType {
        ADDITION(0),
        SUBTRACTION(1),
        PRODUCT(2),
        DIVISION(3),
        NONE(4);

        int type;

        OperationType(int t) {
            this.type = t;
        }
    }

}
