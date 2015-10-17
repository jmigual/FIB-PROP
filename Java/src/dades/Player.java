package dades;

import java.io.Serializable;

/**
 * Player's class to store their name and password
 */
public class Player implements Serializable {

    /**
     * Contains the player name
     */
    private String playerName;

    /**
     * Contains the player's password hash with SHA-512
     */
    private byte[] hashPassword;

    public Player(String playerName, byte[] hashPassword)
    {
        this.playerName = playerName;
        this.hashPassword = hashPassword;
    }

    public Player(String playerName)
    {
        this.playerName = playerName;
    }

    public boolean equals(Player p) { return p.playerName.equals(this.playerName); }

    public String getPlayerName() { return playerName; }

    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public byte[] getHashPassword() { return hashPassword; }

    public void setHashPassword(byte[] hashPassword) { this.hashPassword = hashPassword; }
}
