package domini.BoardCreator;

import dades.Table;
import domini.Basic.Cell;
import domini.KKBoard;
import domini.KKRegion.KKRegion;

import java.util.ArrayList;

/**
 * Created by arnau_000 on 05/11/2015.
 */
public class HumanBoardCreator extends BoardCreator {

    public HumanBoardCreator(int size, Table<KKBoard> tableBKK) {
        super(size, tableBKK);
    }

    public boolean loadBoard(String name) {
/*        for (KKBoard b : mTableKKB){
            if (b.getName() == name){
                mBoard = b;
                return true;
            }
        }*/
        return false;
    }

    public void clearBoard() {
        for (int i = 0; i < mSize; ++i) {
            for (int j = 0; j < mSize; ++j) {
                mBoard.getCell(i, j).setValue(0);
            }
        }
    }

    public boolean createRegion(boolean forceOverlappingRegionsDestruction, ArrayList<Cell> cells, char operation,
                                int result) {
        if (!forceOverlappingRegionsDestruction) {
            for (Cell c : cells) {
                if (((KKRegion) c.getRegion()).getOperationValue() != 0) {
                    return false;
                }
            }
        }

        KKRegion.OperationType KKop = KKRegion.OperationType.NONE;
        switch (operation) {
            case '+':
                KKop = KKRegion.OperationType.ADDITION;
                break;
            case '-':
                KKop = KKRegion.OperationType.SUBTRACTION;
                break;
            case '*':
                KKop = KKRegion.OperationType.PRODUCT;
                break;
            case '/':
                KKop = KKRegion.OperationType.DIVISION;
                break;
            case ' ':
                KKop = KKRegion.OperationType.NONE;
                break;
            default:
                return false;
        }
        mBoard.createRegion(cells, KKop, result);
        return true;
    }
}
