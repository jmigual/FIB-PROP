package presentacio;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by Inigo on 04/12/2015.
 */
public class MainController extends AnchorPane {

    @FXML
    private StackPane leftArea;
    private AnchorPane rootlayout;
    private Application main;
    public MainController(Application main){
        this.main=main;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootlayout = loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public StackPane getLeftArea() {
        return leftArea;
    }

    public AnchorPane getRootlayout() {
        return rootlayout;
    }

    public void configureUser() {

    }

    public void exit() {
        Platform.exit();
    }

    public void trolla(){}

}
