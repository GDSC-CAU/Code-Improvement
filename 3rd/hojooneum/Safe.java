import java.util.HashMap;

public class Safe implements ISafe {
    public HashMap<String, Double> deposits;
    public Safe() {
        this.deposits = new HashMap<String, Double>();
    }

    @Override
    public void deposit(String currency, double amount) throws DepositException {
        if (!deposits.containsKey(currency)) deposits.put(currency, amount);
        else deposits.put(currency,deposits.get(currency) + amount);
    }

    @Override
    public double withdraw(String currency, double amount) throws WithdrawException {
        if (!deposits.containsKey(currency)) throw new WithdrawException("No According Currency");
        if (deposits.get(currency) < amount) throw new WithdrawException("Not enough Money");
        deposits.replace(currency, deposits.get(currency) - amount);
        return amount;
    }
}
