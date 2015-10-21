package domini.KKRegion;

import domini.Basic.Cell;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionProduct extends KKRegion {

    KKRegionProduct(int size,int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.PRODUCT;
    }

    public KKRegionProduct(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.PRODUCT;
    }

    @Override
    public void calculatePossibilities() {

    }
}
