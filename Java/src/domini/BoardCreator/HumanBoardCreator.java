package domini.BoardCreator;

import com.sun.org.apache.xpath.internal.operations.Bool;
import dades.Table;
import domini.Basic.Cell;
import domini.Basic.Column;
import domini.Basic.Row;
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

    public boolean loadBoard(String name, int size) {
        for (KKBoard b : mTableKKB){
            if (b.getName().equals(name) && b.getSize() == size){
                mBoard = b.getCopy();
                return true;
            }
        }
        return false;
    }

    /**
     * Tries to create a region.
     *
     * @param forceOverlappingRegionsDestruction If false, the region is not created if that means modifying or even
     *                                           deleting other regions. If true, the region is created even if it
     *                                           overlaps other regions. In this case, the other regions are deleted and
     *                                           their remaining cells become part of the default region.
     * @param cells                              ArrayList containing all the cells of the new region.
     * @param operation                          Operation of the region. It is ignored if size==1 and it can't be NONE
     *                                           if size>1 and it can't be SUBTRACTION or DIVISION if size>2
     * @param result                             Result of the operation
     * @return "the region has been created"
     */
    public boolean createRegion(boolean forceOverlappingRegionsDestruction, ArrayList<Cell> cells, char operation,
                                int result) throws Exception {

        if (!forceOverlappingRegionsDestruction) {
            for (Cell c : cells) {
                if (((KKRegion) c.getRegion()).getOperationValue() != 0) {
                    return false;
                }
            }
        } else {
            for (Cell c : cells) {
                if (c.getRegion() != defaultRegion) {
                    deleteRegion((KKRegion) c.getRegion());
                }
            }
        }

        KKRegion.OperationType KKop;
        if (cells.size() == 1) {
            KKop = KKRegion.OperationType.NONE;
        } else if (cells.size() > 1) {
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
                default:
                    Exception e = new Exception("HBC: createRegion(): invalid operation character");
                    e.printStackTrace();
                    return false;
            }
        } else {
            Exception e = new Exception("HBC: createRegion(): invalid cells array");
            e.printStackTrace();
            return false;
        }
        mBoard.createRegion(cells, KKop, result);
        return true;
    }

    public void deleteRegion(KKRegion region){
        ArrayList<Cell> C = region.getCells();
        for (Cell c : C){
            c.setRegion(defaultRegion);
        }
        mBoard.getKkregions().remove(mBoard.getKkregions().indexOf(region));
    }

    public boolean isContiguous(ArrayList<Cell> C){
        if (C.size() < 1){
            return true;
        }

        Cell c = C.get(0);
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i=0; i<C.size(); ++i){
            visited.add(false);
        }
        isContiguousRec(C, visited, c);
        for (Boolean b : visited){
            if (!b){
                return false;
            }
        }
        return true;
    }

    private void isContiguousRec(ArrayList<Cell> C, ArrayList<Boolean> visited, Cell c){
        visited.set(C.indexOf(c), true);
        int x = c.getRow().getPos();
        int y = c.getColumn().getPos();
        if (x > 0){
            Cell newc = find(C, x-1, y);
            if (newc != null && !visited.get(C.indexOf(newc))){
                isContiguousRec(C, visited, newc);
            }
        }
        if (y > 0){
            Cell newc = find(C, x, y-1);
            if (newc != null && !visited.get(C.indexOf(newc))){
                isContiguousRec(C, visited, newc);
            }
        }
        if (x < mBoard.getSize()-1){
            Cell newc = find(C, x+1, y);
            if (newc != null && !visited.get(C.indexOf(newc))){
                isContiguousRec(C, visited, newc);
            }
        }
        if (y < mBoard.getSize()-1){
            Cell newc = find(C, x, y+1);
            if (newc != null && !visited.get(C.indexOf(newc))){
                isContiguousRec(C, visited, newc);
            }
        }
    }

    private Cell find(ArrayList<Cell> C, int x, int y){
        for (Cell c : C){
            if (c.getRow().getPos() == x && c.getColumn().getPos() == y){
                return c;
            }
        }
        return null;
    }
}
