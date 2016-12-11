package wepesa.data;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Harsh.Pokharna on 7/1/2015.
 */
public class DatabaseAdapter {

    private static ComboPooledDataSource comboPooledDataSource;
    private static DatabaseAdapter instance;

    private static Logger LOG = LogManager.getRootLogger();

    private DatabaseAdapter(){

    }

    public static DatabaseAdapter getInstance()
    {
        if (instance == null)
        {
            instance = new DatabaseAdapter();
        }
        return instance;
    }


    public void init() throws Exception {

        comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass("org.sqlite.JDBC");
        comboPooledDataSource.setJdbcUrl("jdbc:sqlite:/Users/harsh/workspace/Ethereum/wallet/wepesa_backend/database/wepesa_db.db");

        comboPooledDataSource.setMaxPoolSize(50);
        comboPooledDataSource.setMinPoolSize(5);
        comboPooledDataSource.setAcquireIncrement(5);

        LOG.debug("Database Adapter initialised");

    }

    public void close(){
        comboPooledDataSource.close();

        LOG.debug("Database Adapter closed");
    }

    public Connection getConnection() throws SQLException {
        return comboPooledDataSource.getConnection();
    }
}
