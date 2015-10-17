package dades;

/**
 * Created by Joan on 17/10/2015.
 */
public class PlayersAdmin {

    Table<Player> _players;

    public PlayersAdmin(Table<Player> players)
    {
        _players = players;
    }

    public void createPlayer(String name, String password)
    {
        String hash = getHash(password);
    }

    // TODO: Improve this function to be a real hash function with SHA-512
    private String getHash(String s) { return s + "pepe"; }
}
