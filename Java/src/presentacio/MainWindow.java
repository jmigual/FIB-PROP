package presentacio;
/**
 * Created by Joan on 03/12/2015.
 */

import dades.KKDB;
import domini.BoardCreator.CpuBoardCreator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;
import presentacio.KKPrinter.KKPrinterSingleSelect;


public class MainWindow extends Application {

    protected Stage primaryStage;
    protected AnchorPane rootLayout;
    protected GridPane gridPane;
    protected StackPane leftArea;
    protected KKDB db;
    private KKPrinter printer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        db = new KKDB();
        db.load();
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
                printer=new KKPrinterMultipleSelect(printer);
                printer.updateCells();
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


        primaryStage.setScene(scene);
        primaryStage.show();

    }
    private void numEvent(KeyEvent event, int n){
        /*
        if (event.isControlDown())printer.getSelectedCell().switchAnnotation(n);
        else printer.getSelectedCell().setValue(n);
        printer.updateCells();
        printer.updateAnnotations();*/
    }
    protected void createGrid() {
        db.getBoards().clear();
        int size=6;
        CpuBoardCreator creator = new CpuBoardCreator(size, db.getBoards());
        try {
            creator.createBoard();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        creator.saveBoard("test", "CPU");
        db.save();

        printer = new KKPrinterSingleSelect(creator.getBoard(), leftArea);
    }


}
