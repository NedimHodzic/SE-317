public class SavingsAccount {
    private String accountName;
    private int pin;
    private double balance;
    private double totalDepositAmount;
    private double totalTransferAmount;

    public SavingsAccount(String accountName, int pin, double balance) {
        this.accountName = accountName;
        this.pin = pin;
        this.balance = balance;
    }

    //Login method
    public void login(String accountName, int pin) throws Exception {
        if(!accountName.equals(this.accountName) || pin != this.pin) {
            throw new Exception("Login failed. Invalid account name or pin number.");
        }
        else {
            System.out.println("Successfully logged in.");
        }
    }

    //Deposit method
    public void deposit(double depositAmount) throws Exception {
        //Making sure you are depositing a valid amount
        if (depositAmount <= 0) {
            throw new Exception("You can only deposit an amount greater than 0.\n");
        }
        //Checking if the total amount deposited is already 5000
        else if (totalDepositAmount == 5000) {
            throw new Exception("You have already reached your $5000 deposit limit for today for your savings account.\n");
        }
        //Checking if the total amount deposited plus the new amount to deposit will surpass 5000
        else if (depositAmount + totalDepositAmount > 5000) {
            throw new Exception("You are exceeding your $5000 deposit limit for your savings account, please deposit a smaller amount.\n");
        }
        else {
            balance += depositAmount;
            totalDepositAmount += depositAmount;
            System.out.println("You have successfully deposited $" + depositAmount +
                    " into your savings account! Your balance is now $" + balance + ".\n");
        }
    }

    //Transfer method
    public void transfer(double amount, CheckingAccount checking) throws Exception {
        //Making sure you are transferring a valid amount
        if (amount <= 0) {
            throw new Exception("You can only transfer an amount greater than 0.\n");
        }
        //Checking if the transfer amount exceeds bank balance
        else if (amount > balance) {
            throw new Exception("You can't transfer more than you have in your savings account!\n");
        }
        //Checking if the total amount transferred plus the new amount to transfer will surpass 100
        else if (amount + totalTransferAmount > 100) {
            throw new Exception("You are exceeding your $100 transfer limit for your savings account, please transfer a smaller amount.\n");
        }
        else {
            balance -= amount;
            totalTransferAmount += amount;
            checking.receive(amount);
            System.out.println("You have successfully transferred $" + amount +
                    " from your savings account into your checking account! Your savings balance is now $" + balance + ".\n");
        }
    }

    //Get balance method
    public double getBalance() {
        return balance;
    }

    //Method to get money transferred from checking
    public void receive(double amount) {
        balance += amount;
    }
}
