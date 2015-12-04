package presentacio;
/**
 * Created by Joan on 03/12/2015.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {

    private Stage primaryStage;
    private Pane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("App molt guai");

        initRootLayout();
    }

    private void initRootLayout() {
        try {
            // Load root layout from xml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainWindow.class.getResource("./MainWindow.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void exit() {
        Platform.exit();
    }
}
