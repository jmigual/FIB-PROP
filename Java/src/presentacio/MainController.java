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
import presentacio.Stats.StatsBoardController;
import presentacio.Stats.StatsGlobalController;
import presentacio.Stats.StatsPersonalController;
import sun.applet.Main;

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

    public void showGlobal(){
        shownStage = new Stage();
        StatsGlobalController config = new StatsGlobalController(main);
        shownStage.initModality(Modality.APPLICATION_MODAL);
        shownStage.setScene(new Scene(config));
        shownStage.sizeToScene();
        shownStage.show();
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats/Stats_Global.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            leftArea.getChildren().clear();
            leftArea.getChildren().add(loader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }*/
    }

    public void showPersonal(){
        shownStage = new Stage();
        StatsPersonalController config = new StatsPersonalController(main);
        shownStage.initModality(Modality.APPLICATION_MODAL);
        shownStage.setScene(new Scene(config));
        shownStage.sizeToScene();
        shownStage.show();
    }

    public void showByboard(){
        shownStage = new Stage();
        StatsBoardController config = new StatsBoardController(main);
        shownStage.initModality(Modality.APPLICATION_MODAL);
        shownStage.setScene(new Scene(config.getRootLayout()));
        shownStage.sizeToScene();
        shownStage.show();
    }


    public void exit() {
        Platform.exit();
    }

    public void trolla(){}

}
