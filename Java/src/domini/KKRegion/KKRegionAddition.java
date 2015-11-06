package domini.KKRegion;

import domini.Basic.Cell;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionAddition extends KKRegion implements Serializable{

    public KKRegionAddition(int size, int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.ADDITION;
    }

    public KKRegionAddition(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.ADDITION;
    }

    public KKRegionAddition(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.ADDITION;
    }

    @Override
    public void calculatePossibilities() {
        int sum = operationValue;
        int count = cells.size() - 1;
        for (Cell b : cells)
            if (b.getValue() != 0) {
                sum -= b.getValue();
                count--;
            }
        int min = Math.max(1, sum - maxValue * count);
        int max = Math.min(maxValue, sum - count);
        for (int i = 1; i <= maxValue; i++) possibilities[i - 1] = (i >= min && i <= max);
    }
}
