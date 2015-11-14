package domini;

import domini.Basic.Board;
import domini.Basic.Cell;
import domini.Basic.Column;
import domini.Basic.Row;
import domini.KKRegion.*;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by Joan??? on 20/10/2015.
 */
public class KKBoard extends Board implements Serializable {

    boolean _hasSolution = false;
    int _numSolution = 0;
    private ArrayList<KKRegion> _kkregions;
    private String _name;
    private String _creator;


    public KKBoard(int size) {
        super(size);
        _kkregions = new ArrayList<>(_size * _size );
    }

    /*
    * Make a copy of the implicit parameter by Serializing and Deserializing
     */
    public KKBoard getCopy() {

        KKBoard ret = null;
        byte[] tempData = null;
        try {
            //OutputStream fileOut = Files.newOutputStream(Paths.get(DB.getDataPath() + "aux.ser"), CREATE, WRITE);
            ByteArrayOutputStream outS = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outS);
            out.writeObject(this);
            out.close();
            tempData = outS.toByteArray();
        } catch (IOException i) {
            i.printStackTrace();
        }
        try {
            ByteArrayInputStream inS = new ByteArrayInputStream(tempData);
            ObjectInputStream in = new ObjectInputStream(inS);
            ret = (KKBoard) in.readObject();
            in.close();
            inS.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("KKBoard class not found");
            c.printStackTrace();
        }
        return ret;

    }
    public void setName(String _name) {
        this._name = _name;
    }
    public String get_name() {

        return _name;
    }

    public String getCreator() {
        return _creator;
    }

    public void setCreator(String _creator) {
        this._creator = _creator;
    }

    public KKBoard getSolution() {
        KKBoard ret = getCopy();
        ret.solve();
        return ret;
    }

    @Override
    public void solve() {
        boolean b = precalculate();
        if (b) b = recursiveSolve(0, 0);
        _hasSolution = b;
        System.out.println(b);
    }

    private boolean precalculate() {
        for (KKRegion r : _kkregions) {
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

    private boolean recursiveSolve(int i, int j) {
        if (j == this.getSize()) return true;

        int j_f, i_f;
        i_f = i + 1;
        j_f = j;
        if (i_f == this.getSize()) {
            i_f = 0;
            j_f = j + 1;
        }

        if (this.getCell(i, j).getValue() != 0) {
            return recursiveSolve(i_f, j_f);
        }

        /*this.getCell(i, j).getColumn().calculatePossibilities();
        this.getCell(i, j).getRow().calculatePossibilities();
        this.getCell(i, j).getRegion().calculatePossibilities();
        this.getCell(i, j).calculatePossibilities();*/

        precalculate();

        calculateIndividualPossibilities();


        if (this.getCell(i, j).getValue() != 0) {
            return recursiveSolve(i_f, j_f);
        }

        if (getCell(i, j).getPossibilities().length == 0) return false;
        boolean ret = false;
        for (int a = 1; a <= this.getSize(); ++a) {
            if (this.getCell(i, j).getPossibility(a)) {
                this.getCell(i, j).setValue(a);
                ret = ret || recursiveSolve(i_f, j_f);
                if (!ret) this.getCell(i, j).setValue(0);
                else return ret;
            }
        }
        return false;
    }

    public int getNumSolutions(int max){
        KKBoard aux = this.getCopy();
        aux.recursive_solve_until(0,0,max);
        return aux._numSolution;
    }

    private boolean recursive_solve_until(int i, int j, int max) {
        if(_numSolution == max) return true;
        if (j == this.getSize()) {
            ++_numSolution;
            return true;
        }

        int j_f, i_f;
        i_f = i + 1;
        j_f = j;
        if (i_f == this.getSize()) {
            i_f = 0;
            j_f = j + 1;
        }

        if (this.getCell(i, j).getValue() != 0) {
            return recursiveSolve(i_f, j_f);
        }

        /*this.getCell(i, j).getColumn().calculatePossibilities();
        this.getCell(i, j).getRow().calculatePossibilities();
        this.getCell(i, j).getRegion().calculatePossibilities();
        this.getCell(i, j).calculatePossibilities();*/


        //precalculate();

        calculateIndividualPossibilities();


        if (this.getCell(i, j).getValue() != 0) {
            return recursiveSolve(i_f, j_f);
        }

        if (getCell(i, j).getPossibilities().length == 0) return false;
        boolean ret = false;
        for (int a = 1; a <= this.getSize(); ++a) {
            if (this.getCell(i, j).getPossibility(a)) {
                this.getCell(i, j).setValue(a);
                ret = ret || recursiveSolve(i_f, j_f);
                if (!ret) this.getCell(i, j).setValue(0);
                //if (ret) return ret;
            }
        }
        return false;
    }

    public void calculateIndividualPossibilities() {
        for (int i = 0; i < _kkregions.size(); i++) {
            _kkregions.get(i).calculatePossibilities();
        }
        for (Column _column : _columns) {
            _column.calculatePossibilities();
        }
        for (Row _row : _rows) {
            _row.calculatePossibilities();
        }
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                Cell c = _boardInfo.get(i).get(j);
                if (c.getValue() == 0) {
                    c.calculatePossibilities();
                }
            }
        }
        for (KKRegion _kkregion : _kkregions) {
            _kkregion.calculateIndividualPossibilities();
        }
        for (Column _column : _columns) {
            _column.calculateIndividualPossibilities();
        }
        for (Row _row : _rows) {
            _row.calculateIndividualPossibilities();
        }
    }

    @Override
    public boolean hasSolution() {
        KKBoard Aux = this.getCopy();
        Aux.solve();
        return Aux._hasSolution;

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
     * @param cells of the KKBoard
     * @param op define the kind of operation
     * @param opValue how much is the operation result
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
        for (Cell cell : cells) cell.setRegion(_kkregions.get(_kkregions.size() - 1));
        //_kkregions.get(_kkregions.size() - 1);
        return true;
    }
}
