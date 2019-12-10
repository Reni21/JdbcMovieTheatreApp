package com.theatre.movie.persistence.transaction;

import lombok.AllArgsConstructor;

import java.util.function.Supplier;

/**
 * The {@code TransactionHandler} class manages and control the steps of transaction
 * with help of wrapper pattern
 * Properties: <b>transactionManager</b>
 *
 * @author Hlushchenko Renata
 * @see com.theatre.movie.persistence.transaction.TransactionManager
 */

@AllArgsConstructor
public class TransactionHandler {
    private final TransactionManager transactionManager;

    public <T> T runInTransaction(Supplier<T> supplier) {
        transactionManager.begin();
        try {
            supplier.get();
            transactionManager.commit();
        } catch (Throwable e) {
            transactionManager.rollback();
            throw e;
        }
        return supplier.get();
    }

    public void runInTransaction(Runnable runnable) {
        transactionManager.begin();
        try {
            runnable.run();
            transactionManager.commit();
        } catch (Throwable e) {
            transactionManager.rollback();
            throw e;
        }
    }
}


