package wepesa.api.impl.core;

import com.squareup.okhttp.*;

import java.io.IOException;

/**
 * Created by harsh on 13/12/16.
 */
public class EthereumClient {

    public static final String API_KEY = "49d8335bce2340ef9da1bf13f4b7881a";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static EthereumClient instance;
    private static OkHttpClient okHttpClient;

    private EthereumClient() {
        okHttpClient = new OkHttpClient();
    }

    public static EthereumClient getInstance() {
        if (instance == null) {
            instance = new EthereumClient();
        }

        return instance;
    }

    public String getNewEthereumAddress() throws IOException {
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url("https://api.blockcypher.com/v1/eth/main/addrs?token=" + API_KEY)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public String getBalance(String address) throws Exception{

        Request request = new Request.Builder()
                .url("https://block.io/api/v2/get_address_balance/?api_key=" + API_KEY + "&addresses= " + address)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        System.out.print(response);
        return response.body().string();
    }


}
