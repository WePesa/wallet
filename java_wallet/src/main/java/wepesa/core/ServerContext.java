package wepesa.core;


import java.util.concurrent.ExecutorService;
import java.util.logging.LogManager;

public class ServerContext
{
//    private static Logger LOG = LogManager.getRootLogger();

    public static ServerContext instance;

    public static void init(ExecutorService workerPool)
    {
        instance = new ServerContext(workerPool);
//        LOG.debug("[ServerContext initialized]");


    }

    public static void destroy()
    {
        getInstance().workerPool.shutdownNow();

//        LOG.debug("[ServerContext destroyed]");

//        DatabaseAdapter databaseAdapter = DatabaseAdapter.getInstance();
//        databaseAdapter.close();
    }

    public static ServerContext getInstance()
    {
        if (instance == null)
        {
//            LOG.fatal("[ServerContext not initialized]");
            throw new IllegalStateException("ServerContext not initialized");
        }
        return instance;
    }

    private ExecutorService workerPool;

    private ServerContext(ExecutorService workerPool)
    {
        this.workerPool = workerPool;
    }

    public ExecutorService getWorkerPool()
    {
        return workerPool;
    }
}

