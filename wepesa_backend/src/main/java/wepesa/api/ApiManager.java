package wepesa.api;

import wepesa.api.impl.BitcoinApiImpl;
import wepesa.api.impl.EtherApiImpl;
import wepesa.api.impl.UserApiImpl;
import wepesa.api.impl.ZCashApiImpl;

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
    private EtherApi etherApi;
    private ZCashApi zCashApi;

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

    public EtherApi getEtherApi() {

        if(etherApi == null)
        {
            etherApi = new EtherApiImpl();
        }

        return etherApi;
    }

    public ZCashApi getZCashApi() {

        if(zCashApi == null)
        {
            zCashApi = new ZCashApiImpl();
        }

        return zCashApi;
    }
}
