package dades;

import java.io.Serializable;

/**
 * Created by Joan on 17/10/2015.
 */
public class Player implements Serializable {

    /**
     * Contains the player name
     */
    private String playerName;

    /**
     * Contains the player's password hash with SHA-512
     */
    private String hashPassword;

    public boolean equals(Player p) { return p.playerName.equals(this.playerName); }

    public String getPlayerNameName() { return playerName; }

    public void setPlayerNameName(String playerName) { this.playerName = playerName; }

    public String getHashPassword() { return hashPassword; }

    public void setHashPassword(String hashPassword) { this.hashPassword = hashPassword; }
}
