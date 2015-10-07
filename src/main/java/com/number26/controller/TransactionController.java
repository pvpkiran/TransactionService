package com.number26.controller;

import com.number26.exceptions.TransactionException;
import com.number26.pojo.Response.PutResponse;
import com.number26.pojo.Response.SumResponse;
import com.number26.pojo.Transaction;
import com.number26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("transactionservice")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping("/")
    public String welcomeScreen() {
        return "Welcome to Transaction Service.";
    }

    @RequestMapping(value = "/transaction/{transaction_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public PutResponse saveTransaction(@PathVariable long transaction_id, @Valid @RequestBody Transaction transaction) throws TransactionException {
        return transactionService.saveTransaction(transaction_id, transaction);
    }

    @RequestMapping(value = "/transaction/types/{transaction_type}", method = RequestMethod.GET)
    public List<Long> getTransactionsOfType(@PathVariable String transaction_type){
        return transactionService.getTransactionsOfType(transaction_type);
    }

    @RequestMapping(value = "/sum/{transaction_id}", method = RequestMethod.GET)
    public SumResponse getTransactionsSum(@PathVariable long transaction_id) throws TransactionException {
        return transactionService.getTransactionsSum(transaction_id);
    }

    @ExceptionHandler(TransactionException.class)
    public ModelAndView handleException(HttpServletRequest req, TransactionException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}
