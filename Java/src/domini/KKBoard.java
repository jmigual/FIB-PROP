package domini;

import domini.Basic.Board;
import domini.Basic.Cell;
import domini.Basic.Column;
import domini.Basic.Row;
import domini.KKRegion.*;
import domini.stats.Playable;
import javafx.application.Platform;
import presentacio.KKPrinter.KKPrinter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;


/**
 * Created by Joan??? on 20/10/2015.
 */
public class KKBoard extends Board implements Playable {

    boolean _hasSolution = false;
    int _numSolution = 0;
    private ArrayList<KKRegion> _kkregions;
    private String _name;
    private String _creator;
    private String mID;


    public KKBoard(int size) {
        super(size);
        _kkregions = new ArrayList<>(_size * _size);

        Random rand = new Random();
        mID = Double.toString(rand.nextDouble()) + Long.toString(System.currentTimeMillis());
    }

    private int getHash(String s) {
        int hash = 7;
        for (int i = 0; i < s.length(); i++) {
            hash = hash * 31 + s.charAt(i);
        }
        return hash;

    }

    public int getID() {
        return getHash(_name);
    }

    @Override
    public int hashCode() {
        return getHash(mID);
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

    public String getName() {

        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
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
        boolean b = preCalculate();
        if (b) b = recursiveSolve(0, 0, this);
        _hasSolution = b;
        System.out.println(b);
    }

    public void solveslow(KKPrinter printer) {
        boolean b = preCalculate();
        if (b) b = recursiveSolveStep(0, 0, printer, this);
        _hasSolution = b;
        System.out.println(b);
    }

    private boolean recursiveSolveStep(int i, int j, KKPrinter printer, KKBoard kkboard) {
        if (j == this.getSize()) {

            this._boardInfo = kkboard._boardInfo;
            return true;
        }

        showAndWait(printer, kkboard);

        int j_f, i_f;
        i_f = i + 1;
        j_f = j;
        if (i_f == this.getSize()) {
            i_f = 0;
            j_f = j + 1;
        }

        if (kkboard.getCell(i, j).getValue() != 0) {
            return recursiveSolveStep(i_f, j_f, printer, kkboard);
        }

        boolean b = kkboard.preCalculate();
        if (!b) {
            return false;
        }

        //boolean b=calculateIndividualPossibilities();
        boolean[] possibilities = kkboard.getCell(i, j).getPossibilities().clone();


        if (kkboard.getCell(i, j).getValue() != 0) {
            return recursiveSolveStep(i_f, j_f, printer, kkboard);
        }


        boolean ret = false;
        for (int a = 1; a <= this.getSize(); ++a) {
            if (possibilities[a - 1]) {
                kkboard.getCell(i, j).setValue(a);
                ret = recursiveSolveStep(i_f, j_f, printer, kkboard.getCopy()) || ret;
                if (!ret) kkboard.getCell(i, j).setValue(0);
                else return ret;
            }
        }

        return false;
    }

    public boolean preCalculate() {
        for (KKRegion r : _kkregions) {
            if (r.size() == 1) r.getCell(0).setValue(r.getOperationValue());
        }
        boolean changed = true;
        //while (changed) {
            changed = false;

            if (!calculateIndividualPossibilities()) return false;
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
        //}
        return true;
    }

    private boolean recursiveSolve(int i, int j, KKBoard kkboard) {
        if (j == this.getSize()) {

            for (int ii=0; ii<_size; ii++){
                for (int jj=0; jj<_size; jj++){
                    getCell(ii,jj).setValue(kkboard.getCell(ii,jj).getValue());
                }
            }
            return true;
        }

        int j_f, i_f;
        i_f = i + 1;
        j_f = j;
        if (i_f == this.getSize()) {
            i_f = 0;
            j_f = j + 1;
        }

        if (kkboard.getCell(i, j).getValue() != 0) {
            return recursiveSolve(i_f, j_f, kkboard);
        }

        boolean b = kkboard.preCalculate();
        if (!b) {
            return false;
        }

        //boolean b=calculateIndividualPossibilities();
        boolean[] possibilities = kkboard.getCell(i, j).getPossibilities().clone();

        if (kkboard.getCell(i, j).getValue() != 0) {
            return recursiveSolve(i_f, j_f, kkboard);
        }


        boolean ret = false;
        for (int a = 1; a <= this.getSize(); ++a) {
            if (possibilities[a - 1]) {
                kkboard.getCell(i, j).setValue(a);
                ret = recursiveSolve(i_f, j_f, kkboard.getCopy()) || ret;
                if (!ret) kkboard.getCell(i, j).setValue(0);
                else return ret;
            }
        }

        return false;
    }

    public boolean calculateIndividualPossibilities() {
        int res;
        res = _kkregions.parallelStream().mapToInt(e -> {
            if (! e.isCorrect()) return 1;
            e.calculatePossibilities();
            return 0;
        }).sum();
        if (res > 0) return false;

        res = _columns.parallelStream().mapToInt(e -> {
            e.calculatePossibilities();
            if (e.isCorrect()) return 0;
            return 1;
        }).sum();
        if (res > 0) return false;

        res = _rows.parallelStream().mapToInt(e -> {
            e.calculatePossibilities();
            if (e.isCorrect()) return 0;
            return 1;
        }).sum();
        if (res > 0) return false;
        _boardInfo.parallelStream().flatMap(Collection::stream).forEach(c -> {
            if (c.getValue() == 0) c.calculatePossibilities();
        });

        for (int i = 0; i < 3; ++i) {
            _columns.stream().forEach(Column::calculateIndividualPossibilities);
            _rows.stream().forEach(Row::calculateIndividualPossibilities);
            _kkregions.stream().forEach(KKRegion::calculateIndividualPossibilities);
        }

        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                Cell c = _boardInfo.get(j).get(i);

                if (c.getValue() == 0) {
                    boolean found = false;
                    for (int k = 1; k <= _size && !found; k++) {
                        if (c.getPossibility(k)) found = true;
                    }
                    if (!found) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasSolution() {
        KKBoard Aux = this.getCopy();
        Aux.solve();
        return Aux._hasSolution;

    }

    public ArrayList<KKRegion> getKkregions() {
        return _kkregions;
    }

    /**
     * ADDITION(0),
     * SUBTRACTION(1),
     * PRODUCT(2),
     * DIVISION(3),
     * NONE(4);
     *
     * @param cells   of the KKBoard
     * @param op      define the kind of operation
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

    private void showAndWait(KKPrinter printer, KKBoard kkboard) {
        Platform.runLater(() -> {
            kkboard.calculateIndividualPossibilities();
            printer.setBoard(kkboard);
            printer.updateCells();
            for (int a = 0; a < printer.getBoard().getSize(); a++) {
                for (int x = 0; x < printer.getBoard().getSize(); x++) {
                    Cell c = printer.getBoard().getCell(a, x);
                    for (int k = 1; k <= printer.getBoard().getSize(); k++) {
                        c.setAnnotation(k, c.getPossibility(k));
                    }
                }
            }
            printer.updateAnnotations();
        });


        Scanner in = new Scanner(System.in);
        in.next();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KKBoard)) return false;

        KKBoard board = (KKBoard) o;

        return mID.equals(board.mID);

    }
}
