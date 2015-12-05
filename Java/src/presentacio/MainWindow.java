package presentacio;
/**
 * Created by Joan on 03/12/2015.
 */

import dades.KKDB;
import domini.Basic.Board;
import domini.BoardCreator.CpuBoardCreator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainWindow extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private GridPane gridPane;
    private StackPane leftArea;
    private boolean createdGrid;
    private KKDB db;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        createdGrid=false;
        db= new KKDB();
        db.load();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("App molt guai");

        initRootLayout();
    }

    private void initRootLayout() {
        // Load root layout from xml file
        MainController mainController = new MainController();
        leftArea = mainController.getLeftArea();
        rootLayout = mainController.getRootlayout();
        createGrid();

        // Show the scene containing the root layout
        Scene scene = new Scene(rootLayout);
        addResizeListeners(scene);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
    private void createGrid(){
        KKPrinter printer;
        if (db.getBoards().size()==0) {
            CpuBoardCreator creator = new CpuBoardCreator(9, db.getBoards());
            try {
                creator.createBoard();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            creator.saveBoard("test", "CPU");
            db.save();

            printer = new KKPrinter(creator.getBoard(), leftArea);
        }
        else{
            printer = new KKPrinter(db.getBoards().get(0), leftArea);
        }
    }


    private void addResizeListeners(Scene scene) {

        scene.widthProperty().addListener(observable -> resized());
        scene.heightProperty().addListener(observable -> resized());
    }

    private void resized() {
    }


}
