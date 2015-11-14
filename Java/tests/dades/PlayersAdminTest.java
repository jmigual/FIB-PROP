package dades;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class to check if Players Admin works properly
 */
public class PlayersAdminTest {

    String playerName = "Pepe";
    String password = "123#~~◘123";

    PlayersAdmin p;

    @Before
    public void init() {
        KKDB db = new KKDB();
        p = db.getPlayersAdmin();
    }

    @Test
    public void testCreatePlayer() throws Exception {
        p.createPlayer(playerName, password);
        //assertEquals(playerName, )
    }

    @Test
    public void testChangePassword() throws Exception {
        System.out.println("M'han cridat 2");
    }

    @Test
    public void testCheckLogin() throws Exception {
        System.out.println("M'han cridat 3");
    }
}