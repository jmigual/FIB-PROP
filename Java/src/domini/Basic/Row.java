package domini.Basic;

import java.io.Serializable;

/**
 * Row from a board
 */
public class Row extends CellLine implements Serializable{

    /**
     * Default constructor
     * @param size Row's size
     * @param pos Row's position in the board
     */
    public Row(int size, int pos) {
        super(size, pos);
    }
}
