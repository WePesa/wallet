package wepesa.api.impl;

import wepesa.api.ApiManager;
import wepesa.api.BitcoinApi;
import wepesa.api.UserApi;
import wepesa.data.DatabaseService;
import wepesa.model.User;
import wepesa.model.UserAddresses;

/**
 * Created by harsh on 11/12/16.
 */
public class UserApiImpl implements UserApi {

    @Override
    public UserAddresses registerUser(User user) {

        DatabaseService.insertUserIntoTable(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword());

        return null;
    }

    @Override
    public int loginUser(String username, String password) {

        int result = DatabaseService.readUserFromTable(username, password);
        System.out.print(result);
        return result;
    }
}
