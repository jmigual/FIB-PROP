package domini.KKRegion;

import domini.Basic.Cell;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionProduct extends KKRegion {

    KKRegionProduct(int size) {
        super(size);
        this.opType = OperationType.PRODUCT;
    }

    public KKRegionProduct(Cell[] cells) {
        super(cells);
        this.opType = OperationType.PRODUCT;
    }

    @Override
    public void calculatePossibilities() {

    }
}
