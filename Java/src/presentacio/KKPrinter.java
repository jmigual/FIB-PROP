package presentacio;

import domini.Basic.Board;
import domini.Basic.Cell;
import domini.Basic.Region;
import domini.KKRegion.KKRegion;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.util.HashSet;

/**
 * Created by Inigo on 05/12/2015.
 */
public class KKPrinter {

    private String backgroundColor = "white";
    private String selectedColor = "skyBlue";
    private String errorColor = "Red";
    private String borderColor = "black";
    private int borderWidth = 5;
    private String regionDividerColor = "black";


    private GridPane gridPane;

    public Board getBoard() {
        return board;
    }

    private Board board;
    private StackPane stackPane;
    private StackPane selected;

    public KKPrinter(Board board, StackPane stackPane) {
        this.board = board;
        this.stackPane = stackPane;
        createGrid();
    }

    private void createGrid() {
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        stackPane.getChildren().add(gridPane);

        //adding size constraints

        NumberBinding minimum = Bindings.min(stackPane.widthProperty(), stackPane.heightProperty());
        gridPane.prefHeightProperty().bind(minimum);
        gridPane.prefWidthProperty().bind(minimum);
        gridPane.maxWidthProperty().bind(gridPane.prefWidthProperty());
        gridPane.maxHeightProperty().bind(gridPane.prefHeightProperty());
        gridPane.setMinSize(0, 0);

        //column and row constraints
        double size = board.getSize();
        ColumnConstraints columnConstraints = new ColumnConstraints();
        //columnConstraints.setPercentWidth(100 / size);
        columnConstraints.setHalignment(HPos.CENTER);

        RowConstraints rowConstraints = new RowConstraints();
        //rowConstraints.setPercentHeight(100 / size);
        rowConstraints.setValignment(VPos.CENTER);


        for (int i = 0; i < size; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        //adding content of cells

        HashSet<KKRegion> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                StackPane newStackPane = new StackPane();

                //label inside stackpane
                Label newLabel = new Label(Integer.toString(board.getCell(i, j).getValue()));
                if (board.getCell(i, j).getValue() == 0) newLabel.setText("");
                newLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", minimum.divide(size * 3).asString(), "px;"));
                newStackPane.getChildren().add(newLabel);
                newStackPane.prefHeightProperty().bind(gridPane.prefHeightProperty().divide(size));
                newStackPane.prefWidthProperty().bind(gridPane.prefWidthProperty().divide(size));
                newStackPane.maxHeightProperty().bind(gridPane.maxHeightProperty().divide(size));
                newStackPane.maxWidthProperty().bind(gridPane.maxWidthProperty().divide(size));
                newStackPane.setMinSize(0, 0);
                gridPane.setSnapToPixel(true);
                newLabel.setId("Label#" + Integer.toString(i) + "#" + Integer.toString(j));

                KKRegion r = (KKRegion) board.getCell(i, j).getRegion();
                if (!set.contains(r)) {
                    set.add(r);
                    Label operationLabel = new Label(" " + Integer.toString(r.getOperationValue()) + " " + r.getOperation().toString());
                    operationLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", minimum.divide(size * 5).asString(), "px;"));
                    StackPane.setAlignment(operationLabel, Pos.TOP_LEFT);
                    // StackPane.setMargin(operationLabel,new Insets(0,0,0,2));
                    newStackPane.getChildren().add(operationLabel);
                }

                newStackPane.setOnMouseClicked(event -> {
                    Object source = event.getSource();
                    if (source instanceof StackPane) select((StackPane) source);
                });
                gridPane.add(newStackPane, j, i); //WTF who puts columns first?
            }
        }

        //setting grid style
        gridPane.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-color: " + borderColor + "; -fx-border-width: " + Integer.toString(borderWidth) + "px;");

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null) {
                int i = GridPane.getRowIndex(node);
                int j = GridPane.getColumnIndex(node);
                Cell c = board.getCell(i, j);
                Region r = c.getRegion();

                String style = "-fx-border-color: " + regionDividerColor + "; -fx-border-width: ";

                if (i > 0 && board.getCell(i - 1, j).getRegion() != r) {  //ADALT
                    style += "2px";
                } else style += "0px";
                style += " ";
                if (j < size - 1 && board.getCell(i, j + 1).getRegion() != r) {  //DRETA
                    style += "2px";
                } else style += "0px";

                style += " ";
                if (i < size - 1 && board.getCell(i + 1, j).getRegion() != r) {  //ABAIX
                    style += "2px";
                } else style += "0px";

                style += " ";
                if (j > 0 && board.getCell(i, j - 1).getRegion() != r) {  //ESQUERRA
                    style += "2px";
                } else style += "0px";

                style += ";";

                node.setStyle(style);
            }

        }
        updateCells();
    }

    private void select(StackPane location) {
        selected = location;
        updateCells();
    }


    public void updateCells() {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null) {
                int i = GridPane.getRowIndex(node);
                int j = GridPane.getColumnIndex(node);
                Cell c = board.getCell(i, j);

                Label l = (Label) gridPane.lookup("#Label#" + Integer.toString(i) + "#" + Integer.toString(j));
                l.setText(Integer.toString(board.getCell(i, j).getValue()));
                if (board.getCell(i, j).getValue() == 0) l.setText("");


                String s = node.getStyle();
                s = s.split("-fx-background-color")[0];

                if (!c.getColumn().isCorrect() || !c.getRegion().isCorrect() || !c.getRow().isCorrect()) {
                    s += "-fx-background-color: " + errorColor + ";";
                } else if (node == selected) {
                    s += "-fx-background-color: " + selectedColor + ";";
                }
                node.setStyle(s);

            }
        }
    }

    public Cell getSelectedCell() {
        if (selected == null) return null;
        return board.getCell(GridPane.getRowIndex(selected), GridPane.getColumnIndex(selected));
    }
}
