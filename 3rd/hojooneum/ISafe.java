public interface ISafe {
    void deposit(String currency, double amount) throws DepositException;
    double withdraw(String currency, double amount) throws WithdrawException;
}
