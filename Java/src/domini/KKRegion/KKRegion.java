package domini.KKRegion;

import domini.Basic.Cell;
import domini.Basic.Region;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Region from a Ken-Ken board
 */
public abstract class KKRegion extends Region implements Serializable {

    /**
     * Contains the type of the operation
     */
    protected OperationType opType = OperationType.NONE;
    /**
     * Contains the operation's value
     */
    protected int operationValue;

    /**
     * Constructor with default values
     *
     * @param size         Region's size
     * @param maxCellValue Region's max Cell value
     * @param value        Region's operation value
     */
    public KKRegion(int size, int maxCellValue, int value) {
        super(size, maxCellValue);
        operationValue = value;
    }

    /**
     * Constructor with an existing set of cells
     *
     * @param cells        Cells contained in the region
     * @param maxCellValue Region's max Cell value
     * @param value        Region's operation value
     */
    public KKRegion(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue);
        operationValue = value;
    }

    /**
     * Constructor with an existing collection of cells
     *
     * @param cells        Cells contained in the region
     * @param maxCellValue Region's max Cell value
     * @param value        Region's operation value
     */
    public KKRegion(Cell[] cells, int maxCellValue, int value) {
        super(cells.length, maxCellValue);
        for (int i = 0; i < cells.length; i++) {
            this.cells.set(i, cells[i]);
        }
        operationValue = value;
    }

    /**
     * To check if two regions are the same
     *
     * @param o Object to check if they are the same object
     * @return True if 'o' is equal to the current element
     */
    @Override
    public boolean equals(Object o) {
        return this==o;
    }

    /**
     * To get a hashCode, necessary to the operations with sets
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = opType.hashCode();
        result = 31 * result + operationValue;
        return result;
    }

    /**
     * Returns the Region's operation type
     *
     * @return OperationTyp object containing the current operation
     */
    public OperationType getOperation() {
        return opType;
    }

    /**
     * To get the Region's operation value
     *
     * @return int containing the Region's operation value
     */
    public int getOperationValue() {
        return this.operationValue;
    }

    /**
     * Enum containing all types of operation available on a KKRegion
     */
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

        @Override
        public String toString() {
            String op = " ";
            switch(this) {
                case ADDITION:
                    op = "+";
                    break;
                case SUBTRACTION:
                    op = "-";
                    break;
                case PRODUCT:
                    op = "*";
                    break;
                case DIVISION:
                    op = "/";
                    break;
                case NONE:
                    break;
                default:
                    break;
            }
            return op;
        }
    }


}
