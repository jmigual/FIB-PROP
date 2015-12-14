package presentacio.Stats;

import dades.Player;
import dades.PlayersAdmin;
import domini.stats.KKStats;
import exceptions.PlayerNotExistsExcepction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import presentacio.MainWindow;

import java.io.IOException;

/**
 * Created by Esteve on 14/12/2015.
 */
public class StatsGlobalController extends AnchorPane {

    private KKStats mStats;

    private AnchorPane rootLayout;

    private boolean result = false;

    StatsGlobalController(MainWindow main) {
        //mAdmin = main.getPlayersAdmin();
        mStats = main.getKKStats();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats_Global.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    public boolean getResult() {
        return result;
    }


}
