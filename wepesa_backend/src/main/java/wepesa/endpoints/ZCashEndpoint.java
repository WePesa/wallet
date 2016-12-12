package wepesa.endpoints;

import com.google.gson.reflect.TypeToken;
import wepesa.api.EtherApi;
import wepesa.api.ZCashApi;
import wepesa.utils.GsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Path("zcash")
public class ZCashEndpoint extends AbstractEndpoint {

    @POST
    @Path("/balance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void registerUser(String inputJson, @Suspended AsyncResponse asyncResponse) {

        workerPool.execute(() -> {

            Map<String, String> input = null;
            try {
                Type type = new TypeToken<HashMap<String, String>>() {
                }.getType();
                input = GsonProvider.get().fromJson(inputJson, type);
                if (input == null) {
                    throw new NullPointerException();
                }
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            String address;
            try {
                address = input.get("address");
            }catch (Exception e)
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            if(address == null)
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            ZCashApi zCashApi = apiManager.getZCashApi();

            double balance;
            try {
                balance = zCashApi.getBalance(address);
            } catch (Exception e) {
                e.printStackTrace();

                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
                return;
            }

            asyncResponse.resume(buildSuccessJsonResponse(address));

        });
    }

    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void loginDreamer(String inputJson, @Suspended AsyncResponse asyncResponse) {
        workerPool.execute(() -> {

            Map<String, String> input = null;
            try {
                Type type = new TypeToken<HashMap<String, String>>() {
                }.getType();
                input = GsonProvider.get().fromJson(inputJson, type);
                if (input == null) {
                    throw new NullPointerException();
                }
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            String toAddress;
            String fromAddress;
            double amount;

            try {

                toAddress = input.get("to_address");
                fromAddress = input.get("from_address");
                amount = Double.parseDouble(input.get("amount"));

            }catch (Exception e)
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            if(toAddress == null || fromAddress == null || amount == 0)
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            ZCashApi zCashApi = apiManager.getZCashApi();

            boolean isTransactionSuccessful;
            try {
                isTransactionSuccessful = zCashApi.sendTransaction(toAddress, fromAddress, amount);
            } catch (Exception e) {
                e.printStackTrace();

                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
                return;
            }

            asyncResponse.resume(buildSuccessJsonResponse(isTransactionSuccessful));
        });
    }
}

