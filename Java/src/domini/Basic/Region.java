package domini.Basic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contains some cells
 */
public abstract class Region extends ItemPossibilities implements Serializable{

    protected ArrayList<Cell> cells;

    public Region(int size, int maxCellValue) {
        super(maxCellValue);
        cells = new ArrayList<Cell>(size);
    }

    public Region(ArrayList<Cell> cells, int maxCellValue) {
        super(maxCellValue);
        this.cells = new ArrayList<>(cells.size());
        for (int i=0; i<cells.size(); i++)this.cells.add(cells.get(i));
    }

    public ArrayList<Cell> getCells() {
        return this.cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells.clear();
        for (int i=0; i<cells.size(); i++)this.cells.add(cells.get(i));
    }

    public Cell getCell(int n) {
        return cells.get(n);
    }

    public void setCell(int n, Cell c) {
        cells.set(n, c);
    }

    public void addCell(int n, Cell c) { cells.add(n, c); }

    public int size() {
        return cells.size();
    }
}
