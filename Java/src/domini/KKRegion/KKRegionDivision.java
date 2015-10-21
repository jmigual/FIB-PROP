package domini.KKRegion;

import domini.Basic.Cell;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionDivision extends KKRegion{

    public KKRegionDivision(int size) {
        super(size);
        this.opType = OperationType.DIVISION;
    }

    public KKRegionDivision(Cell[] cells) {
        super(cells);
        this.opType = OperationType.DIVISION;
    }

    @Override
    public void calculatePossibilities() {

    }
}
