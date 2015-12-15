package presentacio;

import javafx.scene.layout.AnchorPane;

import java.util.Stack;

/**
 * Created by Joan on 15/12/2015.
 */
public class ControllerSwitch {

    private Stack<Controller> mPreviows;

    private AnchorPane mArea;

    public ControllerSwitch(AnchorPane area) {
        mPreviows = new Stack<>();
        mArea = area;
    }

    public void add(Controller c) {
        switchController(c);
        mPreviows.push(c);
    }

    public void rollBack() {
        if (mPreviows.isEmpty()) {
            switchController(null);
            return;
        }

        mPreviows.pop().stop();
        switchController(mPreviows.peek());
    }

    private void switchController(Controller c) {
        mArea.getChildren().clear();
        if (c == null) return;

        if (! mPreviows.isEmpty() && mPreviows.peek() != null) mPreviows.peek().stop();

        AnchorPane newPane = c.getRootLayout();
        AnchorPane.setBottomAnchor(newPane, 0.);
        AnchorPane.setTopAnchor(newPane, 0.);
        AnchorPane.setLeftAnchor(newPane, 0.);
        AnchorPane.setRightAnchor(newPane, 0.);
        mArea.getChildren().add(newPane);
    }
}
