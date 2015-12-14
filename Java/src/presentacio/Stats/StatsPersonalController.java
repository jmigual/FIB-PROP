package presentacio.Stats;

import dades.Player;
import domini.stats.KKStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import presentacio.MainWindow;

import java.io.IOException;

/**
 * Created by Esteve on 14/12/2015.
 */
public class StatsPersonalController extends AnchorPane {

    private KKStats mStats;

    private AnchorPane rootLayout;

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
        mStats = main.getKKStats();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats_Personal.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        createDefault(main.actualPlayer);

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefault(Player actual) {
        puntuaciofm = new Label();
        Integer aux = mStats.score(actual);
        puntuaciofm.textProperty().setValue(aux.toString());

        posiciofm = new Label();
        aux = mStats.rank(actual);
        posiciofm.textProperty().setValue(aux.toString());

        boardsfm = new Label();
        aux = mStats.countMatches(actual);
        boardsfm.textProperty().setValue(aux.toString());

        matchesfm = new Label();
        aux = mStats.countSolvedGames(actual);
        matchesfm.textProperty().setValue(aux.toString());

    }

    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    public boolean getResult() {
        return result;
    }


}
