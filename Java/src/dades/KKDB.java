package dades;

/**
 * Class used only in the Ken-Ken program
 */
public class KKDB extends DB{

    private Table<Player> _players;

    public KKDB()
    {
        load();
    }

    public PlayersAdmin getPlayersAdmin()
    {
        return new PlayersAdmin(_players);
    }

    public void save()
    {
        _players.save(getOutputStream("players"));
    }

    public void load()
    {
        _players.load(getInputStream("players"));
    }
}
