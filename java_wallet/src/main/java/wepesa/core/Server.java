package wepesa.core;


import com.sun.net.httpserver.HttpServer;
import com.sun.xml.internal.ws.api.policy.PolicyResolver;
import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.strategies.WorkerThreadIOStrategy;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Server
{
    private static Logger LOG = LogManager.getRootLogger();

    public static final URI BASE_URI = UriBuilder
            .fromPath(Constants.BASE_URI)
            .scheme(Constants.HTTP_SCHEME)
            .host(Constants.HOST)
            .port(Constants.PORT)
            .build();

    public static void main(String[] args) throws IOException
    {

        final HttpServer server = startHttpServer();

        TCPNIOTransport transport = server.getListeners().iterator().next().getTransport();
        transport.setIOStrategy(WorkerThreadIOStrategy.getInstance());

        ThreadPoolConfig ioThreadPoolConfig = transport.getWorkerThreadPoolConfig();
        processHttpIoThreadPoolConfig(ioThreadPoolConfig);

//        LOG.debug("[Server ThreadPool Config: name=" + ioThreadPoolConfig.getPoolName() +
//                "; core_size=" + ioThreadPoolConfig.getCorePoolSize() +
//                "; max_size=" + ioThreadPoolConfig.getMaxPoolSize() + "]");

        server.start();

//        LOG.debug("[Jenko Server started at " + BASE_URI.toString() + "]");

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                shutdown(server);
            }
        });
    }

    public static HttpServer startHttpServer()
    {
        initServerContext();
//        final JenkoApplication protoApiServer = new JenkoApplication();
//        final ResourceConfig resourceConfig = ResourceConfig.forApplication(protoApiServer);
//        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
    }

    public static void shutdown(HttpServer server)
    {
//        LOG.debug("[Jenko Server stopped]");
//        PolicyResolver.ServerContext.destroy();
//        server.shutdownNow();
    }

    private static void initServerContext()
    {
        ExecutorService workerPool = getWorkerThreadPool();
//        PolicyResolver.ServerContext.init(workerPool);
    }

    private static void processHttpIoThreadPoolConfig(ThreadPoolConfig threadPoolConfig)
    {
        String THREAD_NAME_FORMAT = "http-io-%d";
        int IO_THREAD_POOL_SIZE = 3;
        int IO_THREAD_POOL_MAX_SIZE = 5;

        ThreadFactory httpThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(THREAD_NAME_FORMAT)
                .build();

        threadPoolConfig.setPoolName("http-io");
        threadPoolConfig.setThreadFactory(httpThreadFactory);
        threadPoolConfig.setCorePoolSize(IO_THREAD_POOL_SIZE);
        threadPoolConfig.setMaxPoolSize(IO_THREAD_POOL_MAX_SIZE);
    }

    private static ExecutorService getWorkerThreadPool()
    {
        String THREAD_NAME_FORMAT = "worker-thread-%d";
        int WORKER_THREAD_POOL_SIZE = 10;

        ThreadFactory workerThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(THREAD_NAME_FORMAT)
                .build();
        return Executors.newFixedThreadPool(WORKER_THREAD_POOL_SIZE, workerThreadFactory);
    }
}


