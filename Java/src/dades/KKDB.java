package dades;

/**
 * Class used only in the Ken-Ken program
 */
public class KKDB extends DB{

    public Table<Player> _players;

    public void save()
    {
        _players.save(getOutputStream("players"));
    }

    public void load()
    {
        _players.load(getInputStream("players"));
    }
}
