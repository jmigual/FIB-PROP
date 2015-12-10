package presentacio;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Joan on 10/12/2015.
 */
public class UserConfigController extends AnchorPane {

    private AnchorPane rootLayout;

    UserConfigController(MainWindow main) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserConfig.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AnchorPane getRootLayout() { return rootLayout; }

}
