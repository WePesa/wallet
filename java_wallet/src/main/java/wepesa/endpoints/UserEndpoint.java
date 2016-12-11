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

            System.out.println(inputJson);

            blockchainApiManager.getDreamerApi().dreamerLogin(inputJson, "");

            asyncResponse.resume(Response.status(Response.Status.OK).build());
//            Map<String, String> input = null;
//            try
//            {
//                Type type = new TypeToken<HashMap<String, String>>()
//                {
//                }.getType();
//                input = GsonProvider.get().fromJson(inputJson, type);
//                if (input == null)
//                {
//                    throw new NullPointerException();
//                }
//            }
//            catch (Exception e)
//            {
//                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
//                return;
//            }
//
//            Document document = new Document();
//            document.setName(input.get("name"));
//            document.setCreator(input.get("creator"));
//            document.setEncryptedContent(input.get("encrypted_content"));
//            document.setEncryptedKey(input.get("encrypted_key"));
//            document.setSignature(input.get("signature"));
//
//            UserApi userApi = blockchainApiManager.getUserApi();
//            DocumentApi documentApi = blockchainApiManager.getDocumentApi();
//
//            if (!DocumentHelper.validate(document))
//            {
//                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
//                return;
//            }
//
//            if (!DocumentHelper.isSizeOk(document))
//            {
//                asyncResponse.resume(Response.status(Response.Status.REQUEST_ENTITY_TOO_LARGE).build());
//                return;
//            }
//
//            User creator = userApi.get(document.getCreator());
//            if (creator == null)
//            {
//                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
//                return;
//            }
//
//            if (!DocumentHelper.validateSignature(creator, document))
//            {
//                asyncResponse.resume(Response.status(Response.Status.UNAUTHORIZED).build());
//                return;
//            }
//
//            document.setId(DocumentHelper.generateId(document));
//            document.setCreatedAt(Instant.now().getEpochSecond());
//            documentApi.create(document);
//            asyncResponse.resume(buildSuccessJsonResponse(DocumentHelper.summarize(document)));
        });
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getDreamer(@PathParam("id") String dreamerId, @Suspended AsyncResponse asyncResponse) {
        workerPool.execute(() -> {

            if (dreamerId == null || dreamerId.isEmpty()) {

                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            Dreamer dreamer = blockchainApiManager.getDreamerApi().getDreamer(dreamerId);
            if (dreamer != null) {

                asyncResponse.resume(buildSuccessJsonResponse(dreamer));
            } else {

                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
            }
        });

    }
}

