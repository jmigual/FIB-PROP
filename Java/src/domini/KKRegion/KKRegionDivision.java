package domini.KKRegion;

import domini.Basic.Cell;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * KKRegion containing a division operation
 */
public class KKRegionDivision extends KKRegion implements Serializable {

    /**
     * Constructor with size
     *
     * @param size         KKRegion's size
     * @param maxCellValue Maximum cell value
     * @param value        KKRegion's operation value
     */
    public KKRegionDivision(int size, int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.DIVISION;
    }

    /**
     * Constructor with default cells Array mdoe
     *
     * @param cells        Cells contained in the region
     * @param maxCellValue Maximum cell value
     * @param value        KKRegion's operation value
     */
    public KKRegionDivision(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.DIVISION;
    }

    /**
     * Constructor with default cells
     *
     * @param cells        Cells contained in the region
     * @param maxCellValue Maximum cell value
     * @param value        KKRegion's operation value
     */
    public KKRegionDivision(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.DIVISION;
    }

    /**
     * Calculates all the possibilities for the division operation
     */
    @Override
    public void calculatePossibilities() {
        for (int i = 0; i < maxValue; ++i) possibilities[i] = false;
        boolean isEmpty = true;
        for (Cell b : cells) {
            if (b.getValue() > 0) {
                isEmpty = false;
                if (b.getValue() % operationValue == 0) possibilities[b.getValue() / operationValue - 1] = true;
                if (b.getValue() * operationValue <= possibilities.length)
                    possibilities[b.getValue() * operationValue - 1] = true;
            }
        }

        if (isEmpty) for (int i = 1; i <= maxValue / operationValue; ++i) {
            possibilities[i - 1] = possibilities[operationValue * i - 1] = true;
        }
    }

    /**
     * Checks if the numbers contained in the region respect the division restriction
     *
     * @return True if the cells' values are correct
     */
    @Override
    public boolean isCorrect() {
        boolean isEmpty = true;
        boolean isFull = true;
        boolean ret = false;
        for (Cell b : cells) {
            if (b.getValue() > 0) {
                isEmpty = false;
                if (b.getValue() % operationValue == 0) ret = true;
                if (b.getValue() * operationValue <= possibilities.length) ret = true;
            } else isFull = false;
        }
        if (isEmpty) return true;
        if (!isFull) return ret;
        return (cells.get(0).getValue() * cells.get(1).getValue()) == operationValue;
    }
}
