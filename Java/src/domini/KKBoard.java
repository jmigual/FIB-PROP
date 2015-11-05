package domini;

import domini.Basic.Board;
import domini.Basic.Cell;
import domini.KKRegion.*;

import java.util.ArrayList;

/**
 * Created by Joan on 20/10/2015.
 */
public class KKBoard extends Board {

    private ArrayList<KKRegion> _kkregions;


    public KKBoard(int size) {
        super(size);
        _kkregions=new ArrayList<>(_size*_size/2);
    }

    public Board getSolution() {
        return null;
    }

    @Override
    public void solve() {
        recursive_solve(0, 0);
    }

    private boolean recursive_solve(int i, int j) {
        if (j == this.getSize()) return true;

        this.getCell(i, j).getColumn().calculatePossibilities();
        this.getCell(i, j).getRow().calculatePossibilities();
        this.getCell(i, j).getRegion().calculatePossibilities();
        this.getCell(i, j).calculatePossibilities();

        int j_f, i_f;
        i_f = i + 1;
        j_f = j;
        if (i_f == this.getSize()) {
            i_f = 0;
            j_f = j + 1;
        }


        if (getCell(i, j).getPossibilities().length == 0) return false;
        boolean ret = false;
        for (int a = 0; a < this.getSize(); ++a) {
            if (this.getCell(i, j).getPossibilities()[a]) this.getCell(i, j).setValue(a + 1);
            ret = ret || recursive_solve(i_f, j_f);
            if (ret) return ret;
        }
        return ret;
    }

    @Override
    public boolean hasSolution() {
        return false;
    }

    public ArrayList<KKRegion> get_kkregions() {
        return _kkregions;
    }

    /**
     * ADDITION(0),
     * SUBTRACTION(1),
     * PRODUCT(2),
     * DIVISION(3),
     * NONE(4);
     *
     * @param cells
     * @param op
     */
    public boolean createRegion(ArrayList<Cell> cells, KKRegion.OperationType op, int opValue) {
        if (op == KKRegion.OperationType.ADDITION) _kkregions.add(new KKRegionAddition(cells, this.getSize(), opValue));
        else if (op == KKRegion.OperationType.SUBTRACTION)
            _kkregions.add(new KKRegionSubtraction(cells, this.getSize(), opValue));
        else if (op == KKRegion.OperationType.PRODUCT)
            _kkregions.add(new KKRegionProduct(cells, this.getSize(), opValue));
        else if (op == KKRegion.OperationType.DIVISION)
            _kkregions.add(new KKRegionDivision(cells, this.getSize(), opValue));
        else if (op == KKRegion.OperationType.NONE)
            _kkregions.add(new KKRegionAddition(cells, this.getSize(), opValue));
        else return false;
        for (int i = 0; i < cells.size(); ++i) cells.get(i).setRegion(_kkregions.get(_kkregions.size() - 1));
        //_kkregions.get(_kkregions.size() - 1);
        return true;
    }
}
