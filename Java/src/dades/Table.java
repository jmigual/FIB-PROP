package dades;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("unchecked")

/** Table class used to store elements and load and save data from Serializable objects
 */
public class Table<E extends Serializable> extends ArrayList<E> {

    /** Constructs an empty table **/
    public Table() {}

    /** Constructs an empty Table with the specified initial capacity and with its capacity increment equal to zero */
    public Table(int initialCapacity) { super (initialCapacity); }

    /**
     * Constructs a table containing the elements of the specified collection, in the order they are returned by the
     * collection's iterator
     */
    public Table(Collection<? extends E> c) { super (c); }

    /**
     * Load all the data from the Stream, this method overwrites all data in the table
     */
    public void load(ObjectInputStream in)
    {
        this.clear();
        int size = 0;
        try {
            size = in.readInt();
            this.ensureCapacity(size);

            for (int i = 0; i < size; ++i) {
                this.add((E) in.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    /**
     * Save all the data to the Stream
     */
    public void save(ObjectOutputStream out)
    {
        try {
            out.writeInt(this.size());
            for(E aux : this) out.writeObject(aux);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
