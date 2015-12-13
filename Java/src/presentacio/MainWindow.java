package presentacio;
/**
 * Created by Joan on 03/12/2015.
 */

import dades.KKDB;
import dades.PlayersAdmin;
import domini.BoardCreator.CpuBoardCreator;
import exceptions.PlayerExistsException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow extends Application {

    protected Stage primaryStage;
    protected AnchorPane rootLayout;
    protected GridPane gridPane;
    protected StackPane leftArea;
    protected KKDB db;
    protected String currentPlayer;
    private KKPrinterSingleSelect printer;

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    private String mUsername;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        db = new KKDB();
        db.load();
        try {
            db.getPlayersAdmin().createPlayer("admin", "admin");
        } catch (PlayerExistsException e) {
            System.err.println("This player already exists");
        }
        this.currentPlayer = "admin";

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("App molt guai");

        initRootLayout();
    }

    public void stop() {
        db.save();
    }

    protected void initRootLayout() {
        // Load root layout from xml file
        MainController mainController = new MainController(this);
        leftArea = mainController.getLeftArea();
        rootLayout = mainController.getRootlayout();
        createGrid();

        // Show the scene containing the root layout
        Scene scene = new Scene(rootLayout);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                printer.getBoard().clear();
                printer.getBoard().solve();
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.BACK_SPACE) {
                printer.getBoard().clear();
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                printer.clear();
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT0) {
                printer.getSelectedCell().setValue(0);
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT0) {
                printer.getSelectedCell().setValue(0);
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT1) {
                printer.getSelectedCell().setValue(1);
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT2) {
                printer.getSelectedCell().setValue(2);
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT3) {
                printer.getSelectedCell().setValue(3);
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT4) {
                printer.getSelectedCell().setValue(4);
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT5) {
                printer.getSelectedCell().setValue(5);
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT6) {
                printer.getSelectedCell().setValue(6);
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT7) {
                printer.getSelectedCell().setValue(7);
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT8) {
                printer.getSelectedCell().setValue(8);
                printer.updateCells();
            }
            if (event.getCode() == KeyCode.DIGIT9) {
                printer.getSelectedCell().setValue(9);
                printer.updateCells();
            }
        });


        primaryStage.setScene(scene);
        primaryStage.show();

    }

    protected void createGrid() {
        db.getBoards().clear();
        CpuBoardCreator creator = new CpuBoardCreator(12, db.getBoards());
        try {
            creator.createBoard();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        creator.saveBoard("test", "CPU");
        db.save();

        printer = new KKPrinterSingleSelect(creator.getBoard(), leftArea);
    }

    public PlayersAdmin getPlayersAdmin() { return db.getPlayersAdmin(); }
}
