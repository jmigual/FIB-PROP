package presentacio;

import javafx.scene.layout.AnchorPane;

import java.util.Stack;

/**
 * Created by Joan on 15/12/2015.
 */
public class ControllerSwitch {

    private Stack<Controller> mPrevious;

    private Controller mCurrent;
    private AnchorPane mArea;

    public ControllerSwitch(AnchorPane area) {
        mPrevious = new Stack<>();
        mArea = area;
    }

    public void add(Controller c) {
        switchController(c);
        mPrevious.push(c);
    }

    public void rollBack() {
        if (mPrevious.isEmpty()) {
            switchController(null);
            return;
        }
        else if (mPrevious.size() == 1) {
            mPrevious.pop().stop();
            switchController(null);
            return;
        }

        mPrevious.pop().stop();
        switchController(mPrevious.peek());
    }

    public void switchController(Controller c) {
        mArea.getChildren().clear();
        if (c == null) return;

        if (! mPrevious.isEmpty() && mPrevious.peek() != null) mPrevious.peek().stop();

        AnchorPane newPane = c.getRootLayout();
        AnchorPane.setBottomAnchor(newPane, 0.);
        AnchorPane.setTopAnchor(newPane, 0.);
        AnchorPane.setLeftAnchor(newPane, 0.);
        AnchorPane.setRightAnchor(newPane, 0.);
        mArea.getChildren().add(newPane);
        mCurrent = c;
    }

    public Controller getController() {
        return mCurrent;
    }
}
