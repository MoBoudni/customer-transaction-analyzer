package org.boudni.transactionanalyzer.integration;

import org.boudni.transactionanalyzer.io.TransactionFileReader;
import org.boudni.transactionanalyzer.repository.InMemoryTransactionRepository;
import org.boudni.transactionanalyzer.service.TransactionAnalyzer;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class EndToEndIntegrationTest {

    @Test
    void fullPipelineProcessesSampleCsv() throws Exception {
        TransactionFileReader reader = new TransactionFileReader();
        var repo = new InMemoryTransactionRepository();
        var analyzer = new TransactionAnalyzer();

        var txs = reader.readCsv(Path.of("src/test/resources/sample-data/transactions-valid.csv"));
        txs.forEach( repo::add);

        var totals = analyzer.calculateTotalPerCustomer(repo.getAll());
        assertFalse(totals.isEmpty());
        assertEquals(2, totals.size()); // alice and bob in sample
    }
}
