package presentacio.UserConfig;

import dades.Player;
import dades.PlayersAdmin;
import exceptions.PlayerNotExistsExcepction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import presentacio.Controller;
import presentacio.MainWindow;

import java.io.IOException;

/**
 * Created by Joan on 10/12/2015.
 */
public class UserConfigController extends AnchorPane implements Controller {

    private PlayersAdmin mAdmin;

    private AnchorPane rootLayout;
    @FXML
    private TextField name;
    @FXML
    private TextField userName;
    @FXML
    private Label passAlert;
    @FXML
    private Label checkAlert;
    @FXML
    private Label fields;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordR;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    private boolean result = false;

    public UserConfigController(MainWindow main) {
        mAdmin = main.getPlayersAdmin();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserConfig.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player p;
        try {
            p = mAdmin.getPlayer(main.getUsername());
            createFields(p);
        } catch (PlayerNotExistsExcepction e) {
            e.printStackTrace();
            ((Stage) (this.getScene().getWindow())).close();
        }

    }

    private boolean checkAccept() {
        if (password.getText().length() > 0 && !password.getText().equals(passwordR.getText())) {
            passAlert.setVisible(true);
            return false;
        } else passAlert.setVisible(false);
        return true;
    }

    @Override
    public AnchorPane getRootLayout() {
        return this;
    }

    @Override
    public void stop() {

    }

    @Override
    public void setScene(Scene scene) {

    }

    public boolean getResult() {
        return result;
    }

    private void createFields(Player player) {
        this.name.setText(player.getName());
        this.userName.setText(player.getUserName());
    }

    @FXML
    private void dialogAccept() {
        if (!checkAccept()) return;

        if (!mAdmin.checkLogin(userName.getText(), oldPassword.getText())) {
            checkAlert.setVisible(true);
            return;
        } else checkAlert.setVisible(false);

        if (password.getText().length() > 0) {
            mAdmin.changePassword(userName.getText(), oldPassword.getText(), password.getText());
        }

        mAdmin.changeName(name.getText(), userName.getText());

        result = true;
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void dialogReject() {
        result = false;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
