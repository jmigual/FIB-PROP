package domini.KKRegion;

import domini.Basic.Cell;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionDivision extends KKRegion{

    public KKRegionDivision(int size,int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.DIVISION;
    }

    public KKRegionDivision(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.DIVISION;
    }

    @Override
    public void calculatePossibilities() {

    }
}
