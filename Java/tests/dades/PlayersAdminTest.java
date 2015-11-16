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
    String newPassword = "12345678";

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
        Player player = p.createPlayer(playerName, password);
        assertTrue("Change password", p.changePassword(playerName, password, newPassword));
        assertArrayEquals("PlayerNewPassword", PlayersAdmin.getHash(newPassword), player.getHash());
    }

    @Test
    public void testCheckLogin() throws Exception {
        Player player = p.createPlayer(playerName, password);
        assertTrue("Check login", p.checkLogin(playerName, password));
    }
}