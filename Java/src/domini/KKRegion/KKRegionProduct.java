package domini.KKRegion;

import domini.Basic.Cell;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionProduct extends KKRegion implements Serializable{

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

    @Override
    public boolean isCorrect() {
        float prod = operationValue;
        int count = cells.size() - 1;
        for (Cell b : cells)
            if (b.getValue() != 0) {
                prod /= (float)b.getValue();
                count--;
            }
        if (count==-1)return prod==1;
        if (prod%1!=0) return false;
        int min = (int) Math.ceil(prod / Math.pow(maxValue, count));
        for (int i = min; i <= maxValue; i++)if(prod % i == 0) return true;
        return false;
    }
}
