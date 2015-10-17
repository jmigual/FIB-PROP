package dades;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Manages the creation, deletion and existence of players
 */
public class PlayersAdmin {

    Table<Player> _players;

    /**
     * PlayersAdmin constructor, creates a player administrator
     * @param players Table containing all players
     */
    public PlayersAdmin(Table<Player> players) {
        _players = players;
    }

    /**
     * Creates a new player
     * @param name Player's name, must be unique
     * @param password Player's, password
     * @return <b>True</b> if the player is added successfully
     */
    public boolean createPlayer(String name, String password) {
        try {
            // Create the password's hash and the player to check
            byte[] hash = getHash(password);
            Player p = new Player(name, hash);

            // Check if the player's already added
            if (_players.contains(p)) return false;
            _players.add(p);

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Changes the password from a player
     * @param name Player's name
     * @param currentPassword Current player's password
     * @param newPassword New player's password
     * @return <b>True</b> if the player is found and the password is changed successfully, otherwise returns
     * <b>False</b>
     */
    public boolean changePassword(String name, String currentPassword, String newPassword) {
        try {
            byte[] hash = getHash(newPassword);
            Player p = new Player(name, hash);

            // Search if the player exists
            if (_players.contains(p)) {
                // Get the player
                Player p2 = _players.get(_players.indexOf(p));

                // Check if the password matches
                if (!Arrays.equals(p2.getHashPassword(), getHash(currentPassword))) return false;
                // Change the password (hash)
                p2.setHashPassword(hash);
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if a player exists and has the selected password
     * @param name Player's name
     * @param password Player's password
     * @return <b>True</b> if the Player exists and has the selected password
     * @throws Exception General exception with the error message
     */
    public boolean checkLogin(String name, String password) throws Exception {
        Player p = new Player(name);

        if (_players.contains(p)) {
            try {
                // Check if the introduced password's hash and the user hash match
                return Arrays.equals(_players.get(_players.indexOf(p)).getHashPassword(), getHash(password));
            } catch (Exception e){
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks if a player exists
     * @param name Player's name
     * @return <b>True</b> if the player exists, otherwise returns False
     */
    public boolean exists(String name) {
        return _players.contains(new Player(name));
    }

    /**
     * Removes the Player from the database permanently
     * @param name Player's name
     * @param password Player's password
     * @return <b>True</b> if the player is removed
     * @throws Exception General exception with the error message
     */
    public boolean removePlayer(String name, String password) throws Exception {
        byte[] hash;
        try {
            hash = getHash(password);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) { throw new Exception(e); }
        Player p = new Player(name, hash);

        if (_players.contains(p)) {
            int index = _players.indexOf(p);
            // Check if the password's hash matches
            if (Arrays.equals(_players.get(index).getHashPassword(), hash)) {
                // Delete the player
                _players.remove(index);
                return true;
            }
        }
        return false;
    }


    private byte[] getHash(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return md.digest(s.getBytes("UTF-8"));
    }
}
