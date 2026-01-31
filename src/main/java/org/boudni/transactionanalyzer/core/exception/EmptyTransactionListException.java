package org.boudni.transactionanalyzer.core.exception;

/**
 * Wird geworfen, wenn eine Operation eine nicht leere Transaktionsliste erwartet.
 */
public class EmptyTransactionListException extends RuntimeException {
    public EmptyTransactionListException(String message) {
        super(message);
    }
}
