package org.boudni.transactionanalyzer.service;

import org.boudni.transactionanalyzer.core.model.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.*;

class TransactionAnalyzerTest {

    private final TransactionAnalyzer analyzer = new TransactionAnalyzer();

    @Test
    void totalPerCustomer() {
        List<Transaction> txs = List.of(
                new Transaction("A", 10.0, "X", LocalDate.now()),
                new Transaction("A", 5.0, "Y", LocalDate.now()),
                new Transaction("B", 7.0, "X", LocalDate.now())
        );
        Map<String, Double> totals = analyzer.calculateTotalPerCustomer(txs);
        assertEquals(15.0, totals.get("A"));
        assertEquals(7.0, totals.get("B"));
    }

    @Test
    void mostFrequentCategory() {
        List<Transaction> txs = List.of(
                new Transaction("A", 10.0, "X", LocalDate.now()),
                new Transaction("B", 5.0, "X", LocalDate.now()),
                new Transaction("C", 7.0, "Y", LocalDate.now())
        );
        var opt = analyzer.findMostFrequentCategory(txs);
        assertTrue(opt.isPresent());
        assertEquals("X", opt.get());
    }

    @Test
    void averageAmountEmptyReturnsEmpty() {
        OptionalDouble avg = analyzer.calculateAverageAmount(List.of());
        assertTrue(avg.isEmpty());
    }
}
