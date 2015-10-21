package domini.KKRegion;

import domini.Basic.Cell;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionAddition extends KKRegion {

    public KKRegionAddition(int size) {
        super(size);
        this.opType = OperationType.ADDITION;
    }

    public KKRegionAddition(Cell[] cells) {
        super(cells);
        this.opType = OperationType.ADDITION;
    }

    @Override
    public void calculatePossibilities() {

    }
}
