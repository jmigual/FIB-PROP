package dades;

import java.io.*;

/**
 * Abstract DB class used to store get the default table Input/Output Object Streams
 */
public abstract class DB {

    final String dataPath = "./data/";

    /**
     * Constructs an empty DB
     */
    public DB() {}

    public abstract void load();

    public abstract void save();

    public ObjectOutputStream getOutputStream(String name)
    {
        try
        {
            FileOutputStream f = new FileOutputStream(dataPath + name + ".db");
            return new ObjectOutputStream(f);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ObjectInputStream getInputStream(String name)
    {
        try {
            FileInputStream f = new FileInputStream(dataPath + name + ".db");
            return new ObjectInputStream(f);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }
}
