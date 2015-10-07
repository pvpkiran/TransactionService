package com.number26.service;

import com.number26.dao.InMemoryStore;
import com.number26.exceptions.TransactionException;
import com.number26.pojo.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {TransactionService.class, InMemoryStore.class})
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    @Test
    public void testSaveTransactionWithoutParentId() throws TransactionException {
        Transaction transaction = new Transaction(new Double(5000), "cars");
        transactionService.saveTransaction(10, transaction);

        List<Long> storedTransactions = transactionService.getTransactionsOfType("cars");
        assertTrue(!storedTransactions.isEmpty());
    }

    @Test
    public void testSaveTransactionWithParentId() throws TransactionException {
        Transaction transaction = new Transaction(new Double(5000), "cars");
        transactionService.saveTransaction(10, transaction);

        Transaction newTransaction = new Transaction(new Double(10000), "shopping", 10);
        transactionService.saveTransaction(11, newTransaction);

        List<Long> storedTransactionsOfTypeCars = transactionService.getTransactionsOfType("cars");
        assertTrue(!storedTransactionsOfTypeCars.isEmpty());
        assertTrue(storedTransactionsOfTypeCars.size() == 1);

        List<Long> storedTransactionsOfTypeShopping = transactionService.getTransactionsOfType("shopping");//Test for get transactions by type
        assertTrue(!storedTransactionsOfTypeShopping.isEmpty());
        assertTrue(storedTransactionsOfTypeShopping.size() == 1);
    }

    @Test
    public void testGetSumofTransactions() throws TransactionException {
        Transaction transaction = new Transaction(new Double(5000), "cars");
        transactionService.saveTransaction(10, transaction);

        Transaction newTransaction = new Transaction(new Double(10000), "shopping", 10);
        transactionService.saveTransaction(11, newTransaction);
        assertEquals(new Double(15000), transactionService.getTransactionsSum(10).getSum());
        assertEquals(new Double(10000), transactionService.getTransactionsSum(11).getSum());
    }

    @Test(expected = TransactionException.class)
    public void testSaveForInvalidParentId() throws TransactionException {
        Transaction transaction = new Transaction(new Double(10000), "creditcard", 101);
        transactionService.saveTransaction(12, transaction);
    }

    @Test(expected = TransactionException.class)
    public void testSumForInvalidTransactionId() throws TransactionException {
        transactionService.getTransactionsSum(1234);
    }
}
