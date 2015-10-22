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
        for (int i = 0; i < maxValue; ++i) possibilities[i] = false;

        for (int i = 1; i <= maxValue/operationValue; ++i) {
            possibilities[i] = possibilities[operationValue*i] = true;
        }

        for (int i = 1; i < cells.length; ++i) {
            if (cells[i].getValue() > 0) possibilities[cells[i].getValue() - 1] = false;
        }
    }
}
