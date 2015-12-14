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

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.io.IOException;

/**
 * Created by Esteve on 14/12/2015.
 */
public class StatsGlobalController extends AnchorPane {

    private KKStats mStats;

    private AnchorPane rootLayout;

    @FXML
    private TableView Taula;

    @FXML
    private TableColumn columnaRank;

    @FXML
    private TableColumn columnaName;

    @FXML
    private TableColumn columnaScore;


    public StatsGlobalController(MainWindow main, javafx.scene.layout.StackPane Lay) {
        //mStats = main.getKKStats();
        //Taula = new TableView();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats_Global.fxml"));
        loader.setController(this);

        try {
            Lay = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Taula.

    }

    public AnchorPane getRootLayout() {
        return rootLayout;
    }


}
