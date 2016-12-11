package wepesa.api;

import wepesa.api.impl.BitcoinApiImpl;
import wepesa.api.impl.UserApiImpl;

public class ApiManager
{
    private static ApiManager instance;

    public static ApiManager getInstance()
    {
        if (instance == null)
        {
            instance = new ApiManager();
        }

        return instance;
    }

    /* Blockchain APIs */
    private UserApi userApi;
    private BitcoinApi bitcoinApi;

    private ApiManager()
    {

    }

    public UserApi getUserApi() {

        if(userApi == null)
        {
            userApi = new UserApiImpl();
        }

        return userApi;
    }

    public BitcoinApi getBitcoinApi() {

        if(bitcoinApi == null)
        {
            bitcoinApi = new BitcoinApiImpl();
        }

        return bitcoinApi;
    }
}
