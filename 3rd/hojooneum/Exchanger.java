import java.util.HashMap;

public class Exchanger implements IExchanger {
    private Safe safe;
    private HashMap<String, Double> exchangeRateTable;
    public Exchanger() {
     this.safe = new Safe();
     this.exchangeRateTable = new HashMap<String, Double>() {{
         put("USD", 1.);
         put("KRW", 1334.);
         put("JPY", 137.7);
     }};
    }

    @Override
    public void exchange(String src, String tgt, Double amount) throws ExchangeException {
        if (!safe.deposits.containsKey(src)) throw new ExchangeException("No currency");
        if (safe.deposits.get(src) < amount) throw new ExchangeException("Not enouch money");
        double amountToExchange = safe.withdraw(src, amount);
        safe.deposit(tgt, amountToExchange * exchangeRate);
    }

    @Override
    public double getExchangeRate(String currency) throws ExchangeException {
        if (!exchangeRateTable.containsKey(currency)) throw new ExchangeException("No currency");
        return exchangeRateTable.get(currency);
    }

    @Override
    public void deposit(String currency, double amount) throws DepositException {
        safe.deposit(currency, amount);
    }
    @Override
    public void withdraw(String currency, double amount) throws WithdrawException {
        safe.withdraw(currency, amount);
    }
    @Override
    public void updateExchangeRate() throws UpdateException {

    }
}
