import com.formdev.flatlaf.FlatLightLaf;
import controller.MainController;
import model.SalaryModel;
import view.MainView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {

        FlatLightLaf.setup();
        UIManager.put("Button.arc", 12);
        UIManager.put("Component.arc", 8);
        UIManager.put("Component.focusWidth", 1);

        SwingUtilities.invokeLater(() -> {
            SalaryModel model = new SalaryModel();
            MainView view = new MainView(model);
            new MainController(model, view);
            view.setVisible(true);
        });
    }
}