package wepesa.api;


import wepesa.model.User;

/**
 * Created by harsh on 18/11/16.
 */
public interface UserApi {

    void registerUser(User user) throws Exception;

    boolean loginUser(String username, String password);
}
