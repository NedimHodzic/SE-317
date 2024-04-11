import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Used to grab info from the file
        File bank = new File("src/bank.txt");
        File company = new File("src/utility.txt");
        Scanner fileScanner;
        ArrayList<String> bankInfo = new ArrayList<>();
        ArrayList<String> utilityInfo = new ArrayList<>();

        try {
            //Grabbing info from the files
            fileScanner = new Scanner(bank);
            while(fileScanner.hasNextLine()) {
                bankInfo.add(fileScanner.nextLine());
            }
            fileScanner = new Scanner(company);
            while(fileScanner.hasNextLine()) {
                utilityInfo.add(fileScanner.nextLine());
            }

            //Setting the various accounts with the user info
            CheckingAccount checking = new CheckingAccount(bankInfo.get(0), Integer.parseInt(bankInfo.get(1)),
                    Double.parseDouble(bankInfo.get(2)));
            SavingsAccount savings = new SavingsAccount(bankInfo.get(0), Integer.parseInt(bankInfo.get(1)),
                    Double.parseDouble(bankInfo.get(3)));

            String[] historySplit = utilityInfo.get(1).split(",");
            double[] historyArray = {Double.parseDouble(historySplit[0]), Double.parseDouble(historySplit[1]),
                    Double.parseDouble(historySplit[2])};

            UtilityCompany utility = new UtilityCompany(Integer.parseInt(utilityInfo.get(0)), historyArray,
                    Double.parseDouble(utilityInfo.get(2)), utilityInfo.get(3), Boolean.parseBoolean(utilityInfo.get(4)));

            System.out.println("Welcome to the ATM!");

            //Loop to continue processing user choices until they end the "day"
            while(true) {
                System.out.println("What would you like to access?");
                System.out.println("Press 1 for Checking Account, 2 for Savings Account, 3 for Utility Company, or 4 to end the day: ");
                int choice = scanner.nextInt();

                if(choice == 1) {
                    checkingChoice(checking, savings, utility);
                }
                else if(choice == 2) {
                    savingsChoice(savings, checking);
                }
                else if(choice == 3) {
                    utilityChoice(utility);
                }
                else if(choice == 4){
                    System.out.println("Goodbye!");
                    break;
                }
                else {
                    System.err.println("Invalid option.\n");
                }
            }

            //Checking if there is no pending bill and asks the user if they want to make one
            if(utility.isBillPaid()) {
                System.out.println("\nWould you like to add a new bill?");
                System.out.println("Press 1 for yes or 2 for no: ");
                int choice = scanner.nextInt();
                boolean valid = false;
                if(choice == 1) {
                    //Continues looping until the user creates a valid bill
                    while(!valid) {
                        try {
                            System.out.println("Enter the bill amount: ");
                            double amount = scanner.nextDouble();
                            System.out.println("Enter the date in MM/DD/YYYY format: ");
                            String dueDate = scanner.next();
                            valid = utility.newBill(amount, dueDate);
                        } catch(Exception e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }
            }

            try {
                //Updating each file in case anything changed like a balance
                FileWriter writer = new FileWriter("src/bank.txt");
                writer.write(bankInfo.get(0) + "\n");
                writer.append(bankInfo.get(1)).append("\n");
                writer.append(Double.toString(checking.getBalance())).append("\n");
                writer.append(Double.toString(savings.getBalance()));
                writer.close();
                writer = new FileWriter("src/utility.txt");
                writer.write(utilityInfo.get(0) + "\n");
                writer.append(Double.toString(utility.getPaidBills()[0])).append(",");
                writer.append(Double.toString(utility.getPaidBills()[1])).append(",");
                writer.append(Double.toString(utility.getPaidBills()[2])).append("\n");
                writer.append(Double.toString(utility.getNextBillAmount())).append("\n");
                writer.append(utility.getNextBillDueDate()).append("\n");
                writer.append(Boolean.toString(utility.isBillPaid()));
                writer.close();

            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage().split(" ")[0] + ".");
            } catch (IOException e) {
                System.err.println("Error occurred.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage().split(" ")[0] + ".");
        }
    }

    //Method to handle when the user accesses their checking account
    public static void checkingChoice(CheckingAccount checking, SavingsAccount savings, UtilityCompany utility) {
        Scanner checkingScanner = new Scanner(System.in);

        System.out.println("Please enter your account name: ");
        String accountName = checkingScanner.nextLine();
        System.out.println("Please enter your account pin: ");
        int pin = checkingScanner.nextInt();

        try {
            checking.login(accountName, pin);
            System.out.println("\nYou have accessed your Checking Account.");
            //Loop to continue processing user choices until they decide to exit their checking account
            while(true) {
                System.out.println("What would you like to do?");
                System.out.println("Press 1 to deposit, 2 to withdraw, 3 to transfer to Savings,\n" +
                        "4 to pay Utility bills, 5 to view your checking current balance, or 6 to exit: ");
                int checkingChoice = checkingScanner.nextInt();

                if(checkingChoice == 1) {
                    try {
                        System.out.println("How much would you like to deposit?");
                        double depositAmount = checkingScanner.nextDouble();
                        checking.deposit(depositAmount);
                    } catch(Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                else if(checkingChoice == 2) {
                    try {
                        System.out.println("How much would you like to withdraw?");
                        double withdrawAmount = checkingScanner.nextDouble();
                        checking.withdraw(withdrawAmount);
                    } catch(Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                else if(checkingChoice == 3) {
                    try {
                        System.out.println("How much would you like to transfer?");
                        double amount = checkingScanner.nextDouble();
                        checking.transfer(amount, savings);
                    } catch(Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                else if(checkingChoice == 4) {
                    try {
                        checking.payBills(utility);
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if(checkingChoice == 5) {
                    System.out.println("Current balance: $" + checking.getBalance() + "\n");
                }
                else if(checkingChoice == 6) {
                    System.out.println();
                    break;
                }
                else {
                    System.out.println("Invalid option.\n");
                }
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //Method to handle when the user accesses their savings account
    public static void savingsChoice(SavingsAccount savings, CheckingAccount checking) {
        Scanner savingsScanner = new Scanner(System.in);

        System.out.println("Please enter your account name: ");
        String accountName = savingsScanner.nextLine();
        System.out.println("Please enter your account pin: ");
        int pin = savingsScanner.nextInt();

        try {
            savings.login(accountName, pin);
            System.out.println("\nYou have accessed your Savings Account.");
            //Loop to continue processing user choices until they decide to exit their savings account
            while(true) {
                System.out.println("What would you like to do?");
                System.out.println("Press 1 to deposit, 2 to transfer to Checking,\n" +
                        "3 to view your savings current balance, or 4 to exit: ");
                int savingsChoice = savingsScanner.nextInt();

                if(savingsChoice == 1) {
                    try {
                        System.out.println("How much would you like to deposit?");
                        double depositAmount = savingsScanner.nextDouble();
                        savings.deposit(depositAmount);
                    } catch(Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                else if(savingsChoice == 2) {
                    try {
                        System.out.println("How much would you like to transfer?");
                        double amount = savingsScanner.nextDouble();
                        savings.transfer(amount, checking);
                    } catch(Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                else if(savingsChoice == 3) {
                    System.out.println("Current balance: $" + savings.getBalance() + "\n");
                }
                else if(savingsChoice == 4) {
                    System.out.println();
                    break;
                }
                else {
                    System.out.println("Invalid option.\n");
                }
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //Method to handle when the user accesses their utility company
    public static void utilityChoice(UtilityCompany utility) {
        Scanner utilityScanner = new Scanner(System.in);

        System.out.println("Please enter your account number: ");
        int accountNumber = utilityScanner.nextInt();

        try {
            utility.login(accountNumber);
            System.out.println("\nYou have accessed your Utility Company.");
            //Loop to continue processing user choices until they decide to exit their utility company
            while(true) {
                System.out.println("What would you like to do?");
                System.out.println("Press 1 to view payment history,\n" +
                        "2 to view next bill payment, or 3 to exit: ");
                int utilityChoice = utilityScanner.nextInt();

                if(utilityChoice == 1) {
                    utility.viewHistory();
                }
                else if(utilityChoice == 2) {
                    utility.viewNextBill();
                }
                else if(utilityChoice == 3) {
                    System.out.println();
                    break;
                }
                else {
                    System.out.println("Invalid option.\n");
                }
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
