package domini.BoardCreator;

import dades.Table;
import domini.KKBoard;
import javafx.scene.control.Tab;

/**
 * Created by arnau_000 on 05/11/2015.
 */

public class BoardCreator {

    protected static int mSize;
    protected static KKBoard mBoard;
    private static Table<KKBoard> mTableKKB;


    public static KKBoard getBoard() {
        return mBoard;
    }

    public static void setBoard(KKBoard board) {
        mBoard = board;
    }


    public BoardCreator(int size, Table<KKBoard> tableKKB){
        mBoard = new KKBoard(size);
        mSize = size;
        mTableKKB = tableKKB;
    }

    public static void setBoardCreator(int size, Table<KKBoard> tableKKB){
        mBoard = new KKBoard(size);
        mSize = size;
        mTableKKB = tableKKB;
    }

    public static void saveBoard(String name){
        //KKBoard B = mBoard.clone();
        //B.setName(name);
        //mTableKKB.add(B);
        mTableKKB.add(mBoard);
    }

}
