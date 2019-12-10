package com.theatre.movie.service;

import com.theatre.movie.persistence.DataSourceConnectionFactoryWithPool;
import com.theatre.movie.persistence.transaction.TransactionHandler;
import com.theatre.movie.persistence.transaction.TransactionManager;

/**
 * The {@code TransactionHandlerFactory} class provides method for provide
 * transaction handler represented by {@link com.theatre.movie.persistence.transaction.TransactionHandler} class
 * <p>
 * Properties: <b>TransactionHandlerFactory.TRANSACTION_MANAGER</b>,
 * <b>TransactionHandlerFactory.TRANSACTION_HANDLER</b>,
 *
 * @author Hlushchenko Renata
 * @see com.theatre.movie.persistence.transaction.TransactionManager
 */

public class TransactionHandlerFactory {
    private static final TransactionManager TRANSACTION_MANAGER = new TransactionManager(
            DataSourceConnectionFactoryWithPool.getInstance()
    );
    private static final TransactionHandler TRANSACTION_HANDLER = new TransactionHandler(TRANSACTION_MANAGER);

    public static TransactionHandler getTransactionHandler() {
        return TRANSACTION_HANDLER;
    }
}
