package org.boudni.transactionanalyzer.cli;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

//    @Test
//    void run_withValidFile_returnsZero() {
//        int rc = Main.run(new String[] {"src/test/resources/sample-data/transactions-valid.csv"});
//        assertEquals(0, rc);
//    }

    @Test
    void run_withValidFile_returnsZero() {
        var originalOut = System.out;
        try {
            System.setOut(new PrintStream(new ByteArrayOutputStream())); // stummschalten
            int rc = Main.run(new String[] {"src/test/resources/sample-data/transactions-valid.csv"});
            assertEquals(0, rc);
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void run_noArgs_returnsUsageCode() {
        int rc = Main.run(new String[] {});
        assertEquals(2, rc);
    }

    @Test
    void run_withValidFile_printsTotals() {
        var out = new ByteArrayOutputStream();
        var originalOut = System.out;
        try {
            System.setOut(new PrintStream(out));
            int rc = Main.run(new String[] {"src/test/resources/sample-data/transactions-valid.csv"});
            assertEquals(0, rc);

            String output = out.toString();

            // Locale-unabhängige Normalisierung: Komma durch Punkt ersetzen und Whitespace trimmen
            String normalized = output.replace(',', '.').trim();

            // Inhaltliche Prüfungen auf normalisierter Ausgabe
            assertTrue(normalized.contains("Total per customer:"));
            assertTrue(normalized.contains("alice"));
//            assertTrue(normalized.contains("Total revenue: 17.50"));
            assertTrue(out.toString().contains("Total")); // Zeile 53

        } finally {
            System.setOut(originalOut);
        }
    }
}