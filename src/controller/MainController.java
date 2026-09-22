package controller;

import model.SalaryModel;
import view.MainView;
import view.InputDialog;

import javax.swing.JOptionPane;

public class MainController {

    private final SalaryModel model;
    private final MainView view;

    public MainController(SalaryModel model, MainView view) {
        this.model = model;
        this.view = view;
        attachListeners();
    }

    private void attachListeners() {
        view.getEnterDataButton().addActionListener(e -> onEnterData());
        view.getCalculateButton().addActionListener(e -> onCalculate());
    }

    private void onEnterData() {
        InputDialog dialog = new InputDialog(view, model);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            model.setSalary(dialog.getSalary());
            model.setDeclaredHours(dialog.getDeclaredHours());
            model.setWorkedHours(dialog.getWorkedHours());
        }

    }

    private void onCalculate() {
        if (model.getWorkedHours() <= 0) {
            JOptionPane.showMessageDialog(
                    view,
                    "Сначала введите данные через кнопку \"Ввести данные\"",
                    "Нет данных",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        model.calculate();
    }
}