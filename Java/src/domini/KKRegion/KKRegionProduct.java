package domini.KKRegion;

import domini.Basic.Cell;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * KKRegion containing a Product operation
 */
public class KKRegionProduct extends KKRegion implements Serializable {

    /**
     * Constructor with size
     *
     * @param size         KKRegion's size
     * @param maxCellValue Maximum cell value
     * @param value        KKRegion's operation value
     */
    public KKRegionProduct(int size, int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.PRODUCT;
    }

    /**
     * Constructor with default cells Array mdoe
     *
     * @param cells        Cells contained in the region
     * @param maxCellValue Maximum cell value
     * @param value        KKRegion's operation value
     */
    public KKRegionProduct(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.PRODUCT;
    }

    /**
     * Constructor with default cells
     *
     * @param cells        Cells contained in the region
     * @param maxCellValue Maximum cell value
     * @param value        KKRegion's operation value
     */
    public KKRegionProduct(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.PRODUCT;
    }

    /**
     * Calculates all the possibilities in the region
     */
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

    /**
     * Checks if the region has correct values
     *
     * @return True if the region values are available
     */
    @Override
    public boolean isCorrect() {
        float prod = operationValue;
        int count = cells.size() - 1;
        for (Cell b : cells)
            if (b.getValue() != 0) {
                prod /= (float) b.getValue();
                count--;
            }
        if (count == -1) return prod == 1;
        if (prod % 1 != 0) return false;
        int min = (int) Math.ceil(prod / Math.pow(maxValue, count));
        for (int i = min; i <= maxValue; i++) if (prod % i == 0) return true;
        return false;
    }
}
