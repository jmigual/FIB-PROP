package domini.Basic;

import dades.KKDB;
import dades.Player;
import dades.PlayersAdmin;
import domini.KKBoard;
import domini.stats.Matchable;
import domini.stats.Playable;
import exceptions.PlayerNotExistsExcepction;
import presentacio.Drivers.DriverAdminPlayers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Contains information about a Match, such as the Player and the current Board
 * Also contains a history of the Moves made by the user, so that they can be reverted as pleased
 */
public class Match implements Matchable {
    //ATTRIBUTES

    /**
     * Basic attributes
     */
    private KKBoard _board;
    private Board _solution;
    private String _player;

    private boolean _finished;

    // NOTE: Això està malament
    public Player getPlayer(){
        KKDB _db;
        PlayersAdmin _playersAdmin;
        _db = new KKDB();
        _db.load();
        _playersAdmin = _db.getPlayersAdmin();

        try {
            return _playersAdmin.getPlayer(_player);
        } catch (PlayerNotExistsExcepction e) {
            e.printStackTrace();
        }
        return null;
    }
    public Playable getGame(){
        return _board;
    }
    public boolean finished(){
        return _finished;
    }
    public int computeTime(){
        return (int) _score;
    }

    /**
     * The score and time the match has been going on. The Score gets updated each time
     */
    private long _score;
    private long _time;
    private long _penalty;

    /**
     * List of the last Moves made by the Player and the actual one
     */
    private ArrayList<Move> _moves;
    private int _index;


    //OPERATIONS

    /**
     * Constructor giving the Board and Player
     */
    public Match(KKBoard board, String player) {
        _board = board.getCopy();
        _solution = _board.getSolution();
        _player = player;
        _score = 0;
        _time = System.currentTimeMillis();
        _penalty = 0;
        _finished = false;
        _moves = new ArrayList<>();
        _index = -1;
    }

    /**
     * Tells whether the match has been completed or not (usefull for recovering running matches)
     */
    public boolean hasFinished() {
        return _finished;
    }

    public long getScore() {
        if (_finished) return _score;
        _score = 10 * _board.getSize() * _board.getSize() * _board.getSize()
                - (_penalty + (System.currentTimeMillis() - _time)/1000);
        if (_score<0) _score =0;
        return _score;
    }

    /**
     * Access to the different attributes of the class
     */
   /* public void setScore(float score) {
        _score = score;
    }*/

    public KKBoard getBoard() {
        return _board;
    }
/*
    public String getPlayer() {
        return _player;
    }
 */

    /**Classes that use Move to implement the Undo function*/
    /**
     * To make a move
     *
     * @param i     row of the Cell
     * @param j     column of the Cell
     * @param value value the Cell is being changed to
     *
     * Return : whether the move is correct looking column and row
     */
    public boolean makeMove(int i, int j, int value) {
        Cell cell = _board.getCell(i-1, j-1);
        Move move = new Move(cell, cell.getValue(), value);

        move.applyMove();

        for (int it = _moves.size() - 1; it > _index; --it) _moves.remove(it);

        _moves.add(move);
        _index = _moves.size() - 1;

        //CHECK ERROR
        if (!_board.getColumns().get(j-1).isCorrect() || !_board.getRows().get(i-1).isCorrect()){
            _penalty = _penalty + 20;                                                                   //PENALITZACIO PER FER ERROR
            //move.revertMove();
            return false;
        }

        return true;
    }

    /** Undo, returns false if it can't be done*/
    public boolean back() {
        _penalty = _penalty + 5;                                                                     //PENALITZACIO PER FER RETURN
        if (_index == -1) return false;
        _moves.get(_index).revertMove();
        --_index;

        return true;
    }

    /**Redo, returns false if it can't be done */
    public boolean forward() {
        if (_index >= _moves.size() - 1) return false;
        ++_index;
        _moves.get(_index).applyMove();

        return true;
    }

    /**Checks if the game is finished */

    public boolean checkFinish () {
        if (!_board.isCorrect())return false;
        _finished = true;
        _time = System.currentTimeMillis();
        return true;
    }

    /**
     * Used to handle the annotations of the cells (activate/deactivate)
     */

    public void addAnnotation(int i, int j, int value, boolean ann) {
        Cell cell = _board.getCell(i, j);
        cell.setAnnotation(value, ann);
    }

    /**
     * HINTS
     * 0: Shows if actual values are correct
     * 1: Puts a random correct number of a cell
     *
     * Returns the cell(s) that have been modified
     */

    public ArrayList<Cell>  hint(int num) {
        ArrayList<Cell> ret = new ArrayList<>();
        switch (num) {
            case 0:
                _penalty = _penalty + 50;                                                             //PENALITZACIO PER COMPROVAR
                for (int i=0; i<_board._size; ++i) {
                    for (int j = 0; j<_board._size; ++j){
                        Cell cell = _board.getCell(i,j);
                        if (cell.getValue()!=0 && cell.getValue() !=_solution.getCell(i,j).getValue())
                            ret.add(cell);
                    }
                }
                break;

            case 1:
                _penalty = _penalty + 60;                                                             //PENALITZACIO PER OBTENIR NUMERO

                if (isComplete()) return null;

                Random random = new Random();
                int range = _board.getSize();
                int i;
                int j;

                while (true) {
                    i = random.nextInt(range);
                    j = random.nextInt(range);
                    Cell cell = _board.getCell(i,j);

                    if (cell.getValue()==0) {
                        cell.setValue(_solution.getCell(i,j).getValue());
                        ret.add(cell);
                        return ret;
                    }
                }

            case 2:
                _penalty = _penalty + 20;                                                           //PENALITZACIo FACT
                return null;
        }

        return ret;
    }

    public boolean isComplete() {
        for (int i=0; i<_board.getSize(); ++i) for (int j=0; j<_board.getSize(); ++j)
            if (_board.getCell(i,j).getValue() ==0) return false;
        return true;
    }

}
