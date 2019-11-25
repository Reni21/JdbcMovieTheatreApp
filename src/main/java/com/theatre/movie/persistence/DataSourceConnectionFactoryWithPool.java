package com.theatre.movie.persistence;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceConnectionFactoryWithPool implements ConnectionFactory {
    private static Logger LOG = Logger.getLogger(DataSourceConnectionFactoryWithPool.class);
    private static final DataSourceConnectionFactoryWithPool INSTANCE = new DataSourceConnectionFactoryWithPool();
    private static DataSource dataSource;

    static {
        try {
            Context initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/theatreDS");
        } catch (NamingException e) {
            LOG.error("Could not find DataSource JNDI", e);
        }
    }

    private DataSourceConnectionFactoryWithPool() {
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            LOG.debug("Connection received " + connection + " "+ connection.hashCode());
        } catch (SQLException e) {
            LOG.error("Some problem was occurred while getting connection to BD", e);
        }
        return connection;
    }

    public static DataSourceConnectionFactoryWithPool getInstance() {
        return INSTANCE;
    }

}
