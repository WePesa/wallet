package wepesa.api.impl;

import wepesa.api.BlockchainApi;

/**
 * Created by harsh on 12/12/16.
 */
public class BlockchainApiImpl implements BlockchainApi {

    @Override
    public double getBalance(String address) {
        return 0;
    }

    @Override
    public boolean sendTransaction(String toAddress, String fromAddress, double amount) {
        return false;
    }
}
