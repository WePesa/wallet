package wepesa.api.impl;

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

        // TODO -- Create new addresses on all nodes
        return null;
    }

    @Override
    public boolean loginUser(String username, String password) {

        boolean isLoginSuccessful = DatabaseService.readUserFromTable(username, password);
        return isLoginSuccessful;
    }
}
