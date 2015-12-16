package presentacio.CollectionView;

import domini.KKBoard;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import presentacio.MainWindow;

import java.util.Map;

/**
 * Created by Esteve on 16/12/2015.
 */
public class CollectionViewMatchController extends CollectionViewController {

    public CollectionViewMatchController(MainWindow main) {
        super(main);

        String username = main.getUsername();
        for (Map.Entry<String, CheckBox> e : mPlayers.entrySet()) {
            e.getValue().setSelected(e.getKey().equals(username));
            e.getValue().setDisable(true);
        }
        applyFilters();
    }


}


