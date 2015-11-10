package domini.Basic;

import java.io.Serializable;
import java.util.ArrayList;
import dades.Player;

/**
 * Contains information about a Match, such as the Player and the current Board
 */
public class Match implements Serializable {
    //ATTRIBUTES

    /** Basic attributes */
    private Board _board;
    private Player _player;

    private boolean _finished;

    /** The score and time the match has been going on (probably connected)*/
    private float _score;
    private float _time;

    /**List of the last Moves made by the Player and the actual one*/
    private ArrayList<Move> _moves;
    private int _index;


    //OPERATIONS

    /** Constructor giving the Board and Player */

    public Match (Board board, Player player) {
        _board = board;
        _player = player;
        _score = _time = 0;
        _finished = false;
        _moves = new ArrayList<>();
        _index = 0;
    }

    /** Tells whether the match has been completed or not (usefull for recovering running matches) */
    public boolean hasFinished (){return _finished;}

    /** Access to the different attributes of the class */
    public void setScore (float score){ _score = score;}
    public float getScore (){ return _score;}

    public Board getBoard (){return _board;}

    public Player getPlayer (){return _player;}


    /**Classes that use Move to implement the Undo function*/
    /** To make a move
      * @param i row of the Cell
     * @param j column of the Cell
     * @param value value the Cell is being changed to
     */
    public void makeMove (int i, int j, int value) {
        Cell cell = _board.getCell(i, j);
        Move move = new Move(cell, cell.getValue(), value);

        for (int it=_index + 1; it<_moves.size(); ++it) _moves.remove(it);

        _moves.add(move);
        _index = _moves.size()-1;
        move.applyMove();

        //CHECK ERROR
    }

    public boolean back (){
        if (_index == 0) return false;
        _moves.get(_index).revertMove();
        --_index;

        return true;
    }

    public boolean forward (){
        if (_index >= _moves.size()-1) return false;
        ++_index;
        _moves.get(_index).applyMove();

        return true;
    }

    /** Used to handle the annotations of the cells */

    public void addAnnotation (int i, int j, int value, boolean ann) {
        Cell cell = _board.getCell(i,j);
        cell.setAnnotation(value, ann);
    }

    /**HINTS
     * 0: Shows if actual values are correct
     * 1: Puts a random correct number of a cell
     * */

    public void hint (int num) {
        switch (num) {
            case 0:
                _score = _score - 10;
                break;
            case 1:
                _score = _score - 20;
                break;
        }
    }

}
