package presentacio.CollectionView;

import dades.Table;
import domini.BoardCreator.CpuBoardCreator;
import domini.KKBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import presentacio.Controller;
import presentacio.KKPrinter.KKPrinterNoSelect;
import presentacio.MainWindow;

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

    protected HashMap<String, CheckBox> mPlayers;

    @FXML
    protected VBox leftArea;

    @FXML
    protected VBox rightArea;

    public CollectionViewController(MainWindow main) {
        mBoards = main.getBoards();
        mPlayers = new HashMap<>();

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

        stubCreaTaulers();
        loadPlayers();
        applyFilters();
    }

    @Override
    public AnchorPane getRootLayout() {
        return this;
    }

    @Override
    public void stop() {

    }

    private void stubCreaTaulers() {
        CpuBoardCreator creator = new CpuBoardCreator(9, mBoards);

        ArrayList<String> names = new ArrayList<>();
        names.add("pere");
        names.add("joan");
        names.add("anna");
        names.add("admin");
        names.add("joan2");
        names.add("pere");
        names.add("joan3");
        names.add("anna3");
        names.add("admin3");
        names.add("joan23");
        names.add("pere4");
        names.add("joan5");
        names.add("anna5");
        names.add("admi5n");
        names.add("joan25");
        names.add("pere6");
        names.add("joan6");
        names.add("anna6");
        names.add("admin6");
        names.add("joan26");
        names.add("pere7");
        names.add("joan7");
        names.add("anna7");
        names.add("admin7");
        names.add("joan27");



        if (mBoards.size() < 25) {
            for (int i = 0; i < 25; ++i) {
                try {
                    creator.createBoard();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                creator.saveBoard("auto" + Integer.toString(i), names.get(i % names.size()));
            }
        }
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
        for (KKBoard board : mBoards) {
            if (mPlayers.get(board.getCreator()).isSelected()) rightArea.getChildren().add(createPane(board));
        } 
    }

    public HBox createPane(KKBoard board) {
        HBox all = new HBox();
        all.setMinHeight(300.0);
        all.setPrefHeight(300.0);
        VBox.setVgrow(all, Priority.SOMETIMES);
        
        VBox text = new VBox();
        text.getChildren().add(new Label("Nom: " + board.getName()));
        text.getChildren().add(new Label("Tamany: " + Integer.toString(board.getSize())));
        StackPane pane = new StackPane();
        HBox.setHgrow(pane, Priority.ALWAYS);
        KKPrinterNoSelect printer = new KKPrinterNoSelect(board, pane);
        all.getChildren().addAll(text, pane);

        return all;
    }

    @FXML
    public void dialogAccept() {

    }

    @FXML
    public void dialogReject() {

    }
}
