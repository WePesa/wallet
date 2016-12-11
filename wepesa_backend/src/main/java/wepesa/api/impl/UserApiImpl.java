package wepesa.api.impl;

import wepesa.api.UserApi;
import wepesa.model.User;
import wepesa.model.UserAddresses;

/**
 * Created by harsh on 11/12/16.
 */
public class UserApiImpl implements UserApi {

    @Override
    public UserAddresses registerUser(User user) throws Exception {
        return null;
    }

    @Override
    public boolean loginUser(String username, String password) {
        return false;
    }
}
