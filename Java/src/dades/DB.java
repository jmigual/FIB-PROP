package dades;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Joan on 15/10/2015.
 */
public abstract class DB {



    /**
     * Constructs an empty DB
     */
    public DB() {}

    public abstract void load();

    public abstract void save();

    private ObjectOutputStream getOutputStream(String name)
    {
        FileOutputStream f = new FileOutputStream(name + ".db");
    }
}
