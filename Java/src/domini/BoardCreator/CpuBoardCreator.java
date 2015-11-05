package domini.BoardCreator;

import domini.Basic.Region;
import sun.plugin2.gluegen.runtime.CPU;

import java.util.ArrayList;

/**
 * Created by arnau_000 on 05/11/2015.
 */
public class CpuBoardCreator extends BoardCreator {
    int mMaxRegionSize;
    ArrayList<ArrayList<Region>> mRegionShapes;

    public CpuBoardCreator(int size){
        super(size);
    }


}
