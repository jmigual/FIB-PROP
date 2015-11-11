package dades;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.*;

/**
 * Abstract DB class used to get the default table Input/Output Object Streams
 */
public abstract class DB {

    /**
     * Contains the default data path
     **/
    private static final String dataPath = "./data/";

    /**
     * Constructs an empty DB
     */
    public DB() {
        Path p = Paths.get(dataPath);

        if (Files.notExists(p)) {
            try {
                Files.createDirectories(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads all the data from disc
     */
    public abstract void load();

    /**
     * Saves all data to the disc
     */
    public abstract void save();

    /**
     * To get a stream to save the data
     * @param name Name of the stream to get
     * @return An stream where the data can be stored, it must be closed before the object is deleted
     */
    public static ObjectOutputStream getOutputStream(String name) {
        try {
            OutputStream o = Files.newOutputStream(Paths.get(dataPath + name + ".db"), CREATE, WRITE);
            return new ObjectOutputStream(o);
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
    public static ObjectInputStream getInputStream(String name) throws IOException {
        Files.exists(Paths.get(dataPath + name + ".db"));
        InputStream i = Files.newInputStream(Paths.get(dataPath + name + ".db"), READ);
        return new ObjectInputStream(i);
    }
}
