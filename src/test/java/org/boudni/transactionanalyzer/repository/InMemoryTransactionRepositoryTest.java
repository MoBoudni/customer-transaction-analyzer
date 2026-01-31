package org.boudni.transactionanalyzer.repository;

import org.boudni.transactionanalyzer.core.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTransactionRepositoryTest {

    private InMemoryTransactionRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryTransactionRepository();
        repo.clear();
    }

    @Test
    void addAndGetAll_shouldContainAddedTransaction() {
        Transaction t = new Transaction("alice", 10.0, "FOOD", LocalDate.of(2023,1,1));
        repo.add(t);

        List<Transaction> all = repo.getAll();
        assertEquals(1, all.size());
        assertTrue(all.contains(t));
    }

    @Test
    void getAll_shouldReturnUnmodifiableList() {
        Transaction t = new Transaction("bob", 5.0, "BOOK", LocalDate.of(2023,2,2));
        repo.add(t);

        List<Transaction> all = repo.getAll();
        assertThrows(UnsupportedOperationException.class, () -> all.add(t));
    }

    @Test
    void findByCustomer_shouldReturnOnlyMatchingCustomerTransactions() {
        Transaction t1 = new Transaction("carol", 7.5, "GROCERY", LocalDate.of(2023,3,3));
        Transaction t2 = new Transaction("dave", 12.0, "GROCERY", LocalDate.of(2023,3,4));
        Transaction t3 = new Transaction("carol", 3.0, "TOY", LocalDate.of(2023,3,5));
        repo.add(t1);
        repo.add(t2);
        repo.add(t3);

        List<Transaction> carolTx = repo.findByCustomer("carol");
        assertEquals(2, carolTx.size());
        assertTrue(carolTx.contains(t1));
        assertTrue(carolTx.contains(t3));
    }

    @Test
    void remove_shouldRemoveTransaction() {
        Transaction t = new Transaction("eve", 20.0, "TRAVEL", LocalDate.of(2023,4,1));
        repo.add(t);
        assertTrue(repo.remove(t));
        assertFalse(repo.getAll().contains(t));
    }

    @Test
    void clear_shouldEmptyRepository() {
        repo.add(new Transaction("f", 1.0, "X", LocalDate.now()));
        repo.clear();
        assertTrue(repo.getAll().isEmpty());
    }
}