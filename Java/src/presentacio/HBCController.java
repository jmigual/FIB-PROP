package presentacio;

import dades.Table;
import domini.BoardCreator.HumanBoardCreator;
import domini.KKBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;

import java.io.IOException;

/**
 * Created by arnau_000 on 10/12/2015.
 */
public class HBCController extends AnchorPane implements Controller {
    
    AnchorPane rootLayout;
    StackPane stackPane;
    KKPrinter printer;
    HumanBoardCreator hbc;
    FXMLLoader loader;
    
    @FXML
    private StackPane KenkenPane;

    public HBCController(int size){
        loader = new FXMLLoader(getClass().getResource("HBCWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
           rootLayout = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        hbc = new HumanBoardCreator(size, new Table<>()); // real table should be passed
        printer = new KKPrinterMultipleSelect(hbc.getBoard(), KenkenPane);

    }

    public void changeMode(){

    }

    @Override
    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void stop() {
        
    }
}
