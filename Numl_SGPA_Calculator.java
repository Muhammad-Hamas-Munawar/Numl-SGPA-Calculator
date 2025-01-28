import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GPA Calculator Application Numl_SGPA_Calculator
 *
 * @author ME
 */
public class Numl_SGPA_Calculator extends JFrame {

    private static final int MAX_SUBJECTS = 10;
    private JButton addSubjectButton;
    private JButton calculateSgpaButton;
    private JLabel sgpaLabel;
    private JPanel inputPanel;
    private List<JComponent[]> subjectRows;

    /**
     * Constructor to initialize the Numl_SGPA_Calculator.
     */
    public Numl_SGPA_Calculator() {
        setTitle("GPA Calculator");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());
        subjectRows = new ArrayList<>();
        initComponents();
        setLocationRelativeTo(null); // Center the frame on screen
    }

    /**
     * Initialize and organize components.
     */
    private void initComponents() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Title label
        JLabel titleLabel = new JLabel("GPA Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 7;
        constraints.anchor = GridBagConstraints.CENTER;
        add(titleLabel, constraints);

        // Column headers
        addColumnHeaders(constraints);

        // Input panel for subjects
        inputPanel = new JPanel(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(inputPanel, constraints);

        // Add initial subject row
        addSubjectFields();

        // Buttons
        addSubjectButton = new JButton("Add Subject");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.WEST;
        add(addSubjectButton, constraints);

        calculateSgpaButton = new JButton("Calculate SGPA");
        constraints.gridx = 5;
        constraints.gridy = 4;
        add(calculateSgpaButton, constraints);

        sgpaLabel = new JLabel("SGPA: 0.00"); // Added default SGPA label
        sgpaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 6;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;
        add(sgpaLabel, constraints);

        // Button actions
        addSubjectButton.addActionListener(e -> {
            if (subjectRows.size() < MAX_SUBJECTS) {
                addSubjectFields();
            } else {
                showError("Maximum limit of " + MAX_SUBJECTS + " subjects reached.");
            }
        });

        calculateSgpaButton.addActionListener(e -> calculateSgpa());
    }

    /**
     * Add column headers to the frame.
     */
    private void addColumnHeaders(GridBagConstraints constraints) {
        String[] headers = { "No.", "Subject", "Total Marks", "Obtained Marks", "Credit Hours", "Calculate",
                "Subject GPA" };
        for (int i = 0; i < headers.length; i++) {
            JLabel headerLabel = new JLabel(headers[i], JLabel.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // Padding for alignment
            constraints.gridx = i;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.anchor = GridBagConstraints.CENTER;
            add(headerLabel, constraints);
        }
    }

    /**
     * Add a new row of subject input fields.
     */
    private void addSubjectFields() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;

        JLabel subjectNoLabel = new JLabel(String.valueOf(subjectRows.size() + 1), JLabel.CENTER);
        JTextField subjectTextField = new JTextField(10);
        JTextField totalMarksTextField = new JTextField("100", 10);
        JTextField obtainedMarksTextField = new JTextField(10);
        JTextField creditHoursTextField = new JTextField(10);
        JLabel subjectGpaLabel = new JLabel("N/A", JLabel.CENTER);
        JButton rowCalculateButton = new JButton("Calculate");

        int row = subjectRows.size();
        constraints.gridy = row;

        constraints.gridx = 0;
        inputPanel.add(subjectNoLabel, constraints);

        constraints.gridx = 1;
        inputPanel.add(subjectTextField, constraints);

        constraints.gridx = 2;
        inputPanel.add(totalMarksTextField, constraints);

        constraints.gridx = 3;
        inputPanel.add(obtainedMarksTextField, constraints);

        constraints.gridx = 4;
        inputPanel.add(creditHoursTextField, constraints);

        constraints.gridx = 5;
        inputPanel.add(rowCalculateButton, constraints);

        constraints.gridx = 6;
        inputPanel.add(subjectGpaLabel, constraints);

        subjectRows.add(new JComponent[] { subjectTextField, totalMarksTextField, obtainedMarksTextField,
                creditHoursTextField, subjectGpaLabel });

        rowCalculateButton.addActionListener(e -> calculateRowGpa(subjectTextField, totalMarksTextField,
                obtainedMarksTextField, creditHoursTextField, subjectGpaLabel));

        revalidate();
        repaint();
    }

    /**
     * Calculate GPA for a specific row.
     */
    private void calculateRowGpa(JTextField subjectField, JTextField totalMarksField, JTextField obtainedMarksField,
            JTextField creditHoursField, JLabel gpaLabel) {
        try {
            double totalMarks = Double.parseDouble(totalMarksField.getText());
            double obtainedMarks = Double.parseDouble(obtainedMarksField.getText());
            double creditHours = Double.parseDouble(creditHoursField.getText());

            if (totalMarks <= 0 || creditHours <= 0) {
                showError("Total marks and credit hours must be greater than zero.");
                return;
            }

            double percentage = (obtainedMarks / totalMarks) * 100;
            double gpa = calculateGpaFromPercentage(percentage);

            gpaLabel.setText(String.format("%.2f", gpa));
        } catch (NumberFormatException ex) {
            showError("Please enter valid numeric values.");
        }
    }

    /**
     * Calculate GPA from percentage.
     */
    private double calculateGpaFromPercentage(double percentage) {
        if (percentage >= 90)
            return 4.0;
        if (percentage >= 80)
            return 4.0;
        if (percentage >= 75)
            return 3.5 + ((percentage - 75) / 10);
        if (percentage >= 70)
            return 3.0 + ((percentage - 70) / 10);
        if (percentage >= 65)
            return 2.5 + ((percentage - 65) / 10);
        if (percentage >= 60)
            return 2.0 + ((percentage - 60) / 10);
        if (percentage >= 55)
            return 1.5 + ((percentage - 55) / 10);
        if (percentage >= 50)
            return 1.0 + ((percentage - 50) / 10);
        return 0.0;
    }

    /**
     * Calculate SGPA based on all rows.
     */
    private void calculateSgpa() {
        try {
            double totalGpa = 0;
            double totalCreditHours = 0;

            for (JComponent[] row : subjectRows) {
                double gpa = Double.parseDouble(((JLabel) row[4]).getText());
                double creditHours = Double.parseDouble(((JTextField) row[3]).getText());

                totalGpa += gpa * creditHours;
                totalCreditHours += creditHours;
            }

            if (totalCreditHours == 0) {
                showError("Total credit hours cannot be zero.");
                return;
            }

            double sgpa = totalGpa / totalCreditHours;
            sgpaLabel.setText("SGPA: " + String.format("%.2f", sgpa));
        } catch (NumberFormatException ex) {
            showError("Please ensure all rows are calculated and contain valid data.");
        }
    }

    /**
     * Display an error message.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Main method to run the GPA Calculator application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Numl_SGPA_Calculator().setVisible(true));
    }
}
