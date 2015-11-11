package presentacio;

import dades.KKDB;
import dades.PlayersAdmin;
import domini.Basic.Driver;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Driver to test AdminPlayers and DB
 */
public class DriverAdminPlayers implements Driver {

    private PlayersAdmin _pAdmin;

    private String _currentPlayer;

    private PrintStream out = System.out;

    private Scanner in = new Scanner(System.in);

    public DriverAdminPlayers(PlayersAdmin p) {
        this._pAdmin = p;
        _currentPlayer = "";
    }

    /**
     * Main function of the DriverAdminPlayers' class
     *
     * @param args Basic arguments to enter, they're ignored
     */
    public static void main(String[] args) {

        KKDB db = new KKDB();
        db.load();
        PlayersAdmin p = db.getPlayersAdmin();
        DriverAdminPlayers pa = new DriverAdminPlayers(p);
        pa.run();
        db.save();
    }

    /**
     * Shows the console input to allow a Player log in and checks some parameters
     */
    public void loginUser() {
        out.print("Nom d'usuari: ");
        String name = in.next();
        out.print("Contrasenya: ");
        String pass = in.next();

        if (!_pAdmin.checkLogin(name, pass)) {
            System.err.println("No s'ha pogut iniciar sessió nom d'usuari o contrasenya incorrectes");
        }

        _currentPlayer = name;
    }

    /**
     * Removes the current logged in user
     */
    public void logoutUser() {
        _currentPlayer = "";
    }

    /**
     * Shows the console input to check if a Player exists
     */
    public void checkPlayer() {
        out.print("Nom d'usuari: ");

        if (_pAdmin.exists(in.next())) out.println("L'usuari existeix");
        else out.println("L'usuari no existeix");
    }

    /**
     * Shows a menu and allows the user to interactuate with the PlayersAdmin
     */
    public void run() {
        while (true) {
            out.println("Selecciona una opció:\n" +
                    "1) Afegir jugador\n" +
                    "2) Eliminar jugador\n" +
                    "3) Canviar contrasenya\n" +
                    "4) Comprovar si existeix un jugador\n" +
                    "5) Iniciar sessió\n" +
                    "6) Tancar sessió\n" +
                    "7) Veure tots els usuaris\n" +
                    "8) Sortir");

            if (!in.hasNextInt()) break;

            switch (in.nextInt()) {
                // Add player
                case 1: {
                    boolean correct = false;
                    String name = "";
                    String pass = "";
                    while (!correct) {
                        while (name.length() == 0) {
                            out.print("Entra el nom d'usuari: ");
                            name = in.next();

                            if (name.length() == 0) out.println("El nom d'usuari no pot ser buit");
                        }

                        while (pass.length() < 8) {
                            out.println("La contrasenya ha de ser com a mínim de 8 caràcters");
                            out.print("Entra la contrasenya: ");
                            pass = in.next();
                        }

                        out.println("Nom: " + name + " contrasenya: " + pass);
                        out.print("És correcte (S/N)? ");
                        String temp = in.next();
                        temp = temp.toLowerCase();

                        correct = "s".equals(temp) || "y".equals(temp) || "si".equals(temp) || "yes".equals(temp);
                    }

                    _pAdmin.createPlayer(name, pass);

                    break;
                }
                // Delete player
                case 2: {
                    out.print("Nom d'usuari: ");
                    String name = in.next();
                    out.print("Contrasenya: ");
                    String pass = in.next();

                    if (!_pAdmin.removePlayer(name, pass)) {
                        System.err.println("Error eliminant l'usuari, nom o contrasenya incorrectes");
                    }
                    break;
                }
                // Change password
                case 3: {
                    out.print("Nom d'usuari: ");
                    String name = in.next();
                    out.print("Contrasenya: ");
                    String pass = in.next();
                    out.print("Nova contrasenya: ");
                    String nPass1 = in.next();
                    out.print("Repeteix la nova contrasenya: ");
                    String nPass2 = in.next();

                    if (!nPass1.equals(nPass2)) {
                        System.err.println("La nova contrasenya no coincideix en els dos casos");
                        break;
                    }

                    if (!_pAdmin.changePassword(name, pass, nPass1)) {
                        System.err.println("No s'ha pogut canviar la contrasenya comprova que el nom d'usuari i la\n" +
                                "contrasenya són correctes");
                    }

                    break;
                }
                // Check player
                case 4: {
                    this.checkPlayer();
                    break;
                }
                // Log in
                case 5: {
                    this.loginUser();
                    break;
                }
                // Close session
                case 6: {
                    this.logoutUser();
                    break;
                }
                // View all players
                case 7: {
                    _pAdmin.viewAllPlayers();
                    break;
                }
                case 8: {
                    return;
                }
            }
        }
    }

    /**
     * To get the current logged in player
     *
     * @return Current logged in player
     */
    public String getCurrentPlayer() {
        return _currentPlayer;
    }
}
