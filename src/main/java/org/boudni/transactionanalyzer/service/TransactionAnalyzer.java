package org.boudni.transactionanalyzer.service;

import org.boudni.transactionanalyzer.core.model.Transaction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.OptionalDouble;

public class TransactionAnalyzer {

    public double totalRevenue(List<Transaction> txs) {
        if (txs == null || txs.isEmpty()) return 0.0;
        return txs.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public Map<String, Double> calculateTotalPerCustomer(List<Transaction> txs) {
        if (txs == null || txs.isEmpty()) return Collections.emptyMap();
        return txs.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomer,
                        Collectors.summingDouble(Transaction::getAmount)));
    }

    public OptionalDouble calculateAverageAmount(List<Transaction> txs) {
        if (txs == null || txs.isEmpty()) return OptionalDouble.empty();
        return txs.stream().mapToDouble(Transaction::getAmount).average();
    }

    public Optional<String> findMostFrequentCategory(List<Transaction> txs) {
        if (txs == null || txs.isEmpty()) return Optional.empty();
        return txs.stream()
                .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public List<Map.Entry<String, Double>> topNCustomersByRevenue(List<Transaction> txs, int n) {
        if (txs == null || txs.isEmpty() || n <= 0) return Collections.emptyList();
        return calculateTotalPerCustomer(txs).entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey)) // deterministischer Tie‑Breaker
                .limit(n)
                .collect(Collectors.toList());
    }
}