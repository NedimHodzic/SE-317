public class CalculatorModel {
    public double memory;

    //All exception handling is done with the try catches

    public CalculatorModel() {
        memory = 0;
    }

    //Basic operations
    //If x and y are both valid numbers add them, display Error otherwise
    public String add(String x, String y) {
        try {
            //Checking if a second operand was inputted
            if(y.charAt(0) == ' ') {
                throw new Exception();
            }
            return Double.toString(Double.parseDouble(x) + Double.parseDouble(y));
        } catch(Exception e) {
            System.err.println("Invalid input");
            return "Error";
        }
    }
    //If x and y are both valid numbers subtract them, display Error otherwise
    public String subtract(String x, String y) {
        try {
            //Checking if a second operand was inputted
            if(y.charAt(0) == ' ') {
                throw new Exception();
            }
            return Double.toString(Double.parseDouble(x) - Double.parseDouble(y));
        } catch(Exception e) {
            System.err.println("Invalid input");
            return "Error";
        }
    }
    //If x and y are both valid numbers multiply them, display Error otherwise
    public String multiply(String x, String y) {
        try {
            //Checking if a second operand was inputted
            if(y.charAt(0) == ' ') {
                throw new Exception();
            }
            return Double.toString(Double.parseDouble(x) * Double.parseDouble(y));
        } catch(Exception e) {
            System.err.println("Invalid input");
            return "Error";
        }
    }
    //If x and y are both valid numbers divide them, display Error otherwise
    public String divide(String x, String y) {
        try {
            //Checking if a second operand was inputted or divide by 0
            if(y.charAt(0) == ' ' || Double.parseDouble(y) == 0) {
                throw new Exception();
            }
            return Double.toString(Double.parseDouble(x) / Double.parseDouble(y));
        } catch(Exception e) {
            System.err.println("Invalid input");
            return "Error";
        }
    }

    //Advanced operations
    //If x is a valid number square it, display Error otherwise
    public String square(String x) {
        try {
            return Double.toString(Double.parseDouble(x) * Double.parseDouble(x));
        } catch(Exception e) {
            System.err.println("Invalid input");
            return "Error";
        }
    }
    //If x is a valid number square root it, display Error otherwise
    public String sqrt(String x) {
        try {
            return Double.toString(Math.sqrt(Double.parseDouble(x)));
        } catch(Exception e) {
            System.err.println("Invalid input");
            return "Error";
        }
    }

    //Memory operations
    //If output is a valid number and the result of an equation add it to memory, display Error otherwise
    public String memPlus(String output) {
        try {
            //Checking if the output is a real number
            if(output.equals("NaN")) {
                throw new Exception();
            }
            memory += Double.parseDouble(output);
            return Double.toString(memory);
        } catch(Exception e) {
            System.err.println("Invalid input");
            return "Error";
        }
    }
    //If output is a valid number and the result of an equation subtract it from memory, display Error otherwise
    public String memSubtract(String output) {
        try {
            //Checking if the output is a real number
            if(output.equals("Undefined")) {
                throw new Exception();
            }
            memory -= Double.parseDouble(output);
            return Double.toString(memory);
        } catch(Exception e) {
            System.err.println("Invalid input");
            return "Error";
        }
    }
    public String memRecall() {
        return Double.toString(memory);
    }
    public void memClear() {
        memory = 0;
    }

    //Delete and clear
    public String delete(String screen) {
        if(screen.equals("Error") || screen.equals("Undefined")) {
            return "";
        }
        else if(screen.length() > 0) {
            return screen.substring(0, screen.length() - 1);
        }
        return screen;
    }
    public String clear() {
        memory = 0;
        return "";
    }

    //Switch sign of the operand
    //If x is a valid number negate it, display Error otherwise
    public String switchSign(String x) {
        try {
            return Double.toString(Double.parseDouble(x) * -1);
        } catch(Exception e) {
            System.err.println("Invalid input");
            return "Error";
        }
    }
}
