package domini.Basic;

import java.io.Serializable;

/**
 * Created by Joan on 15/10/2015.
 */
public class Column extends CellLine implements Serializable{
    public Column(int size, int pos) {
        super(size, pos);
    }
}
