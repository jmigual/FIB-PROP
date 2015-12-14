package domini.stats;

import dades.Player;
import dades.Table;

import java.util.ArrayList;

public class KKStats extends Stats {

    public KKStats(Table<Player> players, Table<? extends Playable> games, Table<? extends Matchable> matches)
    {
        super(players,games,matches);
    }

    /////// PLAYER STATS ////////////////////////////////////////////////////////////////////
    public int score(Player player){
        return 10;
    }
}