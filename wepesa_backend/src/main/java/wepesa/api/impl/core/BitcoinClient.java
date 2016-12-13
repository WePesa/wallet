package wepesa.api.impl.core;

/**
 * Created by harsh on 13/12/16.
 */
public class BitcoinClient {

    public static final String API_KEY = "44b4-3005-53da-371c";

    private static BitcoinClient instance;

    private BitcoinClient()
    {

    }

    public static BitcoinClient getInstance()
    {
        if (instance == null)
        {
            instance = new BitcoinClient();
        }

        return instance;
    }

}
