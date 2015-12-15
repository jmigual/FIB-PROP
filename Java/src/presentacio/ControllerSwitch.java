package presentacio;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.util.Stack;

/**
 * Created by Joan on 15/12/2015.
 */
public class ControllerSwitch {

    private Stack<Controller> mPrevious;

    private Controller mCurrent;
    private AnchorPane mArea;

    public Scene getScene() {
        return mScene;
    }

    public void setScene(Scene mScene) {
        this.mScene = mScene;
    }

    private Scene mScene;

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

        if (mCurrent != null) mCurrent.stop();

        AnchorPane newPane = c.getRootLayout();
        AnchorPane.setBottomAnchor(newPane, 0.);
        AnchorPane.setTopAnchor(newPane, 0.);
        AnchorPane.setLeftAnchor(newPane, 0.);
        AnchorPane.setRightAnchor(newPane, 0.);
        c.setScene(mScene);
        mArea.getChildren().add(newPane);
        mCurrent = c;
    }

    public Controller getController() {
        return mCurrent;
    }
}
