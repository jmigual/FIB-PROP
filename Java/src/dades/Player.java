package dades;

import java.io.Serializable;

/**
 * Player's class to store their name and password
 */
public class Player implements Serializable {

    /**
     * Contains the player name
     */
    private String _name;

    /**
     * Contains the player's password hash with SHA-512
     */
    private byte[] _hash;

    /////////////
    // METHODS //
    /////////////

    /**
     * Constructs a new player with the password's hash and name
     *
     * @param name  Player's name
     * @param _hash Player's password hash with SHA-512
     */
    public Player(String name, byte[] _hash) {
        this._name = name;
        this._hash = _hash;
    }

    /**
     * Constructs a new Player with the specified name
     *
     * @param name Player's name
     */
    public Player(String name) {
        this._name = name;
    }

    /**
     * Compare if two players are the same, only compares their names
     *
     * @param o Player to compare with
     * @return Returns <b>True</b> if the players have the same name, otherwise returns <b>False</b>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return _name.equals(player._name);

    }

    /**
     * Get the Player's hashCode
     *
     * @return An int containing the Player's hashCode
     */
    @Override
    public int hashCode() {
        return _name.hashCode();
    }

    /**
     * To get the player's password hash
     *
     * @return Returns the password's hash
     */
    public byte[] getHash() {
        return _hash;
    }

    /**
     * To set the player's password hash
     *
     * @param _hash Player's password hash with SHA-512
     */
    public void setHash(byte[] _hash) {
        this._hash = _hash;
    }

    /**
     * To get the Player's name
     *
     * @return Player's name
     */
    public String getName() {
        return _name;
    }

    /**
     * To set the Player's name
     *
     * @param _name Player's name to set
     */
    public void setName(String _name) {
        this._name = _name;
    }
}
