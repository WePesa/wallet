package wepesa.endpoints;

import com.google.gson.reflect.TypeToken;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Path("wallet")
public class WalletEndpoint extends AbstractEndpoint
{
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getUser(@PathParam("username") String username, @Suspended AsyncResponse asyncResponse)
    {
        workerPool.execute(() -> {

            if (username == null || username.isEmpty())
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

//            User user = blockchainApiManager.getUserApi().get(username);
//            if (user != null)
//            {
//                asyncResponse.resume(buildSuccessJsonResponse(user));
//            }
//            else
//            {
//                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
//            }
        });
    }
}

