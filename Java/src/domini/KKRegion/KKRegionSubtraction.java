package domini.KKRegion;

import domini.Basic.Cell;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionSubtraction extends KKRegion {

    public KKRegionSubtraction(int size,int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.SUBTRACTION;
    }

    public KKRegionSubtraction(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.SUBTRACTION;
    }

    @Override
    public void calculatePossibilities() {

    }
}
