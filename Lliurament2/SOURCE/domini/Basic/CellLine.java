package domini.Basic;

import java.io.Serializable;

/**
 * Contains some cells but they're on the same line
 */
public class CellLine extends Region implements Serializable {

    /**
     * CellLine's position in the board
     */
    protected int pos;

    /**
     * Default constructor
     *
     * @param size CellLine's size
     * @param pos  CellLine's position in the board
     */
    public CellLine(int size, int pos) {
        super(size, size);
        this.pos = pos;
    }

    /**
     * To get the current position
     *
     * @return The current position
     */
    public int getPos() {
        return pos;
    }

    /**
     * Calculates all the CellLine's possibilities with the current set numbers
     */
    public void calculatePossibilities() {
        for (int i = 0; i < possibilities.length; i++) possibilities[i] = true;
        for (Cell cell : cells) {
            int pos = cell.getValue() - 1;
            if (pos != -1) possibilities[pos] = false;
        }

    }

    /**
     * Checks if all the cells respect the restrictions
     *
     * @return True if all cells' values are correct
     */
    public boolean isCorrect() {
        boolean[] appeared = new boolean[possibilities.length];
        for (int i = 0; i < appeared.length; i++) appeared[i] = false;
        for (Cell cell : cells) {
            int pos = cell.getValue() - 1;
            if (pos != -1) {
                if (appeared[pos]) return false;
                else appeared[pos] = true;
            }
        }
        return true;
    }
}
