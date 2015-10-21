package domini.KKRegion;

import domini.Basic.Cell;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionSubstraction extends KKRegion {

    public KKRegionSubstraction(int size) {
        super(size);
        this.opType = OperationType.SUBSTRACTION;
    }

    public KKRegionSubstraction(Cell[] cells) {
        super(cells);
        this.opType = OperationType.SUBSTRACTION;
    }

    @Override
    public void calculatePossibilities() {

    }
}
