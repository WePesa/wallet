package wepesa.core;

import wepesa.endpoints.BitcoinEndpoint;
import wepesa.endpoints.EtherEndpoint;
import wepesa.endpoints.HealthEndpoint;
import wepesa.endpoints.UserEndpoint;
import wepesa.logging.RequestLogger;
import wepesa.logging.ResponseLogger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath(Constants.BASE_URI)
public class WePesaApplication extends Application
{
    public WePesaApplication()
    {
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new HashSet<>();

        /* Logger */
        resources.add(RequestLogger.class);
        resources.add(ResponseLogger.class);

        /* Endpoints */
        resources.add(HealthEndpoint.class);
        resources.add(UserEndpoint.class);
        resources.add(BitcoinEndpoint.class);
        resources.add(EtherEndpoint.class);

        /* Error Handler */
        resources.add(DefaultExceptionHandler.class);

        return resources;
    }
}