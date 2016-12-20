package wepesa.api.impl;

import wepesa.api.ApiManager;
import wepesa.api.BitcoinApi;
import wepesa.api.EtherApi;
import wepesa.api.UserApi;
import wepesa.core.Constants;
import wepesa.data.DatabaseService;
import wepesa.model.User;
import wepesa.model.UserAddresses;

/**
 * Created by harsh on 11/12/16.
 */
public class UserApiImpl implements UserApi {

    @Override
    public UserAddresses registerUser(User user) throws Exception {

        int insertionResult = DatabaseService.insertUserIntoTable(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword());

        if(insertionResult == Constants.USER_EMAIL_EXISTS)
        {
            return null;
        }

        ApiManager apiManager = ApiManager.getInstance();
        BitcoinApi bitcoinApi = apiManager.getBitcoinApi();
        String btcAddress = bitcoinApi.getNewAddress();

        UserAddresses userAddresses = new UserAddresses();
        userAddresses.setBtcAddress(btcAddress);
//        userAddresses.setEthAddress("sdjhgdhgkdjdfnkfdsbkjs");

        EtherApi etherApi = apiManager.getEtherApi();

        return userAddresses;
    }

    @Override
    public int loginUser(String username, String password) {

        int result = DatabaseService.readUserFromTable(username, password);
        System.out.print(result);
        return result;
    }
}
