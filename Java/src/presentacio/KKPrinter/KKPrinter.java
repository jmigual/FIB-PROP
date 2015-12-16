package presentacio.KKPrinter;

import domini.Basic.Board;
import domini.Basic.Cell;
import domini.Basic.Region;
import domini.KKBoard;
import domini.KKRegion.KKRegion;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.util.HashSet;
import java.util.Objects;

/**
 * Created by Inigo on 05/12/2015.
 */
public abstract class KKPrinter {

    protected String backgroundColor = "white";
    protected String selectedColor = "skyBlue";
    protected String errorColor = "Red";
    protected String selectedErrorColor = "purple";
    protected String borderColor = "black";
    protected int borderWidth = 5;
    protected String cellDividerColor = "gray";
    protected String regionDividerColor = "black";
    protected String annotationColor = "darkGray";
    protected int dividerWidth=2;
    boolean opcio = false;



    public KKBoard getBoard() {
        return board;
    }

    public void setBoard(KKBoard board) {
        this.board = board;
    }

    protected KKBoard board;
    protected StackPane stackPane;
    protected GridPane gridPane;


    public KKPrinter(KKBoard board, StackPane stackPane) {
        this.board = board;
        this.stackPane = stackPane;
        createGrid();
    }
    public KKPrinter(KKPrinter kkPrinter){
        this.board=kkPrinter.board;
        this.stackPane=kkPrinter.stackPane;
        this.gridPane=kkPrinter.gridPane;

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && node instanceof StackPane) {
                addEvents((StackPane)node);
            }
        }
    }

    public void clear(){
        stackPane.getChildren().remove(gridPane);
    }

    protected final void createGrid() {
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

        HashSet<KKRegion> set = new HashSet<KKRegion>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                StackPane newStackPane = new StackPane();

                //label inside stackpane
                Label newLabel = new Label(Integer.toString(board.getCell(i, j).getValue()));
                if (board.getCell(i, j).getValue() == 0) newLabel.setText("");
                newLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", minimum.divide(size * 1.3).asString(), "px;"));
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
                    if (r.getOperationValue()!=0) {
                        String operationLabelText=" " + Integer.toString(r.getOperationValue());
                        if (r.getCells().size()!=1)operationLabelText+= " " + r.getOperation().toString();
                        Label operationLabel = new Label(operationLabelText);
                        operationLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", newStackPane.heightProperty().divide(5).asString(), "px;"));
                        StackPane.setAlignment(operationLabel, Pos.TOP_LEFT);
                        // StackPane.setMargin(operationLabel,new Insets(0,0,0,2));
                        newStackPane.getChildren().add(operationLabel);
                    }
                }
                addEvents(newStackPane);
                addAnnotationGrid(newStackPane);

                gridPane.add(newStackPane, j, i); //WTF who puts columns first?
            }
        }
        //setting grid style
        gridPane.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-color: " + borderColor + "; -fx-border-width: " + Integer.toString(borderWidth) + "px;");
        if (opcio) {
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getRowIndex(node) != null) {
                    int i = GridPane.getRowIndex(node);
                    int j = GridPane.getColumnIndex(node);
                    Cell c = board.getCell(i, j);
                    Region r = c.getRegion();

                    String style = "-fx-border-width: "+Integer.toString(dividerWidth)+"px; -fx-border-color: ";

                    if (i > 0 && board.getCell(i - 1, j).getRegion() != r) {  //ADALT
                        style += regionDividerColor;
                    } else style += cellDividerColor;
                    style += " ";
                    if (j < size - 1 && board.getCell(i, j + 1).getRegion() != r) {  //DRETA
                        style += regionDividerColor;
                    } else style += cellDividerColor;

                    style += " ";
                    if (i < size - 1 && board.getCell(i + 1, j).getRegion() != r) {  //ABAIX
                        style += regionDividerColor;
                    } else style += cellDividerColor;

                    style += " ";
                    if (j > 0 && board.getCell(i, j - 1).getRegion() != r) {  //ESQUERRA
                        style += regionDividerColor;
                    } else style += cellDividerColor;

                    style += ";";

                    node.setStyle(style);
                }

            }
        }
        else{
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getRowIndex(node) != null) {
                    int i = GridPane.getRowIndex(node);
                    int j = GridPane.getColumnIndex(node);
                    Cell c = board.getCell(i, j);
                    Region r = c.getRegion();

                    String style = "-fx-border-color: "+regionDividerColor+"; -fx-border-width: ";

                    if (i > 0 && board.getCell(i - 1, j).getRegion() != r) {  //ADALT
                        style += Integer.toString(dividerWidth)+"px";
                    } else style += "0px";
                    style += " ";
                    if (j < size - 1 && board.getCell(i, j + 1).getRegion() != r) {  //DRETA
                        style += Integer.toString(dividerWidth)+"px";
                    } else style += "0px";

                    style += " ";
                    if (i < size - 1 && board.getCell(i + 1, j).getRegion() != r) {  //ABAIX
                        style += Integer.toString(dividerWidth)+"px";
                    } else style += "0px";

                    style += " ";
                    if (j > 0 && board.getCell(i, j - 1).getRegion() != r) {  //ESQUERRA
                        style += Integer.toString(dividerWidth)+"px";
                    } else style += "0px";

                    style += ";";

                    node.setStyle(style);
                }

            }
        }
        updateCells();
        updateAnnotations();
    }

    private void addEvents(StackPane newStackPane) {

        newStackPane.setOnDragDetected( event -> {
            newStackPane.startFullDrag();
            Object source = event.getSource();
            if (source instanceof StackPane) select((StackPane) source, false);
        });
        newStackPane.setOnMouseClicked(event -> {
            Object source = event.getSource();
            if (source instanceof StackPane) select((StackPane) source, false);
        });
        newStackPane.setOnMouseDragEntered(event->{
            Object source = event.getSource();

            if (source instanceof StackPane) select((StackPane) source, true);

        });
    }
    
    private void addAnnotationGrid(StackPane stackPane) {
        int size=board.getSize();
        int gridSize=(int)Math.ceil(Math.sqrt(size));
        GridPane annotationGrid= new GridPane();
        //annotationGrid.setGridLinesVisible(true);
        annotationGrid.prefHeightProperty().bind(stackPane.widthProperty().divide(1.5));
        annotationGrid.prefWidthProperty().bind(stackPane.widthProperty().divide(1.5));
        annotationGrid.maxWidthProperty().bind(annotationGrid.prefWidthProperty());
        annotationGrid.maxHeightProperty().bind(annotationGrid.prefHeightProperty());
        annotationGrid.setMinSize(0, 0);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100 / gridSize);
        columnConstraints.setHalignment(HPos.CENTER);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100 / gridSize);
        rowConstraints.setValignment(VPos.CENTER);


        for (int i = 0; i < gridSize; i++) {
            annotationGrid.getColumnConstraints().add(columnConstraints);
            annotationGrid.getRowConstraints().add(rowConstraints);
        }
        annotationGrid.setId("AnnotationGrid");
        stackPane.getChildren().add(annotationGrid);
        StackPane.setAlignment(annotationGrid,Pos.BOTTOM_RIGHT);

        for (int n=0; n<size; n++){
            int i=n/gridSize;
            int j=n-i*gridSize;
            Label l = new Label (Integer.toString(n+1));
            l.setId("OperationLabel#"+Integer.toString(n+1));
            l.styleProperty().bind(Bindings.concat("-fx-text-fill: "+annotationColor+"; -fx-font-size: ", stackPane.widthProperty().divide(gridSize * 1.2 * (n >= 9 ? 2 : 1)).asString(), "px;"));
            annotationGrid.add(l,j,i);
        }
    }
    protected abstract void select(StackPane location, boolean dragging);

    public void updateRegions(){
        clear();
        createGrid();
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
                String color = calculateColor(c);
                if (!Objects.equals(color, backgroundColor))s += "-fx-background-color: " + color + ";";
                node.setStyle(s);
            }
        }
    }
    public void updateContent() {
        updateCells();
        updateAnnotations();
    }

    public void updateAnnotations(){
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && node instanceof StackPane) {
                StackPane stackPane=(StackPane)node;
                int i = GridPane.getRowIndex(node);
                int j = GridPane.getColumnIndex(node);
                Cell c = board.getCell(i, j);
                for (int n=1; n<=board.getSize(); n++){
                    Label l=(Label)stackPane.lookup("#OperationLabel#"+Integer.toString(n));
                    l.setVisible(c.getAnnotation(n) && c.getValue()==0);
                }


            }
        }
    }

    protected abstract String calculateColor(Cell c);


}
