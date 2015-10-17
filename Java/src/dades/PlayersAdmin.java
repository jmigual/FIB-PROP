package dades;

/**
 * Manages the creation, deletion and existence of players
 */
public class PlayersAdmin {

    Table<Player> _players;

    public PlayersAdmin(Table<Player> players)
    {
        _players = players;
    }

    public boolean createPlayer(String name, String password)
    {
        // Create the password's hash and the player to check
        String hash = getHash(password);
        Player p = new Player(name, hash);

        // Check if the player's already added
        if (_players.contains(p)) return false;
        _players.add(p);

        return true;
    }

    public boolean changePassword(String name, String currentPassword, String newPassword)
    {
        String hash = getHash(newPassword);
        Player p = new Player(name, hash);

        // Search if the player exists
        if (_players.contains(p))
        {
            // Get the player
            Player p2 = _players.get(_players.indexOf(p));

            // Check if the password matches
            if (! p2.getHashPassword().equals(getHash(currentPassword))) return false;
            // Change the password (hash)
            p2.setHashPassword(hash);
            return true;
        }
        return false;
    }

    public boolean checkLogin(String name, String password)
    {
        Player p = new Player(name);
        return _players.contains(p) && _players.get(_players.indexOf(p)).getHashPassword().equals(getHash(password));
    }

    public boolean exists(String name) { return _players.contains(new Player(name)); }

    public boolean removePlayer(String name, String password)
    {
        String hash = getHash(password);
        Player p = new Player(name, hash);

        if (_players.contains(p))
        {
            int index = _players.indexOf(p);
            // Check if the password's hash matches
            if (_players.get(index).getHashPassword().equals(hash))
            {
                // Delete the player
                _players.remove(index);
                return true;
            }
        }
        return false;
    }

    // TODO: Improve this function to be a real hash function with SHA-512
    private String getHash(String s) { return s + "pepe"; }
}
