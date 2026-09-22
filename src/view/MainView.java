package view;

import model.SalaryModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainView extends JFrame implements PropertyChangeListener {

    private final SalaryModel model;

    private JLabel hourlyRateLabel;
    private JLabel breathCostLabel;
    private JLabel heavyBreathCostLabel;
    private JLabel clickCostLabel;
    private JLabel lineCostLabel;
    private JLabel bugCostLabel;

    private JButton enterDataButton;
    private JButton calculateButton;

    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 15);
    private static final Font VALUE_FONT = new Font("SansSerif", Font.BOLD, 15);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 20);

    public MainView(SalaryModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        setTitle("Cost of Slave");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32)); // "воздух" по краям окна

        JLabel title = new JLabel("Стоимость раба");
        title.setFont(TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // отступ снизу от заголовка

        JPanel resultsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 20); // вертикальный отступ между строками + горизонтальный между колонками
        gbc.anchor = GridBagConstraints.WEST;

        hourlyRateLabel = new JLabel("—");
        breathCostLabel = new JLabel("—");
        heavyBreathCostLabel = new JLabel("—");
        clickCostLabel = new JLabel("—");
        lineCostLabel = new JLabel("—");
        bugCostLabel = new JLabel("—");

        addRow(resultsPanel, gbc, 0, "\uD83D\uDCB0Ставка в час:", hourlyRateLabel);
        addRow(resultsPanel, gbc, 1, " Цена вдоха:", breathCostLabel);
        addRow(resultsPanel, gbc, 2, " Цена глубокого вздоха:", heavyBreathCostLabel);
        addRow(resultsPanel, gbc, 3, "\uD83D\uDDB1Цена клика:", clickCostLabel);
        addRow(resultsPanel, gbc, 4, " \uD83D\uDCDDЦена строки кода:", lineCostLabel);
        addRow(resultsPanel, gbc, 5, "\uD83D\uDC1BЦена бага:", bugCostLabel);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        enterDataButton = new JButton("Ввести данные");
        calculateButton = new JButton("Рассчитать");
        enterDataButton.setFont(LABEL_FONT);
        calculateButton.setFont(LABEL_FONT);
        buttonsPanel.add(enterDataButton);
        buttonsPanel.add(calculateButton);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(24, 0, 0, 0)); // отступ сверху от кнопок


        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(resultsPanel);

        root.add(title, BorderLayout.NORTH);
        root.add(centerWrapper, BorderLayout.CENTER);
        root.add(buttonsPanel, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JLabel valueLabel) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel nameLabel = new JLabel(labelText);
        nameLabel.setFont(LABEL_FONT);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        valueLabel.setFont(VALUE_FONT);
        panel.add(valueLabel, gbc);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("calculated".equals(evt.getPropertyName())) {
            hourlyRateLabel.setText(String.format("%.4f", model.getHourlyRate()));
            breathCostLabel.setText(String.format("%.6f", model.getBreathCost()));
            heavyBreathCostLabel.setText(String.format("%.6f", model.getHeavyBreathCost()));
            clickCostLabel.setText(String.format("%.6f", model.getClickCost()));
            lineCostLabel.setText(String.format("%.6f", model.getLineCost()));
            bugCostLabel.setText(String.format("%.6f", model.getBugCost()));

        }
    }

    public JButton getEnterDataButton() {
        return enterDataButton;
    }

    public JButton getCalculateButton() {
        return calculateButton;
    }


}