package presentacio.BoardCreator;

import domini.BoardCreator.CpuBoardCreator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import presentacio.Controller;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;
import presentacio.KKPrinter.KKPrinterNoSelect;
import presentacio.MainWindow;

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
    private MainWindow mMain;


    private static int MAX_SIZE = 12;


    @FXML
    private StackPane KenkenPane;
    private ArrayList<Slider> sliderSizes;
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

        mMain = mainWindow;

        loader = new FXMLLoader(getClass().getResource("CBCWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
           rootLayout = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cbc = new CpuBoardCreator(MAX_SIZE, MainWindow.db.getBoards());
        //printer = new KKPrinterNoSelect(cbc.getBoard(), KenkenPane);

        sliderSizes = new ArrayList<>(13);
        sliderSizes.add(0, null);
        sliderSizes.add(1, SliderSize1);
        sliderSizes.add(2, SliderSize2);
        sliderSizes.add(3, SliderSize3);
        sliderSizes.add(4, SliderSize4);
        sliderSizes.add(5, SliderSize5);
        sliderSizes.add(6, SliderSize6);
        sliderSizes.add(7, SliderSize7);
        sliderSizes.add(8, SliderSize8);
        sliderSizes.add(9, SliderSize9);
        sliderSizes.add(10, SliderSize10);
        sliderSizes.add(11, SliderSize11);
        sliderSizes.add(12, SliderSize12);
    }

    public void maxSizeButtonPressed(){
        if (!isPositiveInteger(MaxSizeInput.getText())) {
            warn("Entrada invàlida", "No s'ha llegit cap nombre enter positiu.", "La mida màxima ha de ser un nombre" +
                    " enter entre 1 i 12.");
            return;
        }
        int m = Integer.parseInt(MaxSizeInput.getText());
        if (m < 1){
            warn("Entrada invalida", "Nombre massa petit.", "La mida màxima ha de ser un número enter entre 1 i 12.");
            return;
        } else if (m > 12) {
            warn("Entrada invalida", "Nombre massa gran.", "La mida màxima ha de ser un número enter entre 1 i 12.");
            return;
        }

        cbc.setMaxRegionSize(m);
    }

    public void generateKenkenButtonPressed(){

        updateWeights();

        try {
            cbc.createBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean updateWeights() {

        // Get Size Weights
        double d = 0;
        for (int i=1; i<=cbc.getMaxRegionSize(); ++i){
            try {
                cbc.setSizeWeight(i, (int) sliderSizes.get(i).getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
            d += sliderSizes.get(i).getValue();
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

        return true;
    }

    public void cancelButtonPressed(){
        mMain.getMainController().dialogCancelled();
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
