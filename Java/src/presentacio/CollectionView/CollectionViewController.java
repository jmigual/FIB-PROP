package presentacio.CollectionView;

import dades.Table;
import domini.Basic.Match;
import domini.BoardCreator.CpuBoardCreator;
import domini.KKBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import presentacio.Controller;
import presentacio.KKPrinter.KKPrinterNoSelect;
import presentacio.MainWindow;
import presentacio.MatchShiat.MatchController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Created by Joan on 14/12/2015.
 */
public class CollectionViewController extends AnchorPane implements Controller {

    protected Table<KKBoard> mBoards;


    protected Table<Match> mMatch;

    protected HashMap<String, CheckBox> mPlayers;

    protected ArrayList<RadioButton> mSelBoard;

    protected KKPrinterNoSelect mPrinter;

    protected MainWindow mMain;

    @FXML
    protected VBox leftArea;

    @FXML
    protected VBox rightArea;

    @FXML
    protected StackPane kkPane;

    public CollectionViewController(MainWindow main) {
        mBoards = main.getBoards();
        mMatch = main.getmMatch();
        mPlayers = new HashMap<>();
        mSelBoard = new ArrayList<>();
        mMain = main;

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("CollectionView.fxml"));
        loader.setController(this);

        SplitPane second;
        try {
            second = loader.load();
            this.getChildren().add(second);

            AnchorPane.setBottomAnchor(second, .0);
            AnchorPane.setTopAnchor(second, .0);
            AnchorPane.setLeftAnchor(second, .0);
            AnchorPane.setRightAnchor(second, .0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadPlayers();
        createBoardsPane();
        applyFilters();
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

    protected void loadPlayers() {
        HashSet<String> player;
        player = mBoards.stream().map(KKBoard::getCreator).collect(Collectors.toCollection(HashSet::new));

        for (String s : player) {
            HBox box = new HBox();
            CheckBox check = new CheckBox();
            check.setSelected(true);

            box.getChildren().addAll(check, new Label(s));

            leftArea.getChildren().add(box);
            VBox.setMargin(box, new Insets(5.0));

            mPlayers.put(s, check);
        }
    }

    public void applyFilters() {
        for (RadioButton but : mSelBoard) {
            KKBoard board = (KKBoard) but.getUserData();
            boolean view = mPlayers.get(board.getCreator()).isSelected();
            but.setVisible(view);
            but.setManaged(view);
        }
    }

    protected void createBoardsPane() {

        ToggleGroup toggle = new ToggleGroup();
        for (KKBoard board : mBoards) {
            RadioButton radio = new RadioButton();
            radio.setUserData(board);
            radio.setToggleGroup(toggle);
            radio.setText(board.getName() +
                    " de tamany: " + Integer.toString(board.getSize()) +
                    "  Creat per: " + board.getCreator());

            radio.setOnAction(event -> {
                if (mPrinter == null) mPrinter = new KKPrinterNoSelect((KKBoard)radio.getUserData(), kkPane);
                else {
                    mPrinter.setBoard((KKBoard)radio.getUserData());
                    mPrinter.updateRegions();
                }
            });

            rightArea.getChildren().add(radio);
            mSelBoard.add(radio);
        }
    }

    @FXML
    public void dialogAccept() {
        KKBoard sel = null;
        for (RadioButton r : mSelBoard) {
            if (r.isSelected()) sel = (KKBoard) r.getUserData();
        }
        Table<Match> taula = mMain.db.getMatches();
        Match m = new Match(sel, mMain.getUsername());
        taula.add(m);
        MatchController mc = new MatchController(m, mMain);
        mMain.getMainController().getContSwitch().switchController(mc);
    }

    @FXML
    public void dialogReject() {
        mMain.getMainController().dialogCancelled();
    }
}
