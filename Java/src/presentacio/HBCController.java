package presentacio;

import dades.Table;
import domini.Basic.Cell;
import domini.BoardCreator.HumanBoardCreator;
import domini.KKBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;
import presentacio.KKPrinter.KKPrinterRegionSelect;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by arnau_000 on 10/12/2015.
 */
public class HBCController extends AnchorPane implements Controller {

    private AnchorPane rootLayout;
    private KKPrinter printer;
    private HumanBoardCreator hbc;
    private FXMLLoader loader;
    private Scene scene;
    private boolean createRegionMode;
    
    @FXML
    private StackPane KenkenPane;
    @FXML
    private Label modeLabel;
    @FXML
    private HBox gridRow3;
    @FXML
    private TextField ResultValueInput;
    @FXML
    private RadioButton AdditionRadioButton;
    @FXML
    private RadioButton ProductRadioButton;
    @FXML
    private RadioButton SubstractionRadioButton;
    @FXML
    private RadioButton DivisionRadioButton;
    @FXML
    private Button CreateRegionButton;

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
        createRegionMode = true;

        /* es pot fer sense -- de moment
        // Key events
        Scene scene = new Scene(rootLayout);
        scene.setOnKeyTyped(event -> {
            if (event.getCode().isDigitKey()) {
                numberPressed(Integer.parseInt(event.getCharacter()));
            }
        });
        */
    }

    public void changeMode(){
        createRegionMode = !createRegionMode;
        if (createRegionMode) {
            printer = new KKPrinterMultipleSelect(printer);
            modeLabel.setText("Mode de creació de regions");
        } else {
            printer = new KKPrinterRegionSelect(printer);
            modeLabel.setText("Mode d'edició de regions");
        }
    }

    /*
    private void numberPressed(int n){
        if (! createRegionMode){
            ((KKPrinterRegionSelect) printer).getSelectedKKRegion() // canviar le resultat de la KKRegion (no és trivial)
        }
    }
    */

    public void createRegionbuttonPressed(){
        ArrayList<Cell> C = new ArrayList<Cell>(hbc.getBoard().getSize() * hbc.getBoard().getSize());

        C.clear();
        // Get operation
        char op = '+';
        // Get result
        int opValue = Integer.parseInt(ResultValueInput.getText());
        // Get cells
        C = ((KKPrinterMultipleSelect) printer).getSelectedCells();

        try {
            if (!hbc.createRegion(false, C, op, opValue)) {
                System.out.print("Aquesta regió n'eliminarà altres ja creades. Segueixes volent-la crear? (s/n)");
                String s = "s";
                while (!s.equals("s") || !s.equals("n")) {
                    if (s.equals("s")){
                        hbc.createRegion(true, C, op, opValue);
                        break;
                    } else if (s.equals("n")) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void stop() {
        
    }
}
