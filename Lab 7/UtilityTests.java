import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class UtilityTests {
    ByteArrayOutputStream print = new ByteArrayOutputStream();
    CheckingAccount checkingAccount;
    SavingsAccount savingsAccount;
    UtilityCompany utilityCompany;

    @Before
    public void initialization() {
        checkingAccount = new CheckingAccount("nedim", 1234, 5000);
        savingsAccount = new SavingsAccount("nedim", 1234, 1500);
        utilityCompany = new UtilityCompany(1234, new double[]{400, 450, 500},
                650, "04/15/2023", false);
        System.setOut(new PrintStream(print));
    }

    //Login tests
    @Test
    public void invalidAccountNumber() {
        assertThrows(Exception.class, () -> utilityCompany.login(1000));
    }
    @Test
    public void successfulLogin() throws Exception {
        utilityCompany.login(1234);
        assertEquals("Successfully logged in.\n", print.toString());
    }

    //View history tests
    @Test
    public void viewHistory() {
        utilityCompany.viewHistory();
        assertEquals("Bill History:\nLast bill paid: $400.0" +
                "\nSecond to last bill paid: $450.0" +
                "\nThird to last bill paid: $500.0\n\n", print.toString());
    }
    @Test
    public void viewHistoryAfterOnePayment() throws Exception {
        checkingAccount.payBills(utilityCompany);
        print.reset();
        utilityCompany.viewHistory();
        assertEquals("Bill History:\nLast bill paid: $650.0" +
                "\nSecond to last bill paid: $400.0" +
                "\nThird to last bill paid: $450.0\n\n", print.toString());
    }
    @Test
    public void viewHistoryAfterTwoPayments() throws Exception {
        checkingAccount.payBills(utilityCompany);
        utilityCompany.newBill(700, "04/19/2023");
        checkingAccount.payBills(utilityCompany);
        print.reset();
        utilityCompany.viewHistory();
        assertEquals("Bill History:\nLast bill paid: $700.0" +
                "\nSecond to last bill paid: $650.0" +
                "\nThird to last bill paid: $400.0\n\n", print.toString());
    }
    @Test
    public void viewHistoryAfterThreePayments() throws Exception {
        checkingAccount.payBills(utilityCompany);
        utilityCompany.newBill(700, "04/19/2023");
        checkingAccount.payBills(utilityCompany);
        utilityCompany.newBill(230, "04/17/2023");
        checkingAccount.payBills(utilityCompany);
        print.reset();
        utilityCompany.viewHistory();
        assertEquals("Bill History:\nLast bill paid: $230.0" +
                "\nSecond to last bill paid: $700.0" +
                "\nThird to last bill paid: $650.0\n\n", print.toString());
    }
    @Test
    public void viewHistoryAfterFourPayments() throws Exception {
        checkingAccount.payBills(utilityCompany);
        utilityCompany.newBill(700, "04/19/2023");
        checkingAccount.payBills(utilityCompany);
        utilityCompany.newBill(230, "04/17/2023");
        checkingAccount.payBills(utilityCompany);
        utilityCompany.newBill(480, "04/17/2023");
        checkingAccount.payBills(utilityCompany);
        print.reset();
        utilityCompany.viewHistory();
        assertEquals("Bill History:\nLast bill paid: $480.0" +
                "\nSecond to last bill paid: $230.0" +
                "\nThird to last bill paid: $700.0\n\n", print.toString());
    }

    //View next bill tests
    @Test
    public void noPendingBill() throws Exception {
        checkingAccount.payBills(utilityCompany);
        print.reset();
        utilityCompany.viewNextBill();
        assertEquals("You have no pending bills!\n\n", print.toString());
    }
    @Test
    public void pendingBill() {
        utilityCompany.viewNextBill();
        assertEquals("You have a bill due on 04/15/2023 worth $650.0.\n\n", print.toString());
    }
    @Test
    public void multiplePendingBills() throws Exception {
        utilityCompany.viewNextBill();
        assertEquals("You have a bill due on 04/15/2023 worth $650.0.\n\n", print.toString());
        checkingAccount.payBills(utilityCompany);
        utilityCompany.newBill(275, "04/28/2023");
        print.reset();
        utilityCompany.viewNextBill();
        assertEquals("You have a bill due on 04/28/2023 worth $275.0.\n\n", print.toString());
    }

    //Create bill tests
    @Test
    public void invalidAmount() throws Exception {
        checkingAccount.payBills(utilityCompany);
        assertThrows(Exception.class, () -> utilityCompany.newBill(-194, "04/27/2023"));
        assertEquals(650, utilityCompany.getNextBillAmount(), 1);
        assertEquals("04/15/2023", utilityCompany.getNextBillDueDate());
        assertTrue(utilityCompany.isBillPaid());
    }
    @Test
    public void invalidDate() throws Exception {
        checkingAccount.payBills(utilityCompany);
        assertThrows(Exception.class, () -> utilityCompany.newBill(780, "04/27/20"));
        assertEquals(650, utilityCompany.getNextBillAmount(), 1);
        assertEquals("04/15/2023", utilityCompany.getNextBillDueDate());
        assertTrue(utilityCompany.isBillPaid());
    }
    @Test
    public void newBill() throws Exception {
        checkingAccount.payBills(utilityCompany);
        utilityCompany.newBill(780, "04/27/2023");
        assertEquals(780, utilityCompany.getNextBillAmount(), 1);
        assertEquals("04/27/2023", utilityCompany.getNextBillDueDate());
        assertFalse(utilityCompany.isBillPaid());
    }
}
