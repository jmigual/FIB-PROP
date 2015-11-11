package domini.KKRegion;

import domini.Basic.Cell;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * KKRegion with the substraction operation
 */
public class KKRegionSubtraction extends KKRegion implements Serializable{

    /**
     * Constructor with size
     * @param size KKRegion's size
     * @param maxCellValue Maximum cell value
     * @param value KKRegion's operation value
     */
    public KKRegionSubtraction(int size, int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.SUBTRACTION;
    }

    /**
     * Constructor with default cells Array mdoe
     * @param cells Cells contained in the region
     * @param maxCellValue Maximum cell value
     * @param value KKRegion's operation value
     */
    public KKRegionSubtraction(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.SUBTRACTION;
    }

    /**
     * Constructor with default cells
     * @param cells Cells contained in the region
     * @param maxCellValue Maximum cell value
     * @param value KKRegion's operation value
     */
    public KKRegionSubtraction(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.SUBTRACTION;
    }

    /**
     * Calculates all the possibilities with the substraction restriction
     */
    @Override
    public void calculatePossibilities() {
        for (int i = 0; i < maxValue; ++i) possibilities[i] = false;

        boolean isEmpty=true;
        for (Cell b: cells){
            if (b.getValue()>0){
                isEmpty=false;
                if (b.getValue()-operationValue>0)possibilities[b.getValue()-operationValue-1]=true;
                if (b.getValue()+operationValue<=possibilities.length)possibilities[b.getValue()+operationValue-1]=true;
            }
        }

        if (isEmpty)for (int i = 1; i <= maxValue - operationValue; ++i) {
            possibilities[i - 1] = possibilities[i + operationValue - 1] = true;
        }
    }

    /**
     * Checks if all the contained cells respect the division restriction
     * @return True if all the cells values are correct
     */
    @Override
    public boolean isCorrect() {
        boolean isEmpty=true;
        boolean isFull=true;
        boolean ret=false;
        for (Cell b: cells){
            if (b.getValue()>0){
                isEmpty=false;
                if (b.getValue()-operationValue>0)ret=true;
                if (b.getValue()+operationValue<=possibilities.length)ret=true;
            }
            else isFull=false;
        }
        if (isEmpty)return true;
        if (!isFull)return ret;
        return (Math.abs(cells.get(0).getValue()-cells.get(1).getValue())==operationValue);
    }
}
