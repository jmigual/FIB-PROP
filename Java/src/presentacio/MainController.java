package presentacio;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presentacio.CollectionView.CollectionViewController;
import presentacio.CollectionView.CollectionViewEditorController;
import presentacio.UserConfig.UserConfigController;

import java.io.IOException;
import java.util.Stack;

/**
 * Created by Inigo on 04/12/2015.
 */
public class MainController extends AnchorPane implements Controller {

    @FXML
    private AnchorPane leftArea;
    private AnchorPane rootLayout;
    private MainWindow main;
    private ControllerSwitch contSwitch;

    public MainController(MainWindow main) {
        this.main = main;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        contSwitch = new ControllerSwitch(leftArea);
    }

    public AnchorPane getLeftArea() {
        return leftArea;
    }

    public void configureUser() {
        Stage shownStage = new Stage();
        UserConfigController config = new UserConfigController(main);
        shownStage.initModality(Modality.APPLICATION_MODAL);
        shownStage.setScene(new Scene(config.getRootLayout()));
        shownStage.sizeToScene();
        shownStage.show();
    }

    public void exit() {
        Platform.exit();
    }

    public void humanCreateBoardClicked(){
        HBCController hbcc = new HBCController(9);
        contSwitch.add(hbcc);
    }

    public void cpuCreateBoardClicked(){

    }

    @Override
    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void stop() {

    }

    public void dialogCancelled() {
        contSwitch.switchController(null);
    }

    @FXML
    private void editBoard() {
        contSwitch.switchController(new CollectionViewEditorController(main));
    }

    public ControllerSwitch getContSwitch() { return contSwitch; }
}
