package presentacio.BoardCreator;

import domini.Basic.Cell;
import domini.BoardCreator.HumanBoardCreator;
import domini.KKRegion.KKRegion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import presentacio.Controller;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;
import presentacio.KKPrinter.KKPrinterRegionSelect;
import presentacio.KKPrinter.KKPrinterSingleSelect;
import presentacio.MainWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private boolean annotationsMode;

    private static int MAX_SIZE = 12;

    public int getSize() {
        return size;
    }

    private int size;

    @FXML
    private StackPane KenkenPane;
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
    @FXML
    private Button FillWithRandomNumbersButton;
    @FXML
    private Button HasSolutionButton;
    @FXML
    private Button SolveButton;
    @FXML
    private Button DeleteRegionButton;
    @FXML
    private ToggleButton ModeToggleButton;
    @FXML
    private Button ClearBoardButton;
    @FXML
    private ToggleButton AnnotacionsModeToggleButton;
    @FXML
    private Button CancelButton;

    public HBCController(MainWindow mainWindow){

        size = askSize();

           if (size < 0){
               size = 3;
           }

        loader = new FXMLLoader(getClass().getResource("HBCWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
           rootLayout = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        hbc = new HumanBoardCreator(size, MainWindow.db.getBoards());
        printer = new KKPrinterMultipleSelect(hbc.getBoard(), KenkenPane);
        createRegionMode = true;
        DeleteRegionButton.setVisible(false);
        annotationsMode = false;
        FillWithRandomNumbersButton.setVisible(false);
        ClearBoardButton.setVisible(false);
    }

    private int askSize(){
        List<String> choices = new ArrayList<>();
        for (int i=2; i<=MAX_SIZE; ++i){
            choices.add(String.valueOf(i));
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("mida", choices);
        dialog.setTitle("Mida del kenken");
        dialog.setHeaderText(null);
        dialog.setContentText("Quina mida vols que tingui el kenken?");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return Integer.parseInt(result.get());
        }
        return -1;
    }

    public void modeToggleButtonPressed(){
        createRegionMode = !createRegionMode;
        if (createRegionMode) {
//            ((KKPrinterRegionSelect) printer).deselect();
            printer = new KKPrinterMultipleSelect(printer);
            Create_ModifyRegionButton.setText("Crea la regi�");

            DeleteRegionButton.setVisible(false);
        } else {
            ((KKPrinterMultipleSelect) printer).deselect();
            printer = new KKPrinterRegionSelect(printer);
            Create_ModifyRegionButton.setText("Modifica la regi�");

            DeleteRegionButton.setVisible(true);
        }
        printer.updateCells();
    }

    public void annotacionsModeToggleButtonPressed(){
        annotationsMode = !annotationsMode;
        if (annotationsMode) {
            // deselect ...
            printer = new KKPrinterSingleSelect(printer);

            // Show buttons
            FillWithRandomNumbersButton.setVisible(true);
            ClearBoardButton.setVisible(true);

            // Hide buttons
            ResultValueInput.setVisible(false);
            AdditionRadioButton.setVisible(false);
            ProductRadioButton.setVisible(false);
            SubstractionRadioButton.setVisible(false);
            DivisionRadioButton.setVisible(false);
            Create_ModifyRegionButton.setVisible(false);
            HasSolutionButton.setVisible(false);
            SolveButton.setVisible(false);
            DeleteRegionButton.setVisible(false);
            ModeToggleButton.setVisible(false);

        } else {
            if (createRegionMode){
                printer = new KKPrinterMultipleSelect(printer);
            } else {
                printer = new KKPrinterRegionSelect(printer);
            }
            // Hide buttons
            FillWithRandomNumbersButton.setVisible(false);
            ClearBoardButton.setVisible(false);

            // Show buttons
            ResultValueInput.setVisible(true);
            AdditionRadioButton.setVisible(true);
            ProductRadioButton.setVisible(true);
            SubstractionRadioButton.setVisible(true);
            DivisionRadioButton.setVisible(true);
            Create_ModifyRegionButton.setVisible(true);
            HasSolutionButton.setVisible(true);
            SolveButton.setVisible(true);
            DeleteRegionButton.setVisible(true);
            ModeToggleButton.setVisible(true);
        }

        printer.updateCells();

        checkConsistency();
    }

    public void checkConsistency(){
        KKRegion troll = hbc.removeTroll();
        if (!hbc.hasConsistentState()){
            warn("Estat inconsistent!", null, "");
            System.out.println();
        }
        hbc.addTroll(troll);
    }

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
            case "RadioButton[id=SubstractionRadioButton, styleClass=radio-button]'Resta'":
                op = '-';
                break;
            case "RadioButton[id=DivisionRadioButton, styleClass=radio-button]'Divisio'":
                op = '/';
                break;
        }

        if (op == '-' && C.size() > 2) {
            warn("Entrada invalida", "Atenci�!", "No es pot crear una regio de tipus resta amb un nombre de cel.les diferent a 2.");
            return;
        } else if (op == '/' && C.size() > 2) {
            warn("Entrada invalida", "Atenci�!", "No es pot crear una regio de tipus divisio amb un nombre de cel.les diferent a 2.");
            return;
        } else if (!isPositiveInteger(ResultValueInput.getText())) {
            warn("Entrada invalida", "Atenci�!", "El resultat ha de ser un nombre natural.");
            return;
        } else if (C.size() < 1) {
            warn("Entrada invalida", "Atenci�!", "No has seleccionat cap cel.la.");
            return;
        } else {

            // Get result
            int opValue = Integer.parseInt(ResultValueInput.getText());

            if (createRegionMode) {

                if (!hbc.isContiguous(C)){
                    warn("Entrada inv�lida", "Atenci�!", "La regi� que has seleccionat t� parts no contig�es. " +
                            "No es pot crear una regi� amb aquesta forma.");
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
                ((KKPrinterMultipleSelect) printer).deselect();

            } else {

                // Modify region mode
                if (((KKPrinterRegionSelect) printer).getSelectedKKRegion() != hbc.getBoard().getKkregions().get(0)) {
                    hbc.removeRegion(((KKPrinterRegionSelect) printer).getSelectedKKRegion());
                }
                try {
                    hbc.createRegion(false, C, op, opValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                ((KKPrinterRegionSelect) printer).deselect();

            }

            printer.updateRegions();
            printer.updateCells();
        }
    }

    public void deleteRegionButtonPressed(){
        if (!createRegionMode){
            if (((KKPrinterRegionSelect) printer).getSelectedKKRegion() != hbc.getBoard().getKkregions().get(0)) {
                hbc.removeRegion(((KKPrinterRegionSelect) printer).getSelectedKKRegion());
            }
            //((KKPrinterRegionSelect) printer).deselect();
            printer.updateRegions();
        } else {
            warn("Acci� prohibida", "Atenci�!", "No es poden eliminar regions en el mode de creaci� de regions.");
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

    public void fillWithRandomNumbersButtonPressed(){
        KKRegion troll = hbc.removeTroll();
        hbc.fillBoardWithRandomNumbers();
        hbc.addTroll(troll);
        printer.updateCells();
    }

    public void clearBoardButtonPressed(){
        hbc.clearBoard();
        printer.updateCells();
    }

    public void hasSolutionButtonPressed(){
        if (!hbc.isFinished()){
            warn("Solucionador de Kenkens", "Kenken inacabat", "El solucionador no pot buscar la soluci� d'un kenken " +
                    "inacabat. Assigna una regi� a cada cel�la abans.");
            return;
        }
        KKRegion troll = hbc.removeTroll();
        if (hbc.getBoard().hasSolution()) {
            inform("Solucionador de Kenkens", "El Kenken t� soluci�!", "El solucionador ha trobat com a m�nim una " +
                    "soluci� al Kenken proposat.");
        } else {
            inform("Solucionador de Kenkens", "El Kenken no t� soluci�!", "El solucionador no ha trobat cap " +
                    "soluci� al Kenken proposat.");
        }
        hbc.addTroll(troll);
    }

    public void solveButtonPressed(){
        if (!hbc.isFinished()){
            warn("Solucionador de Kenkens", "Kenken inacabat", "El solucionador no pot buscar la soluci� d'un kenken " +
                    "inacabat. Assigna una regi� a cada cel�la abans.");
            return;
        }
        KKRegion troll = hbc.removeTroll();
        if (! hbc.getBoard().hasSolution()){
            warn("Solucionador de Kenkens", "El kenken no t� soluci�!", "El solucionador no ha trobat cap " +
                    "soluci� al kenken proposat.");
        } else {
            hbc.getBoard().solve();
            printer.updateContent();
        }
        hbc.addTroll(troll);

        checkConsistency();
    }

    public void cancelButtonPressed(){
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    private void warn(String title, String header, String body){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(body);
        a.showAndWait();
    }
    private void inform(String title, String header, String body) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
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
        scene.setOnKeyPressed(Event -> {

        });
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
        scene.setOnKeyPressed(event -> {
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
            if (event.getCode() == KeyCode.A) numEvent(event,10);
            if (event.getCode() == KeyCode.B) numEvent(event,11);
            if (event.getCode() == KeyCode.C) numEvent(event,12);

            if (event.getCode()==KeyCode.S && event.isControlDown()){
                if (printer instanceof KKPrinterMultipleSelect){
                    ((KKPrinterMultipleSelect) printer).selectAll();
                }
            }
            if (event.getCode()==KeyCode.ESCAPE){
                if (printer instanceof KKPrinterMultipleSelect){
                    ((KKPrinterMultipleSelect) printer).deselect();
                }
            }
        });
    }

    private void numEvent(KeyEvent event, int n) {
        if (printer instanceof KKPrinterSingleSelect && n <= size) {
            if (event.isControlDown()) ((KKPrinterSingleSelect) printer).getSelectedCell().switchAnnotation(n);
            else ((KKPrinterSingleSelect) printer).getSelectedCell().setValue(n);
            printer.updateCells();
            printer.updateAnnotations();
        }
    }
}
