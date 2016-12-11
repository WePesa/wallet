package wepesa.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("health-check")
public class HealthEndpoint extends AbstractEndpoint {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void healthCheck(@Suspended AsyncResponse asyncResponse) {
        workerPool.execute(() -> {

            asyncResponse.resume(buildSuccessJsonResponse("It Works!"));

        });
    }
}

