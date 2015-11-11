package domini.BoardCreator;

import dades.Table;
import domini.KKBoard;
import javafx.scene.control.Tab;

import java.util.ArrayList;

/**
 * Created by arnau_000 on 05/11/2015.
 */

public class BoardCreator {

    protected int mSize;
    protected KKBoard mBoard;
    private Table<KKBoard> mTableKKB;


    public KKBoard getBoard() {
        return mBoard;
    }

    public void setBoard(KKBoard board) {
        mBoard = board;
    }


    public BoardCreator(int size, Table<KKBoard> tableKKB){
        mBoard = new KKBoard(size);
        mSize = size;
        mTableKKB = tableKKB;
    }

    public void setBoardCreator(int size, Table<KKBoard> tableKKB){
        mBoard = new KKBoard(size);
        mSize = size;
        mTableKKB = tableKKB;
    }

    public void saveBoard(String name){
        mTableKKB.add(mBoard);
    }

    protected boolean fillBoardWithRandomNumbers(){
        return backtracking(0,0);
    }

    private boolean backtracking(int i, int j){
        if (mBoard.getCell(i,j).getValue() == 0){
            return backtracking(i + (j+1)/mSize, (j+1)%mSize);
        }
        int c=0;
        ArrayList<Integer> v = new ArrayList<>(mSize);
        for (int k=1; k<=9; ++k){
            if (1==0){

            }
        }
        if (c==0){
            return false;
        }
        else {

        }
        return 1==0;
    }

    private boolean fits(int i, int j, int n){
        return 1==0;
    }
}
