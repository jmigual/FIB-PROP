package presentacio.MatchShiat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import presentacio.Controller;
import presentacio.MainWindow;

import java.util.ArrayList;


/**
 * Created by Marc on 16/12/2015.
 */
public class FactController extends AnchorPane implements Controller {

    private MainWindow _main;
    private AnchorPane rootLayout;


    @FXML
    private TextField fact;

    @FXML
    private Button ok;

    @FXML
    private Label fLabel;


    @FXML
    private void factoritzacio() {
        int num = 0;

        try {
            num =Integer.valueOf(fact.getText());
        }
        catch (Exception e){
            fLabel.setTextFill(Paint.valueOf("Red"));
            fLabel.setText("No es un nombre valid!");
            return;
        }

        fLabel.setTextFill(Paint.valueOf("Black"));
        String F = "1";
        for (int i = 2; i <= num; i++) {
            while (num % i == 0) {
                F = F + ", " + Integer.toString(i);
                num /= i;
            }
        }
        fLabel.setText(F);

        ok.setDisable(true);
    }

    public FactController (MainWindow main){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Factoritzacio.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        _main = main;
    }


    @Override
    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void stop() {}

    @Override
    public void setScene(Scene scene) {}
}
