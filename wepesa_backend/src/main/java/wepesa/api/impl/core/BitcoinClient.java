package wepesa.api.impl.core;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by harsh on 13/12/16.
 */
public class BitcoinClient {

    public static final String API_KEY = "44b4-3005-53da-371c";

    private static BitcoinClient instance;
    private static OkHttpClient okHttpClient;

    private BitcoinClient()
    {
        okHttpClient = new OkHttpClient();
    }

    public static BitcoinClient getInstance()
    {
        if (instance == null)
        {
            instance = new BitcoinClient();
        }

        return instance;
    }

    public Response getNewBitcoinAddress() throws IOException {
        Request request = new Request.Builder()
                .url("https://block.io/api/v2/get_new_address/?api_key=" + API_KEY)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        System.out.print(response);
        return response;
    }
}
