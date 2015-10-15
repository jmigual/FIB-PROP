package dades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/** Table class used to store elements and load and save data from Serializable objects
 */
public class Table<E extends Serializable> extends ArrayList<E> {

    /** Constructs an empty table **/
    public Table() {}

    /** Constructs an empty Table with the specified initial capacity and with its capacity increment equal to zero */
    public Table(int initialCapacity) { super (initialCapacity); }

    /** Constructs a table containing the elements of the specified collection, in the order they are returned by the
     * collection's iterator
     */
    public Table(Collection<? extends E> c) { super (c); }
}
