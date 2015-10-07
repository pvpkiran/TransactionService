package com.number26.service;

import com.number26.dao.DataStore;
import com.number26.exceptions.TransactionException;
import com.number26.pojo.Response.PutResponse;
import com.number26.pojo.Response.SumResponse;
import com.number26.pojo.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    DataStore dataStore;

    public DataStore getDataStore() {
        return dataStore;
    }

    public PutResponse saveTransaction(long id, Transaction transaction) throws TransactionException {
        if(isParentIdPresent(transaction))
            if(!checkIfParentIdValid(transaction))
                throw new TransactionException("Invalid parent_id.");//Message can be moved to a properties file
        transaction.setTransaction_id(id);
        dataStore.addToStore(id, transaction);
        return new PutResponse("ok");
    }

    public List<Long> getTransactionsOfType(String transaction_type) {
        return dataStore.getTransactionsOfType(transaction_type);
    }

    public SumResponse getTransactionsSum(long transaction_id) throws TransactionException{
        if(dataStore.getTransaction(transaction_id) == null)
            throw new TransactionException("Given Transaction Id doesn't exist");//Message can be moved to a properties file

        Double sum = dataStore.getTransactionsSum(transaction_id);
        return new SumResponse(sum);
    }

    private boolean checkIfParentIdValid(Transaction transaction) {
        return transaction.getParent_id()==0 || dataStore.getTransaction(transaction.getParent_id()) != null ;
    }

    private boolean isParentIdPresent(Transaction transaction){
        return transaction.getParent_id() != 0;
    }
}
