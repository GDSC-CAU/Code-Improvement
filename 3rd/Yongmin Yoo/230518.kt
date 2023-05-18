internal interface Exchange{
    @kotlin.Throws(ValueException::class)
    fun getDollar(val won)
    @kotlin.Throws(ValueException::class)
    fun getWon(val dollar)

    fun getExchangeRate()
    @kotlin.Throws(ValueException::class)
    fun setExchangeRate(val rate)

    @kotlin.Throws(ValueException::class)
    fun getMoneyFromAccount(val accountNum, val amount, val isDollar)
    @kotlin.Throws(ValueException::class)
    fun putMoneyToAccount(val accountNum, val amount, val isDollar)
}

class Account(val user: String){
    var accountNum: String = ""
    var accountAmount: Number = 0

    @kotlin.Throws(AccountException::class)
    fun initAccount(val accountNum: String){
        if(!accountNum.equals("")){
            throw AccountException("Account is Already Initialized")
        }

        this.accountNum = accountNum
    }

    @kotlin.Throws(AccountException::class)
    fun putMoney(val amount: Number){
        if(accountNum.equals("")){
            throw AccountException("Account is not Initialized")
        }

        accountAmount += amount
        return accountAmount
    }

    @kotlin.Throws(AccountException::class)
    @kotlin.Throws(MoneyException::class)
    fun withdrawMoney(val amount: Number){
        if(accountNum.equals("")){
            throw AccountException("Account is not Initialized")
        }

        if(accountAmount < amount){
            throw MoneyException("Account Balance is Smaller than Amount")
        }

        accountAmount -= amount
        return accountAmount
    }
}

class MoneyExchange(var exchangeRate: Int): Exchange{
    fun getDollar(val won: Number){
        if(won < 0){
            throw ValueException("Won Value must not be Negative")
        }

        return won / exchangeRate
    }

    fun getWon(val dollar: Number){
        if(dollar < 0){
            throw ValueException("Dollar Value must not be Negative")
        }

        return dollar * exchangeRate
    }

    fun getExchangeRate(){
        return this.exchangeRate
    }

    fun setExchangeRate(val rate: Number){
        if(rate <= 0){
            throw ValueExceptiopn("Exchange Rate must not be smaller than Zero")
        }

        this.exchangeRate = rate
    }

    fun putMoneyToAccount(val account: Account, val amount: Number, val isDollar: Boolean){
        if(amount < 0){
            throw ValueException("Money Value must not be Negative")
        }

        if(isDollar){
            amount = getWon(amount)
        }

        account.putMoney(amount)
    }

    fun withdrawMoneyFromAccount(val account: Account, var amount: Number, val isDollar: Boolean){
        if(amount < 0){
            throw ValueException("Money Value must not be Negative")
        }

        if(isDollar){
            amount = getWon(amount)
        }

        account.withdrawMoney(amount)
    }
}
