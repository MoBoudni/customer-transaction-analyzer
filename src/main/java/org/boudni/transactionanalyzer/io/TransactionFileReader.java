package org.boudni.transactionanalyzer.io;

import org.boudni.transactionanalyzer.core.model.Transaction;
import org.boudni.transactionanalyzer.core.exception.InvalidAmountException;
import org.boudni.transactionanalyzer.core.exception.InvalidTransactionDateException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileReader {

    public List<Transaction> readCsv(Path path) throws IOException {
        try (Reader in = Files.newBufferedReader(path)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withTrim()
                    .parse(in);
            List<Transaction> result = new ArrayList<>();
            int line = 1;
            for (CSVRecord record : records) {
                line++;
                String customer = record.get("customer");
                String amountStr = record.get("amount");
                String category = record.get("category");
                String dateStr = record.get("date");
                double amount;
                try {
                    amount = Double.parseDouble(amountStr);
                } catch (NumberFormatException e) {
                    throw new InvalidAmountException("Invalid amount at line " + line + ": " + amountStr, e);
                }
                if (amount < 0) {
                    throw new InvalidAmountException("Negative amount at line " + line + ": " + amount);
                }
                LocalDate date;
                try {
                    date = LocalDate.parse(dateStr);
                } catch (DateTimeParseException e) {
                    throw new InvalidTransactionDateException("Invalid date at line " + line + ": " + dateStr, e);
                }
                result.add(new Transaction(customer, amount, category, date));
            }
            return result;
        }
    }
}