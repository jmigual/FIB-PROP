package domini.Basic;

import dades.KKDB;
import dades.Player;
import dades.PlayersAdmin;
import dades.Table;
import domini.KKBoard;
import exceptions.PlayerExistsException;
import exceptions.PlayerNotExistsExcepction;
import presentacio.Drivers.DriverAdminPlayers;
import presentacio.Drivers.DriverBoardCreator;
import presentacio.Drivers.DriverKKBoard;
import presentacio.Drivers.DriverMatch;

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
            try {
                _player = _playersAdmin.getPlayer(_driverAP.getCurrentPlayer());
            } catch (PlayerNotExistsExcepction e) {
                try {
                    _player = _playersAdmin.createPlayer("Admin", "admin", "admin");
                } catch (PlayerExistsException ee) {
                    try {
                        _player = _playersAdmin.getPlayer("admin");
                    } catch (PlayerNotExistsExcepction eee) {
                        eee.printStackTrace();
                    }
                }
            }
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
        out.println("4. Que la CPU em resolgui un taulell");
        out.println("5. Veure base de dades");
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
                out.print("Nom del taulell: ");
                KKBoard board = null;
                String nameB = in.next();
                for (KKBoard b : _db.getBoards()) if (b.getName().equals(nameB)) board = b.getCopy();

                if (board==null) out.println("Nom erroni");
                else {
                    DriverKKBoard driverKKB = new DriverKKBoard(board);
                    driverKKB.run();
                }

                break;

            case 5:
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

                _player.setHash(PlayersAdmin.getHash(nPass1));
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
                out.print("Boards creats: ");
                Table <KKBoard> taulaB = _db.getBoards();
                for (KKBoard b : taulaB){
                    if (b.getCreator().equals(_player.getName())) out.print(b.getName() + " ");
                }

                out.print("\n \n");

                out.println("Partides comensades/acabades: ");
                Table <Match> taulaM= _db.getMatches();
                for (Match m : taulaM){
                    String s = "No acabat";
                    if (m.hasFinished()) s = "Acabat  Score : " + m.getScore();
                    if (m.getPlayer().equals(_player.getName())) out.println(m.getBoard().getName() + " " + s);
                }
                out.println();

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
                    out.println(taulaB.get(i).getName() + " de tamany " + Integer.toString(taulaB.get(i).getSize()) +
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
                    p = taulaM.get(i).getPlayer().getName();

                    out.println("Board " + b.getName() +  " Player " + p + "  " + s);
                }
                break;

            case 4:
                out.println("PER IMPLEMENTAR CLASE STATS");
                break;

            case 5:
                break;
        }
    }
}
