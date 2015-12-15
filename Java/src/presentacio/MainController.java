package presentacio;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import presentacio.CollectionView.CollectionViewEditorController;
import presentacio.Stats.StatsBoardController;
import presentacio.Stats.StatsGlobalController;
import presentacio.Stats.StatsPersonalController;
import presentacio.UserConfig.UserConfigController;

import java.io.IOException;

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

    public void showLoginBox() {
        Stage s = createNewWindow(new LoginBoxController(main), StageStyle.UTILITY);
        s.setOnCloseRequest(event -> System.exit(0));
        s.setTitle("Iniciar sessió");
    }

    public AnchorPane getLeftArea() {
        return leftArea;
    }

    public void configureUser() {
        createNewWindow(new UserConfigController(main));
    }

    public void showGlobal() {
        createNewWindow(new StatsGlobalController(main));
    }

    public void showPersonal() {
        createNewWindow(new StatsPersonalController(main));
    }


    public void showByboard() {
        createNewWindow(new StatsBoardController(main));
    }


    public void exit() {
        Platform.exit();
    }

    public void humanCreateBoardClicked() {
        HBCController hbcc = new HBCController(main);
        contSwitch.add(hbcc);
    }

    public void cpuCreateBoardClicked() {

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

    private Stage createNewWindow(Parent c) {
        return createNewWindow(c, StageStyle.DECORATED);
    }

    private Stage createNewWindow(Parent c, StageStyle style) {
        Stage shownStage = new Stage();
        shownStage.initStyle(style);
        shownStage.initModality(Modality.APPLICATION_MODAL);
        shownStage.setScene(new Scene(c));
        shownStage.sizeToScene();
        shownStage.getIcons().add(main.getIcon());
        shownStage.show();
        return shownStage;
    }

    public void dialogCancelled() {
        contSwitch.switchController(null);
    }

    @FXML
    private void editBoard() {
        contSwitch.switchController(new CollectionViewEditorController(main));
    }

    public ControllerSwitch getContSwitch() {
        return contSwitch;
    }
}
