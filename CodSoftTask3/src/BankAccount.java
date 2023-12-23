import java.util.HashMap;
import java.util.Map;

class AccountHolder {
    private double balance;

    public AccountHolder(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public String withdraw(double amount) {
        if (amount > balance) {
            return "Insufficient funds. Withdrawal failed.";
        } else {
            balance -= amount;
            return "Withdrawal successful. New balance: " + balance;
        }
    }
    
    
}

class AccountHolderDatabase {
    private Map<Integer, AccountHolder> AccountHolders;

    public AccountHolderDatabase() {
        this.AccountHolders = new HashMap<>();
    }

    public void addAccountHolder(int accountNumber, double initialBalance) {
        AccountHolder AccountHolder = new AccountHolder(initialBalance);
        AccountHolders.put(accountNumber, AccountHolder);
    }

    public AccountHolder getAccountHolder(int accountNumber) {
        return AccountHolders.get(accountNumber);
    }
}

class ATM {
    private AccountHolderDatabase AccountHolderDatabase;

    public ATM(AccountHolderDatabase AccountHolderDatabase) {
        this.AccountHolderDatabase = AccountHolderDatabase;
    }

    public String deposit(int accountNumber, double amount) {
        AccountHolder AccountHolder = AccountHolderDatabase.getAccountHolder(accountNumber);
        if (AccountHolder != null) {
            AccountHolder.deposit(amount);
            return "Deposit successful. New balance: " + AccountHolder.getBalance();
        } else {
            return "AccountHolder not found.";
        }
    }

    public String withdraw(int accountNumber, double amount) {
        AccountHolder AccountHolder = AccountHolderDatabase.getAccountHolder(accountNumber);
        if (AccountHolder != null) {
            return AccountHolder.withdraw(amount);
        } else {
            return "AccountHolder not found.";
        }
    }

    public String checkBalance(int accountNumber) {
        AccountHolder AccountHolder = AccountHolderDatabase.getAccountHolder(accountNumber);
        if (AccountHolder != null) {
            return "Account Number: " + accountNumber + "\nCurrent balance: " + AccountHolder.getBalance();
        } else {
            return "Account not found.";
        }
    }
}
