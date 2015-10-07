package com.number26.dao;

import com.number26.pojo.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class InMemoryStore implements DataStore{
    Map<Long, Transaction> inMemoryStore = new ConcurrentHashMap<>();

    @Override
    public void addToStore(Long id, Transaction transaction) {
        inMemoryStore.put(id, transaction);
    }

    @Override
    public Transaction getTransaction(Long id) {
        return inMemoryStore.get(id);
    }

    @Override
    public List<Long> getTransactionsOfType(String transaction_type) {
        return inMemoryStore.keySet()
                .stream()
                .filter(transactionTypePredicate(transaction_type))
                .collect(Collectors.toList());
    }

    @Override
    public Double getTransactionsSum(long transaction_id) {
        Double totalSum = inMemoryStore.keySet()
                .stream().filter(transactionIdPredicate(transaction_id))
                .map(id -> inMemoryStore.get(id).getAmount())
                .reduce(0.0, (a, b) -> a+b);

        return totalSum;
    }

    public Predicate<Long> transactionTypePredicate(String transaction_type){
        return id -> {
            Transaction transaction = inMemoryStore.get(id);
            if(transaction.getType().equals(transaction_type))
                return true;
            return false;
        };
    }

    public Predicate<Long> transactionIdPredicate(long transaction_id) {
        return id -> {
            if (id == transaction_id)
                return true;
            Transaction transaction = inMemoryStore.get(id);
            if (transaction.getParent_id() == transaction_id)
                return true;
            return false;
        };
    }
}
