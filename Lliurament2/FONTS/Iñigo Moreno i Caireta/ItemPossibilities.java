package domini.Basic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class used to store the possibilities from an item, it's useful when there's an extensive computation
 */
public abstract class ItemPossibilities implements Serializable {
    protected boolean[] possibilities;

    protected int maxValue;

    public ItemPossibilities(int n) {
        possibilities = new boolean[n];
        this.maxValue = n;
        for (int i = 0; i < possibilities.length; i++) possibilities[i] = true;
    }

    public boolean getPossibility(int value) {
        return possibilities[value - 1];
    }

    public void setPossibility(int value, boolean possibility) {
        this.possibilities[value - 1] = possibility;
    }

    public boolean[] getPossibilities() {
        return this.possibilities;
    }

    public void setPossibilities(boolean[] p) {
        this.possibilities = p;
    }

    public ArrayList<Boolean> getPossibilitiesList() {
        ArrayList<Boolean> res = new ArrayList<>(possibilities.length);
        for (int i = 0; i < possibilities.length; i++) res.set(i, possibilities[i]);
        return res;
    }

    public abstract void calculatePossibilities();
}
