import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTests {
    CalculatorModel calc;
    @Before
    public void initialization() {
        calc = new CalculatorModel();
    }

    //Addition tests
    @Test
    public void normalAdd() {
        assertEquals("7.0", calc.add("3","4"));
    }
    @Test
    public void invalidAdd() {
        assertEquals("Error", calc.add("1.1.1", "0"));
    }
    @Test
    public void noSecondOperatorAdd() {
        //The space in front of the 8 signifies that the user did not enter a second operand
        assertEquals("Error", calc.add("8", " 8"));
    }

    //Subtraction tests
    @Test
    public void normalSubtract() {
        assertEquals("1.0", calc.subtract("20","19"));
    }
    @Test
    public void invalidSubtract() {
        assertEquals("Error", calc.subtract(".3.4", "1098"));
    }
    @Test
    public void noSecondOperatorSubtract() {
        //The space in front of the 77 signifies that the user did not enter a second operand
        assertEquals("Error", calc.subtract("77", " 77"));
    }

    //Multiplication tests
    @Test
    public void normalMultiply() {
        assertEquals("200.0", calc.multiply("100","2"));
    }
    @Test
    public void invalidMultiply() {
        assertEquals("Error", calc.multiply("-1..1", "67"));
    }
    @Test
    public void noSecondOperatorMultiply() {
        //The space in front of the 40 signifies that the user did not enter a second operand
        assertEquals("Error", calc.multiply("40", " 40"));
    }

    //Division tests
    @Test
    public void normalDivide() {
        assertEquals("5.0", calc.divide("55","11"));
    }
    @Test
    public void invalidDivide() {
        assertEquals("Error", calc.divide("xyz", "18"));
    }
    @Test
    public void noSecondOperatorDivide() {
        //The space in front of the 39 signifies that the user did not enter a second operand
        assertEquals("Error", calc.divide("39", " 39"));
    }
    @Test
    public void divideByZero() {
        assertEquals("Error", calc.divide("13", "0"));
    }

    //Square tests
    @Test
    public void normalSquare() {
        assertEquals("81.0", calc.square("9"));
    }
    @Test
    public void invalidSquare() {
        assertEquals("Error", calc.square("Error"));
    }
    @Test
    public void negativeSquare() {
        assertEquals("9.0", calc.square("-3"));
    }

    //Square root tests
    @Test
    public void normalSQRT() {
        assertEquals("4.0", calc.sqrt("16"));
    }
    @Test
    public void invalidSQRT() {
        assertEquals("Error", calc.sqrt("1.1.1.1.1."));
    }
    @Test
    public void negativeSQRT() {
        assertEquals("NaN", calc.sqrt("-49"));
    }

    //Mem+ tests
    @Test
    public void normalMPlus() {
        String output = calc.add("47", "3");
        assertEquals("50.0", calc.memPlus(output));
        assertEquals(50, calc.memory, 1);
    }
    @Test
    public void invalidMPlus() {
        String output = calc.add("1.1.5", "509123");
        assertEquals("Error", calc.memPlus(output));
        assertEquals(0, calc.memory, 1);
    }
    @Test
    public void noEquationPerformedMPlus() {
        assertEquals("Error", calc.memPlus(""));
        assertEquals(0, calc.memory, 1);
    }
    @Test
    public void clearThenMPlus() {
        String output = calc.add("100", "521");
        calc.memPlus(output);
        calc.memClear();
        String output2 = calc.add("33", "333");
        assertEquals("366.0", calc.memPlus(output2));
        assertEquals(366, calc.memory, 1);
    }

    //Mem- tests
    @Test
    public void normalMSubtract() {
        String output = calc.subtract("340", "40");
        assertEquals("-300.0", calc.memSubtract(output));
        assertEquals(-300, calc.memory, 1);
    }
    @Test
    public void invalidMSubtract() {
        String output = calc.subtract("..1..1", "41435");
        assertEquals("Error", calc.memSubtract(output));
        assertEquals(0, calc.memory, 1);
    }
    @Test
    public void noEquationPerformedMSubtract() {
        assertEquals("Error", calc.memSubtract(""));
        assertEquals(0, calc.memory, 1);
    }
    @Test
    public void clearThenMSubtract() {
        String output = calc.subtract("400000", "5");
        calc.memPlus(output);
        calc.memClear();
        String output2 = calc.subtract("10", "3");
        assertEquals("-7.0", calc.memSubtract(output2));
        assertEquals(-7, calc.memory, 1);
    }

    //MemRecall tests
    @Test
    public void normalRecall() {
        String output = calc.add("3", "7");
        calc.memPlus(output);
        assertEquals("10.0", calc.memRecall());
    }
    @Test
    public void invalidMRecall() {
        String output = calc.add("4..4.", "44");
        calc.memPlus(output);
        assertEquals("0.0", calc.memRecall());
    }
    @Test
    public void noEquationPerformedMRecall() {
        calc.memPlus("");
        assertEquals("0.0", calc.memRecall());
    }

    //MemClear tests
    @Test
    public void clearNothing() {
        calc.memClear();
        assertEquals(0, calc.memory, 1);
    }
    @Test
    public void addThenClear() {
        String output = calc.add("-1", "5");
        calc.memPlus(output);
        calc.memClear();
        assertEquals(0, calc.memory, 1);
    }
    @Test
    public void subtractThenClear() {
        String output = calc.add("412414", "23129181");
        calc.memPlus(output);
        calc.memClear();
        assertEquals(0, calc.memory, 1);
    }

    //Delete tests
    @Test
    public void oneDelete() {
        String output = calc.delete("1234.5");
        assertEquals("1234.", output);
    }
    @Test
    public void multipleDeletes() {
        String output = calc.delete("1234.5");
        output = calc.delete(output);
        output = calc.delete(output);
        output = calc.delete(output);
        assertEquals("12", output);
    }
    @Test
    public void errorDelete() {
        String output = calc.delete("Error");
        assertEquals("", output);
    }
    @Test
    public void undefinedDelete() {
        String output = calc.delete("Undefined");
        assertEquals("", output);
    }

    //Clear tests
    @Test
    public void emptyClear() {
        String output = calc.clear();
        assertEquals("", output);
        assertEquals(0.0, calc.memory, 1);
    }
    @Test
    public void filledClear() {
        String output = calc.add("45", "9");
        calc.memPlus(output);
        String output2 = calc.clear();
        assertEquals("", output2);
        assertEquals(0.0, calc.memory, 1);
    }

    //Switch sign tests
    @Test
    public void normalSignSwitch() {
        assertEquals("-77.0", calc.switchSign("77"));
    }
    @Test
    public void invalidSignSwitch() {
        assertEquals("Error", calc.switchSign(":):(:|"));
    }
}
