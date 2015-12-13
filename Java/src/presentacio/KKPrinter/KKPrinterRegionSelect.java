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
public class KKPrinterRegionSelect extends KKPrinter {
    public KKPrinterRegionSelect(KKBoard board, StackPane stackPane) {
        super(board, stackPane);
    }
    public KKPrinterRegionSelect(KKPrinter kkPrinter) {
        super(kkPrinter);
    }

    public KKRegion getSelectedKKRegion() {
        return selectedKKRegion;
    }

    private KKRegion selectedKKRegion;


    @Override
    protected void select(StackPane location, boolean dragged) {
        int i = GridPane.getRowIndex(location);
        int j = GridPane.getColumnIndex(location);
        Cell c = board.getCell(i, j);
        selectedKKRegion=(KKRegion)c.getRegion();
        updateCells();
    }

    @Override
    protected String calculateColor(Cell c) {
        boolean error = (!c.getColumn().isCorrect() || !c.getRegion().isCorrect() || !c.getRow().isCorrect());
        boolean selected = false;
        if (selectedKKRegion!=null)selected= selectedKKRegion.getCells().contains(c);
        if (((KKRegion)c.getRegion()).getOperationValue()==0)error=false;
        if (error && selected)return selectedErrorColor;
        if (error) return errorColor;
        if (selected)return selectedColor;
        return backgroundColor;
    }
}
