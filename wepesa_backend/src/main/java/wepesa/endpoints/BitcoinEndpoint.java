package wepesa.endpoints;

import com.google.gson.reflect.TypeToken;
import wepesa.api.UserApi;
import wepesa.model.User;
import wepesa.model.UserAddresses;
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

@Path("bitcoin")
public class BitcoinEndpoint extends AbstractEndpoint {

    @POST
    @Path("/balance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void registerUser(String inputJson, @Suspended AsyncResponse asyncResponse) {

        workerPool.execute(() -> {

        });
    }

    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void loginDreamer(String inputJson, @Suspended AsyncResponse asyncResponse) {
        workerPool.execute(() -> {

            
        });
    }
}

