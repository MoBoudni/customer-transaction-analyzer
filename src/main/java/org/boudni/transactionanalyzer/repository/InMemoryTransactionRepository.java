package org.boudni.transactionanalyzer.repository;

import org.boudni.transactionanalyzer.core.model.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public synchronized void add(Transaction t) {
        transactions.add(t);
    }

    @Override
    public synchronized boolean remove(Transaction t) {
        return transactions.remove(t);
    }

    @Override
    public List<Transaction> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(transactions));
    }

    @Override
    public synchronized List<Transaction> findByCustomer(String customer) {
        return transactions.stream()
                .filter(tx -> tx.getCustomer().equals(customer))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public synchronized void clear() {
        transactions.clear();

    }
}
