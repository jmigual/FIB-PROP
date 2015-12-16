package domini.stats;

import dades.Player;
import dades.Table;

import java.util.ArrayList;

public class KKStats extends Stats {

    public KKStats(Table<Player> players, Table<? extends Playable> games, Table<? extends Matchable> matches)
    {
        super(players,games,matches);
    }

    /////// PLAYER STATS ///////
    public int score(Player player){
        int aux = 0;
        for (Matchable m : _matches) if (m.getPlayer().equals(player) && m.finished()) aux += m.computeTime();
        return aux;
    }

    public int rank(Player player) {
        int score = score(player), rank = 1;
        for (Player p : _players) if (score > score(p)) ++rank;
        return rank;
    }

    public int countMatches(Player player) {
        int count = 0;
        for (Matchable m : _matches) if (m.getPlayer().equals(player)) ++count;
        return count;
    }
}