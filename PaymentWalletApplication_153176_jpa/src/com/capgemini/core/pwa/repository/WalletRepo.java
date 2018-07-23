package com.capgemini.core.pwa.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;

import com.capgemini.core.pwa.bean.Customer;
import com.capgemini.core.pwa.bean.TransactionDetails;

public interface WalletRepo {
public boolean save(Customer customer);
public Customer findOne(String mobileNo);
public boolean saveTransaction(TransactionDetails details);
public List<TransactionDetails> printTransactionHistory(String mobileNo);
public abstract void beginTransaction();
public abstract void commitTransaction();


}
