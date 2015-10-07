package com.number26.controller;

import com.number26.TransactionApplication;
import com.number26.pojo.Response.PutResponse;
import com.number26.pojo.Response.SumResponse;
import com.number26.pojo.Transaction;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {TransactionApplication.class})
@IntegrationTest({"server.port=0"})
public class TransactionControllerIT {
    private int port = 8080;
    private static final String TRANSACTION_SERVICE = "transactionservice";

    private URL baseURL;
    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.baseURL = new URL("http://localhost:" + port + "/");
        template = new TestRestTemplate();
    }

    @Test
    public void testWelcomeScreen() throws Exception {
        ResponseEntity<String> response = template.getForEntity(baseURL.toString()+TRANSACTION_SERVICE+"/", String.class);
        Assert.assertThat(response.getBody(), IsEqual.equalTo("Welcome to Transaction Service."));
    }

    @Test
    public void testSaveTransaction(){
        Transaction transaction = new Transaction(new Double(5000), "cars");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transaction> entity = new HttpEntity(transaction, headers);
        ResponseEntity<PutResponse> response = template
                .exchange(baseURL.toString() + TRANSACTION_SERVICE + "/transaction/10", HttpMethod.PUT, entity, PutResponse.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody().getStatus(), "ok");

        Transaction newTransaction = new Transaction(new Double(5000), "shopping", 10);
        entity = new HttpEntity(newTransaction, headers);
        ResponseEntity<PutResponse> newResponse = template
                .exchange(baseURL.toString() + TRANSACTION_SERVICE + "/transaction/11", HttpMethod.PUT, entity, PutResponse.class);

        Assert.assertEquals(newResponse.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(newResponse.getBody().getStatus(), "ok");
    }

    @Test
    public void testGetTransactionsByType(){
        Transaction transaction = new Transaction(new Double(5000), "cars");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transaction> entity = new HttpEntity(transaction, headers);
        ResponseEntity<PutResponse> response = template
                .exchange(baseURL.toString() + TRANSACTION_SERVICE + "/transaction/10", HttpMethod.PUT, entity, PutResponse.class);
        ResponseEntity<List> transactions = template.getForEntity(baseURL.toString() + TRANSACTION_SERVICE + "/transaction/types/cars", List.class);
        Assert.assertTrue(transactions.getBody().size() > 0);
    }

    @Test
    public void testSumOfTransactions(){
        Transaction transaction = new Transaction(new Double(5000), "cars");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transaction> entity = new HttpEntity(transaction, headers);

        template.put(baseURL.toString() + TRANSACTION_SERVICE + "/transaction/10", entity);

        Transaction newTransaction = new Transaction(new Double(10000), "shopping", 10);
        entity = new HttpEntity(newTransaction, headers);
        template.put(baseURL.toString() + TRANSACTION_SERVICE + "/transaction/11", entity);

        ResponseEntity<SumResponse> response = template.getForEntity(baseURL.toString() + TRANSACTION_SERVICE + "/sum/10", SumResponse.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(new Double(15000), response.getBody().getSum());
        response = template.getForEntity(baseURL.toString() + TRANSACTION_SERVICE + "/sum/11", SumResponse.class);
        Assert.assertEquals(new Double(10000), response.getBody().getSum());

    }
}
