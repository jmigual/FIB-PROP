package dades;

import java.io.Serializable;

/**
 * Player's class to store their name and password
 */
public class Player implements Serializable {

    /**
     * Contains the player name
     */
    private String mName;
    /**
     * Contains the player's username
     */
    private String mUserName;
    /**
     * Contains the player's password hash with SHA-512
     */
    private byte[] mHash;

    /**
     * Constructs a new player with the password's hash, username and name
     *
     * @param name     Player's name
     * @param userName Player's username
     * @param hash     Player's password hash with SHA-512
     */
    public Player(String name, String userName, byte[] hash) {
        this.mName = name;
        this.mUserName = userName;
        this.mHash = hash;
    }

    /**
     * Constructs a new player with the password's hash and username
     *
     * @param userName Player's username
     * @param hash     Player's password hash with SHA-512
     */
    public Player(String userName, byte[] hash) {
        this.mUserName = userName;
        this.mHash = hash;
    }

    /////////////
    // METHODS //
    /////////////

    /**
     * Constructs a new Player with the specified name
     *
     * @param userName Player's username
     */
    public Player(String userName) {
        this.mUserName = userName;
    }

    /**
     * To get the player's username
     * @return String containing the username
     */
    public String getUserName() {
        return mUserName;
    }

    /**
     * To set the player's username
     * @param userName Username to set to the player
     */
    public void setUserName(String userName) {
        this.mUserName = userName;
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

        return mUserName.equals(player.mUserName);
    }

    /**
     * Get the Player's hashCode
     *
     * @return An int containing the Player's hashCode
     */
    @Override
    public int hashCode() {
        return mName.hashCode();
    }

    /**
     * To get the player's password hash
     *
     * @return Returns the password's hash
     */
    public byte[] getHash() {
        return mHash;
    }

    /**
     * To set the player's password hash
     *
     * @param _hash Player's password hash with SHA-512
     */
    public void setHash(byte[] _hash) {
        this.mHash = _hash;
    }

    /**
     * To get the Player's name
     *
     * @return Player's name
     */
    public String getName() {
        return mName;
    }

    /**
     * To set the Player's name
     *
     * @param _name Player's name to set
     */
    public void setName(String _name) {
        this.mName = _name;
    }
}
