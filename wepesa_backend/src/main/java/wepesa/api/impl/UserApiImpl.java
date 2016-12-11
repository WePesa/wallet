package wepesa.api.impl;

import wepesa.api.UserApi;
import wepesa.model.User;

/**
 * Created by harsh on 11/12/16.
 */
public class UserApiImpl implements UserApi {

    @Override
    public void registerUser(User user) throws Exception {

    }

    @Override
    public boolean loginUser(String username, String password) {
        return false;
    }
}
