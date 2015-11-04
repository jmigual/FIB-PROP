package domini.Basic;

import java.util.ArrayList;

public abstract class Board {
    private ArrayList<ArrayList<Cell>> _boardInfo;
    private ArrayList<Column> _columns;
    private ArrayList<Row> _rows;
    private int _size;

    /**
     * To initialize a empty Board of size x size
     *
     * @param size Size of the Board
     */
    public Board(int size) {
        _size = size;
        ArrayList<ArrayList<Cell>> _boardInfo = new ArrayList<ArrayList<Cell>>(_size);
        ArrayList<Row> _rows = new ArrayList<Row>(_size);
        ArrayList<Column> _columns = new ArrayList<Column>(_size);
        for (int i = 0; i < _size; ++i) {
            _boardInfo.set(i, new ArrayList<Cell>(size));
            _rows.set(i, new Row(_size, i));
            for (int j = 0; j < _size; ++j) {
                if (i == 0) _columns.set(j, new Column(_size, j));
                _boardInfo.get(i).set(j, new Cell(_size, _rows.get(i), _columns.get(j)));
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
