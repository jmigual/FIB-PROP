package presentacio;

import dades.Table;
import domini.BoardCreator.HumanBoardCreator;
import domini.KKBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;

import java.io.IOException;

/**
 * Created by arnau_000 on 10/12/2015.
 */
public class HBCController {

    @FXML
    AnchorPane allArea;
    StackPane stackPane;
    KKPrinter printer;
    HumanBoardCreator hbc;
    FXMLLoader loader;

    public HBCController(int size){
        loader = new FXMLLoader(getClass().getResource("HBCWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            allArea = loader.load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (allArea.lookup("#KenkenPane") instanceof StackPane) {
            stackPane = (StackPane) allArea.lookup("#KenkenPane");
        } else {
            new Exception("HBCController(AnchorPane, size) called without a valid AnchorPane");
        }

        hbc = new HumanBoardCreator(size, new Table<>()); // real table should be passed
        printer = new KKPrinterMultipleSelect(hbc.getBoard(), stackPane);

        /*

        try {
        rootlayout = loader.load();
        } catch (IOException exception) {
        throw new RuntimeException(exception);
        }

        */

    }

    public void changeMode(){

    }
}
