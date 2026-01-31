package org.boudni.transactionanalyzer.repository;

import org.boudni.transactionanalyzer.core.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {

    private TransactionRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryTransactionRepository();
    }

    @Test
    void addAndGetAll() {
        Transaction tx = new Transaction("c1", 10.0, "A", LocalDate.now());
        repo.add(tx);
        List<Transaction> all = repo.getAll();
        assertEquals(1, all.size());
        assertTrue(all.contains(tx));
    }

    @Test
    void getAllIsUnmodifiable() {
        Transaction tx = new Transaction("c1", 10.0, "A", LocalDate.now());
        repo.add(tx);
        List<Transaction> all = repo.getAll();
        assertThrows(UnsupportedOperationException.class, () -> all.add(tx));
    }

    @Test
    void findByCustomerReturnsCorrectList() {
        Transaction t1 = new Transaction("alice", 5.0, "X", LocalDate.now());
        Transaction t2 = new Transaction("bob", 7.0, "Y", LocalDate.now());
        Transaction t3 = new Transaction("alice", 3.0, "Z", LocalDate.now());
        repo.add(t1);
        repo.add(t2);
        repo.add(t3);

        List<Transaction> alice = repo.findByCustomer("alice");
        assertEquals(2, alice.size());
        assertTrue(alice.contains(t1));
        assertTrue(alice.contains(t3));
    }
}

public interface TransactionRepositoryTest {
}
