package domini.KKRegion;

import domini.Basic.Cell;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * KKRegion with addition operation
 */
public class KKRegionAddition extends KKRegion implements Serializable {

    /**
     * Constructor with size
     *
     * @param size         KKRegion's size
     * @param maxCellValue Maximum cell value
     * @param value        KKRegion's operation value
     */
    public KKRegionAddition(int size, int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.ADDITION;
    }

    /**
     * Constructor with default cells Array mdoe
     *
     * @param cells        Cells contained in the region
     * @param maxCellValue Maximum cell value
     * @param value        KKRegion's operation value
     */
    public KKRegionAddition(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.ADDITION;
    }

    /**
     * Constructor with default cells
     *
     * @param cells        Cells contained in the region
     * @param maxCellValue Maximum cell value
     * @param value        KKRegion's operation value
     */
    public KKRegionAddition(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.ADDITION;
    }

    /**
     * Calculates all the possibilities with the Addition restriction
     */
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

    /**
     * Checks if all the contained cells respect the addition restriction
     *
     * @return True if all the cells' values are correct
     */
    @Override
    public boolean isCorrect() {

        int sum = operationValue;
        int count = cells.size() - 1;
        for (Cell b : cells)
            if (b.getValue() != 0) {
                sum -= b.getValue();
                count--;
            }
        if (sum < 0) return false;
        if (count == -1) return sum == 0;
        int min = Math.max(1, sum - maxValue * count);
        int max = Math.min(maxValue, sum - count);
        return !(min > 9 || max < 0);
    }
}
