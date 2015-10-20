package domini.Basic;

/**
 * Created by Joan on 19/10/2015.
 */

public class Cell {
    int value;
    boolean[] possibilities;
    boolean[] annotations;
    Region region;
    Column column;
    Row row;

    public Cell(int max, Region region, Column column, Row row) {
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

    public Column getColumn() {
        return column;
    }

    public Row getRow() {
        return row;
    }

    public boolean getPossibility(int value) {
        return possibilities[value - 1];
    }

    public void setPossibility(int value, boolean possibility) {
        this.possibilities[value - 1] = possibility;
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

}
