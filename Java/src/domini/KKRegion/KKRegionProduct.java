package domini.KKRegion;

import domini.Basic.Cell;

import java.util.ArrayList;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionProduct extends KKRegion {

    public KKRegionProduct(int size, int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.PRODUCT;
    }

    public KKRegionProduct(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.PRODUCT;
    }

    public KKRegionProduct(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.PRODUCT;
    }

    @Override
    public void calculatePossibilities() {
        int prod = operationValue;
        int count = cells.size() - 1;
        for (Cell b : cells)
            if (b.getValue() != 0) {
                prod /= b.getValue();
                count--;
            }
        int min = (int) Math.ceil(prod / Math.pow(maxValue, count));
        for (int i = 1; i <= maxValue; i++)
            possibilities[i - 1] = (prod % i == 0 && i >= min);
    }
}
