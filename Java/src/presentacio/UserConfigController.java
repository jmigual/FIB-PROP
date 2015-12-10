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
        FXMLLoader load = new FXMLLoader(getClass().getResource("UserConfig.fxml"));
        //load.setRoot(main);
        load.setController(this);

        try {
            rootLayout = load.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AnchorPane getRootLayout() { return rootLayout; }

}
