package domini.Basic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contains some cells
 */
public abstract class Region extends ItemPossibilities implements Serializable{

    /**
     * Contains all Region's cells
     */
    protected ArrayList<Cell> cells;

    /**
     * Constructor with default values
     * @param size Region's size
     * @param maxCellValue Region's max Cell value
     */
    public Region(int size, int maxCellValue) {
        super(maxCellValue);
        cells = new ArrayList<>(size);
    }

    /**
     * Constructor with initialized cells
     * @param cells Cells contained in the Region
     * @param maxCellValue
     */
    public Region(ArrayList<Cell> cells, int maxCellValue) {
        super(maxCellValue);
        this.cells = new ArrayList<>(cells.size());
        for (int i=0; i<cells.size(); i++)this.cells.add(cells.get(i));
    }

    /**
     * To get the cells contained in the Region
     * @return Collection containing all the cells in the Region
     */
    public ArrayList<Cell> getCells() {
        return this.cells;
    }

    /**
     * Sets the cells contained in the Region
     * @param cells Collection of cells
     */
    public void setCells(ArrayList<Cell> cells) {
        this.cells.clear();
        for (Cell c: cells)this.cells.add(c);
    }

    /**
     * To get an especific cell from the Region
     * @param n Cell index in the region
     * @return Cell object at index n
     */
    public Cell getCell(int n) {
        return cells.get(n);
    }

    /**
     * Sets the specified Cell at the selected index
     * @param n Cell index
     * @param c Cell object to replace at index 'n'
     */
    public void setCell(int n, Cell c) {
        cells.set(n, c);
    }

    /**
     * Adds a cell at the specified position of the Region
     * @param n Cell index
     * @param c Cell object to set at index 'n'
     */
    public void addCell(int n, Cell c) { cells.add(n, c); }

    /**
     * To get the size of the region (number of cells contained)
     * @return int containing the number of cells in the region
     */
    public int size() {
        return cells.size();
    }

    public abstract boolean isCorrect();

    public void calculateIndividualPossibilities(){
        for (Cell c: cells){
            c.annotations=c.possibilities.clone();
            for (int i=0; i<c.possibilities.length; i++)c.possibilities[i]=false;
        }
        dfs(0);
    }
    private boolean dfs (int i){
        if (i==cells.size()) return this.isCorrect();
        Cell c=cells.get(i);
        if (c.getValue()!=0){
            return dfs(i+1);
        }
        boolean ret = false;
        for (int j = 1; j <= c.getPossibilities().length; j++) {
            if (c.getAnnotation(j)) {
                c.setValue(j);
                if (this.isCorrect() && dfs(i + 1)) {
                    c.setPossibility(j, true);
                    ret = true;
                }
                c.setValue(0);
            }
        }
        return ret;
    }
}
