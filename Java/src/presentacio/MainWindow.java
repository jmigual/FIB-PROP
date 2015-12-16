package presentacio;
/**
 * Created by Joan on 03/12/2015.
 */

import dades.KKDB;
import dades.Player;
import dades.PlayersAdmin;
import dades.Table;
import domini.BoardCreator.CpuBoardCreator;
import domini.KKBoard;
import domini.stats.KKStats;
import exceptions.PlayerExistsException;
import exceptions.PlayerNotExistsExcepction;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterSingleSelect;


public class MainWindow extends Application {

    protected Stage primaryStage;
    protected AnchorPane rootLayout;
    protected GridPane gridPane;
    protected StackPane stackLeftArea;
    public static KKDB db;
    protected KKStats mstats;
    protected MainController mainController;
    private KKPrinter printer;
    protected String mUsername;

    public Image getIcon() {
        return mIcon;
    }

    private Image mIcon;

    public Table<KKBoard> getTaulers() {
        return taulers;
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

        mIcon = new Image(getClass().getResource("asterisk.png").toString());

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("KanKun");
        this.primaryStage.getIcons().add(mIcon);

        mainController = new MainController(this);


        //Inicialització dels stats
        this.mstats = new KKStats(db.getPlayers(), db.getBoards(), db.getMatches());

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

        // Show the scene containing the root layout
        Scene scene = new Scene(rootLayout);


        mainController.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        // TODO: Nota això s'ha de descomentar quan fem el release
        //mainController.showLoginBox();
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
    }

    public PlayersAdmin getPlayersAdmin() {
        return db.getPlayersAdmin();
    }

    public KKStats getKKStats() {
        return mstats;
    }

    public Player getPlayer() {
        try {
            return db.getPlayersAdmin().getPlayer(mUsername);
        } catch (PlayerNotExistsExcepction e) {
            e.printStackTrace();
            throw new RuntimeException("Player not exists");
        }
    }
}
