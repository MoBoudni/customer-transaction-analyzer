package org.boudni.transactionanalyzer.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.boudni.transactionanalyzer.core.model.Transaction;
import org.boudni.transactionanalyzer.service.TransactionAnalyzer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionReportGenerator {

    private final TransactionAnalyzer analyzer;
    private final ObjectMapper objectMapper;

    public TransactionReportGenerator() {
        this.analyzer = new TransactionAnalyzer();
        this.objectMapper = new ObjectMapper();
    }

    // Für Tests und DI
    public TransactionReportGenerator(TransactionAnalyzer analyzer, ObjectMapper objectMapper) {
        this.analyzer = analyzer == null ? new TransactionAnalyzer() : analyzer;
        this.objectMapper = objectMapper == null ? new ObjectMapper() : objectMapper;
    }

    /**
     * Erzeugt einen lesbaren Text‑Report mit "Total per customer" und "Total revenue".
     * Gibt bei leeren oder null Transactions einen Hinweistext zurück.
     */
    public String generateSummaryReport(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return "No transactions available.";
        }

        Map<String, Double> totals = analyzer.calculateTotalPerCustomer(transactions);
        double totalRevenue = analyzer.totalRevenue(transactions);

        String customers = totals.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .map(e -> String.format("%s: %.2f", e.getKey(), e.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));

        return String.format("Total per customer:%n%s%nTotal revenue: %.2f", customers, totalRevenue);
    }

    /**
     * Erzeugt eine CSV mit Header "customer,total".
     * Liefert einen leeren String bei null oder leerer Map.
     */
    public String generateCustomerTotalsCsv(Map<String, Double> totalsPerCustomer) {
        if (totalsPerCustomer == null || totalsPerCustomer.isEmpty()) {
            return "";
        }

        String header = "customer,total";
        String rows = totalsPerCustomer.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .map(e -> String.format("%s,%.2f", escapeCsv(e.getKey()), e.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));

        return header + System.lineSeparator() + rows;
    }

    /**
     * Erzeugt einen JSON‑Report mit zwei Feldern:
     * - totalsPerCustomer: Map<String, Double>
     * - totalRevenue: double
     * Bei Serialisierungsfehlern wird eine RuntimeException geworfen.
     */
    public String generateJsonReport(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return "{\"totalsPerCustomer\":{},\"totalRevenue\":0.0}";
        }

        Map<String, Double> totals = analyzer.calculateTotalPerCustomer(transactions);
        double totalRevenue = analyzer.totalRevenue(transactions);

        var payload = Map.of(
                "totalsPerCustomer", totals,
                "totalRevenue", totalRevenue
        );

        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to generate JSON report", e);
        }
    }

    // Kleiner CSV‑Escaper für einfache Fälle (Kommas und Anführungszeichen)
    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}