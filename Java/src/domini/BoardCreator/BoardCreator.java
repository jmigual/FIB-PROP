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
    protected Table<KKBoard> mTableKKB;
    protected Random mRand;

    public KKRegion getDefaultRegion() {
        return defaultRegion;
    }

    protected KKRegion defaultRegion;

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
        mRand = new Random(System.currentTimeMillis());
    }

    protected void newBoard(){
        mBoard = new KKBoard(mSize);
        // Create a default region for the whole Board
        ArrayList<Cell> tot = new ArrayList<Cell>(mSize * mSize);
        for (int i = 0; i < mSize; ++i) {
            for (int j = 0; j < mSize; ++j) {
                tot.add(i * mSize + j, mBoard.getCell(i, j));
            }
        }
        mBoard.createRegion(tot, KKRegion.OperationType.PRODUCT, 0);
        defaultRegion = mBoard.getKkregions().get(0);
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

    public void saveBoard(String name, String creator) {
        clearBoard();
        mBoard.setName(name);
        mBoard.setCreator(creator);
        mTableKKB.add(mBoard);
    }

    public boolean fillBoardWithRandomNumbers() {
        removeDefaultRegion();
        boolean ret = backtracking(0, 0);
        createDefaultRegion();
        return ret;
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
        mBoard.getCell(i, j).getRegion().calculatePossibilities();
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

    public boolean isFinished(){
        for (int i=0; i<mBoard.getSize(); ++i){
            for (int j=0; j<mBoard.getSize(); ++j){
                if (mBoard.getCell(i,j).getRegion() == defaultRegion){
                    return false;
                }
            }
        }
        return true;
    }

    public void removeDefaultRegion(){
        if (!boardContainsDefaultRegion()){
            System.out.println("removeDefaultRegion called but there is no defaultRegion set.");
            return;
        }
        mBoard.getKkregions().remove(mBoard.getKkregions().indexOf(defaultRegion));
    }

    public void createDefaultRegion(){
        if (boardContainsDefaultRegion()){
            System.out.println("createDefaultRegion called but defaultRegion is already set -> it will be reset");
            return;
        }
        mBoard.getKkregions().add(defaultRegion);
    }

/*
    public void createDefaultRegion(){
        if (boardContainsDefaultRegion()){
            System.out.println("createDefaultRegion called but defaultRegion is already set -> it will be reset");
            removeDefaultRegion();
        }
        // Create a default region for all the cells without Region
        ArrayList<Cell> C = new ArrayList<Cell>(mSize * mSize);
        for (int i = 0; i < mSize; ++i) {
            for (int j = 0; j < mSize; ++j) {
                if (mBoard.getCell(i,j).getRegion() == defaultRegion) {
                    C.add( mBoard.getCell(i, j));
                }
            }
        }
        if (C.size() > 0) {
            mBoard.createRegion(C, KKRegion.OperationType.PRODUCT, 0);
            defaultRegion = mBoard.getCell(C.get(0).getColumn().getPos(), )
        }
    }*/

    public boolean boardContainsDefaultRegion(){
        for (int i = 0; i < mSize; ++i) {
            for (int j = 0; j < mSize; ++j) {
                if (mBoard.getCell(i,j).getRegion() == defaultRegion) {
                    return true;
                }
            }
        }
        return false;
    }

/*    public void createDefaultRegion(){
        // Copy all regions to aux and then delete them
        ArrayList<KKRegion> aux = new ArrayList<>();
        int n = mBoard.getKkregions().size();
        for (int i=0; i<n; ++i){
            aux.add(mBoard.getKkregions().get(0));
            mBoard.getKkregions().remove(0);
        }
        // Create a default region for the whole Board
        ArrayList<Cell> tot = new ArrayList<Cell>(mSize * mSize);
        for (int i = 0; i < mSize; ++i) {
            for (int j = 0; j < mSize; ++j) {
                tot.add(i * mSize + j, mBoard.getCell(i, j));
            }
        }
        mBoard.createRegion(tot, KKRegion.OperationType.PRODUCT, 0);
        defaultRegion = mBoard.getKkregions().get(0);
        // Create copies of the regions in aux
        for (KKRegion r : aux){
            mBoard.createRegion(r.getCells(), r.getOperation(), r.getOperationValue());
        }
    }
    */
}
