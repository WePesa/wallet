package wepesa.api;

/**
 * Created by harsh on 12/12/16.
 */
// public interface BitcoinApi {

//    double getBalance(String address);

//    boolean sendTransaction(String toAddress, String fromAddress, double amount);
// }
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BitcoinApi {

    protected CurrencyValue balance;
    protected CurrencyValue openOrders;

    protected Currency currency;
    protected List<WalletTransaction> transactions = new ArrayList<>();


    public BitcoinApi() {
    }

    public BitcoinApi(Currency c) {
        this.currency = c;
        this.balance = new CurrencyValue(c);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setBalance(CurrencyValue balance) {

        if(balance.getCurrency() != this.currency)
            throw new IllegalArgumentException("invalid currency: " + balance.getCurrency() + " != " + currency);

        this.balance = balance;
    }

    public void setOpenOrders(CurrencyValue value) {
        if(balance.getCurrency() != this.currency)
            throw new IllegalArgumentException("invalid currency: " + balance.getCurrency() + " != " + currency);

        this.openOrders = value;
    }

    public CurrencyValue getBalance() {
        return balance;
    }

    public CurrencyValue getOpenOrders() {
        return openOrders;
    }

    public void addTransaction(WalletTransaction trans) {

        switch(trans.getType()) {
            case DEPOSIT:
            case IN:
                balance.add(trans.getValue());
                break;

            case FEE:
            case OUT:
            case WITHDRAW:
            case SPENT:
                balance.subtract(trans.getValue());
                break;

            default:
                throw new IllegalArgumentException("transaction type not implemented in wallet");
        }

        if(trans.getBalance() != null) {
            this.balance = new CurrencyValue(trans.getBalance());
        }
        else {
            // copy value, as this.balance reference changes when adding transactions
            trans.setBalance(new CurrencyValue(this.balance));
        }

        transactions.add(trans);
    }

    public void setTransactions(List<WalletTransaction> transactions) {
        this.transactions = transactions;
    }

    public List<WalletTransaction> getTransactions() {
        return transactions;
    }

    public Performance getPerformance() {
        return getPerformance(new Date(0L));
    }

    public Performance getPerformance(Date since) {

        Performance perf = new CurrencyPerformance(getCurrency());
        for(WalletTransaction trans : getTransactions()) {
            if(since.before(trans.getTimestamp())) {
                perf.includeTransaction(trans);
            }
        }

        return perf;
    }


    public String toString() {
        return "Wallet: " + getCurrency() + ": " + getBalance();
    }
}
