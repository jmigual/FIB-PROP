package presentacio.CollectionView;

import domini.KKBoard;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import presentacio.MainWindow;
import presentacio.Stats.StatsBoardController;

/**
 * Created by Joan on 16/12/2015.
 */
public class CollectionViewStatsController extends CollectionViewController {


    public CollectionViewStatsController(MainWindow main) {
        super(main);
    }

    @Override
    public void dialogAccept() {
        KKBoard sel = null;
        for (RadioButton r : mSelBoard) {
            if (r.isSelected()) sel = (KKBoard) r.getUserData();
        }

        StatsBoardController stats = new StatsBoardController(mMain, sel);
        if (stats.getResult()) {
            Stage s = mMain.getMainController().createNewWindow(stats, StageStyle.UTILITY);
            s.setTitle("Estadística del tauler");
        }
    }
}
