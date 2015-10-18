package domini;

public class Board {

    public Cell getCell(int i, int j) {
        return BoardInfo[i][j];
    }

    public void setCell(int i, int j, Cell C){
        BoardInfo[i][j] = C;
    }

    public abstract boolean hasSoltuion();
    public abstract int numSolotions();
    public abstract Board getSoltution();
    public abstract void Solve();


    private Cell[][] BoardInfo;
    private Region[] Regions;
    private Column[] Columns;
    private Row[] Rows;
}
