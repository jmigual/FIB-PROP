package presentacio.BoardCreator;

import domini.KKBoard;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import presentacio.Controller;
import presentacio.KKPrinter.KKPrinterNoSelect;

/**
 * Created by arnau_000 on 16/12/2015.
 */
public class BoardViewerController extends AnchorPane implements Controller {

    private KKBoard board;
    private KKPrinterNoSelect kkPrinterNoSelect;
    private AnchorPane rootLayout;
    @FXML
    private StackPane kenkenPane;

    public BoardViewerController(KKBoard board){
        this.board = board;
        kkPrinterNoSelect = new KKPrinterNoSelect(board, kenkenPane);
    }

    @Override
    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void stop() {

    }

    @Override
    public void setScene(Scene scene) {

    }
}
