package domini.Basic;

/**
 * Contains some cells
 */
public abstract class Region extends ItemPossibilities {

    Cell[] cells;

    public Region(int size) {
        super(size);
        cells = new Cell[size];
    }

    public Region(Cell[] cells) {
        super(cells.length);
        this.cells = cells;
    }

    public Cell[] getCells() {
        return this.cells;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }

    public Cell getCell(int n) {
        return cells[n];
    }

    public void setCell(int n, Cell c) {
        cells[n] = c;
    }


}
