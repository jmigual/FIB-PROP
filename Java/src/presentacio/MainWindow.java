package presentacio;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Joan on 03/12/2015.
 */
public class MainWindow extends JFrame {
    private JPanel panel1;
    private ResourceBundle trans;

    public MainWindow() {
        Locale loc = new Locale("es");
        trans = ResourceBundle.getBundle("MainWindow", loc);
        createUIComponents();
    }

    private void createUIComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(trans.getString("windowName"));
        Dimension d = new Dimension();
        d.setSize(200, 200);
        setMinimumSize(d);
        setLocationRelativeTo(null);
        createMenuBar();
        setContentPane(panel1);
    }

    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();

        JMenu boardMenu = new JMenu(trans.getString("menu_board"));
        JMenu userMenu = new JMenu(trans.getString("menu_user"));
        JMenu statsMenu = new JMenu(trans.getString("menu_stats"));
        JMenu languageMenu = new JMenu(trans.getString("menu_language"));



        menubar.add(boardMenu);
        menubar.add(userMenu);
        menubar.add(statsMenu);
        menubar.add(languageMenu);
        setJMenuBar(menubar);
    }


    public static void main(String[] args) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
