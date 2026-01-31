package org.boudni.transactionanalyzer.core.model;

import org.boudni.transactionanalyzer.core.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    @Test
    void createValidTransaction(){
        Transaction tx = new Transaction("cust-1", 42.5, "FOOD", LocalDate.of(2025, 1, 1));
        assertEquals("cust-1", tx.getCustomer());
        assertEquals(42.5, tx.getAmount());
        assertEquals("FOOD", tx.getCategory());
        assertEquals(LocalDate.of(2025, 1, 1), tx.getDate());
    }

    @Test
    void negativeAmountThrowsInvalidAmountException() {
        assertThrows(InvalidAmountException.class, () ->
                new Transaction("cust-1", -10.0, "FOOD", LocalDate.now())
        );
    }

    @Test
    void nullCustomerThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Transaction(null, 10.0, "FOOD", LocalDate.now())
        );



    }
}
