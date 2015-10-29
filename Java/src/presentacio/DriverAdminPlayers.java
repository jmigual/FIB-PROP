package presentacio;

import dades.KKDB;
import dades.PlayersAdmin;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by Joan on 29/10/2015.
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

    public DriverAdminPlayers(PlayersAdmin p) { this._pAdmin = p; }

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
                    "6) Tancar sessió" +
                    "7) Sortir");

            if (! in.hasNextInt()) break;

            switch (in.nextInt()) {

            }
        }
    }
}
