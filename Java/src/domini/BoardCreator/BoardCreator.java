package domini.BoardCreator;

import dades.Table;
import domini.Basic.Cell;
import domini.KKBoard;
import domini.KKRegion.KKRegion;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by arnau_000 on 05/11/2015.
 */

public class BoardCreator {

    protected int mSize;
    protected KKBoard mBoard;
    private Table<KKBoard> mTableKKB;
    protected Random mRand;


    public KKBoard getBoard() {
        return mBoard;
    }

    public void setBoard(KKBoard board) {
        mBoard = board;
    }


    public BoardCreator(int size, Table<KKBoard> tableKKB) {
        mSize = size;
        newBoard();
        mTableKKB = tableKKB;
        mRand = new Random();
    }

    protected void newBoard(){
        mBoard = new KKBoard(mSize);
        // Create a troll region for the whole the Board
        ArrayList<Cell> tot = new ArrayList<Cell>(mSize * mSize);
        for (int i = 0; i < mSize; ++i) {
            for (int j = 0; j < mSize; ++j) {
                tot.add(i * mSize + j, mBoard.getCell(i, j));
            }
        }
        mBoard.createRegion(tot, KKRegion.OperationType.PRODUCT, 0);
    }

    public void clearBoard() {
        for (int i = 0; i < mSize; ++i) {
            for (int j = 0; j < mSize; ++j) {
                mBoard.getCell(i, j).setValue(0);
            }
        }
    }

    public void setBoardCreator(int size, Table<KKBoard> tableKKB) {
        mBoard = new KKBoard(size);
        mSize = size;
        mTableKKB = tableKKB;
    }

    public void saveBoard(String name) {
        mTableKKB.add(mBoard);
    }

    public boolean fillBoardWithRandomNumbers() {
        return backtracking(0, 0);
    }

    private boolean backtracking(int i, int j) {
        if (i == mSize) return true;
        // If the Cell's value is already set, we do nothing
        if (mBoard.getCell(i, j).getValue() != 0) {
            return backtracking(i + (j + 1) / mSize, (j + 1) % mSize);
        }
        ArrayList<Integer> v = new ArrayList<>();
        for (int k = 1; k <= mSize; ++k) {
            if (fits(i, j, k)) {
                v.add(k);
            }
        }
        // boolean ret = false;
        v = randomOrder(v);
        for (int k : v) {
            mBoard.getCell(i, j).setValue(k);
            if (backtracking(i + (j + 1) / mSize, (j + 1) % mSize)) {
                return true;
            } else {
                mBoard.getCell(i, j).setValue(0);
            }
        }
        return false;
    }

    private boolean fits(int i, int j, int n) {
        mBoard.getColumns().get(j).calculatePossibilities();
        mBoard.getRows().get(i).calculatePossibilities();
        mBoard.getCell(i, j).calculatePossibilities();
        return mBoard.getCell(i, j).getPossibility(n);
    }

    private ArrayList<Integer> randomOrder(ArrayList<Integer> v) {
        ArrayList<Integer> vaux = new ArrayList<>();
        while (v.size() > 0) {
            int r = mRand.nextInt(v.size());
            vaux.add(v.get(r));
            v.remove(r);
        }
        return vaux;
    }
}
