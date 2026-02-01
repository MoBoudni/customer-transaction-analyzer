package org.boudni.transactionanalyzer.cli;

import org.boudni.transactionanalyzer.core.model.Transaction;
import org.boudni.transactionanalyzer.io.TransactionFileReader;
import org.boudni.transactionanalyzer.repository.InMemoryTransactionRepository;
import org.boudni.transactionanalyzer.repository.TransactionRepository;
import org.boudni.transactionanalyzer.service.TransactionAnalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private Main() { /* no instances */ }

    public static void main(String[] args) {
        int exit = run(args);
        System.exit(exit);
    }

    public static int run(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println("Usage: java -jar transaction-analyzer.jar <transactions.csv>");
            return 2; // usage error
        }

        Path csv = Path.of(args[0]);
        if (!Files.exists(csv)) {
            System.err.println("File not found: " + csv);
            return 3; // file not found
        }

        TransactionFileReader reader = new TransactionFileReader();
        TransactionRepository repo = new InMemoryTransactionRepository();
        TransactionAnalyzer analyzer = new TransactionAnalyzer();

        try {
            List<Transaction> txs = reader.readCsv(csv);
            txs.forEach(repo::add);

            Map<String, Double> totals = analyzer.calculateTotalPerCustomer(repo.getAll());
            if (totals.isEmpty()) {
                System.out.println("No transactions found.");
                return 0;
            }

            System.out.println("Total per customer:");
            totals.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .forEach(e -> System.out.printf("%s: %.2f%n", e.getKey(), e.getValue()));

            // optional: show overall stats
            double totalRevenue = analyzer.totalRevenue(repo.getAll());
            System.out.printf("Total revenue: %.2f%n", totalRevenue);
            return 0; // success

        } catch (IOException e) {
            LOG.log(Level.SEVERE, "I/O error while reading CSV: " + csv, e);
            System.err.println("Failed to read CSV: " + e.getMessage());
            return 4; // IO error

        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "Processing error", e);
            System.err.println("Processing error: " + e.getMessage());
            return 5; // processing error
        }
    }
}