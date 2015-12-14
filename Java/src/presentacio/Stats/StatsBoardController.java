package presentacio.Stats;

import dades.KKDB;
import dades.Table;
import domini.KKBoard;
import domini.stats.KKStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import presentacio.MainWindow;

import java.io.IOException;

/**
 * Created by Esteve on 14/12/2015.
 */
public class StatsBoardController extends AnchorPane {

    private KKStats mStats;

    private AnchorPane rootLayout;

    private TableView<InfoRanking> table;

    private boolean result = false;

    @FXML
    private javax.swing.text.TableView tablefm;
    @FXML
    private ComboBox combofm;

    public StatsBoardController(MainWindow main) {
        mStats = main.getKKStats();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats_Board.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        showCombo();

        createDefault();

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showCombo(){
        KKDB db = new KKDB();
        db.load();
        Table<KKBoard> boards = db.getBoards();
        for(int i=0; i<boards.size(); ++i){
            combofm.getItems().add(boards.get(i).get_name());
        }

    }

    private void createDefault() {
        // rank column
        TableColumn<InfoRanking, Integer> rankColumn = new TableColumn<>("Position");
        rankColumn.setMinWidth(50);
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        
        // Name column
        TableColumn<InfoRanking, String> nameColumn = new TableColumn<>("Player");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Score column
        TableColumn<InfoRanking, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setMinWidth(50);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        table = new TableView<>();
        table.setItems(getStubItems());
        table.getColumns().addAll(rankColumn, nameColumn, scoreColumn);

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
        return info;
    }
}
