package presentacio.KKPrinter;

import domini.Basic.Board;
import domini.Basic.Cell;
import domini.KKBoard;
import domini.KKRegion.KKRegion;
import javafx.scene.layout.StackPane;

/**
 * Created by Iñigo on 10/12/2015.
 */
public class KKPrinterNoSelect extends KKPrinter {
    public KKPrinterNoSelect (KKBoard board, StackPane stackPane) {
        super(board,stackPane);
    }
    public KKPrinterNoSelect(KKPrinter kkPrinter) {
        super(kkPrinter);
    }

    @Override
    protected void select(StackPane location, boolean dragged) {

    }

    @Override
    protected String calculateColor(Cell c) {
        if ((!c.getColumn().isCorrect() || !c.getRegion().isCorrect() || !c.getRow().isCorrect()) && !(((KKRegion)c.getRegion()).getOperationValue()==0)) return errorColor;
        return backgroundColor;
    }
}
