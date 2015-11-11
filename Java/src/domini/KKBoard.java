package domini;

import domini.Basic.Board;
import domini.Basic.Cell;
import domini.KKRegion.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Joan on 20/10/2015.
 */
public class KKBoard extends Board implements Serializable {

    private ArrayList<KKRegion> _kkregions;


    public KKBoard(int size) {
        super(size);
        _kkregions = new ArrayList<>(_size * _size / 2);
    }

    public Board getSolution() {
        return null;
    }

    @Override
    public void solve() {
        boolean b = precalculate();
        if (b) b = recursive_solve(0, 0);
        System.out.println(b);
    }

    private boolean precalculate() {
        for (int i = 0; i < _kkregions.size(); i++) {
            KKRegion r = _kkregions.get(i);
            if (r.size() == 1) r.getCell(0).setValue(r.getOperationValue());
        }
        boolean changed = true;
        while (changed) {
            changed = false;
            calculateIndividualPossibilities();
            //mira si hi ha alguna cella amb 1 sola possibilitat
            for (int i = 0; i < _size; i++) {
                for (int j = 0; j < _size; j++) {
                    Cell c = _boardInfo.get(i).get(j);
                    if (c.getValue() == 0) {
                        int value = 0;
                        for (int k = 1; k <= c.getPossibilities().length & value != -1; k++) {
                            if (c.getPossibility(k)) {
                                if (value == 0) value = k;
                                else value = -1;
                            }
                        }
                        if (value == 0) return false;

                        if (value != -1) {
                            c.setValue(value);
                            changed = true;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean recursive_solve(int i, int j) {
        if (j == this.getSize()) return true;

        int j_f, i_f;
        i_f = i + 1;
        j_f = j;
        if (i_f == this.getSize()) {
            i_f = 0;
            j_f = j + 1;
        }

        if (this.getCell(i, j).getValue() != 0) {
            return recursive_solve(i_f, j_f);
        }

        /*this.getCell(i, j).getColumn().calculatePossibilities();
        this.getCell(i, j).getRow().calculatePossibilities();
        this.getCell(i, j).getRegion().calculatePossibilities();
        this.getCell(i, j).calculatePossibilities();*/


        //precalculate();
        
        calculateIndividualPossibilities();


        if (this.getCell(i, j).getValue() != 0) {
            return recursive_solve(i_f, j_f);
        }

        if (getCell(i, j).getPossibilities().length == 0) return false;
        boolean ret = false;
        for (int a = 1; a <= this.getSize(); ++a) {
            if (this.getCell(i, j).getPossibility(a)) {
                this.getCell(i, j).setValue(a);
                ret = ret || recursive_solve(i_f, j_f);
                if (!ret) this.getCell(i, j).setValue(0);
                if (ret) return ret;
            }
        }
        return false;
    }

    public void calculateIndividualPossibilities(){
        for (int i = 0; i < _kkregions.size(); i++) {
            _kkregions.get(i).calculatePossibilities();
        }
        for (int i = 0; i < _columns.size(); i++) {
            _columns.get(i).calculatePossibilities();
        }
        for (int i = 0; i < _rows.size(); i++) {
            _rows.get(i).calculatePossibilities();
        }
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                Cell c = _boardInfo.get(i).get(j);
                if (c.getValue() == 0) {
                    c.calculatePossibilities();
                }
            }
        }
        for (int i = 0; i < _kkregions.size(); i++) {
            _kkregions.get(i).calculateIndividualPossibilities();
        }
        for (int i = 0; i < _columns.size(); i++) {
            _columns.get(i).calculateIndividualPossibilities();
            i = i;
        }
        for (int i = 0; i < _rows.size(); i++) {
            _rows.get(i).calculateIndividualPossibilities();
        }
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
