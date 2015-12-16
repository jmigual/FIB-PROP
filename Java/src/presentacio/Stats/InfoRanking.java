package presentacio.Stats;

/**
 * Created by esteve on 14/12/2015.
 */
public class InfoRanking {
    private Integer rank;
    private String name;
    private String userName;
    private Integer score;

    public InfoRanking(){
        this.rank = -1;
        this.name = "";
        this.score = 0;
    }
    public InfoRanking(int rank, String userName, String name, int score){
        this.rank = rank;
        this.userName = userName;
        this.name = name;
        this.score = score;
    }

    // Comentari troll perquè no es canvii de nom

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
