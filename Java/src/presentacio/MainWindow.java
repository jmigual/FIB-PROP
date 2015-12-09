package presentacio;
/**
 * Created by Joan on 03/12/2015.
 */

import dades.KKDB;
import domini.BoardCreator.CpuBoardCreator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainWindow extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private GridPane gridPane;
    private StackPane leftArea;
    private KKDB db;
    private KKPrinter printer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        db= new KKDB();
        db.load();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("App molt guai");

        initRootLayout();
    }

    public void stop(){
        db.save();
    }

    private void initRootLayout() {
        // Load root layout from xml file
        MainController mainController = new MainController(this);
        leftArea = mainController.getLeftArea();
        rootLayout = mainController.getRootlayout();
        createGrid();

        // Show the scene containing the root layout
        Scene scene = new Scene(rootLayout);
        scene.setOnKeyPressed(event -> {
            if (event.getCode()==KeyCode.ENTER){
                printer.getBoard().clear();
                printer.getBoard().solve();
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.BACK_SPACE){
                printer.getBoard().clear();
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT0){
                printer.getSelectedCell().setValue(0);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT0){
                printer.getSelectedCell().setValue(0);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT1){
                printer.getSelectedCell().setValue(1);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT2){
                printer.getSelectedCell().setValue(2);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT3){
                printer.getSelectedCell().setValue(3);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT4){
                printer.getSelectedCell().setValue(4);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT5){
                printer.getSelectedCell().setValue(5);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT6){
                printer.getSelectedCell().setValue(6);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT7){
                printer.getSelectedCell().setValue(7);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT8){
                printer.getSelectedCell().setValue(8);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT9){
                printer.getSelectedCell().setValue(9);
                printer.updateCells();
            }
        });

        addResizeListeners(scene);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
    private void createGrid(){
        if (db.getBoards().size()==0) {
            CpuBoardCreator creator = new CpuBoardCreator(12, db.getBoards());
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
    public void trolla(){
    }

}
