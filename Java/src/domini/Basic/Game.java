package domini.Basic;

import dades.KKDB;
import dades.Player;
import dades.PlayersAdmin;
import dades.Table;
import domini.KKBoard;
import presentacio.DriverAdminPlayers;
import presentacio.DriverBoardCreator;
import presentacio.DriverMatch;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static KKDB _db;
    private static Player _player;

    private static PrintStream out = System.out;
    private static Scanner in = new Scanner(System.in);

    private static PlayersAdmin _playersAdmin;
    private static DriverAdminPlayers _driverAP;

    private static boolean logged;

    public static void main(String[] args) {
        while (true){
            _db = new KKDB();
            _db.load();
            _playersAdmin = _db.getPlayersAdmin();
            _driverAP = new DriverAdminPlayers(_playersAdmin);

            while (_driverAP.getCurrentPlayer().equals("")) login();
            _player = _playersAdmin.getPlayer(_driverAP.getCurrentPlayer());
            logged = true;

            while (logged) mainMenu();
        }
    }

    private static void login() {
        out.println();
        out.println("1. Entrar amb un usuari existent");
        out.println("2. Crear nou usuari");

        switch (in.nextInt()) {
            case 1:
                _driverAP.loginUser();

                break;
            case 2:
                _driverAP.createPlayer();
                _db.save();
        }
    }

    private static void mainMenu(){
        out.println();
        out.println("1. El meu usuari");
        out.println("2. Jugar!!");
        out.println("3. Crear un taulell");
        out.println("4. Veure base de dades");
        out.println("0. Tancar sessio");

        switch (in.nextInt()){
            case 1:
                userMenu();
                break;

            case 2:
                String[] s = new String[1];
                s[0] = _player.getName();

                _db.save();
                DriverMatch.main(s);
                _db.load();
                break;

            case 3:
                String[] s2 = new String[1];
                s2[0] = _player.getName();

                _db.save();
                DriverBoardCreator.main(s2);
                _db.load();
                break;
            case 4:
                dbMenu();
                break;

            case 0:
                logged = false;
        }
    }

    private static void userMenu(){
        out.println();
        out.println("Usuari: " + _player.getName());
        out.println("1. Modificar contrasenya");
        out.println("2. Eliminar usuari");
        out.println("3. Veure stats personals");
        out.println("4. Enrere");

        switch (in.nextInt()){
            case 1:
                out.print ("Nova contrasenya: ");
                String nPass1 = in.next();
                out.print("Repeteix la nova contrasenya: ");
                String nPass2 = in.next();

                if (!nPass1.equals(nPass2)) {
                    System.err.println("La nova contrasenya no coincideix en els dos casos");
                    break;
                }

                try {
                    _player.setHash(PlayersAdmin.getHash(nPass1));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                _db.save();
                break;

            case 2:
                out.print("Torna a entrar la teva contrasenya: ");
               _playersAdmin.removePlayer(_player.getName(), in.next());
                _db.save();
                out.println("La compte '" + _driverAP.getCurrentPlayer() + "' ha estat eliminada!");
                logged = false;
                break;

            case 3:
                break;

            case 4:
                break;

        }
    }

    private static void dbMenu(){
        out.println();
        out.print("1. Veure colleccio de taulells\n" +
                "2. Veure llista d'usuaris\n" +
                "3. Veure partides\n" +
                "4. Ranking/Stats?\n" +
                "5. Enrere\n");
        switch (in.nextInt()){
            case 1:
                Table<KKBoard> taulaB = _db.getBoards();
                for (int i = 0; i < taulaB.size(); i++) {
                    out.println(taulaB.get(i).get_name() + " de tamany " + Integer.toString(taulaB.get(i).getSize()) +
                            " fet per " + taulaB.get(i).getCreator());
                }
                break;

            case 2:
                ArrayList<String> players = _playersAdmin.getAllPlayersNames();
                for (int i = 0; i < players.size(); i++) out.println(players.get(i));
                break;

            case 3:
                Table<Match> taulaM = _db.getMatches();
                String s;
                KKBoard b;
                String p;

                for (int i = 0; i < taulaM.size(); i++) {
                    s = "No acabat";
                    if (taulaM.get(i).hasFinished()) s = "Acabat  Score : " + taulaM.get(i).getScore();

                    b = taulaM.get(i).getBoard();
                    p = taulaM.get(i).getPlayer();

                    out.println("Board " + b.get_name() +  " Player " + p + "  " + s);
                }
                break;

            case 4:
                break;

            case 5:
                break;
        }
    }

}
