package presentacio.CollectionView;

import javafx.scene.control.CheckBox;
import presentacio.MainWindow;

import java.util.Map;

/**
 * Created by Joan on 15/12/2015.
 */
public class CollectionViewEditorController extends CollectionViewController {

    public CollectionViewEditorController(MainWindow main) {
        super(main);

        String username = main.getUsername();
        for (Map.Entry<String, CheckBox> e : mPlayers.entrySet()) {
            e.getValue().setSelected(e.getKey().equals(username));
            e.getValue().setDisable(true);
        }
        applyFilters();
    }
}
