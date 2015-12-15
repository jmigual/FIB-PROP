package presentacio;

import dades.KKDB;
import dades.Table;
import domini.Basic.Match;
import domini.KKBoard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presentacio.Stats.StatsBoardController;
import presentacio.Stats.StatsGlobalController;
import presentacio.Stats.StatsPersonalController;
import sun.applet.Main;
import presentacio.CollectionView.CollectionViewController;
import presentacio.CollectionView.CollectionViewEditorController;
import presentacio.UserConfig.UserConfigController;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Inigo on 04/12/2015.
 */
public class MainController extends AnchorPane {

    @FXML
    private AnchorPane leftArea;
    private AnchorPane rootLayout;
    private MainWindow main;
    private ControllerSwitch contSwitch;
    private Controller actualController;

    public MainController(MainWindow main) {
        this.main = main;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        contSwitch = new ControllerSwitch(leftArea);
    }

    public AnchorPane getLeftArea() {
        return leftArea;
    }

    public void configureUser() {
        Stage shownStage = new Stage();
        UserConfigController config = new UserConfigController(main);
        shownStage.initModality(Modality.APPLICATION_MODAL);
        shownStage.setScene(new Scene(config.getRootLayout()));
        shownStage.sizeToScene();
        shownStage.show();
    }

    public void showGlobal(){
        Stage shownStage = new Stage();
        StatsGlobalController config = new StatsGlobalController(main);
        shownStage.initModality(Modality.APPLICATION_MODAL);
        shownStage.setScene(new Scene(config));
        shownStage.sizeToScene();
        shownStage.show();
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats/Stats_Global.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            leftArea.getChildren().clear();
            leftArea.getChildren().add(loader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }*/
    }

    public void showPersonal(){
        Stage shownStage = new Stage();
        StatsPersonalController config = new StatsPersonalController(main);
        shownStage.initModality(Modality.APPLICATION_MODAL);
        shownStage.setScene(new Scene(config));
        shownStage.sizeToScene();
        shownStage.show();
    }


    public void showByboard(){
        Stage shownStage = new Stage();
        StatsBoardController config = new StatsBoardController(main);
        shownStage.initModality(Modality.APPLICATION_MODAL);
        shownStage.setScene(new Scene(config.getRootLayout()));
        shownStage.sizeToScene();
        shownStage.show();
    }


    public void exit() {
        Platform.exit();
    }

    public void humanCreateBoardClicked(){
        HBCController hbcc = new HBCController(main);
        contSwitch.add(hbcc);
    }

    public void createMatch(){
        KKDB db = new KKDB();
        db.load();
        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);

        Table<KKBoard> taulaB = db.getBoards();
        Table<Match> taula = db.getMatches();

        if (taulaB.size() > 0) {
            out.println("Hi ha aquests taulers: ");
            for (int i = 0; i < taulaB.size(); i++) {
                out.println(Integer.toString(i) + ": " + taulaB.get(i).getName() + " de tamany " +
                        Integer.toString(taulaB.get(i).getSize())+ " fet per " + taulaB.get(i).getCreator());
            }
            out.println("Quin tauler vols? (si no vols cap posa -1)");

            int aux = in.nextInt();

            if (aux > -1 && aux < taulaB.size()) {
                Match m = new Match(taulaB.get(aux), main.getUsername());
                taula.add(m);
                db.save();

                MatchController mc = new MatchController(m);
                switchController(mc);
            }
        } else out.println("No hi ha taulers a la Base de Dades");

    }

    public void loadMatch() {
        KKDB db = new KKDB();
        db.load();
        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);

        Table<Match> taula = db.getMatches();
        if (taula.size() > 0) {
            out.println("Hi ha aquestes partides: ");

            String s;
            KKBoard b;
            String p;

            for (int i = 0; i < taula.size(); i++) {
                s = "No acabat";
                if (taula.get(i).hasFinished()) s = "Acabat  Score : " + taula.get(i).getScore();
                b = taula.get(i).getBoard();
                p = taula.get(i).getPlayer().getUserName();

                out.println(Integer.toString(i) + ": Board " + b.getName() + " Player " + p + "  " + s);
            }
            out.println("Quin match vols reanudar? ");

            int n = in.nextInt();
            MatchController mc = new MatchController(taula.get(n));
            switchController(mc);
        }

        else out.println("No hi ha cap Match guardat");
    }

    public void cpuCreateBoardClicked(){

    }

    public AnchorPane getRootLayout() {
        return rootLayout;
    }



    private void switchController(Controller c){
        if (actualController!=null)actualController.stop();
        leftArea.getChildren().clear();
        AnchorPane newPane = c.getRootLayout();
        AnchorPane.setBottomAnchor(newPane, 0.);
        AnchorPane.setTopAnchor(newPane, 0.);
        AnchorPane.setLeftAnchor(newPane, 0.);
        AnchorPane.setRightAnchor(newPane, 0.);
        leftArea.getChildren().add(newPane);
        actualController=c;
        c.setScene(this.getScene());
    }

    public void dialogCancelled() {
        contSwitch.switchController(null);
    }

    @FXML
    private void editBoard() {
        contSwitch.switchController(new CollectionViewEditorController(main));
    }

    public ControllerSwitch getContSwitch() { return contSwitch; }
}
