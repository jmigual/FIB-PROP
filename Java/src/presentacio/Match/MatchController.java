package presentacio.Match;

import domini.Basic.Cell;
import domini.Basic.Match;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import presentacio.Controller;
import presentacio.KKPrinter.KKPrinter;
import presentacio.KKPrinter.KKPrinterMultipleSelect;
import presentacio.KKPrinter.KKPrinterSingleSelect;

import javafx.util.Duration;
import presentacio.MainWindow;

import java.util.ArrayList;

/**
 * Created by Marc on 15/12/2015.
 */
public class MatchController extends AnchorPane implements Controller {
    private AnchorPane rootLayout;
    private KKPrinter printer;
    private MainWindow _main;
    private Scene scene;

    private Match _match;

    @FXML
    private StackPane kenken;

    @FXML
    private Button ann;

    @FXML
    private Label score;

    @FXML
    private Button sortir;

    @FXML
    private MenuItem h1;

    @FXML
    private MenuItem h2;

    @FXML
    private MenuItem h3;

    @FXML
    private VBox vbox;

    @FXML
    private HBox hbox;

    @FXML
    private Pane pane;

    @FXML
    private HBox ajudesBox;

    public Match getMatch(){
        return _match;
    }

    public void setMatch(Match match) {
        _match = match;
    }

    public MatchController (Match match, MainWindow main){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MatchWindow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            rootLayout = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        _main = main;
        _match = match;
        printer = new KKPrinterSingleSelect(_match.getBoard(), kenken);
        score.setText("0");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                ae -> periodic()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void periodic (){
        score.setText(Long.toString(_match.getScore()));
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
        if (printer instanceof KKPrinterSingleSelect &&  n<=_match.getBoard().getSize()) {
            Cell cell = ((KKPrinterSingleSelect) printer).getSelectedCell();
            if (cell!= null) {
                if (event.isControlDown() && n > 0) {
                    cell.switchAnnotation(n);
                    printer.updateAnnotations();
                } else { //FER MOVIMENT

                    if (!_match.makeMove(cell.getRow().getPos() + 1, cell.getColumn().getPos() + 1, n)) playsound(1);
                    if (_match.isComplete()) {
                        if (_match.checkFinish()) {
                            //out.println("FELICITATS! Has completat aquest taulell amb una puntuacio de " + _match.getScore());
                            // finish = true;
                            playsound(2);
                            printer.updateContent();
                            inform("", "FELICITATS! HAS ACABAT EL KENKEN!", "Has aconseguit una puntuació de " + Long.toString(_match.getScore()));

                            close();
                        }
                        else inform("", "QUINA PENA!", "La solució no és correcte");
                    }
                    printer.updateContent();
                }
            }


        }
        else if (printer instanceof KKPrinterMultipleSelect && n>0 && n<=_match.getBoard().getSize()){
            ArrayList<Cell> cells = ((KKPrinterMultipleSelect) printer).getSelectedCells();
            if (cells != null) for (Cell c : cells) c.switchAnnotation(n);
            printer.updateAnnotations();
        }
    }

    @FXML
    private void revertir (){
        if (!_match.back()) playsound(0);
        printer.updateContent();
    }

    @FXML
    private void refer(){
        if (!_match.forward()) playsound(0);
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
            ann.setText("Anotació");
        }
        printer.updateContent();
    }

    void playsound(int i){
        AudioClip claps = null;
        switch(i) {
            case 0://No pots clicar
                claps= new AudioClip(getClass().getResource("click.mp3").toString());
                break;
            case 1://error
                claps= new AudioClip(getClass().getResource("error.mp3").toString());
                break;
            case 2://aplause
                claps= new AudioClip(getClass().getResource("app-5.mp3").toString());
                break;

        }
        claps.play();

    }

    public void close(){
        _main.getMainController().dialogCancelled();
    }

    public void clearAnn(){
        for (int i = 0; i<_match.getBoard().getSize(); ++i)
            for (int j = 0; j< _match.getBoard().getSize(); ++j)
                for (int k = 0; k<_match.getBoard().getSize(); ++k)_match.getBoard().getCell(i,j).setAnnotation(k+1,false);
        printer.updateContent();
    }

    public void clearNum(){
        for (int i = 0; i<_match.getBoard().getSize(); ++i)
            for (int j = 0; j< _match.getBoard().getSize(); ++j) _match.getBoard().getCell(i,j).setValue(0);
        printer.updateContent();
    }

    public void hints(ActionEvent event) {
        MenuItem mi = (MenuItem)event.getSource();
        ArrayList<Cell> cells = null;
        if (mi.equals(h1)) {
            try {
                cells = _match.hint(0);
            }
            catch (Exception e){
                warn("Error!","NO S'HA TROBAT CAP SOLUCIÓ!","No hi ha cap solució a partir dels valors insertats");
                return;
            }

            for (Cell c: cells) printer.marker(c, "Red");
        }

        if (mi.equals(h2)) {
            try {
                cells = _match.hint(1);
            }
            catch (Exception e){
                warn("Error!","NO S'HA TROBAT CAP SOLUCIÓ!","No hi ha cap solució a partir dels valors insertats");
                return;
            }
            if (cells == null) warn("Error", "No queden espais lliures!", "Borra el contingut d'alguna casella");
            else {
                printer.updateContent();
                printer.marker(cells.get(0), "Green");
                if (_match.checkFinish() && _match.isComplete()) {
                    playsound(2);
                    printer.updateContent();
                    inform("", "FELICITATS! HAS ACABAT EL KENKEN!", "Has aconseguit una puntuació de " + Long.toString(_match.getScore()));

                    close();
                }
            }
        }

        if (mi.equals(h3)){
            try {
                _match.hint(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            _main.getMainController().showFactBox();
        }
    }

    public void solve(){
        _match.getBoard().clear();
        _match.getBoard().solve();

        printer.updateContent();
        try {
            _match.hint(3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        scene.setOnKeyPressed(event -> {
        });

        ajudesBox.setDisable(true);
        hbox.setDisable(true);
        pane.setDisable(true);
    }

    private void warn(String title, String header, String body){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(body);
        a.showAndWait();
    }
    private void inform(String title, String header, String body) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(body);
        a.showAndWait();
    }

}
