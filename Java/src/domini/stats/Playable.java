package domini.stats;

// aquesta interface és la que ens permetrà fer servir stats tant amb game com amb board.

import java.io.Serializable;

public interface Playable extends Serializable {
    int getID();
    int getSize();
}