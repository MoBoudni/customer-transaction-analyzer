package org.boudni.transactionanalyzer.repository;

import org.boudni.transactionanalyzer.core.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    void add(Transaction t);
    boolean remove(Transaction t);
    List<Transaction> getAll();
    List<Transaction> findByCustomer(String customer);
    void clear();
}
