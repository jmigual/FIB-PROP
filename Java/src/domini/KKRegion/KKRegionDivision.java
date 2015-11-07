package domini.KKRegion;

import domini.Basic.Cell;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Joan on 21/10/2015.
 */
public class KKRegionDivision extends KKRegion implements Serializable{

    public KKRegionDivision(int size, int maxCellValue, int value) {
        super(size, maxCellValue, value);
        this.opType = OperationType.DIVISION;
    }

    public KKRegionDivision(Cell[] cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.DIVISION;
    }

    public KKRegionDivision(ArrayList<Cell> cells, int maxCellValue, int value) {
        super(cells, maxCellValue, value);
        this.opType = OperationType.DIVISION;
    }

    @Override
    public void calculatePossibilities() {
        boolean isEmpty=true;
        for (Cell b: cells){
            if (b.getValue()>0){
                isEmpty=false;
                if (b.getValue()%operationValue==0)possibilities[b.getValue()/operationValue-1]=true;
                if (b.getValue()*operationValue<=possibilities.length)possibilities[b.getValue()*operationValue-1]=true;
            }
        }

        if (isEmpty)for (int i = 1; i <= maxValue / operationValue; ++i) {
            possibilities[i-1] = possibilities[operationValue * i-1] = true;
        }
    }
}
