package wepesa.api;

import java.io.IOException;

/**
 * Created by harsh on 12/12/16.
 */
public interface BitcoinApi {

    String getBalance(String address) throws Exception;

    boolean sendTransaction(String toAddress, String fromAddress, double amount);

    String getNewAddress() throws Exception;
}
