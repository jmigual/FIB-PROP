package presentacio;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by Inigo on 04/12/2015.
 */
public class MainController extends AnchorPane {

    @FXML
    private StackPane leftArea;
    private AnchorPane rootlayout;
    private Stage shownStage;
    private MainWindow main;

    public MainController(MainWindow main){
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
        shownStage = new Stage();
        UserConfigController config = new UserConfigController(main);
        shownStage.initModality(Modality.APPLICATION_MODAL);
        shownStage.setScene(new Scene(config.getRootLayout()));
        shownStage.sizeToScene();
        shownStage.show();
    }

    public void exit() {
        Platform.exit();
    }

    public void trolla(){}

    public void humanCreateBoardClicked(){
        HBCController hbcc = new HBCController(leftArea, 9);

    }

    public void cpuCreateBoardClicked(){

    }

}
