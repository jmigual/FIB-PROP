package presentacio;

import domini.Basic.Cell;
import domini.Basic.Match;
import domini.BoardCreator.HumanBoardCreator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;
import presentacio.KKPrinter.KKPrinterSingleSelect;

import java.util.ArrayList;

/**
 * Created by Marc on 15/12/2015.
 */
public class MatchController extends AnchorPane implements Controller {
    private AnchorPane rootLayout;
    private KKPrinter printer;

    private Scene scene;

    private Match _match;

    @FXML
    private StackPane kenken;

    @FXML
    private Button ann;

    public Match getMatch(){
        return _match;
    }

    public void setMatch(Match match) {
        _match = match;
    }

    public MatchController (Match match){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MatchWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        _match = match;
        printer = new KKPrinterSingleSelect(_match.getBoard(), kenken);
    }

    @Override
    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void stop() {
        scene.setOnKeyPressed(event -> {
        });
    }

    @Override
    public void setScene(Scene scene) {
        this.scene=scene;
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DIGIT0 || event.getCode() == KeyCode.NUMPAD0) numEvent(event,0);
            if (event.getCode() == KeyCode.DIGIT1 || event.getCode() == KeyCode.NUMPAD1) numEvent(event,1);
            if (event.getCode() == KeyCode.DIGIT2 || event.getCode() == KeyCode.NUMPAD2) numEvent(event,2);
            if (event.getCode() == KeyCode.DIGIT3 || event.getCode() == KeyCode.NUMPAD3) numEvent(event,3);
            if (event.getCode() == KeyCode.DIGIT4 || event.getCode() == KeyCode.NUMPAD4) numEvent(event,4);
            if (event.getCode() == KeyCode.DIGIT5 || event.getCode() == KeyCode.NUMPAD5) numEvent(event,5);
            if (event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.NUMPAD6) numEvent(event,6);
            if (event.getCode() == KeyCode.DIGIT7 || event.getCode() == KeyCode.NUMPAD7) numEvent(event,7);
            if (event.getCode() == KeyCode.DIGIT8 || event.getCode() == KeyCode.NUMPAD8) numEvent(event,8);
            if (event.getCode() == KeyCode.DIGIT9 || event.getCode() == KeyCode.NUMPAD9) numEvent(event,9);
            if (event.getCode() == KeyCode.A) numEvent(event,10);
            if (event.getCode() == KeyCode.B) numEvent(event,11);
            if (event.getCode() == KeyCode.C) numEvent(event,12);
            if (event.getCode() == KeyCode.S && event.isControlDown());
        });
    }

    private void numEvent(KeyEvent event, int n){
        if (printer instanceof KKPrinterSingleSelect) {
            Cell cell = ((KKPrinterSingleSelect) printer).getSelectedCell();
            if (event.isControlDown()) {
                cell.switchAnnotation(n);
                printer.updateAnnotations();
            }
            else { //FER MOVIMENT

                if (!_match.makeMove(cell.getRow().getPos()+1, cell.getColumn().getPos()+1, n)) playsound();
                else if (_match.checkFinish()) {
                    //out.println("FELICITATS! Has completat aquest taulell amb una puntuacio de " + _match.getScore());
                   // finish = true;
                    System.out.println("JA ESTA");
                }
                printer.updateContent();
            }


        }
        else if (printer instanceof KKPrinterMultipleSelect){
            ArrayList<Cell> cells = ((KKPrinterMultipleSelect) printer).getSelectedCells();
            for (Cell c : cells) c.switchAnnotation(n);
            printer.updateAnnotations();
        }
    }

    @FXML
    private void revertir (){
        if (!_match.back()) playsound();
        printer.updateContent();
    }

    @FXML
    private void refer(){
        if (!_match.forward()) playsound();
        printer.updateContent();
    }

    @FXML
    private void switchAnn(){
        if (printer instanceof KKPrinterSingleSelect) {
            printer = new KKPrinterMultipleSelect(printer);
            ann.setText("Valors");
        }
        else if (printer instanceof KKPrinterMultipleSelect) {
            printer = new KKPrinterSingleSelect(printer);
            ann.setText("Anotaci�");
        }
    }

    void playsound(){}
}
