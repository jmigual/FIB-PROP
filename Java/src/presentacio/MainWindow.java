package presentacio;
/**
 * Created by Joan on 03/12/2015.
 */

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainWindow extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private GridPane gridPane;
    private StackPane leftArea;
    private boolean createdGrid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        createdGrid=false;
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("App molt guai");

        initRootLayout();
    }

    private void initRootLayout() {
        // Load root layout from xml file
        MainController mainController = new MainController();
        leftArea = mainController.getLeftArea();
        rootLayout = mainController.getRootlayout();

        // Show the scene containing the root layout
        Scene scene = new Scene(rootLayout);
        addResizeListeners(scene);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
    private void createGrid(){
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        leftArea.getChildren().add(gridPane);


        NumberBinding minimum = Bindings.min(leftArea.widthProperty(), leftArea.heightProperty());
        gridPane.prefHeightProperty().bind(minimum);
        gridPane.prefWidthProperty().bind(minimum);
        gridPane.maxWidthProperty().bind(gridPane.prefWidthProperty());
        gridPane.maxHeightProperty().bind(gridPane.prefHeightProperty());
        gridPane.setMinSize(0,0);




        double size = 9;
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100 / size);
        columnConstraints.setHalignment(HPos.CENTER);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100 / size);
        rowConstraints.setValignment(VPos.CENTER);


        for (int i = 0; i < size; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gridPane.add(new Label(Integer.toString(i) + Integer.toString(j)), i, j);
            }
        }

        gridPane.setStyle("-fx-background-color: #CCFF99; -fx-border-color: #000000; -fx-stroke-color: #FF0000;");
        createdGrid=true;
    }
    private void resizeGrid() {
        if (!createdGrid){
            createGrid();
        }

        /*if (leftArea.getWidth()>leftArea.getHeight()){
            gridPane.relocate(leftArea.getLayoutX(), leftArea.getLayoutY());
            gridPane.setPrefSize(leftArea.getWidth(),leftArea.getHeight());
        }*/
    }

    private void addResizeListeners(Scene scene) {

        scene.widthProperty().addListener(observable -> resized());
        scene.heightProperty().addListener(observable -> resized());
    }

    private void resized() {
        System.out.println(leftArea.getHeight());

        resizeGrid();
    }


}
