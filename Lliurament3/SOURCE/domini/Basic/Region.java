package domini.Basic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Contains some cells
 */
public abstract class Region extends ItemPossibilities implements Serializable {

    /**
     * Contains all Region's cells
     */
    protected ArrayList<Cell> cells;

    /**
     * Constructor with default values
     *
     * @param size         Region's size
     * @param maxCellValue Region's max Cell value
     */
    public Region(int size, int maxCellValue) {
        super(maxCellValue);
        cells = new ArrayList<>(size);
    }

    /**
     * Constructor with initialized cells
     *
     * @param cells        Cells contained in the Region
     * @param maxCellValue Maximum Cell Value
     */
    public Region(ArrayList<Cell> cells, int maxCellValue) {
        super(maxCellValue);
        this.cells = new ArrayList<>(cells.size());
        for (Cell cell : cells) this.cells.add(cell);
    }

    /**
     * To get the cells contained in the Region
     *
     * @return Collection containing all the cells in the Region
     */
    public ArrayList<Cell> getCells() {
        return this.cells;
    }

    /**
     * Sets the cells contained in the Region
     *
     * @param cells Collection of cells
     */
    public void setCells(ArrayList<Cell> cells) {
        this.cells.clear();
        this.cells.addAll(cells.stream().collect(Collectors.toList()));
    }

    /**
     * To get an especific cell from the Region
     *
     * @param n Cell index in the region
     * @return Cell object at index n
     */
    public Cell getCell(int n) {
        return cells.get(n);
    }

    /**
     * Sets the specified Cell at the selected index
     *
     * @param n Cell index
     * @param c Cell object to replace at index 'n'
     */
    public void setCell(int n, Cell c) {
        cells.set(n, c);
    }

    /**
     * Adds a cell at the specified position of the Region
     *
     * @param n Cell index
     * @param c Cell object to set at index 'n'
     */
    public void addCell(int n, Cell c) {
        cells.add(n, c);
    }

    /**
     * To get the size of the region (number of cells contained)
     *
     * @return int containing the number of cells in the region
     */
    public int size() {
        return cells.size();
    }

    /**
     * Must check if all the cells in the region have a correct value
     *
     * @return True if there's no cell breaking the region laws
     */
    public abstract boolean isCorrect();

    /**
     * Checks the possibilities for all the cells
     */
    public void calculateIndividualPossibilities() {
        for (Cell c : cells) {
            c.annotations = c.possibilities.clone();
            for (int i = 0; i < c.possibilities.length; i++) c.possibilities[i] = false;
        }
        dfs(0);
    }

    /**
     * Some thing that @iminspace's been doing and he's very happy with it =)
     *
     * @param i
     * @return
     */
    private boolean dfs(int i) {
        if (i == cells.size()) return this.isCorrect();
        Cell c = cells.get(i);
        if (c.getValue() != 0) {
            return dfs(i + 1);
        }
        boolean ret = false;
        for (int j = 1; j <= c.getPossibilities().length; j++) {
            if (c.getAnnotation(j)) {
                c.setValue(j);
                if (this.isCorrect() && c.getColumn().isCorrect() && c.getRow().isCorrect() && dfs(i + 1)) {
                    c.setPossibility(j, true);
                    ret = true;
                }
                c.setValue(0);
            }
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}
