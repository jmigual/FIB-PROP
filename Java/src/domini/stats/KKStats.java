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
        int aux =0;
        for(int i=0; i<_matches.size(); ++i){
            i=i;
            if(_matches.get(i).getPlayer().getName().equals(player.getName()) && _matches.get(i).finished()){
                aux+=_matches.get(i).computeTime();
            }
        }
        return aux;
    }
}