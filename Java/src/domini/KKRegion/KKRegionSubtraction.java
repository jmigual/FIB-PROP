package domini.KKRegion;

import domini.Basic.Cell;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionSubtraction extends KKRegion {

    public KKRegionSubtraction(int size, int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.SUBTRACTION;
    }

    public KKRegionSubtraction(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.SUBTRACTION;
    }

    @Override
    public void calculatePossibilities() {
        for (int i = 0; i < maxValue; ++i) possibilities[i] = false;

        for (int i = 1; i <= maxValue - operationValue; ++i) {
            possibilities[i - 1] = possibilities[i + operationValue - 1] = true;
        }

        for (int i = 0; i < cells.length; ++i) {
            if (cells[i].getValue() > 0) possibilities[cells[i].getValue() - 1] = false;
        }
    }
}
