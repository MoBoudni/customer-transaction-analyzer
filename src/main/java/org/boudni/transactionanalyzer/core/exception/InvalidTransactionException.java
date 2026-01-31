package org.boudni.transactionanalyzer.core.exception;

/**
 * Allgemeine Exception für fehlerhafte Transaktionsdaten.
 * Kann genutzt werden, wenn mehrere Validierungsfehler zusammengefasst werden sollen
 */
public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String message) {
        super(message);
    }
    public InvalidTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
