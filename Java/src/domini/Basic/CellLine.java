package domini.Basic;

/**
 * Created by Inigo on 19/10/2015.
 */
public class CellLine extends Region {
    protected int pos;

    public CellLine(int size, int pos) {
        super(size, size);
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void calculatePossibilities() {
        for (int i = 0; i < possibilities.length; i++) possibilities[i] = true;
        for (int i = 0; i < cells.size(); i++) {
            int pos = cells.get(i).getValue() - 1;
            if (pos != -1) possibilities[pos] = false;
        }

    }

    public boolean isCorrect() {
        boolean[] appeared = new boolean[possibilities.length];
        for (int i = 0; i < appeared.length; i++) appeared[i] = false;
        for (int i = 0; i < cells.size(); i++) {
            int pos = cells.get(i).getValue() - 1;
            if (pos != -1) {
                if (appeared[pos]) return false;
                else appeared[pos] = true;
            }
        }
        return true;
    }
}
