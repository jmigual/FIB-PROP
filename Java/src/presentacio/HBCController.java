package presentacio;

import dades.Table;
import domini.Basic.Cell;
import domini.BoardCreator.HumanBoardCreator;
import domini.KKBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;
import presentacio.KKPrinter.KKPrinterRegionSelect;

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
    @FXML
    private ToggleGroup OperationRadioGroup;

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

    public void createRegionButtonPressed(){
        if (createRegionMode) {
            ArrayList<Cell> C = new ArrayList<>(hbc.getBoard().getSize() * hbc.getBoard().getSize());

            // Get cells
            C.clear();
            C = ((KKPrinterMultipleSelect) printer).getSelectedCells();

            // Get operation
            char op = '+';
            switch (OperationRadioGroup.getSelectedToggle().toString()) {
                case "RadioButton[id=AdditionRadioButton, styleClass=radio-button]'Suma'":
                    op = '+';
                    break;
                case "RadioButton[id=ProductRadioButton, styleClass=radio-button]'Multiplicacio'":
                    op = '*';
                    break;
                case "RadioButton[id=SubstractRadioButton, styleClass=radio-button]'Resta'":
                    op = '-';
                    break;
                case "RadioButton[id=DivisionRadioButton, styleClass=radio-button]'Divisio'":
                    op = '/';
                    break;
            }

            if (op == '-' && C.size() > 2){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Entrada invalida");
                a.setContentText("No es pot crear una regio de tipus resta amb un numero de cel.les diferent a 2.");
                a.showAndWait();
                return;
            } else if (op == '/' && C.size() > 2) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Entrada invalida");
                a.setContentText("No es pot crear una regio de tipus divisio amb un numero de cel.les diferent a 2.");
                a.showAndWait();
                return;
            } else if (!isPositiveInteger(ResultValueInput.getText())) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Entrada invalida");
                a.setContentText("El resultat ha de ser un nombre natural.");
                a.showAndWait();
                return;
            } else {

                // Get result
                int opValue = Integer.parseInt(ResultValueInput.getText());

                try {
                    if (!hbc.createRegion(false, C, op, opValue)) {
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Aquesta regio n'elimina altres ja creades."
                                + "Estas segur que vols crear-la i eliminar les regions que es solapen?");
                        a.setTitle("Confirmacio de l'eliminacio de regions");
                        if (a.showAndWait().get() == ButtonType.OK){
                            hbc.createRegion(true, C, op, opValue);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                printer.updateRegions();
                ((KKPrinterMultipleSelect) printer).deselect();

            }

        } else {
            Exception e = new Exception("CreateRegion called but not in createRegionMode");
            e.printStackTrace();
        }
    }

    private static boolean isPositiveInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    @Override
    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void stop() {
        
    }
}
