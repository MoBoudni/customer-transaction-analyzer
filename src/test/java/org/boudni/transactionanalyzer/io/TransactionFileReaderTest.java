package org.boudni.transactionanalyzer.io;

import org.boudni.transactionanalyzer.core.model.Transaction;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFileReaderTest {

    @Test
    void readValidCsv() throws Exception {
        TransactionFileReader reader = new TransactionFileReader();
        Path sample = Path.of("src/test/resources/sample-data/transactions-valid.csv");
        List<Transaction> list = reader.readCsv(sample);
        assertEquals(3, list.size());
    }

    @Test
    void readInvalidCsvThrowsInvalidAmountOrDate() {
        TransactionFileReader reader = new TransactionFileReader();
        Path bad = Path.of("src/test/resources/sample-data/transactions-invalid.csv");
        assertThrows(RuntimeException.class, () -> reader.readCsv(bad));
    }
}
