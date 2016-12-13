package wepesa.api.impl;

import wepesa.api.BitcoinApi;

/**
 * Created by harsh on 12/12/16.
 */
public class BitcoinApiImpl implements BitcoinApi {

    @Override
    public double getBalance(String address) {
        return 0;
    }

    @Override
    public boolean sendTransaction(String toAddress, String fromAddress, double amount) {
        return false;
    }

    @Override
    public String getNewAddress() {
        return null;
    }
}
