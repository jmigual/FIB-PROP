package presentacio;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import dades.PlayersAdmin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * Created by ets19 on 15/12/2015.
 */
public class LoginBoxController extends AnchorPane implements Controller {

    private PlayersAdmin mPAdmin;

    private AnchorPane rootLayout;

    @FXML
    private TextField user;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordR;

    @FXML
    private Label incorrect;

    public LoginBoxController(MainWindow main) {
        mPAdmin = main.getPlayersAdmin();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginBox.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        incorrect.setManaged(false);
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

    @FXML
    private void logIn() {
        if (mPAdmin.checkLogin(userName.getText(), password.getText())) {
            ((Stage)this.getScene().getWindow()).close();
        }
        else {
            incorrect.setManaged(true);
            incorrect.setVisible(true);
        }
    }

    @FXML
    private void createUser() {
        rootLayout.getChildren().clear();
        Stage stage = (Stage) this.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateUser.fxml"));
        loader.setController(this);
        loader.setRoot(this);


        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(rootLayout));
        stage.sizeToScene();
        stage.centerOnScreen();
    }
}
