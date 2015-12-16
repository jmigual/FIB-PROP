package presentacio.Stats;

import dades.Player;
import dades.Table;
import domini.KKBoard;
import domini.stats.KKStats;
import domini.stats.Playable;
import domini.stats.Ranking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import presentacio.Controller;
import presentacio.MainWindow;

/**
 * Created by Esteve on 14/12/2015.
 */
public class StatsBoardController extends AnchorPane implements Controller {

    private KKStats mStats;

    private AnchorPane rootLayout;

    private boolean result = true;

    private TableView tablefm;

    private Table<KKBoard> boards;

    public StatsBoardController(MainWindow main, KKBoard board) {
        mStats = main.getKKStats();
        boards = main.getTaulers();


        tablefm = new TableView();
        this.getChildren().add(tablefm);

        createDefault(board);
    }

    private void createDefault(KKBoard board) {
        // rank column
        TableColumn<InfoRankings, Integer> rankColumn = new TableColumn<>("Posició");
        rankColumn.setMinWidth(50);
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        // Player column
        TableColumn<InfoRankings, String> playerColumn = new TableColumn<>("Jugador");
        playerColumn.setMinWidth(50);
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        // Name column
        TableColumn<InfoRankings, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Score column
        TableColumn<InfoRankings, Integer> scoreColumn = new TableColumn<>("Puntuació");
        scoreColumn.setMinWidth(50);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));


        //Select game
        ObservableList<InfoRankings> info = getItems(board);
        if (info.size() == 0) {
            tablefm.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cap partida");
            alert.setHeaderText(null);
            alert.setContentText("No s'ha fet cap partida per aquest tauler");
            alert.show();
            result = false;
            return;
        }
        tablefm.setItems(getItems(board));
        tablefm.getColumns().clear();
        tablefm.getColumns().addAll(rankColumn, playerColumn, nameColumn, scoreColumn);

        tablefm.setVisible(true);
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

    public ObservableList<InfoRankings> getItems(Playable game) {
        ObservableList<InfoRankings> info = FXCollections.observableArrayList();
        Ranking r = mStats.recordsGame(game);
        for (int i = 0; i < mStats.recordsGame(game).getSize(); ++i) {
            Player p = r.getPlayer(i);
            info.add(new InfoRankings(i, p.getUserName(), p.getName(), r.getValue(i)));
        }
        return info;
    }
}
