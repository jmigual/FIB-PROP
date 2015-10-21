package domini.Basic;

/**
 * Contains some cells
 */
public abstract class Region extends ItemPossibilities {

    protected Cell[] cells;
    protected int maxCellValue;

    public Region(int size, int maxCellValue) {
        super(maxCellValue);
        cells = new Cell[size];
        this.maxCellValue=maxCellValue;
    }

    public Region(Cell[] cells, int maxCellValue) {
        super(maxCellValue);
        this.cells = cells;
        this.maxCellValue=maxCellValue;
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
