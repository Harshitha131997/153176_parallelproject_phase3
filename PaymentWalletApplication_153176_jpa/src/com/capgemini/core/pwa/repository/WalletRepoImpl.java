package com.capgemini.core.pwa.repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.capgemini.core.pwa.bean.Customer;
import com.capgemini.core.pwa.bean.TransactionDetails;
import com.capgemini.core.pwa.bean.Wallet;
import com.capgemini.core.pwa.exception.InvalidInputException;

public class WalletRepoImpl implements WalletRepo {

	private static EntityManagerFactory factory;
	private static EntityManager entityManager;
	 
	static{
		factory = Persistence.createEntityManagerFactory("JPA-PU");}

	public WalletRepoImpl() {
		if(entityManager==null || !entityManager.isOpen()) {
			entityManager = factory.createEntityManager();
		}
	}

	Customer cust = new Customer();
	
	@Override
	public boolean save(Customer customer) {
	   entityManager.persist(customer);
	   return true;
	}

	@Override
	public Customer findOne(String mobileNo) {
	
		Customer cust = entityManager.find(Customer.class, mobileNo);
         return cust;
	}
    
	@Override
	public boolean saveTransaction(TransactionDetails details) {
		boolean result=false;
		entityManager.persist(details);
	     result= entityManager.contains(details);					
		 return result;
	}

	@Override
	public List<TransactionDetails> printTransactionHistory(String mobileNo) {
	Query query= entityManager.createQuery("Select t from TransactionDetails t where t.mobileNo =:arg").setParameter("arg",mobileNo);
       return  query.getResultList();
	}
	
	@Override
	public void beginTransaction() {
		entityManager.getTransaction().begin();
	}

	@Override
	public void commitTransaction() {
		entityManager.getTransaction().commit();
	}
}

