import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class CheckingTests {
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
    public void invalidAccountName() {
        assertThrows(Exception.class, () -> checkingAccount.login("hodzic", 1234));
    }
    @Test
    public void invalidPin() {
        assertThrows(Exception.class, () -> checkingAccount.login("hodzic", 1234));
    }
    @Test
    public void successfulLogin() throws Exception {
        checkingAccount.login("nedim", 1234);
        assertEquals("Successfully logged in.\n", print.toString());
    }

    //Deposit Tests
    @Test
    public void negativeDeposit() {
        assertThrows(Exception.class, () -> checkingAccount.deposit(-300));
    }
    @Test
    public void oneDeposit() throws Exception {
        checkingAccount.deposit(750);
        assertEquals(5750, checkingAccount.getBalance(), 1);
    }
    @Test
    public void twoDeposits() throws Exception {
        checkingAccount.deposit(750);
        checkingAccount.deposit(800);
        assertEquals(6550, checkingAccount.getBalance(), 1);
    }
    @Test
    public void depositLimitMultiple() throws Exception{
        checkingAccount.deposit(4950);
        assertThrows(Exception.class, () -> checkingAccount.deposit(51));
        assertEquals(9950, checkingAccount.getBalance(), 1);
    }
    @Test
    public void depositLimitSingle() {
        assertThrows(Exception.class, () -> checkingAccount.deposit(5001));
        assertEquals(5000, checkingAccount.getBalance(), 1);
    }

    //Withdraw tests
    @Test
    public void negativeWithdraw() {
        assertThrows(Exception.class, () -> checkingAccount.withdraw(-57));
    }
    @Test
    public void oneWithdraw() throws Exception {
        checkingAccount.withdraw(250);
        assertEquals(4750, checkingAccount.getBalance(), 1);
    }
    @Test
    public void twoWithdraws() throws Exception {
        checkingAccount.withdraw(150);
        checkingAccount.withdraw(20);
        assertEquals(4830, checkingAccount.getBalance(), 1);
    }
    @Test
    public void withdrawLimitMultiple() throws Exception{
        checkingAccount.withdraw(450);
        assertThrows(Exception.class, () -> checkingAccount.withdraw(51));
        assertEquals(4550, checkingAccount.getBalance(), 1);
    }
    @Test
    public void withdrawLimitSingle() {
        assertThrows(Exception.class, () -> checkingAccount.withdraw(501));
        assertEquals(5000, checkingAccount.getBalance(), 1);
    }
    @Test
    public void withdrawMoreThanOwn() throws Exception {
        checkingAccount.transfer(4800, savingsAccount);
        assertThrows(Exception.class, () -> checkingAccount.withdraw(300));
        assertEquals(200, checkingAccount.getBalance(), 1);
    }

    //Transfer tests
    @Test
    public void negativeTransfer() {
        assertThrows(Exception.class, () -> checkingAccount.transfer(-109, savingsAccount));
    }
    @Test
    public void oneTransfer() throws Exception {
        checkingAccount.transfer(1000, savingsAccount);
        assertEquals(4000, checkingAccount.getBalance(), 1);
    }
    @Test
    public void twoTransfers() throws Exception {
        checkingAccount.transfer(60, savingsAccount);
        checkingAccount.transfer(165, savingsAccount);
        assertEquals(4775, checkingAccount.getBalance(), 1);
    }
    @Test
    public void transferMoreThanOwnMultiple() throws Exception {
        checkingAccount.transfer(4980, savingsAccount);
        assertThrows(Exception.class, () -> checkingAccount.transfer(35, savingsAccount));
        assertEquals(20, checkingAccount.getBalance(), 1);
    }
    @Test
    public void transferMoreThanOwnSingle() throws Exception {
        assertThrows(Exception.class, () -> checkingAccount.transfer(5096, savingsAccount));
        assertEquals(5000, checkingAccount.getBalance(), 1);
    }

    //Pay bills tests
    @Test
    public void noPendingBills() throws Exception {
        checkingAccount.payBills(utilityCompany);
        assertThrows(Exception.class, () -> checkingAccount.payBills(utilityCompany));
        assertEquals(4350, checkingAccount.getBalance(), 1);
    }
    @Test
    public void notEnoughMoney() throws Exception {
        checkingAccount.transfer(4950, savingsAccount);
        assertThrows(Exception.class, () -> checkingAccount.payBills(utilityCompany));
        assertEquals(50, checkingAccount.getBalance(), 1);
    }
    @Test
    public void successfulPayment() throws Exception {
        checkingAccount.payBills(utilityCompany);
        assertEquals(4350, checkingAccount.getBalance(), 1);
    }

    //Balance tests
    @Test
    public void noTransactions() {
        assertEquals(5000, checkingAccount.getBalance(), 1);
    }
    @Test
    public void multipleTransactions() throws Exception {
        checkingAccount.deposit(650);
        checkingAccount.withdraw(450);
        checkingAccount.transfer(400, savingsAccount);
        checkingAccount.deposit(300);
        assertEquals(5100, checkingAccount.getBalance(), 1);
    }

    //Receive from savings test
    @Test
    public void transferFromSavingsSingle() throws Exception {
        savingsAccount.transfer(75, checkingAccount);
        assertEquals(5075, checkingAccount.getBalance(), 1);
    }
    @Test
    public void transferFromSavingsMultiple() throws Exception {
        savingsAccount.transfer(75, checkingAccount);
        savingsAccount.transfer(15, checkingAccount);
        assertEquals(5090, checkingAccount.getBalance(), 1);
    }
}
