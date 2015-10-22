package domini.Basic;

/**
 * Class used to store the possibilities from an item, it's useful when there's an extensive computation
 */
public abstract class ItemPossibilities {
    protected boolean[] possibilities;

    protected int maxValue;

    public ItemPossibilities(int n) {
        possibilities = new boolean[n];
        this.maxValue = n;
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

    public abstract void calculatePossibilities();
}
