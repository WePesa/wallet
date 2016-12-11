package wepesa.endpoints;

import com.google.gson.reflect.TypeToken;
import wepesa.utils.GsonProvider;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("user")
public class UserEndpoint extends AbstractEndpoint {
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void registerUser(String inputJson, @Suspended AsyncResponse asyncResponse) {

        workerPool.execute(() -> {

            Map<String, String> input = null;
            try
            {
                Type type = new TypeToken<HashMap<String, String>>()
                {
                }.getType();
                input = GsonProvider.get().fromJson(inputJson, type);
                if (input == null)
                {
                    throw new NullPointerException();
                }
            }
            catch (Exception e)
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

//            asyncResponse.resume(buildSuccessJsonResponse(id));
        });
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void loginDreamer(String inputJson, @Suspended AsyncResponse asyncResponse) {
        workerPool.execute(() -> {


        });
    }
}

