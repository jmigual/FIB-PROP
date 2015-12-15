package presentacio.Stats;

import dades.KKDB;
import dades.Table;
import domini.KKBoard;
import domini.stats.KKStats;
import domini.stats.Playable;
import exceptions.PlayerExistsException;
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


        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showCombo(main.getTaulers());

        createDefault(main.getTaulers());
    }
    private void showCombo(Table<KKBoard> boards){
        for(int i=0; i<boards.size(); ++i){
            combofm.getItems().add(boards.get(i).get_name());
        }

    }

    private void createDefault(Table<KKBoard> boards) {
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

        //Select game

        KKBoard Selected = null;
        for (KKBoard board : boards) {
            if(board.get_name() == combofm.getSelectionModel().toString()) Selected = board;

        }

        table = new TableView<>();
        table.setItems(getStubItems(Selected));
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

    public ObservableList<InfoRanking> getStubItems(Playable game) {
        ObservableList<InfoRanking> info = FXCollections.observableArrayList();
        for(int i=0; i<mStats.recordsGame(game).getSize(); ++i){
            info.add(new InfoRanking(i,mStats.recordsGame(game).getPlayer(i).getName(),
                    mStats.recordsGame(game).getValue(i)));
        }
        return info;
    }
}
