package domini.Basic;

/**
 * Created by Joan on 19/10/2015.
 */

public class Cell extends ItemPossibilities {
    int value;
    boolean[] annotations;
    Region region;
    Column column;
    Row row;

    //Intentem fer primer Row i despres column amb tot -- Esteve
    public Cell(int max, Row row, Column column) {
        super(max);
        this.column = column;
        this.row = row;
        value = 0;
        possibilities = new boolean[max];
        annotations = new boolean[max];
        for (int i = 0; i < possibilities.length; i++) possibilities[i] = true;
        for (int i = 0; i < possibilities.length; i++) annotations[i] = false;
    }

    public Cell(int max, Region region, Column column, Row row) {
        super(max);
        this.region = region;
        this.column = column;
        this.row = row;
        value = 0;
        possibilities = new boolean[max];
        annotations = new boolean[max];
        for (int i = 0; i < possibilities.length; i++) possibilities[i] = true;
        for (int i = 0; i < possibilities.length; i++) annotations[i] = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Column getColumn() {
        return column;
    }

    public Row getRow() {
        return row;
    }

    public boolean getAnnotation(int value) {
        return annotations[value - 1];
    }

    public void switchAnnotation(int value) {
        this.annotations[value - 1] ^= true;
    }

    public void setAnnotation(int value, boolean annotation) {
        this.annotations[value - 1] = annotation;
    }

    @Override
    public void calculatePossibilities() {
        boolean[] pos = this.possibilities;

        // Get region possibilities
        boolean[] aux = region.getPossibilities();
        for (int i = 0; i < pos.length; ++i) pos[i] &= aux[i];

        // Get column possibilities
        aux = column.getPossibilities();
        for (int i = 0; i < pos.length; ++i) pos[i] &= aux[i];

        // Get row possibilities
        aux = row.getPossibilities();
        for (int i = 0; i < pos.length; ++i) pos[i] &= aux[i];
    }
}
