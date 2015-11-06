package domini.Basic;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Board implements Serializable {
    protected ArrayList<ArrayList<Cell>> _boardInfo;
    protected ArrayList<Column> _columns;
    protected ArrayList<Row> _rows;
    protected int _size;

    /**
     * To initialize a empty Board of size x size
     *
     * @param size Size of the Board
     */
    public Board(int size) {
        _size = size;
        _boardInfo = new ArrayList<>(_size);
        _rows = new ArrayList<>(_size);
        _columns = new ArrayList<>(_size);
        for (int i = 0; i < _size; ++i) {
            _boardInfo.add(i, new ArrayList<Cell>(size));
            _rows.add(i, new Row(_size, i));
            for (int j = 0; j < _size; ++j) {
                if (i == 0) _columns.add(j, new Column(_size, j));
                _boardInfo.get(i).add(j, new Cell(_size, _rows.get(i), _columns.get(j)));
                _rows.get(i).addCell(j,_boardInfo.get(i).get(j));
                _columns.get(j).addCell(i, _boardInfo.get(i).get(j));
            }
        }
    }

    /**
     * To get a Cell
     *
     * @param i Number of the row
     * @param j Number of the column
     * @return Returns the cell located in the row and column of the param's
     */
    public Cell getCell(int i, int j) {
        return _boardInfo.get(i).get(j);
    }

    /**
     * An abstract method that should be implemented by subclasses
     *
     * @return If the Board has a solutions with the actual configuration
     */
    public abstract boolean hasSolution();

    /**
     * An abstract method that should be implemented by subclasses
     *
     * @return A Board with the solution
     */
    public abstract Board getSolution();

    /**
     * An abstract method that should be implemented by subclasses
     * The board is solved if it hadSolution, otherwise and error message is displayed
     */
    public abstract void solve();

    /**
     * To get the columns of the Board
     *
     * @return Returns an ArrayList of the columns
     */
    public ArrayList<Column> getColumns() {
        return _columns;
    }

    /**
     * To get the rows of the Board
     *
     * @return Returns an ArrayList of the rows
     */
    public ArrayList<Row> getRows() {
        return _rows;
    }

    /**
     * To get the size of the Board
     *
     * @return Returns the size
     */
    public int getSize() {
        return _size;
    }


}
