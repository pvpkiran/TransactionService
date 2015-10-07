package com.number26.dao;

import com.number26.pojo.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DataStore {
    void addToStore(Long id, Transaction transaction);
    Transaction getTransaction(Long id);
    List<Long> getTransactionsOfType(String transaction_type);
    Double getTransactionsSum(long transaction_id);
}
