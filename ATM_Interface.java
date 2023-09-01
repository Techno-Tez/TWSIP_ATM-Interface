import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ATM_Interface {
    private Map<Integer, BankAccount> accounts;
    private BankAccount currentUserAccount;

    public ATM_Interface() {
        accounts = new HashMap<>();
    }

    public void createAccount(int accountNumber, int pin, int balance) {
        accounts.put(accountNumber, new BankAccount(pin, balance));
    }

    public boolean authenticate(int accountNumber, int pin) {
        BankAccount account = accounts.get(accountNumber);
        return account != null && account.checkPin(pin);
    }

    public void displayWelcomeMessage() {
        System.out.println("Welcome to OurBank ATM");
    }

    public void run() {
        displayWelcomeMessage();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your account number: ");
        int accountNumber = scanner.nextInt();

        System.out.println("Enter your PIN: ");
        int pin = scanner.nextInt();

        if (authenticate(accountNumber, pin)) {
            currentUserAccount = accounts.get(accountNumber);
            displayMenu(scanner);
        } else {
            System.out.println("\n==========================================");
            System.out.println("Authentication failed.");
            System.out.println("==========================================\n");
        }
    }

    private void displayMenu(Scanner scanner) {

        System.out.println("\n==========================================");
        System.out.println("Login Successfull");
        System.out.println("==========================================\n");

        while (true) {
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit\n");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUserAccount.withdrawFunds(scanner);
                    break;
                case 2:
                    currentUserAccount.depositFunds(scanner);
                    break;
                case 3:
                    System.out.println("\n==========================================");
                    System.out.println("Account balance: " + currentUserAccount.getBalance());
                    System.out.println("==========================================\n");
                    break;
                case 4:
                    System.out.println("Thank you for using our ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public static void main(String[] args) {
        ATM_Interface atm = new ATM_Interface();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Create Bank Account");

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        System.out.print("Enter PIN: ");
        int pin = scanner.nextInt();

        System.out.print("Enter initial balance: ");
        int balance = scanner.nextInt();

        atm.createAccount(accountNumber, pin, balance);
        System.out.println("");
        System.out.println("==========================================");
        System.out.println("Account set up successfully.");
        System.out.println("==========================================");
        System.out.println("");
        atm.run();
    }
}

class BankAccount {
    private int pin;
    private int balance;

    public BankAccount(int pin, int balance) {
        this.pin = pin;
        this.balance = balance;
    }

    public boolean checkPin(int enteredPin) {
        return pin == enteredPin;
    }

    public void withdrawFunds(Scanner scanner) {
        System.out.println("Enter withdrawal amount: ");
        int amount = scanner.nextInt();

        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("\n==========================================");
            System.out.println("Withdrawal successful. New balance: " + balance);
            System.out.println("==========================================\n");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void depositFunds(Scanner scanner) {
        System.out.print("Enter deposit amount: ");
        int amount = scanner.nextInt();

        if (amount > 0) {
            balance += amount;
            System.out.println("\n==========================================");
            System.out.println("Deposit successful. New balance: " + balance);
            System.out.println("==========================================\n");
        }
    }

    public int getBalance() {
        return balance;
    }
}
