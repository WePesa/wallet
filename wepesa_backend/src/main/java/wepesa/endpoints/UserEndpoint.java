package wepesa.endpoints;

import com.google.gson.reflect.TypeToken;
import wepesa.api.UserApi;
import wepesa.model.User;
import wepesa.model.UserAddresses;
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

            User user;
            try {
                user = new User();
                user.setFirstName(input.get("first_name"));
                user.setLastName(input.get("last_name"));
                user.setEmail(input.get("email"));
                user.setPassword(input.get("password"));
            } catch (Exception e)
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            if(user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null)
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            UserApi userApi = apiManager.getUserApi();

            UserAddresses userAddresses = new UserAddresses();

            try {
                userAddresses = userApi.registerUser(user);
            } catch (Exception e) {
                e.printStackTrace();

                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
                return;
            }

            asyncResponse.resume(buildSuccessJsonResponse(userAddresses));
        });
    }

    @POST
    @Path("/login")
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

            String username;
            String password;

            try {

                username = input.get("username");
                password = input.get("password");

            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            if(username == null || password == null)
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            UserApi userApi = apiManager.getUserApi();

            int loginResult;

            try {
                loginResult = userApi.loginUser(username, password);
            } catch (Exception e) {
                e.printStackTrace();

                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
                return;
            }

            asyncResponse.resume(buildSuccessJsonResponse(loginResult));
        });
    }
}

