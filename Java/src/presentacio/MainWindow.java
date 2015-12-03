package presentacio;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by Joan on 03/12/2015.
 */
public class MainWindow extends JFrame {
    private JPanel panel1;
    private ResourceBundle trans;

    public MainWindow() {
        trans = ResourceBundle.getBundle("MainWindow");
        createUIComponents();
    }

    private void createUIComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(trans.getString("windowName"));
        Dimension d = new Dimension();
        d.setSize(200, 200);
        setMinimumSize(d);
        setLocationRelativeTo(null);
        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();

        JMenu boardMenu = new JMenu(trans.getString("menu_board"));
        JMenu userMenu = new JMenu(trans.getString("menu_user"));
        JMenu statsMenu = new JMenu(trans.getString("menu_stats"));

        menubar.add(boardMenu);
        menubar.add(userMenu);
        menubar.add(statsMenu);
        setJMenuBar(menubar);
    }


    public static void main(String[] args) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            MainWindow m = new MainWindow();
            m.setVisible(true);
        });
    }
}
