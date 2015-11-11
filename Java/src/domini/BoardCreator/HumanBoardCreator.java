package domini.BoardCreator;

import dades.Table;
import domini.Basic.Cell;
import domini.KKBoard;

import java.util.ArrayList;

/**
 * Created by arnau_000 on 05/11/2015.
 */
public class HumanBoardCreator extends BoardCreator {

    public HumanBoardCreator(int size, Table<KKBoard> tableBKK){
        super(size, tableBKK);
    }

    public boolean loadBoard(String name){
/*        for (KKBoard b : mTableKKB){
            if (b.getName() == name){
                mBoard = b;
                return true;
            }
        }*/
        return false;
    }

    private ArrayList<ArrayList<Cell>> mCellMatrix;
}
