package domini.BoardCreator;

import dades.Table;
import domini.Basic.Cell;
import domini.KKBoard;
import domini.KKRegion.KKRegion;
import domini.KKRegion.KKRegionProduct;

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

        // Check if it overlaps any other regions
        if (!forceOverlappingRegionsDestruction) {
            for (Cell c : cells) {
                if (((KKRegion) c.getRegion()).getOperationValue() != 0) {
                    return false;
                }
            }
        }

        // Look for regions that overlap
        ArrayList<KKRegion> R = new ArrayList<>();
        for (Cell c : cells) {
            if (((KKRegion) c.getRegion()).getOperationValue() != 0 && !R.contains(c.getRegion())) {
                R.add((KKRegion) c.getRegion());
            }
        }

        KKRegion.OperationType KKop;
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
                if (cells.size() > 1) {
                    try {
                        throw new Exception("HBC: createRegion(): invalid operation character");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                } else {
                    KKop = KKRegion.OperationType.NONE;
                }
                break;

        }
        mBoard.createRegion(cells, KKop, result);

        // Delete all overlapped regions
        for (KKRegion r : R){
            mBoard.getKkregions().remove(mBoard.getKkregions().indexOf(r));
            for (int i=0; i<r.getCells().size(); i++){
                if (r.getCells().get(i).getRegion() == r){
                    r.getCells().get(i).setRegion(mBoard.getKkregions().get(0));
                }
            }
        }
        return true;
    }

    public KKRegion removeTroll(){
        KKRegion troll = findTroll();
        mBoard.getKkregions().remove(mBoard.getKkregions().indexOf(troll));
        return troll;
    }

    private KKRegion findTroll(){
        for (KKRegion r : mBoard.getKkregions()){
            if (r.getOperationValue()==0){
                return r;
            }
        }
        System.out.println("Troll not found.");
        return null;
    }

    public void addTroll(KKRegion troll){
        mBoard.getKkregions().add(0, troll);
    }
}
