import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private JPanel buttonPanel;
    private String operator = "";
    private double num1 = 0, num2 = 0;

    public Calculator() {
        // Frame setup
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setPreferredSize(new Dimension(400, 80));
        add(display, BorderLayout.NORTH);

        // Button Panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        add(buttonPanel, BorderLayout.CENTER);

        // Buttons
        String[] buttons = {
			"C", "√", "%","DEL",
            "7", "8", "9", "÷", 
            "4", "5", "6", "×", 
            "1", "2", "3", "-", 
            ".", "0", "=", "+" 
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setFocusPainted(false);
            button.setBackground(Color.LIGHT_GRAY);
            button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        getContentPane().setBackground(Color.WHITE); // Light theme
    }

    @Override
public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    // Clear button
    if (command.equals("C")) {
        display.setText("");
        num1 = num2 = 0;
        operator = "";
        return;
    }

    // Backspace button
    if (command.equals("DEL")) {
        String text = display.getText();
        if (!text.isEmpty()) {
            display.setText(text.substring(0, text.length() - 1)); // Remove the last character
        }
        return;
    }

    // Equals button
    if (command.equals("=")) {
        if (!operator.isEmpty()) {
            num2 = Double.parseDouble(display.getText());
            double result = switch (operator) {
                case "+" -> num1 + num2;
                case "-" -> num1 - num2;
                case "×" -> num1 * num2;
                case "÷" -> num1 / num2;
                case "%" -> num1 % num2;
                default -> 0;
            };
            display.setText(String.valueOf(result));
            operator = "";
        }
        return;
    }

    // Square root button
    if (command.equals("√")) {
        try {
            double value = Double.parseDouble(display.getText());
            if (value >= 0) {
                display.setText(String.valueOf(Math.sqrt(value)));
            } else {
                display.setText("Error"); // Display error for negative input
            }
        } catch (NumberFormatException ex) {
            display.setText("Error"); // Display error if the input is invalid
        }
        return;
    }

    // Operators
    if ("+-×÷%".contains(command)) {
        operator = command;
        num1 = Double.parseDouble(display.getText());
        display.setText("");
        return;
    }

    // Numbers and decimal
    display.setText(display.getText() + command);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
