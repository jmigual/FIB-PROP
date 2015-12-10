package presentacio;

import domini.Basic.Board;
import domini.Basic.Cell;
import domini.KKRegion.KKRegion;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * Created by Iñigo on 10/12/2015.
 */
public class KKPrinterMultipleSelect extends KKPrinter {

    public ArrayList<Cell> getSelectedCells() {
        return selectedCells;
    }

    private ArrayList<Cell> selectedCells=new ArrayList<>();

    boolean deselecting;

    public KKPrinterMultipleSelect(Board board, StackPane stackPane) {
        super(board, stackPane);
    }

    @Override
    protected void select(StackPane location, boolean dragged) {
        int i = GridPane.getRowIndex(location);
        int j = GridPane.getColumnIndex(location);
        Cell c = board.getCell(i, j);
        if(!dragged){
            deselecting=selectedCells.contains(c);
        }
        if(deselecting && selectedCells.contains(c))selectedCells.remove(c);
        else if (!deselecting && !selectedCells.contains(c)) selectedCells.add(c);
        updateCells();
    }
    public void deselect(){
        selectedCells.clear();
        updateCells();
    }
    @Override
    protected String calculateColor(Cell c) {
        if (selectedCells==null)selectedCells=new ArrayList<>();
        boolean error = (!c.getColumn().isCorrect() || !c.getRegion().isCorrect() || !c.getRow().isCorrect());
        if (((KKRegion)c.getRegion()).getOperationValue()==0)error=false;
        boolean selected = selectedCells.contains(c);
        if (error && selected) return selectedErrorColor;
        if (error) return errorColor;
        if (selected) return selectedColor;
        return backgroundColor;
    }

}