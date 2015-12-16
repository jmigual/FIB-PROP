package presentacio;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import presentacio.BoardCreator.CBCController;
import presentacio.BoardCreator.HBCController;
import presentacio.CollectionView.CollectionViewController;
import presentacio.CollectionView.CollectionViewEditorController;
import presentacio.LoginScreen.LoginBoxController;
import presentacio.Stats.StatsBoardController;
import presentacio.Stats.StatsGlobalController;
import presentacio.Stats.StatsPersonalController;
import presentacio.UserConfig.UserConfigController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls all menus and the current shown Scene
 */
public class MainController extends AnchorPane implements Controller {

    /**
     * Max allowed Ken-Ken size
     */
    private static final int MAX_SIZE = 12;

    /**
     * Center area panel
     */
    @FXML
    private AnchorPane leftArea;
    /**
     * Root panel
     */
    private AnchorPane rootLayout;
    /**
     * Reference to the MainWindow
     */
    private MainWindow main;
    /**
     * Controller switcher to change scenes and controllers
     */
    private ControllerSwitch contSwitch;

    /**
     * Default constructor
     * @param main MainWindow's program
     */
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

    /**
     * Shows the login box to init or change the current logged user
     */
    public void showLoginBox() {
        Stage s = createNewWindow(new LoginBoxController(main), StageStyle.UTILITY);
        s.setOnCloseRequest(event -> System.exit(0));
        s.setTitle("Iniciar sessió");
    }

    /**
     * To get the area that can be painted
     * @return AnchorPane remaining area
     */
    public AnchorPane getLeftArea() {
        return leftArea;
    }

    /**
     * Shows the ConfigureUser dialog
     */
    public void configureUser() {
        createNewWindow(new UserConfigController(main));
    }

    /**
     * Shows the global stats
     */
    public void showGlobal() {
        createNewWindow(new StatsGlobalController(main));
    }

    /**
     * Shows the personal stats
     */
    public void showPersonal() {
        createNewWindow(new StatsPersonalController(main));
    }

    /**
     * Shows the stats by board
     */
    public void showByBoard() {
        createNewWindow(new StatsBoardController(main));
    }

    /**
     * Exits the application
     */
    public void exit() {
        Platform.exit();
    }

    /**
     * Shows the Human Board Creator
     */
    public void humanCreateBoardClicked() {
        int size = askSize();
        if (size < 0) return;
        contSwitch.add(new HBCController(main, size));
    }

    /**
     * Shows the CPU Board Creator
     */
    public void cpuCreateBoardClicked() {
        contSwitch.add(new CBCController(main));
    }

    /**
     * Shows the dialog to select a board to start a new match
     */
    public void createMatch() {
        contSwitch.switchController(new CollectionViewController(main));
    }

    /**
     * Shows the dialog to select a match being played
     */
    public void loadMatch() {

    }


    /**
     * Returns the root layout, necessary to switch scenes
     * @return Controller's rootLayout
     */
    @Override
    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    /**
     * Called before stopping the controller
     */
    @Override
    public void stop() {

    }

    /**
     * Called when the controller receives the scene (allows keys configuration)
     * @param scene Current controller's scene
     */
    @Override
    public void setScene(Scene scene) {
        contSwitch.setScene(scene);
    }

    /**
     * Overloaded function to create a new window from the Parent element
     * @param c Parent element
     * @return Stage with the new window
     */
    private Stage createNewWindow(Parent c) {
        return createNewWindow(c, StageStyle.DECORATED);
    }

    /**
     * Creates a new window from the parent element and with the selected style
     * @param c Parent element
     * @param style Selected style for the stage
     * @return Stage with the new window
     */
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

    /**
     * Closes the current session and shows the dialog to init a new session
     */
    public void closeSession() {
        contSwitch.switchController(null);
        showLoginBox();
    }

    /**
     * Hides the current shown dialog
     */
    public void dialogCancelled() {
        contSwitch.switchController(null);
    }

    /**
     * Shows the Scene to edit an existing board
     */
    @FXML
    private void editBoard() {
        contSwitch.switchController(new CollectionViewEditorController(main));
    }

    /**
     * To get the current controller switch
     * @return The current used ControllerSwitch
     */
    public ControllerSwitch getContSwitch() {
        return contSwitch;
    }

    /**
     * Shows a dialog asking the desired size to create a board
     * @return Integer containing the desired size (-1 if cancelled)
     */
    private int askSize() {
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
            return Integer.parseInt(dialog.getSelectedItem());
        }
        return -1;
    }
}
