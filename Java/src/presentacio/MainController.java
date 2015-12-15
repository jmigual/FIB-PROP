package presentacio;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presentacio.CollectionView.CollectionViewController;
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
    private Stage shownStage;
    private MainWindow main;
    private Controller actualController;
    private Stack<Controller> previousController;

    public MainController(MainWindow main) {
        this.main = main;
        this.previousController = new Stack<>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public AnchorPane getLeftArea() {
        return leftArea;
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

    public void humanCreateBoardClicked(){
        HBCController hbcc = new HBCController(9);
        switchController(hbcc);
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

    private void switchController(Controller c) {
        if (c == null) {
            leftArea.getChildren().clear();
            previousController.push(actualController);
            actualController = null;
            return;
        }

        if (actualController != null) actualController.stop();
        leftArea.getChildren().clear();



        AnchorPane newPane = c.getRootLayout();
        AnchorPane.setBottomAnchor(newPane, 0.);
        AnchorPane.setTopAnchor(newPane, 0.);
        AnchorPane.setLeftAnchor(newPane, 0.);
        AnchorPane.setRightAnchor(newPane, 0.);
        leftArea.getChildren().add(newPane);
        actualController = c;
    }

    private void switchPreviowsController() {
        Controller c = previousController.pop();
        leftArea.getChildren().clear();

        if (c == null) {
            actualController = null;
            return;
        }

        if (actualController != null) actualController.stop();
        
    }

    public void dialogCancelled() {
        switchController(previousController.pop());
    }

    @FXML
    private void editBoard() {
        CollectionViewController coll = new CollectionViewController(main);
        switchController(coll);
    }
}
