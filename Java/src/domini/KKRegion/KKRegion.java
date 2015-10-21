package domini.KKRegion;

import domini.Basic.Cell;
import domini.Basic.Region;

/**
 * Region from a Ken-Ken board
 */
public abstract class KKRegion extends Region {

    public enum OperationType {
        ADDITION(0),
        SUBSTRACTION(1),
        PRODUCT(2),
        DIVISION(3),
        NONE(4);

        int type;

        OperationType(int t) { this.type = t; }
    }

    protected OperationType opType = OperationType.NONE;

    public KKRegion(int size) { super(size); }

    public KKRegion(Cell[] cells) { super(cells); }
    
}
