package dades;

import java.io.*;

/**
 * Abstract DB class used to get the default table Input/Output Object Streams
 */
public abstract class DB {

    /** Contains the default data path **/
    final String dataPath = "./data/";

    /** Constructs an empty DB */
    public DB() {}

    /** Loads all the data from disc */
    public abstract void load();

    /** Saves all data to the disc */
    public abstract void save();

    /**
     * To get a stream to save the data
     * @param name Name of the stream to get
     * @return An stream where the data can be stored, it must be closed before the object is deleted
     */
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

    /**
     * To get a location where the data can be read
     * @param name Name of the stream to get
     * @return A stream where the data can be read, it must be closed before the object is destroyed
     */
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
