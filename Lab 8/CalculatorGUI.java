import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI extends JFrame implements ActionListener {
    public CalculatorModel model;
    public JPanel panel;
    public JPanel textPanel;
    public JTextField textField;
    public JButton[] buttons;
    public String[] buttonLabels = {"1", "2", "3", "+", "4", "5", "6", "-", "7", "8", "9", "*", "0", ".", "+/-", "/", "DEL", "^", "SQRT",
    "=", "M+", "M-", "M-REC", "M-C", "C"};
    public String operator = "";
    public boolean operandPressed = false;
    public String operandOne = "";
    public String output = "";

    public CalculatorGUI(CalculatorModel model) {
        //Give the gui the functionality
        this.model = model;

        //Set up the calculator
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 450);
        setLocationRelativeTo(null);

        //Create the panels
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1, 1));
        textPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));

        //Create the text bar where you will see the results
        textField = new JTextField();
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setFont(new Font("Arial", Font.BOLD, 20));
        textField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        textPanel.add(textField);

        //Create the buttons for the calculator
        buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }

        //Add the two panels to the calculator
        add(textPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        //Check which button the user pressed and perform that action. In all the events besides M+ and M- output
        //is reset to ensure you can only add or subtract to memory when an equation is executed
        //When the user inputs a button or decimal
        if(action.matches("[0-9]|\\.")) {
            output = "";
            //If this is the first operand pressed reset the display with the operand
            if(!operandPressed) {
                textField.setText(action);
                operandPressed = true;
            }
            //If the display is currently displaying Error reset the display with the operand
            else if(textField.getText().equals("Error")){
                textField.setText(action);
            }
            //If an operand is pressed after SQRT or ^ is pressed reset the display with Error
            else if(operator.equals("SQRT") || operator.equals("^")) {
                operandOne = "";
                textField.setText("Error");
                operator = "";
            }
            //If this is an operand pressed after the first add it to the display
            else {
                textField.setText(textField.getText() + action);
            }
        }
        //When the user wants to switch the sign of a number
        else if(action.equals("+/-")) {
            output = "";
            //If the display is currently displaying - reset the display
            if(textField.getText().equals("-")) {
                textField.setText("");
                operandPressed = true;
            }
            //If the display is currently empty reset the display with -
            else if(textField.getText().equals("")) {
                textField.setText("-");
                operandPressed = true;
            }
            //If this is the first button pressed reset the display with -
            else if(!operandPressed) {
                textField.setText("-");
                operandPressed = true;
            }
            //If the display is currently displaying Error reset the display with the -
            else if(textField.getText().equals("Error")){
                textField.setText("-");
            }
            //If this is pressed after SQRT or ^ is pressed reset the display with Error
            else if(operator.equals("SQRT") || operator.equals("^")) {
                textField.setText("Error");
            }
            //If this is pressed after an operand change its sign
            else {
                textField.setText(model.switchSign(textField.getText()));
            }
        }
        //When the user inputs a basic operation
        else if(action.matches("[+\\-*/]")) {
            output = "";
            operator = action;
            operandOne = textField.getText();
            operandPressed = false;
            //Add a space to the front of the display in case the user tries to execute the equation before putting
            //a second operand
            textField.setText(" " + textField.getText());
        }
        //When the user inputs an advanced operation
        else if(action.equals("SQRT") || action.equals("^")) {
            output = "";
            operator = action;
            operandOne = textField.getText();
        }
        //When the user wants to execute an operation
        else if(action.equals("=")) {
            String operandTwo = textField.getText();

            //Check which operator was selected
            switch (operator) {
                case "+":
                    textField.setText(model.add(operandOne, operandTwo));
                    break;
                case "-":
                    textField.setText(model.subtract(operandOne, operandTwo));
                    break;
                case "*":
                    textField.setText(model.multiply(operandOne, operandTwo));
                    break;
                case "/":
                    textField.setText(model.divide(operandOne, operandTwo));
                    break;
                case "SQRT":
                    textField.setText(model.sqrt(operandOne));
                    break;
                case "^":
                    textField.setText(model.square(operandOne));
                    break;
            }

            //Set output to the result of the operation. Needed for memory functions
            if(textField.getText().equals("NaN")) {
                textField.setText("Undefined");
            }
            output = textField.getText();

            operator = "";
            operandPressed = false;
        }
        //When the user wants to clear the display
        else if(action.equals("C")) {
            output = "";
            textField.setText(model.clear());
        }
        //When tje user wants to delete something off the display
        else if(action.equals("DEL")) {
            output = "";
            textField.setText(model.delete(textField.getText()));
        }
        //When the user wants to add to memory
        else if(action.equals("M+")) {
            textField.setText(model.memPlus(output));
        }
        //When the user wants to subtract from memory
        else if(action.equals("M-")) {
            textField.setText(model.memSubtract(output));
        }
        //When the user wants to display the memory
        else if(action.equals("M-REC")) {
            output = "";
            textField.setText(model.memRecall());
        }
        //When the user wants to clear the memory
        else if(action.equals("M-C")) {
            output = "";
            model.memClear();
        }
    }
}
