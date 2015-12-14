package presentacio.Stats;

/**
 * Created by esteve on 14/12/2015.
 */
public class infoRanking {
    Integer rank;
    String name;
    Integer score;

    public infoRanking(){
        this.rank = -1;
        this.name = "";
        this.score = 0;
    }

    public infoRanking(int rank,String name,int score){
        this.rank = rank;
        this.name = name;
        this.score = score;
    }
}
