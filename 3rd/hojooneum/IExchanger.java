public interface IExchanger {
    void exchange(String src, String tgt, Double amount) throws ExchangeException;
    void updateExchangeRate() throws UpdateException;

    double getExchangeRate(String currency) throws ExchangeException;

    void deposit(String currency, double amount) throws DepositException;
    double withdraw(String currency, double amount) throws WithdrawException;
}
