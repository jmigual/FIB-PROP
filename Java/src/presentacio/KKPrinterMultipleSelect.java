package presentacio;

import domini.Basic.Board;
import domini.Basic.Cell;
import javafx.scene.layout.GridPane;
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

    public KKPrinterMultipleSelect(Board board, StackPane stackPane) {
        super(board, stackPane);
    }

    @Override
    protected void select(StackPane location) {
        int i = GridPane.getRowIndex(location);
        int j = GridPane.getColumnIndex(location);
        Cell c = board.getCell(i, j);
        if(selectedCells.contains(c))selectedCells.remove(c);
        else selectedCells.add(c);
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
        boolean selected = selectedCells.contains(c);
        if (error && selected) return selectedErrorColor;
        if (error) return errorColor;
        if (selected) return selectedColor;
        return backgroundColor;
    }

}