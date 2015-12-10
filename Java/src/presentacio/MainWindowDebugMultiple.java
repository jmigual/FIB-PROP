package presentacio;
/**
 * Created by Joan on 03/12/2015.
 */

import dades.KKDB;
import domini.BoardCreator.CpuBoardCreator;
import domini.BoardCreator.HumanBoardCreator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainWindowDebugMultiple extends MainWindow{


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




    protected KKPrinterMultipleSelect printer;

    protected void initRootLayout() {
        // Load root layout from xml file
        MainController mainController = new MainController(this);
        leftArea = mainController.getLeftArea();
        rootLayout = mainController.getRootlayout();
        createGrid();

        // Show the scene containing the root layout
        Scene scene = new Scene(rootLayout);
        scene.setOnKeyPressed(event -> {
            if (event.getCode()==KeyCode.ENTER){
                HumanBoardCreator hbc= new HumanBoardCreator(9,db.getBoards());
                hbc.setBoard(db.getBoards().get(0));
                try{
                    hbc.createRegion(true,printer.getSelectedCells(),'+',0);
                }
                catch (Exception e){
                    System.out.println("FUUUUUUU");
                }
                printer.updateRegions();
                printer.deselect();
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.BACK_SPACE){
                printer.getBoard().clear();
                printer.updateCells();
            }

            if (event.getCode()==KeyCode.ESCAPE){
                printer.deselect();
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT0){
                printer.getSelectedCells().get(0).setValue(0);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT0){
                printer.getSelectedCells().get(0).setValue(0);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT1){
                printer.getSelectedCells().get(0).setValue(1);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT2){
                printer.getSelectedCells().get(0).setValue(2);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT3){
                printer.getSelectedCells().get(0).setValue(3);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT4){
                printer.getSelectedCells().get(0).setValue(4);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT5){
                printer.getSelectedCells().get(0).setValue(5);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT6){
                printer.getSelectedCells().get(0).setValue(6);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT7){
                printer.getSelectedCells().get(0).setValue(7);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT8){
                printer.getSelectedCells().get(0).setValue(8);
                printer.updateCells();
            }
            if (event.getCode()==KeyCode.DIGIT9){
                printer.getSelectedCells().get(0).setValue(9);
                printer.updateCells();
            }
        });


        primaryStage.setScene(scene);
        primaryStage.show();

    }

    protected void createGrid() {
        db.getBoards().clear();
        CpuBoardCreator creator = new CpuBoardCreator(9, db.getBoards());
        try {
            creator.createBoard();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        creator.saveBoard("test", "CPU");
        db.save();

        printer = new KKPrinterMultipleSelect(creator.getBoard(), leftArea);
    }
}
