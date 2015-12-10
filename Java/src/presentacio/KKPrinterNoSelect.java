package presentacio;

import domini.Basic.Board;
import domini.Basic.Cell;
import javafx.scene.layout.StackPane;

/**
 * Created by Iñigo on 10/12/2015.
 */
public class KKPrinterNoSelect extends KKPrinter {
    public KKPrinterNoSelect (Board board, StackPane stackPane){
        super(board,stackPane);
    }

    @Override
    protected void select(StackPane location) {

    }

    @Override
    protected String calculateColor(Cell c) {
        if ((!c.getColumn().isCorrect() || !c.getRegion().isCorrect() || !c.getRow().isCorrect())) return errorColor;
        return backgroundColor;
    }
}
