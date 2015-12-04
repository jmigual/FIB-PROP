package presentacio;
/**
 * Created by Joan on 03/12/2015.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow extends Application implements Initializable{

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private Canvas canvas;
    private GraphicsContext gc;
    private GridPane gridPane;
    boolean initialized=false;
    @FXML public Pane leftArea;
    @FXML public MenuItem hola;

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
            addResizeListeners(scene);
            canvas = new Canvas (scene.getWidth(),scene.getHeight());
            gc = canvas.getGraphicsContext2D();

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    private void addGrid(){
        gridPane=new GridPane();
        gridPane.setGridLinesVisible(true);

        rootLayout.getChildren().add(gridPane);

        double size=9;

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100/size);
        columnConstraints.setHalignment(HPos.CENTER);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100/size);
        rowConstraints.setValignment(VPos.CENTER);


        for (int i=0; i<size; i++){
            gridPane.getColumnConstraints().add(columnConstraints);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                gridPane.add(new Label(Integer.toString(i)+Integer.toString(j)), i, j);
            }
        }

        gridPane.setStyle("-fx-background-color: #CCFF99; -fx-border-color: #000000; -fx-stroke-color: #FF0000;");
    }

    private void addResizeListeners(Scene scene) {

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                resized();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                resized();
            }
        });
    }

    private void resized(){
        if (initialized)System.out.println(leftArea.getHeight());
    }

    public void exit() {
        Platform.exit();
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addGrid();
    }
}
