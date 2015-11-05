package domini.BoardCreator;

import domini.KKBoard;

/**
 * Created by arnau_000 on 05/11/2015.
 */

public class BoardCreator {

    private KKBoard mBoard;


    public BoardCreator(int size){
        mBoard = new KKBoard(size);
    }

    public KKBoard getBoard(){
        return mBoard;
    }


}
