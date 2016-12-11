package wepesa.api.impl;

/**
 * Created by harsh on 12/12/16.
 */
public interface BlockchainApi {

    double getBalance(String address);

    boolean sendTransaction(String toAddress, String fromAddress, double amount);
}
