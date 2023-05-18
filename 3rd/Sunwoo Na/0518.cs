namespace RollingRess;

using ExchangeRate = Decimal;

public readonly struct Won
{
    private readonly uint _won;
    public Won(uint won)
        => _won = won;
    
    public Dollar ToDollar(ExchangeRate rate)
        => new(_won * rate);

    public static Won operator + (Won left, Won right)
        => new(left._won + right._won);
    public static Won operator -(Won left, Won right)
        => new(left._won - right._won);

    public static bool operator < (Won left, Won right)
        => left._won < right._won;
    public static bool operator >(Won left, Won right)
        => left._won > right._won;
}

public readonly struct Dollar
{
    private readonly decimal _dollar;
    public Dollar(decimal dollar)
        => _dollar = dollar;

    public Won ToWon(ExchangeRate rate)
    => new((uint)(_dollar * rate));

    public static Dollar operator + (Dollar left, Dollar right)
        => new(left._dollar + right._dollar);
    public static Dollar operator -(Dollar left, Dollar right)
    => new(left._dollar - right._dollar);

    public static bool operator <(Dollar left, Dollar right)
        => left._dollar < right._dollar;
    public static bool operator >(Dollar left, Dollar right)
        => left._dollar > right._dollar;
}

public class Exchanger
{
    public Won Won { get; private set; }
    public Dollar Dollar { get; private set; }

    // 환율 희망사항.. 언젠간 이루어지길 (해외직구 할 거 밀렸어요..)
    public ExchangeRate WonToDollar { get; set; } = 9.523E-4M;
    public ExchangeRate DollarToWon { get; set; } = 1050;

    public Exchanger(Won won, Dollar dollar)
        => (Won, Dollar) = (won, dollar);

    public void Deposit(Won won)
        => Won += won;
    public void Deposit(Dollar dollar)
        => Dollar += dollar;

    public bool Withdraw(Won won)
    {
        if (Won < won)
            return false;

        Won -= won;
        return true;
    }
    public bool Withdraw(Dollar dollar)
    {
        if (Dollar < dollar)
            return false;

        Dollar -= dollar;
        return true;
    }

    public bool Exchange(Won won)
    {
        if (Won < won)
            return false;

        Won -= won;
        Dollar += won.ToDollar(WonToDollar);
        return true;
    }
    public bool Exchange(Dollar dollar)
    {
        if (Dollar < dollar)
            return false;

        Dollar -= dollar;
        Won += dollar.ToWon(DollarToWon);
        return true;
    }
}