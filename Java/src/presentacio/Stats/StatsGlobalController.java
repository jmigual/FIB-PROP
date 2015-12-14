package presentacio.Stats;

import dades.Player;
import dades.PlayersAdmin;
import domini.stats.KKStats;
import exceptions.PlayerNotExistsExcepction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private TableView<InfoRanking> table;

    private boolean result = false;

    public StatsGlobalController(MainWindow main) {
        mStats = main.getKKStats();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats_Global.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        createDefault();

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefault() {
        // Name column
        TableColumn<InfoRanking, String> nameColumn = new TableColumn<>("Player");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Score column
        TableColumn<InfoRanking, String> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setMinWidth(50);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        table = new TableView<>();
        table.setItems(getStubItems());
        table.getColumns().addAll(nameColumn, scoreColumn);

        this.getChildren().add(table);

        AnchorPane.setTopAnchor(table, .0);
        AnchorPane.setLeftAnchor(table, .0);
        AnchorPane.setBottomAnchor(table, .0);
        AnchorPane.setRightAnchor(table, .0);
    }

    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    public boolean getResult() {
        return result;
    }

    public ObservableList<InfoRanking> getStubItems() {
        ObservableList<InfoRanking> info = FXCollections.observableArrayList();
        for(int i=0; i<mStats.rankingGlobal().getSize(); ++i){
            info.add(new InfoRanking(i,mStats.rankingGlobal().getPlayer(i).getName(),
                    mStats.rankingGlobal().getValue(i)));
        }
        /*
        info.add(new InfoRanking(1, "Pere Marc antoni", 15));
        info.add(new InfoRanking(2, "Maria", 123));*/
        return info;
    }
}
