package domini.Basic;


/**
 * Created by Marc on 22/10/2015.
 * Contains information of a Move made by a player.
 */

public class Move {

    //ATTRIBUTES

    private Cell _cell;
    private int _lastNum;
    private int _nextNum;

    //OPERATIONS

    /**
     * Constructor with all the attributes
     */
    public Move(Cell cell, int last, int next) {
        _lastNum = last;
        _nextNum = next;
        _cell = cell;
    }

    /**
     * Functions used by Match to implement Undo
     */
    public void applyMove() {
        _cell.setValue(_nextNum);
    }

    public void revertMove() {
        _cell.setValue(_lastNum);
    }
}
