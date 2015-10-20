package domini.Basic;

public class Board {
    public Board(int size) {
        size = size;
    }

    public Cell getCell(int i, int j) {
        return BoardInfo[i][j];
    }

    public void setCell(int i, int j, Cell C) {
        BoardInfo[i][j] = C;
    }
/*
    public abstract boolean hasSoltuion();
    public abstract int numSolotions();
    public abstract Board getSoltution();
    public abstract void Solve();
    public Region[] getRegions(){
        return Regions;
    }
*/

    public int getSize() {
        return 9;
    }


    private Cell[][] BoardInfo;
    private Region[] Regions;
    private Column[] Columns;
    private Row[] Rows;
}
