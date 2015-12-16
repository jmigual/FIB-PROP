package presentacio;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Inigo on 14/12/2015.
 */
public interface Controller {
    AnchorPane getRootLayout();
    void stop();
    void setScene(Scene scene);
}
