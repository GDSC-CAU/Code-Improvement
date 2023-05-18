class Currency:
    def __init__(self):
        self.exchange_rate = 1300.0
        self.money = 0.0
        self.currency_state = 'won'
    def set_currency_rate(self, rate):
        self.exchange_rate = rate
    def change_currency_state(self):
        if self.currency_state == 'won':
            self.currency_state = 'dollar'
        elif self.currency_state == 'dollar':
            self.currency_state = 'won'
        else:
            print("type error")
    def get_dollar(self, rate, won):
        self.set_currency_rate(rate)
        return won / self.exchange_rate
    def get_won(self, rate, dollar):
        self.set_currency_rate(rate)
        return dollar * self.exchange_rate
    def deposit_money(self, money_amount):
        self.money += money_amount
    def withdraw_money(self, money_amount ):
        self.money -= money_amount
