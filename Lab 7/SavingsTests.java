import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SavingsTests {
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
        assertThrows(Exception.class, () -> savingsAccount.login("hodzic", 1234));
    }
    @Test
    public void invalidPin() {
        assertThrows(Exception.class, () -> savingsAccount.login("hodzic", 1234));
    }
    @Test
    public void successfulLogin() throws Exception {
        savingsAccount.login("nedim", 1234);
        assertEquals("Successfully logged in.\n", print.toString());
    }

    //Deposit Tests
    @Test
    public void negativeDeposit() {
        assertThrows(Exception.class, () -> savingsAccount.deposit(-761));
    }
    @Test
    public void oneDeposit() throws Exception {
        savingsAccount.deposit(481);
        assertEquals(1981, savingsAccount.getBalance(), 1);
    }
    @Test
    public void twoDeposits() throws Exception {
        savingsAccount.deposit(43);
        savingsAccount.deposit(400);
        assertEquals(1943, savingsAccount.getBalance(), 1);
    }
    @Test
    public void depositLimitMultiple() throws Exception{
        savingsAccount.deposit(3000);
        assertThrows(Exception.class, () -> savingsAccount.deposit(2050));
        assertEquals(4500, savingsAccount.getBalance(), 1);
    }
    @Test
    public void depositLimitSingle() {
        assertThrows(Exception.class, () -> savingsAccount.deposit(7861));
        assertEquals(1500, savingsAccount.getBalance(), 1);
    }

    //Transfer tests
    @Test
    public void negativeTransfer() {
        assertThrows(Exception.class, () -> savingsAccount.transfer(-4, checkingAccount));
    }
    @Test
    public void oneTransfer() throws Exception {
        savingsAccount.transfer(57, checkingAccount);
        assertEquals(1443, savingsAccount.getBalance(), 1);
    }
    @Test
    public void twoTransfers() throws Exception {
        savingsAccount.transfer(35, checkingAccount);
        savingsAccount.transfer(32, checkingAccount);
        assertEquals(1433, savingsAccount.getBalance(), 1);
    }
    @Test
    public void transferLimitMultiple() throws Exception {
        savingsAccount.transfer(87, checkingAccount);
        assertThrows(Exception.class, () -> savingsAccount.transfer(140, checkingAccount));
        assertEquals(1413, savingsAccount.getBalance(), 1);
    }
    @Test
    public void transferLimitSingle() {
        assertThrows(Exception.class, () -> savingsAccount.transfer(101, checkingAccount));
        assertEquals(1500, savingsAccount.getBalance(), 1);
    }
    @Test
    public void transferMoreThanOwn() {
        SavingsAccount poorSavingsAccount = new SavingsAccount("nedim", 1234, 50);
        assertThrows(Exception.class, () -> poorSavingsAccount.transfer(70, checkingAccount));
        assertEquals(50, poorSavingsAccount.getBalance(), 1);
    }

    //Balance tests
    @Test
    public void noTransactions() {
        assertEquals(1500, savingsAccount.getBalance(), 1);
    }
    @Test
    public void multipleTransactions() throws Exception {
        savingsAccount.deposit(70);
        savingsAccount.transfer(85, checkingAccount);
        savingsAccount.deposit(950);
        assertEquals(2435, savingsAccount.getBalance(), 1);
    }

    //Receive from checking tests
    @Test
    public void transferFromCheckingSingle() throws Exception {
        checkingAccount.transfer(900, savingsAccount);
        assertEquals(2400, savingsAccount.getBalance(), 1);
    }
    @Test
    public void transferFromCheckingMultiple() throws Exception {
        checkingAccount.transfer(900, savingsAccount);
        checkingAccount.transfer(400, savingsAccount);
        assertEquals(2800, savingsAccount.getBalance(), 1);
    }
}
