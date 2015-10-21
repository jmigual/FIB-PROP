package domini.KKRegion;

import domini.Basic.Cell;
import domini.Basic.Region;

/**
 * Region from a Ken-Ken board
 */
public abstract class KKRegion extends Region {

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

    protected OperationType opType = OperationType.NONE;
    protected int operationValue;

    public KKRegion(int size, int maxCellValue, int value) {
        super(size, maxCellValue);
        operationValue = value;
    }

    public KKRegion(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue);
        operationValue = value;
    }

}
