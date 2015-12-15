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
    private Button Create_ModifyRegionButton;
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
            Create_ModifyRegionButton.setText("Crea la regió");
            modeLabel.setText("Mode de creació de regions");
        } else {
            printer = new KKPrinterRegionSelect(printer);
            Create_ModifyRegionButton.setText("Modifica la regió");
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

    public void create_modifyRegionButtonPressed() {

        // Get cells
        ArrayList<Cell> C;
        if (createRegionMode) {
            C = new ArrayList<>(hbc.getBoard().getSize() * hbc.getBoard().getSize());
            C.clear();
            C = ((KKPrinterMultipleSelect) printer).getSelectedCells();
        } else {
            C = ((KKPrinterRegionSelect) printer).getSelectedKKRegion().getCells();
        }

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

        if (op == '-' && C.size() > 2) {
            warn("Entrada invalida", "Atenció!", "No es pot crear una regio de tipus resta amb un nombre de cel.les diferent a 2.");
            return;
        } else if (op == '/' && C.size() > 2) {
            warn("Entrada invalida", "Atenció!", "No es pot crear una regio de tipus divisio amb un nombre de cel.les diferent a 2.");
            return;
        } else if (!isPositiveInteger(ResultValueInput.getText())) {
            warn("Entrada invalida", "Atenció!", "El resultat ha de ser un nombre natural.");
            return;
        } else if (C.size() < 1) {
            warn("Entrada invalida", "Atenció!", "No has seleccionat cap cel.la.");
            return;
        } else {

            // Get result
            int opValue = Integer.parseInt(ResultValueInput.getText());

            if (createRegionMode) {

                if (!hbc.isContiguous(C)){
                    warn("Entrada invàlida", "Atenció!", "La regió que has seleccionat té parts no contigües. " +
                            "No es pot crear una regió amb aquesta forma.");
                    return;
                }

                try {
                    if (!hbc.createRegion(false, C, op, opValue)) {
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Aquesta regio n'elimina altres ja creades."
                                + "Estas segur que vols crear-la i eliminar les regions que es solapen?");
                        a.setTitle("Confirmacio de l'eliminacio de regions");
                        if (a.showAndWait().get() == ButtonType.OK) {
                            hbc.createRegion(true, C, op, opValue);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                // Modify region mode
                if (((KKPrinterRegionSelect) printer).getSelectedKKRegion() != hbc.getDefaultRegion()) {
                    hbc.deleteRegion(((KKPrinterRegionSelect) printer).getSelectedKKRegion());
                }
                try {
                    hbc.createRegion(false, C, op, opValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            printer.updateRegions();
            ((KKPrinterMultipleSelect) printer).deselect();
        }
    }

    public void deleteRegionButtonPressed(){
        if (!createRegionMode){
            if (((KKPrinterRegionSelect) printer).getSelectedKKRegion() != hbc.getDefaultRegion()) {
                hbc.deleteRegion(((KKPrinterRegionSelect) printer).getSelectedKKRegion());
            }
            //((KKPrinterRegionSelect) printer).deselect();
            printer.updateRegions();
        } else {
            warn("Acció prohibida", "Atenció!", "No es poden eliminar regions en el mode de creació de regions.");
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

    private void warn(String title, String header, String body){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(body);
        a.showAndWait();
    }

    @Override
    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void stop() {
        
    }
}
