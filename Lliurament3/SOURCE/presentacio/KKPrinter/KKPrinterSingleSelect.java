package presentacio.KKPrinter;

import domini.Basic.Board;
import domini.Basic.Cell;
import domini.KKBoard;
import domini.KKRegion.KKRegion;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Created by Iñigo on 10/12/2015.
 */
public class KKPrinterSingleSelect extends KKPrinter {

    public Cell getSelectedCell() {
        return selectedCell;
    }

    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

    private Cell selectedCell;

    public KKPrinterSingleSelect (KKBoard board, StackPane stackPane) {
        super(board,stackPane);
    }
    public KKPrinterSingleSelect(KKPrinter kkPrinter) {
        super(kkPrinter);
    }
    @Override
    protected void select(StackPane location, boolean dragged) {
        int i = GridPane.getRowIndex(location);
        int j = GridPane.getColumnIndex(location);
        if (board.getCell(i,j)==selectedCell)selectedCell=null;
        else selectedCell=board.getCell(i,j);
        updateCells();
    }

    @Override
    protected String calculateColor(Cell c) {
        boolean error = (!c.getColumn().isCorrect() || !c.getRegion().isCorrect() || !c.getRow().isCorrect());
        if (((KKRegion)c.getRegion()).getOperationValue()==0)error=false;
        if (error && c==selectedCell)return selectedErrorColor;
        if (error) return errorColor;
        if (c==selectedCell)return selectedColor;
        return backgroundColor;
    }
}
