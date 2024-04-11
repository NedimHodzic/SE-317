public class UtilityCompany {
    private int accountNumber;
    private double[] paidBills;
    private double nextBillAmount;
    private String nextBillDueDate;
    private boolean billPaid;

    public UtilityCompany(int accountNumber, double[] paidBills, double nextBillAmount, String nextBillDueDate, boolean billPaid) {
        this.accountNumber = accountNumber;
        this.paidBills = paidBills;
        this.nextBillAmount = nextBillAmount;
        this.nextBillDueDate = nextBillDueDate;
        this.billPaid = billPaid;
    }

    //Login method
    public void login(int accountNumber) throws Exception {
        if(accountNumber != this.accountNumber) {
            throw new Exception("Incorrect account number.");
        }
        else {
            System.out.println("Successfully logged in.");
        }
    }

    //View payment history method
    public void viewHistory() {
        System.out.println("Bill History:");
        System.out.println("Last bill paid: $" + paidBills[0] +
                "\nSecond to last bill paid: $" + paidBills[1] +
                "\nThird to last bill paid: $" + paidBills[2] + "\n");
    }

    //View new bill method
    public void viewNextBill() {
        if (billPaid) {
            System.out.println("You have no pending bills!\n");
        } else {
            System.out.println("You have a bill due on " + nextBillDueDate + " worth $" + nextBillAmount + ".\n");
        }
    }

    //Pay bill method
    public void pay() {
        billPaid = true;
        paidBills[2] = paidBills[1];
        paidBills[1] = paidBills[0];
        paidBills[0] = nextBillAmount;
    }

    //Get next bill amount method
    public double getNextBillAmount() {
        return nextBillAmount;
    }
    //Get next bill date method
    public String getNextBillDueDate() {
        return nextBillDueDate;
    }

    //Get payment history method
    public double[] getPaidBills() {
        return paidBills;
    }

    //Get next bill payment status
    public boolean isBillPaid() {
        return billPaid;
    }

    //Method to add a new bill (Made more testing)
    public boolean newBill(double amount, String dueDate) throws Exception {
        if (amount <= 0) {
            throw new Exception("Bill amount must be greater than 0");
        } else if (!dueDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new Exception("Date must be in the format MM/DD/YYYY");
        } else {
            nextBillAmount = amount;
            nextBillDueDate = dueDate;
            billPaid = false;
            System.out.println("New bill has been added!");
            return true;
        }
    }
}
