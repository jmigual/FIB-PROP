package presentacio;

import dades.PlayersAdmin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Joan on 10/12/2015.
 */
public class UserConfigController extends AnchorPane {

    private PlayersAdmin mAdmin;
    private AnchorPane rootLayout;
    @FXML
    private TextField name;
    @FXML
    private TextField userName;
    @FXML
    private Label passAlert;
    @FXML
    private Label fields;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordR;
    @FXML
    private PasswordField oldPasssword;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    private boolean result = false;

    UserConfigController(MainWindow main) {
        mAdmin = main.getPlayersAdmin();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserConfig.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void checkAccept() {
        if (password.getText().length() > 0 && password.getText().equals(passwordR.getText())) {
            passAlert.setVisible(true);
        }


    }

    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    public boolean getResult() {
        return result;
    }
}
