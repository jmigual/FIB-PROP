package presentacio;

import domini.Basic.Board;
import domini.Basic.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Created by Iñigo on 10/12/2015.
 */
public class KKPrinterSingleSelect extends KKPrinter {

    public Cell getSelectedCell() {
        return selectedCell;
    }

    private Cell selectedCell;

    public KKPrinterSingleSelect (Board board, StackPane stackPane){
        super(board,stackPane);
    }
    @Override
    protected void select(StackPane location) {
        int i = GridPane.getRowIndex(location);
        int j = GridPane.getColumnIndex(location);
        selectedCell=board.getCell(i,j);
        updateCells();
    }

    @Override
    protected String calculateColor(Cell c) {
        boolean error = (!c.getColumn().isCorrect() || !c.getRegion().isCorrect() || !c.getRow().isCorrect());
        if (error && c==selectedCell)return selectedErrorColor;
        if (error) return errorColor;
        if (c==selectedCell)return selectedColor;
        return backgroundColor;
    }
}
