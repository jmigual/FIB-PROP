package dades;

import exceptions.PlayerExistsException;
import exceptions.PlayerNotExistsExcepction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Manages the creation, deletion and existence of players
 */
public class PlayersAdmin {

    /**
     * Contains all the players from the DB
     */
    private Table<Player> _players;

    /**
     * PlayersAdmin constructor, creates a player administrator
     *
     * @param players Table containing all players
     */
    public PlayersAdmin(Table<Player> players) {
        _players = players;
    }

    /**
     * Creates a new player without name
     *
     * @param userName Player's username, must be unique
     * @param password Player's password
     * @return The player added
     * @throws PlayerExistsException Thrown when exists a player with the same userName
     */
    public Player createPlayer(String userName, String password) throws PlayerExistsException {
        return this.createPlayer("", userName, password);
    }

    /**
     * Creates a new player
     *
     * @param name     Player's name
     * @param userName Player's username, must be unique
     * @param password Player's, password
     * @return Returns the player added
     * @throws PlayerExistsException It's thrown when exists a player with the same userName
     */
    public Player createPlayer(String name, String userName, String password) throws PlayerExistsException {
        // Create the password's hash and the player to check
        byte[] hash = getHash(password);
        Player p = new Player(name, userName, hash);

        // Check if the player's already added
        if (_players.contains(p)) throw new PlayerExistsException("The user already exists");
        _players.add(p);
        return p;
    }
    
    public void changeName(String name, String userName) {
        try {
            Player p = getPlayer(userName);
            p.setName(name);
        } catch (PlayerNotExistsExcepction e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the password from a player
     *
     * @param userName        Player's name
     * @param currentPassword Current player's password
     * @param newPassword     New player's password
     * @return <b>True</b> if the player is found and the password is changed successfully, otherwise returns
     * <b>False</b>
     */
    public boolean changePassword(String userName, String currentPassword, String newPassword) {
        byte[] hash = getHash(newPassword);
        Player p = new Player(userName, hash);

        // Search if the player exists
        if (_players.contains(p)) {
            // Get the player
            Player p2 = _players.get(_players.indexOf(p));

            // Check if the password matches
            if (!Arrays.equals(p2.getHash(), getHash(currentPassword))) return false;
            // Change the password (hash)
            p2.setHash(hash);
            return true;
        }
        return false;
    }

    /**
     * Checks if a player exists and has the selected password
     *
     * @param userName Player's username
     * @param password Player's password
     * @return <b>True</b> if the Player exists and has the selected password
     */
    public boolean checkLogin(String userName, String password) {
        try {
            return checkLogin(getPlayer(userName), password);
        } catch (PlayerNotExistsExcepction e) {
            return false;
        }
    }

    /**
     * Checks if a player has the selected password
     *
     * @param p        Player to check
     * @param password Password to check
     * @return <em>True</em> if the password matches
     */
    public boolean checkLogin(Player p, String password) {
        return Arrays.equals(p.getHash(), getHash(password));
    }

    /**
     * Checks if a player exists
     *
     * @param userName Player's name
     * @return <b>True</b> if the player exists, otherwise returns False
     */
    public boolean exists(String userName) {
        return _players.contains(new Player(userName));
    }

    /**
     * Removes the Player from the database permanently
     *
     * @param userName Player's userName
     * @param password Player's password
     * @return <b>True</b> if the player is removed
     */
    public boolean removePlayer(String userName, String password) {
        byte[] hash = getHash(password);
        Player p = new Player(userName, hash);

        if (_players.contains(p)) {
            int index = _players.indexOf(p);
            // Check if the password's hash matches
            if (Arrays.equals(_players.get(index).getHash(), hash)) {
                // Delete the player
                _players.remove(index);
                return true;
            }
        }

        return false;
    }

    public void viewAllPlayers() {
        for (Player p : _players) {
            System.out.println(p.getName() + " " + Arrays.toString(p.getHash()));
        }
    }

    public ArrayList<String> getAllPlayersNames() {
        ArrayList<String> ret = new ArrayList<>(_players.size());

        ret.addAll(_players.stream().map(Player::getName).collect(Collectors.toList()));
        return ret;
    }

    /**
     * To get the hash of a string
     *
     * @param s String to be hashed
     * @return byte[] containing the hash
     */
    public static byte[] getHash(String s) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            return md.digest(s.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ("asdf" + s).getBytes();
        }
    }

    /**
     * To get the player with the specified name
     *
     * @param userName Player's name
     * @return The selected player
     */
    public Player getPlayer(String userName)throws PlayerNotExistsExcepction {
        Player p = new Player(userName);
        if (!_players.contains(p)) throw new PlayerNotExistsExcepction();

        return _players.get(_players.indexOf(p));
    }
}
