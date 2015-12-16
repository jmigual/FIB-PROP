package presentacio.Stats;

import dades.Player;
import domini.stats.KKStats;
import domini.stats.Ranking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import presentacio.Controller;
import presentacio.MainWindow;

/**
 * Created by Esteve on 14/12/2015.
 */

public class StatsGlobalController extends AnchorPane implements Controller {

    private KKStats mStats;

    private TableView<InfoRankings> table;

    private boolean result = false;

    public StatsGlobalController(MainWindow main) {
        mStats = main.getKKStats();

        createDefault();
    }

    private void createDefault() {
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

        table = new TableView<>();
        table.setItems(getItems());
        table.getColumns().addAll(rankColumn, playerColumn, nameColumn, scoreColumn);

        this.getChildren().add(table);

        AnchorPane.setTopAnchor(table, .0);
        AnchorPane.setLeftAnchor(table, .0);
        AnchorPane.setBottomAnchor(table, .0);
        AnchorPane.setRightAnchor(table, .0);
    }

    public boolean getResult() {
        return result;
    }

    public ObservableList<InfoRankings> getItems() {
        ObservableList<InfoRankings> info = FXCollections.observableArrayList();
        Ranking rank = mStats.rankingGlobal();

        for (int i = 0; i < rank.getSize(); ++i) {
            Player p = rank.getPlayer(i);
            info.add(new InfoRankings(i, p.getUserName(), p.getName(), rank.getValue(i)));
        }

        return info;
    }

    @Override
    public AnchorPane getRootLayout() {
        return this;
    }

    @Override
    public void stop() {

    }

    @Override
    public void setScene(Scene scene) {

    }
}
