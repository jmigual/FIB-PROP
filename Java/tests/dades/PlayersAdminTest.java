package dades;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        Player player = p.createPlayer(playerName, password);
        assertEquals("PlayerName", playerName, player.getName());
        assertArrayEquals("PlayerPassword", PlayersAdmin.getHash(password), player.getHash());
        assertSame("Same player", player, p.getPlayer(playerName));
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