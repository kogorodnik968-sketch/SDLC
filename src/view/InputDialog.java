package view;

import model.SalaryModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class InputDialog extends JDialog {

    private JTextField salaryField;
    private JTextField declaredHoursField;
    private JTextField workedHoursField;

    private Color defaultFieldColor;
    private static final Color ERROR_COLOR = new Color(255, 200, 200);

    private double salary;
    private double declaredHours;
    private double workedHours;
    private boolean confirmed = false;

    public InputDialog(JFrame owner, SalaryModel model) {
        super(owner, "Ввод данных", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(320, 220);
        setLocationRelativeTo(owner);

        initComponents(model);
    }

    private void initComponents(SalaryModel model) {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        salaryField = new JTextField(prefill(model.getSalary()));
        declaredHoursField = new JTextField(prefill(model.getDeclaredHours()));
        workedHoursField = new JTextField(prefill(model.getWorkedHours()));

        defaultFieldColor = salaryField.getBackground();

        formPanel.add(new JLabel("Зарплата:"));
        formPanel.add(salaryField);
        formPanel.add(new JLabel("Часы по договору:"));
        formPanel.add(declaredHoursField);
        formPanel.add(new JLabel("Отработано часов:"));
        formPanel.add(workedHoursField);

        attachLiveValidation(salaryField);
        attachLiveValidation(declaredHoursField);
        attachLiveValidation(workedHoursField);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Отмена");

        okButton.addActionListener(e -> onOk());
        cancelButton.addActionListener(e -> onCancel());

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }


    private String prefill(double value) {
        return value == 0.0 ? "" : String.valueOf(value);
    }


    private void attachLiveValidation(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validateLive(field); }
            @Override
            public void removeUpdate(DocumentEvent e) { validateLive(field); }
            @Override
            public void changedUpdate(DocumentEvent e) { validateLive(field); }
        });
    }

    private void validateLive(JTextField field) {
        String text = field.getText().trim();

        if (text.isEmpty()) {
            field.setBackground(defaultFieldColor);
            return;
        }

        try {
            double value = Double.parseDouble(text);
            field.setBackground(value > 0 ? defaultFieldColor : ERROR_COLOR);
        } catch (NumberFormatException ex) {
            field.setBackground(ERROR_COLOR);
        }
    }


    private void onOk() {
        StringBuilder errors = new StringBuilder();
        boolean valid = true;

        Double salaryValue = validateField(salaryField, "Зарплата", errors);
        Double declaredValue = validateField(declaredHoursField, "Часы по договору", errors);
        Double workedValue = validateField(workedHoursField, "Отработано часов", errors);

        if (salaryValue == null || declaredValue == null || workedValue == null) {
            valid = false;
        }

        if (!valid) {
            JOptionPane.showMessageDialog(
                    this,
                    errors.toString(),
                    "Ошибка ввода",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        this.salary = salaryValue;
        this.declaredHours = declaredValue;
        this.workedHours = workedValue;
        this.confirmed = true;
        dispose();
    }

    private Double validateField(JTextField field, String label, StringBuilder errors) {
        String text = field.getText().trim();

        if (text.isEmpty()) {
            field.setBackground(ERROR_COLOR);
            errors.append(label).append(": поле не заполнено\n");
            return null;
        }

        try {
            double value = Double.parseDouble(text);
            if (value <= 0) {
                field.setBackground(ERROR_COLOR);
                errors.append(label).append(": число должно быть больше нуля\n");
                return null;
            }
            field.setBackground(defaultFieldColor);
            return value;
        } catch (NumberFormatException ex) {
            field.setBackground(ERROR_COLOR);
            errors.append(label).append(": введите корректное число\n");
            return null;
        }
    }

    private void onCancel() {
        confirmed = false;
        dispose();
    }


    public double getSalary() {
        return salary;
    }

    public double getDeclaredHours() {
        return declaredHours;
    }

    public double getWorkedHours() {
        return workedHours;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}