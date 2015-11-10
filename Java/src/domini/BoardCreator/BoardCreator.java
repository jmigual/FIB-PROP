package domini.BoardCreator;

import dades.Table;
import domini.KKBoard;
import javafx.scene.control.Tab;

/**
 * Created by arnau_000 on 05/11/2015.
 */

public class BoardCreator {

    protected  int mSize;
    protected KKBoard mBoard;
    private Table<KKBoard> mTableKKB;


    public KKBoard getBoard() {
        return mBoard;
    }

    public void setBoard(KKBoard mBoard) {
        this.mBoard = mBoard;
    }

    public BoardCreator(int size, Table<KKBoard> tableKKB){
        mBoard = new KKBoard(size);
        mSize = size;
        mTableKKB = tableKKB;
    }

    public void saveBoard(String name){
        //KKBoard B = mBoard.clone();
        //B.setName(name);
        //mTableKKB.add(B);
        mTableKKB.add(mBoard);
    }

}
