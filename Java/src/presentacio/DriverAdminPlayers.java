package presentacio;

import dades.KKDB;
import dades.PlayersAdmin;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Driver to test AdminPlayers and DB
 */
public class DriverAdminPlayers {

    PlayersAdmin _pAdmin;

    String _currentPlayer;

    public static void main(String[] args) {

        KKDB db = new KKDB();
        db.load();
        PlayersAdmin p = db.getPlayersAdmin();
        DriverAdminPlayers pa = new DriverAdminPlayers(p);
        pa.run();
        db.save();
    }

    public DriverAdminPlayers(PlayersAdmin p) { this._pAdmin = p; _currentPlayer = ""; }

    public void run() {
        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);

        while (true) {
            out.println("Selecciona una opció:\n" +
                    "1) Afegir jugador\n" +
                    "2) Eliminar jugador\n" +
                    "3) Iniciar sessió\n" +
                    "4) Canviar contrasenya\n" +
                    "5) Comprovar si existeix un jugador\n" +
                    "6) Tancar sessió\n" +
                    "7) Sortir");

            if (! in.hasNextInt()) break;

            switch (in.nextInt()) {
                // Add player
                case 1:
                {
                    boolean correct = false;
                    String name;
                    String pass;
                    while (! correct) {
                        out.print("Entra el nom d'usuari: ");
                        name = in.next();
                        out.print("Entra la contrasenya: ");
                        pass = in.next();

                        out.println("Nom: " + name + " contrasenya: " + pass);
                        out.print("És correcte (S/N)? ");
                        String temp = in.next();
                        temp = temp.toLowerCase();

                        correct =  temp.equals("s") || temp.equals("y") || temp.equals("si") || temp.equals("yes");
                    }

                    break;
                }
                // Delete player
                case 2:
                {
                    break;
                }
                // Log in
                case 3:
                {
                    break;
                }
                // Change password
                case 4:
                {
                    break;
                }
                // Check login
                case 5:
                {
                    break;
                }
                // Close session
                case 6:
                {
                    break;
                }
                // Exit
                case 7:
                {

                    return;
                }
            }
        }
    }

    public String getCurrentPlayer() {
        return _currentPlayer;
    }
}
