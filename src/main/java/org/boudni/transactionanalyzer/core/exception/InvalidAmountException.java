package org.boudni.transactionanalyzer.core.exception;

/**
 * Wird geworfen, wenn ein Transaktionsbetrag ungültig ist(z. B. negativ oder nicht parsebar)
 */
public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }
    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
