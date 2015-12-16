package presentacio.BoardCreator;

import domini.BoardCreator.CpuBoardCreator;
import domini.BoardCreator.HumanBoardCreator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import presentacio.Controller;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;
import presentacio.MainWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by arnau_000 on 16/12/2015.
 */
public class CBCController extends AnchorPane implements Controller {

    private AnchorPane rootLayout;
    private KKPrinter printer;
    private CpuBoardCreator cbc;
    private FXMLLoader loader;

    private static int MAX_SIZE = 12;


    @FXML
    private StackPane KenkenPane;
    @FXML
    private Slider SliderSize1;
    @FXML
    private Slider SliderSize2;
    @FXML
    private Slider SliderSize3;
    @FXML
    private Slider SliderSize4;
    @FXML
    private Slider SliderSize5;
    @FXML
    private Slider SliderSize6;
    @FXML
    private Slider SliderSize7;
    @FXML
    private Slider SliderSize8;
    @FXML
    private Slider SliderSize9;
    @FXML
    private Slider SliderSize10;
    @FXML
    private Slider SliderSize11;
    @FXML
    private Slider SliderSize12;
    @FXML
    private Slider SliderAddition;
    @FXML
    private Slider SliderProduct;
    @FXML
    private Slider SliderSubtraction;
    @FXML
    private Slider SliderDivision;
    @FXML
    private Label LabelPlus4;
    @FXML
    private Label LabelPlus8;
    @FXML
    private TextField MaxSizeInput;
    @FXML
    private Button MaxSizeButton;
    @FXML
    private Button GenerateKenkenButton;
    @FXML
    private Button CancelButton;


    public CBCController(MainWindow mainWindow){

        loader = new FXMLLoader(getClass().getResource("HBCWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
           rootLayout = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cbc = new CpuBoardCreator(12, MainWindow.db.getBoards());
        printer = new KKPrinterMultipleSelect(cbc.getBoard(), KenkenPane);

    }

    public void maxSizeButtonPressed(){
        if (!isPositiveInteger(MaxSizeInput.getText())) {
            warn("Entrada invalida", "Atenci?!", "El resultat ha de ser un nombre natural.");
            return;
        }

        cbc.setMaxRegionSize(Integer.parseInt(MaxSizeInput.getText()));
    }

    public void generateKenkenButtonPressed(){
        inform("Not implemented yet.", null, "poz ezo... demà a currar :(");
    }

    public void cancelButtonPressed(){

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
