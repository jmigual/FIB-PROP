package presentacio;

import domini.Basic.Board;
import domini.Basic.Cell;
import domini.Basic.Region;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

/**
 * Created by Inigo on 05/12/2015.
 */
public class KKPrinter {
    private GridPane gridPane;
    private Board board;
    private StackPane stackPane;

    public KKPrinter(Board board, StackPane stackPane){
        this.board=board;
        this.stackPane=stackPane;
        createGrid();
    }

    private void createGrid(){
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        stackPane.getChildren().add(gridPane);


        NumberBinding minimum = Bindings.min(stackPane.widthProperty(), stackPane.heightProperty());
        gridPane.prefHeightProperty().bind(minimum);
        gridPane.prefWidthProperty().bind(minimum);
        gridPane.maxWidthProperty().bind(gridPane.prefWidthProperty());
        gridPane.maxHeightProperty().bind(gridPane.prefHeightProperty());
        gridPane.setMinSize(0,0);


        double size = board.getSize();
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
                StackPane newStackPane= new StackPane();
                Label newLabel= new Label(Integer.toString(board.getCell(i,j).getValue()));
                if (board.getCell(i,j).getValue()==0)newLabel.setText("");
                newStackPane.getChildren().add(newLabel);
                newStackPane.minHeightProperty().bind(gridPane.prefHeightProperty().divide(size));
                gridPane.add(newStackPane, i, j);
            }
        }

        gridPane.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 5px;");

        for (Node node : gridPane.getChildren()){
            if (GridPane.getRowIndex(node)!=null) {
                int i = GridPane.getRowIndex(node);
                int j = GridPane.getColumnIndex(node);
                Cell c = board.getCell(i, j);
                Region r = c.getRegion();

                String style = "-fx-border-color: ";
                String colorOn = "black";
                String colorOff = "#B0B0B0 ";

                if (i > 0 && board.getCell(i - 1, j).getRegion() != r) {  //ADALT
                    style += colorOn + " ";
                } else style += colorOff + " ";

                if (j < size - 1 && board.getCell(i, j + 1).getRegion() != r) {  //DRETA
                    style += colorOn + " ";
                } else style += colorOff + " ";

                if (i < size - 1 && board.getCell(i + 1, j).getRegion() != r) {  //ABAIX
                    style += colorOn + " ";
                } else style += colorOff + " ";

                if (j > 0 && board.getCell(i, j - 1).getRegion() != r) {  //ESQUERRA
                    style += colorOn + " ";
                } else style += colorOff + " ";

                node.setStyle(style);
            }

        }
    }
}
