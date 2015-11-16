package runners;

import dades.PlayersAdminTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by Joan on 14/11/2015.
 */
public class TestRunner1 {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PlayersAdminTest.class);
        for (Failure f : result.getFailures()) {
            System.out.println(f.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
