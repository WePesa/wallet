package wepesa.api;


import wepesa.model.User;
import wepesa.model.UserAddresses;

/**
 * Created by harsh on 18/11/16.
 */
public interface UserApi {

    UserAddresses registerUser(User user) throws Exception;

    int loginUser(String username, String password);
}
