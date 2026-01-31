package org.boudni.transactionanalyzer.core.exception;

/**
 * Allgemeine Exception für fehlerhafte Transaktionsdaten.
 * Kann genutzt werden, wenn mehrere Validierungsfehler zusammengefasst werden sollen.
 */
public class InvalidTransactionDateException extends RuntimeException {
    public InvalidTransactionDateException(String message) {
        super(message);
    }
    public InvalidTransactionDateException(String message, Throwable cause) {
        super(message, cause);

    }

}
