package presentacio.BoardCreator;

import domini.BoardCreator.CpuBoardCreator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import presentacio.Controller;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterNoSelect;
import presentacio.MainWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by arnau_000 on 16/12/2015.
 */
public class CBCController extends AnchorPane implements Controller {

    private AnchorPane rootLayout;
    private CpuBoardCreator cbc;
    private FXMLLoader loader;
    private MainWindow mMain;

    private int maxCreatedRegionSize = 5;

    private static int MAX_SIZE = 12;
    private static ArrayList<Integer> DEFAULT_SIZES_WEIGHTS = new ArrayList<Integer>(
            Arrays.asList(10, 50, 30, 20, 20, 10, 0, 0, 0, 0, 0, 0));


    private ArrayList<Slider> sizeSliders;
    private ArrayList<Label> sizeSlidersLabels;
    @FXML
    private Slider SliderAddition;
    @FXML
    private Slider SliderProduct;
    @FXML
    private Slider SliderSubtraction;
    @FXML
    private Slider SliderDivision;
    @FXML
    private TextField BoardSizeInput;
    @FXML
    private TextField MaxRegionSizeInput;
    @FXML
    private Button GenerateKenkenButton;
    @FXML
    private Button CancelButton;
    @FXML
    private GridPane WeightsGridPane;


    public CBCController(MainWindow mainWindow){

        mMain = mainWindow;

        loader = new FXMLLoader(getClass().getResource("CBCWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
           rootLayout = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cbc = new CpuBoardCreator(6, MainWindow.db.getBoards());

        generateSizeSliders();
    }

    private void generateSizeSliders(){
        sizeSliders = new ArrayList<>(MAX_SIZE);
        sizeSlidersLabels = new ArrayList<>(MAX_SIZE);
        for (int i=0; i<MAX_SIZE; ++i){
            sizeSliders.add(i, new Slider(0, 100, DEFAULT_SIZES_WEIGHTS.get(i)));
            sizeSlidersLabels.add(i, new Label(String.valueOf(i + 1) + ": "));

            WeightsGridPane.add(sizeSlidersLabels.get(i), 0, i);
            WeightsGridPane.add(sizeSliders.get(i), 1, i);
        }

        updateSizeSliders();
    }

    private void updateSizeSliders(){
        int i=0;
        for (; i<maxCreatedRegionSize; ++i){
            sizeSliders.get(i).setVisible(true);
            sizeSlidersLabels.get(i).setVisible(true);
        }
        for (; i<MAX_SIZE; ++i){
            sizeSliders.get(i).setVisible(false);
            sizeSlidersLabels.get(i).setVisible(false);
        }
    }

    public void changeBoardSizeButtonPressed(){
        cbc = new CpuBoardCreator(Integer.valueOf(BoardSizeInput.getText()), MainWindow.db.getBoards());
    }

    public void changeMaxRegionSizeButtonPressed(){
        if (!isPositiveInteger(MaxRegionSizeInput.getText())) {
            warn("Entrada invàlida", "No s'ha llegit cap nombre enter positiu.", "La mida màxima ha de ser un nombre" +
                    " enter entre 1 i 12.");
            return;
        }

        int temp = Integer.parseInt(MaxRegionSizeInput.getText());
        if (temp < 1){
            warn("Entrada invalida", "Nombre massa petit.", "La mida màxima ha de ser un número enter entre 1 i 12.");
            return;
        } else if (temp > 12) {
            warn("Entrada invalida", "Nombre massa gran.", "La mida màxima ha de ser un número enter entre 1 i 12.");
            return;
        }
        maxCreatedRegionSize = temp;

        cbc.setMaxRegionSize(maxCreatedRegionSize);
        updateSizeSliders();
    }

    public void generateKenkenButtonPressed(){

        updateWeights();

        try {
            cbc.createBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }

        seeBoardButtonPressed();
    }

    public void seeBoardButtonPressed(){
    /*    ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(okButtonType);
        boolean disabled = false; // computed based on content of text fields, for example
        dialog.getDialogPane().lookupButton(okButtonType).setDisable(disabled);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            warn("OK",null,"OK");
        }*/


        Dialog d = new Dialog();
        d.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonData.OK_DONE));
        StackPane kenkenPane = new StackPane();
        KKPrinter printer = new KKPrinterNoSelect(cbc.getBoard(), kenkenPane);
        d.setTitle("Kenken creat");
        d.getDialogPane().getChildren().add(kenkenPane);
        d.setResizable(true);
        kenkenPane.resize(400,400);
        printer.updateContent();
        Optional res = d.showAndWait();

        //mMain.getMainController().showKenken(cbc.getBoard());
    }

    private boolean updateWeights() {

        // Get Size Weights
        double d = 0;
        for (int i=0; i<maxCreatedRegionSize; ++i){
            try {
                cbc.setSizeWeight(i+1, (int) sizeSliders.get(i).getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
            d += sizeSliders.get(i).getValue();
        }
        if (d==0){
            warn("Entrada invàlida", null, "No pots posar tots els pesos de les mides a zero.");
            return false;
        }

        // Get Operations Weights
        cbc.setAddWeight((int) SliderAddition.getValue());
        cbc.setProdWeight((int) SliderProduct.getValue());
        cbc.setSubsWeight((int) SliderSubtraction.getValue());
        cbc.setDivWeight((int) SliderDivision.getValue());
        if (SliderAddition.getValue() + SliderProduct.getValue() + SliderSubtraction.getValue()
                + SliderDivision.getValue() == 0){
            warn("Entrada invàlida", null, "No pots posar tots els pesos de les operacions a zero.");
            return false;
        }

        //inform("DBG", null, d + ", +" + cbc.getAddWeight() + ", *" + cbc.getProdWeight() + ", -" +
        //        cbc.getSubsWeight() + ", /" + cbc.getDivWeight());
        return true;
    }

    public void cancelButtonPressed(){
        mMain.getMainController().dialogCancelled();
    }

    public void saveButtonPressed(){
        TextInputDialog dialog = new TextInputDialog("myKenken");
        dialog.setTitle("Guardar un tauler");
        dialog.setHeaderText(null);
        dialog.setContentText("Quin nom vols donar al tauler?");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            cbc.saveBoard(result.get(), mMain.getUsername());
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
    private void inform(String title, String header, String body) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(body);
        a.showAndWait();
    }
/*
    private void prompt(){
        List<String> choices = new ArrayList<>();
        for (int i = 2; i <= MAX_SIZE; ++i) {
            choices.add(String.valueOf(i));
        }

        String defValue = "Mida";

        ChoiceDialog<String> dialog = new ChoiceDialog<>(defValue, choices);
        dialog.setTitle("Mida del kenken");
        dialog.setHeaderText(null);
        dialog.setContentText("Quina mida vols que tingui el kenken?");

        dialog.showAndWait();
        if (!dialog.getSelectedItem().equals(defValue)) {
            //return Integer.parseInt(dialog.getSelectedItem());
        }
    }*/

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
