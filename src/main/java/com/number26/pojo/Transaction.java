package com.number26.pojo;

import javax.validation.constraints.NotNull;

public class Transaction {

    long transaction_id;
    long parent_id;
    @NotNull(message = "Transaction amount is mandatory.")
    Double amount;
    @NotNull(message = "Transaction type is mandatory.")
    String type;

    //Dummy Constructor for the sake of Json Mapping
    public Transaction(){

    }

    public Transaction(final Double amount, final String type) {
        this.amount = amount;
        this.type = type;
    }
    public Transaction(final Double amount, final String type, long parent_id) {
        this(amount, type);
        this.parent_id = parent_id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public long getParent_id() {
        return parent_id;
    }

    public long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }


}
