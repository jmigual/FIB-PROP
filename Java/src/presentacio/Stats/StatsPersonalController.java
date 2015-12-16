package presentacio.Stats;

import dades.Player;
import domini.stats.KKStats;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import presentacio.Controller;
import presentacio.MainWindow;

import java.io.IOException;

/**
 * Created by Esteve on 14/12/2015.
 */
public class StatsPersonalController extends AnchorPane implements Controller {

    private KKStats mStats;

    private AnchorPane rootLayout;

    private MainWindow mMain;

    private TableView<InfoRanking> table;

    @FXML
    private Label puntuaciofm;

    @FXML
    private Label posiciofm;

    @FXML
    private Label boardsfm;

    @FXML
    private Label matchesfm;



    private boolean result = false;

    public StatsPersonalController(MainWindow main) {
        mMain = main;
        mStats = main.getKKStats();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats_Personal.fxml"));
        loader.setRoot(this);
        loader.setController(this);


        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        createDefault();
    }

    private void createDefault() {
        Player actual = mMain.getPlayer();

        puntuaciofm.textProperty().setValue(Integer.toString(mStats.score(actual)));
        posiciofm.textProperty().setValue(Integer.toString(mStats.rank(actual)));
        boardsfm.textProperty().setValue(Integer.toString(mStats.countMatches(actual)));
        matchesfm.textProperty().setValue(Integer.toString(mStats.countSolvedGames(actual)));
    }

    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void stop() {

    }

    @Override
    public void setScene(Scene scene) {

    }

    public boolean getResult() {
        return result;
    }


}
