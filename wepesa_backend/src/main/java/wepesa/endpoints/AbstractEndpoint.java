package wepesa.endpoints;

import wepesa.api.ApiManager;
import wepesa.core.ServerContext;
import wepesa.utils.GsonProvider;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;

public class AbstractEndpoint
{
    protected ExecutorService workerPool;
    protected ApiManager apiManager;

    public AbstractEndpoint()
    {
        workerPool = ServerContext.getInstance().getWorkerPool();
        apiManager = ApiManager.getInstance();
    }

    protected Response buildSuccessJsonResponse(Object o)
    {
        return Response.ok(GsonProvider.get().toJson(o), MediaType.APPLICATION_JSON_TYPE).build();
    }

    protected Response buildEmptySuccessResponse()
    {
        return Response.ok().build();
    }

    protected Response redirect(String url)
    {
        try
        {
            return Response.seeOther(new URI(url)).build();
        }
        catch (URISyntaxException e)
        {
            return Response.serverError().build();
        }
    }
}
