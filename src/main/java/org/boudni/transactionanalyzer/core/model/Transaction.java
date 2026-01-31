package org.boudni.transactionanalyzer.core.model;

import org.boudni.transactionanalyzer.core.exception.InvalidAmountException;
import org.boudni.transactionanalyzer.core.exception.InvalidTransactionDateException;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private String customer, category;
    private double amount;
    private LocalDate date;
    public Transaction(String customer, double amount, String category, LocalDate Date) {

        if (customer == null || customer.isBlank()) {
            throw new IllegalArgumentException("Customer must not be null or blank");
        }
        if (amount < 0){
            throw new InvalidAmountException("amount must be >= 0");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("category must not be null or blank");
        }
        if (date == null) {
            throw new InvalidTransactionDateException("date must not be null");
        }
        this.customer = customer;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Transaction that))
            return false;
        return Double.compare(amount, that.amount) == 0
                && Objects.equals(customer, that.customer)
                && Objects.equals(category, that.category)
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, amount, category, date);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "customer='" + customer + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", date=" + date +
                '}';
    }
}
