package domini.BoardCreator;

import domini.KKBoard;

/**
 * Created by arnau_000 on 05/11/2015.
 */

public class BoardCreator {

    protected int mSize;
    protected KKBoard mBoard;

    public BoardCreator(int size) {
        mBoard = new KKBoard(size);
        mSize = size;
    }

    public KKBoard getBoard() {
        return mBoard;
    }

    public void setBoard(KKBoard mBoard) {
        this.mBoard = mBoard;
    }


}
