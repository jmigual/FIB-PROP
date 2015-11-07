package domini.KKRegion;

import domini.Basic.Cell;
import domini.Basic.Region;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Region from a Ken-Ken board
 */
public abstract class KKRegion extends Region implements Serializable{

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
     * @param size Region's size
     * @param maxCellValue Region's max Cell value
     * @param value Region's operation value
     */
    public KKRegion(int size, int maxCellValue, int value) {
        super(size, maxCellValue);
        operationValue = value;
    }

    /**
     * Constructor with an existing set of cells
     * @param cells Cells contained in the region
     * @param maxCellValue Region's max Cell value
     * @param value Region's operation value
     */
    public KKRegion(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue);
        operationValue = value;
    }

    /**
     * Constructor with an existing collection of cells
     * @param cells Cells contained in the region
     * @param maxCellValue Region's max Cell value
     * @param value Region's operation value
     */
    public KKRegion(Cell[] cells, int maxCellValue, int value) {
        super(cells.length, maxCellValue);
        for (int i = 0; i < cells.length; i++) {
            this.cells.set(i, cells[i]);
        }
        operationValue = value;
    }

    /**
     * Returns the Region's operation type
     * @return OperationTyp object containing the current operation
     */
    public OperationType getOperation() {
        return opType;
    }

    /**
     * To get the Region's operation value
     * @return int containing the Region's operation value
     */
    public int getOperationValue() { return this.operationValue; }

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
    }

    public void setIndividualPossibilities (Cell c){
        //TODO
    }

}
