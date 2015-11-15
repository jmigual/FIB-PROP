package domini.Basic;

import dades.KKDB;
import dades.Player;
import dades.PlayersAdmin;
import presentacio.DriverAdminPlayers;
import presentacio.DriverBoardCreator;
import presentacio.DriverMatch;

import java.io.PrintStream;
import java.util.Scanner;

public class Game {

    private static Match _match;
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

            case 0:
                logged = false;
        }
    }

    private static void userMenu(){
        out.println();
        out.println("Usuari: " + _player.getName());
        out.println("1. Modificar contrasenya");
        //out.println("2. Eliminar usuari");
        out.println("2. Enrere");

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
                break;

        }
    }



}
