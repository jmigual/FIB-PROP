package presentacio;
/**
 * Created by Joan on 03/12/2015.
 */

import dades.KKDB;
import dades.Player;
import dades.Table;
import dades.PlayersAdmin;
import domini.Basic.Cell;
import domini.BoardCreator.CpuBoardCreator;
import domini.KKBoard;
import domini.stats.KKStats;
import exceptions.PlayerNotExistsExcepction;
import exceptions.PlayerExistsException;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;
import presentacio.KKPrinter.KKPrinterSingleSelect;


public class MainWindow extends Application {

    protected Stage primaryStage;
    protected AnchorPane rootLayout;
    protected GridPane gridPane;
    protected StackPane stackLeftArea;
    public static KKDB db;
    protected KKStats mstats;
    public Player actualPlayer;
    protected MainController mainController;
    private KKPrinter printer;
    Thread thread;
    protected String mUsername;

    public Table<KKBoard> getTaulers() {
        return taulers;
    }

    public Player getActualPlayer() {
        return actualPlayer;
    }

    protected Table<KKBoard> taulers;

    public static void main(String[] args) {
        launch(args);
    }

    public MainController getMainController() {
        return mainController;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public Table<KKBoard> getBoards() {
        return db.getBoards();
    }

    @Override
    public void start(Stage primaryStage) {
        db = new KKDB();
        db.load();
        taulers = db.getBoards();
        try {
            db.getPlayersAdmin().createPlayer("CPU", "CPU", "CPU");
            db.getPlayersAdmin().createPlayer("Admin", "admin", "admin");


        } catch (PlayerExistsException e) {
            System.err.println("This player already exists");
        }
        this.mUsername = "admin";

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("App molt guai");
        mainController = new MainController(this);

        try {
            actualPlayer = db.getPlayersAdmin().getPlayer(mUsername);
        } catch (PlayerNotExistsExcepction playerNotExistsExcepction) {
            playerNotExistsExcepction.printStackTrace();
        }


        //Inicialitzaci� dels stats
        this.mstats = new KKStats(db.getPlayers(),db.getBoards(),db.getMatches());


        initRootLayout();

    }

    public void stop() {
        db.save();
    }

    protected void initRootLayout() {
        // Load root layout from xml file
        AnchorPane anchorLeftArea = mainController.getLeftArea();
        stackLeftArea = new StackPane();
        AnchorPane.setBottomAnchor(stackLeftArea, 0.);
        AnchorPane.setTopAnchor(stackLeftArea, 0.);
        AnchorPane.setLeftAnchor(stackLeftArea, 0.);
        AnchorPane.setRightAnchor(stackLeftArea, 0.);
        anchorLeftArea.getChildren().add(stackLeftArea);
        rootLayout = mainController.getRootLayout();
        //createGrid();

        // Show the scene containing the root layout
        Scene scene = new Scene(rootLayout);
        /*scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if(event.isControlDown())printer.getBoard().preCalculate();
                else if (event.isShiftDown()){
                    KKBoard copy=printer.getBoard().getCopy();
                    Task<Integer> task = new Task<Integer>() {
                        @Override protected Integer call() throws Exception {
                            copy.solve();
                            return 0;
                        }
                    };
                    task.setOnSucceeded(stateEvent->{
                        printer.setBoard(copy);
                        printer.updateCells();
                        printer.updateAnnotations();
                    });
                    new Thread(task).start();
                }
                else printer.getBoard().solve();
                //printer.updateCells();
                //printer.updateAnnotations();
            }
            if (event.getCode() == KeyCode.BACK_SPACE) {
                printer.getBoard().clear();
                printer.updateCells();
                printer.updateAnnotations();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                printer=new KKPrinterMultipleSelect(printer);
                printer.updateCells();
            }

            if (event.getCode() == KeyCode.SPACE){
                printer.getBoard().calculateIndividualPossibilities();
                for (int i=0; i<printer.getBoard().getSize(); i++) {
                    for (int j = 0; j < printer.getBoard().getSize(); j++) {
                        Cell c = printer.getBoard().getCell(i, j);
                        for (int k = 1; k <= printer.getBoard().getSize(); k++) {
                            c.setAnnotation(k, c.getPossibility(k));
                        }
                    }
                }
                printer.updateAnnotations();
            }
            if (event.getCode() == KeyCode.DIGIT0 || event.getCode() == KeyCode.NUMPAD0) numEvent(event,0);
            if (event.getCode() == KeyCode.DIGIT1 || event.getCode() == KeyCode.NUMPAD1) numEvent(event,1);
            if (event.getCode() == KeyCode.DIGIT2 || event.getCode() == KeyCode.NUMPAD2) numEvent(event,2);
            if (event.getCode() == KeyCode.DIGIT3 || event.getCode() == KeyCode.NUMPAD3) numEvent(event,3);
            if (event.getCode() == KeyCode.DIGIT4 || event.getCode() == KeyCode.NUMPAD4) numEvent(event,4);
            if (event.getCode() == KeyCode.DIGIT5 || event.getCode() == KeyCode.NUMPAD5) numEvent(event,5);
            if (event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.NUMPAD6) numEvent(event,6);
            if (event.getCode() == KeyCode.DIGIT7 || event.getCode() == KeyCode.NUMPAD7) numEvent(event,7);
            if (event.getCode() == KeyCode.DIGIT8 || event.getCode() == KeyCode.NUMPAD8) numEvent(event,8);
            if (event.getCode() == KeyCode.DIGIT9 || event.getCode() == KeyCode.NUMPAD9) numEvent(event,9);
        });

*/

        mainController.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void numEvent(KeyEvent event, int n) {
        if (printer instanceof KKPrinterSingleSelect) {
            if (event.isControlDown()) ((KKPrinterSingleSelect) printer).getSelectedCell().switchAnnotation(n);
            else ((KKPrinterSingleSelect) printer).getSelectedCell().setValue(n);
            printer.updateCells();
            printer.updateAnnotations();
        }
    }

    protected void createGrid() {
        db.getBoards().clear();
        int size = 11;
        CpuBoardCreator creator = new CpuBoardCreator(size, db.getBoards());
        try {
            creator.createBoard();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        creator.saveBoard("test", "CPU");
        db.save();

        printer = new KKPrinterSingleSelect(creator.getBoard(), stackLeftArea);
        //printer = new KKPrinterSingleSelect(db.getBoards().get(0), stackLeftArea);
    }

    public PlayersAdmin getPlayersAdmin() {
        return db.getPlayersAdmin();
    }
    public KKStats getKKStats() {
        return mstats;
    }
}
