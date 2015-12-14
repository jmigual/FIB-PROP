package presentacio;

import dades.Table;
import domini.BoardCreator.HumanBoardCreator;
import domini.KKBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;

import java.io.IOException;

/**
 * Created by arnau_000 on 10/12/2015.
 */
public class HBCController {

    @FXML
    StackPane stackPane;
    KKPrinter printer;
    HumanBoardCreator hbc;

    public HBCController(StackPane stackPane, int size){
        this.stackPane = stackPane;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

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
