interface CurrencyData {
    country: string;
    currentCurrency: number;
}

interface CurrencyFetcherOption {
    baseUrl: string;
}
class CurrencyFetcher {
    baseUrl: string;

    constructor({ baseUrl }: CurrencyFetcherOption) {
        this.baseUrl = baseUrl;
    }

    public async getCountryCurrency({
        country,
    }: {
        country: string;
    }): Promise<CurrencyData> {
        const targetUrl = `${this.baseUrl}?country=${country}&latest=true`;

        try {
            const response = await fetch(targetUrl, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                },
            });
            const currencyData = response.json() as Promise<CurrencyData>;

            return currencyData;
        } catch (e) {
            throw new Error(
                `${e}.\nFetching failed at ${this.baseUrl} in ${country}.`
            );
        }
    }
}

type CurrencyDataList = Array<CurrencyData>;
class CurrencyDataManager {
    public currencyDataList: CurrencyDataList = [];
    public currencyFetcher: CurrencyFetcher;

    constructor({ currencyFetcher }: { currencyFetcher: CurrencyFetcher }) {
        this.currencyFetcher = currencyFetcher;
    }

    private updateCurrencyDataList(updatedCurrencyData: CurrencyData): void {
        const updatedCurrencyDataList: CurrencyDataList =
            this.currencyDataList.reduce<CurrencyDataList>(
                (updatedD, currentData) => {
                    const isUpdateTargetCountry =
                        updatedCurrencyData.country === currentData.country;
                    if (isUpdateTargetCountry) {
                        updatedD.push(updatedCurrencyData);
                        return updatedD;
                    }
                    updatedCurrencyDataList.push(currentData);
                    return updatedD;
                },
                []
            );
        this.currencyDataList = updatedCurrencyDataList;
    }

    public async getCountryCurrency({
        country,
    }: {
        country: string;
    }): Promise<CurrencyData> {
        const targetCountryCurrencyData =
            await this.currencyFetcher.getCountryCurrency({
                country,
            });
        this.updateCurrencyDataList(targetCountryCurrencyData);
        return targetCountryCurrencyData;
    }
}

type ExchangeCheckData = {
    mount: number;
    fromCountry: string;
    toCountry: string;
};

interface ExchangeManagerOption extends CurrencyFetcherOption {}
class ExchangeManager {
    private exchangeDataList: Array<{
        exchangeKey: string;
        exchangeRate: number;
    }> = [];
    private currencyData$: CurrencyDataManager;
    private currencyFetcher$: CurrencyFetcher;

    constructor({ baseUrl }: ExchangeManagerOption) {
        const fetcher = new CurrencyFetcher({
            baseUrl,
        });
        this.currencyFetcher$ = fetcher;
        this.currencyData$ = new CurrencyDataManager({ currencyFetcher: fetcher });
    }

    get allCurrencyData() {
        return this.currencyData$.currencyDataList;
    }

    public async getExchangeRate({
        mount,
        fromCountry,
        toCountry,
    }: ExchangeCheckData) {
        const fromCountryCurrency = await this.currencyData$.getCountryCurrency({
            country: fromCountry,
        });
        const toCountryCurrency = await this.currencyData$.getCountryCurrency({
            country: toCountry,
        });
        const targetCurrency =
            toCountryCurrency.currentCurrency / fromCountryCurrency.currentCurrency;

        this.exchangeDataList.push({
            exchangeKey: `${fromCountry}-${toCountry}`,
            exchangeRate: targetCurrency,
        });
        return mount * targetCurrency;
    }

    public async sendMoney({
        mount,
        fromCountry,
        toCountry,
        bankName,
    }: ExchangeCheckData & {
        bankName: string;
    }) {
        const calculatedMoney = await this.getExchangeRate({
            mount,
            fromCountry,
            toCountry,
        });
        const sendTargetBankUrl = `~/${bankName}`;
        const sendMoneyInfo = JSON.stringify({
            mount: calculatedMoney,
        });
        try {
            const sendResult = (
                await fetch(sendTargetBankUrl, {
                    body: sendMoneyInfo,
                    headers: {
                        "Content-type": "application/json; charset=UTF-8",
                    },
                })
            ).json();

            return sendResult;
        } catch (e) {
            throw new Error(
                `${e}\nSend Error is occurred in ${fromCountry} at ${bankName}`
            );
        }
    }

    public async getCountryCurrency(country: string) {
        return await this.currencyFetcher$.getCountryCurrency({ country });
    }
}
