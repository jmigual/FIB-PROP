package domini.KKRegion;

import domini.Basic.Cell;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionSubtraction extends KKRegion implements Serializable{

    public KKRegionSubtraction(int size, int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.SUBTRACTION;
    }

    public KKRegionSubtraction(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.SUBTRACTION;
    }

    public KKRegionSubtraction(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.SUBTRACTION;
    }

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
}
