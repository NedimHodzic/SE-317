public class CheckingAccount {
    private String accountName;
    private int pin;
    private double balance;
    private double totalDepositAmount;
    private double totalWithdrawAmount;

    public CheckingAccount(String accountName, int pin, double balance) {
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
        if(depositAmount <= 0) {
            throw new Exception("You can only deposit an amount greater than 0.\n");
        }
        //Checking if the total amount deposited is already 5000
        else if(totalDepositAmount == 5000) {
            throw new Exception("You have already reached your $5000 deposit limit for your checking account today.\n");
        }
        //Checking if the total amount deposited plus the new amount to deposit will surpass 5000
        else if(depositAmount + totalDepositAmount > 5000) {
            throw new Exception("You are exceeding your $5000 deposit limit for your checking account, please deposit a smaller amount.\n");
        }
        else {
            balance += depositAmount;
            totalDepositAmount += depositAmount;
            System.out.println("You have successfully deposited $" + depositAmount +
                    " into your checking account! Your balance is now $" + balance + ".\n");
        }
    }

    //Withdraw method
    public void withdraw(double withdrawAmount) throws Exception {
        //Making sure you are withdrawing a valid amount
        if(withdrawAmount <= 0) {
            throw new Exception("You can only withdraw an amount greater than 0.\n");
        }
        //Checking if the withdraw amount exceeds bank balance
        else if(withdrawAmount > balance) {
            throw new Exception("You can't withdraw more than you have in your checking account!\n");
        }
        //Checking if the total amount withdrew is already 500
        else if(totalWithdrawAmount == 500) {
            throw new Exception("You have already reached your $500 withdraw limit for today for your checking account.\n");
        }
        //Checking if the total amount withdrew plus the new amount to withdraw will surpass 500
        else if(withdrawAmount + totalWithdrawAmount > 500) {
            throw new Exception("You are exceeding your $500 withdraw limit for your checking account, please withdraw a smaller amount.\n");
        }
        else {
            balance -= withdrawAmount;
            totalWithdrawAmount += withdrawAmount;
            System.out.println("You have successfully withdrew $" + withdrawAmount +
                    " from your checking account! Your balance is now $" + balance + ".\n");
        }
    }

    //Transfer method
    public void transfer(double amount, SavingsAccount savings) throws Exception {
        //Making sure you are transferring a valid amount
        if(amount <= 0) {
            throw new Exception("You can only transfer an amount greater than 0.\n");
        }
        //Checking if the transfer amount exceeds bank balance
        else if(amount > balance) {
            throw new Exception("You can't transfer more than you have in your checking account!\n");
        }
        else {
            balance -= amount;
            savings.receive(amount);
            System.out.println("You have successfully transferred $" + amount +
                    " from your checking account into your savings account! Your checking balance is now $" + balance + ".\n");
        }
    }

    //Pay bills method
    public void payBills(UtilityCompany utility) throws Exception{
        double amount = utility.getNextBillAmount();
        //Checking if the bill has been paid
        if(utility.isBillPaid()){
            throw new Exception("You have no pending bills!\n");
        }
        //Checking if the bill amount exceeds bank balance
        else if (amount > balance) {
            throw new Exception("You do not have enough money in your checking account to pay your bill.\n");
        }
        else {
            balance -= amount;
            utility.pay();
            System.out.println("You have paid a bill! Your checking account balance is now $" + balance + ".\n");
        }
    }

    //View balance method
    public double getBalance() {
        return balance;
    }

    //Method to get money transferred from savings
    public void receive(double amount) {
        balance += amount;
    }
}