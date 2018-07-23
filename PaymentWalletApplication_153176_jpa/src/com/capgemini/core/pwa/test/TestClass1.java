package com.capgemini.core.pwa.test;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.capgemini.core.pwa.bean.Customer;
import com.capgemini.core.pwa.bean.Wallet;
import com.capgemini.core.pwa.exception.InsufficientBalanceException;
import com.capgemini.core.pwa.exception.InvalidInputException;
import com.capgemini.core.pwa.service.WalletService;
import com.capgemini.core.pwa.service.WalletServiceImpl;
public class TestClass1 {
	WalletService service;
	
@Before
	public void initData(){
service = new WalletServiceImpl();	
	}
@Test
public void testCreateAccount() throws InvalidInputException {
	Customer customer = new Customer();
	Wallet wallet = new Wallet();
	String name = "Amit";
	String number = "9900112212";
	BigDecimal amount = new BigDecimal(9000);
	customer.setName(name);
	customer.setMobileNo(number);
	wallet.setBalance(amount);
	customer.setWallet(wallet);
	customer = service.createAccount(name, number, amount);
	assertNotEquals(null, customer);
}
@Test
public void testCreateAccounts() throws InvalidInputException {
		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		String name = "Ajay";
		String number = "9963242422";
		BigDecimal amount = new BigDecimal(6000);
		customer.setName(name);
		customer.setMobileNo(number);
		wallet.setBalance(amount);
		customer.setWallet(wallet);
		customer = service.createAccount(name, number, amount);
		assertNotEquals(null, customer);
	}
@Test(expected=InvalidInputException.class)
public void testCreateAccount1() {
	Customer cust = service.createAccount(null,null,null);	
}
@Test(expected=InvalidInputException.class)
public void testCreateAccount2()
{
	Customer  cust = service.createAccount(" ","9866823975",new BigDecimal(5000));
}
@Test(expected=InvalidInputException.class)
public void testCreateAccount3()
{
	Customer  cust = service.createAccount("raghu","$6859",new BigDecimal(5000));
}
@Test(expected=InvalidInputException.class)
public void testCreateAccount4()
{
	Customer  cust1 = service.createAccount("raghu","9866823975",new BigDecimal(-5));
}
@Test(expected=InvalidInputException.class)
public void testCreateAccount5()
{
	Customer  cust = service.createAccount("raghu"," ",new BigDecimal(5000));
}
@Test(expected=InvalidInputException.class)
public void testCreateAccount6()
{
	Customer  cust = service.createAccount(" ","9866823975",new BigDecimal(5000));
}
@Test
public void testCreateAccount7()
{
	Customer  cust1 = service.createAccount("raghu","9866823975",new BigDecimal(5000));
	Customer  cust2 = new Customer();
	cust2.setName("raghu");
	cust2.setMobileNo("9866823975");
	cust2.setWallet(new Wallet(new BigDecimal(5000)));
	assertEquals(cust2,cust1);
}
@Test(expected=InvalidInputException.class)
public void testBalance8()
{
	service.showBalance("9900112");
}
@Test
public void testBalance9()
{
	Customer cust1=new Customer("Ajay","9963242422",new Wallet(new BigDecimal(600)));
	Customer customer = service.showBalance("9963242422");
	assertNotEquals(0,customer.getWallet().getBalance());

}
@Test
public void testDepositAccount10()
{
	Customer customer = service.depositAmount("9900112212", new BigDecimal( 200));
	assertNotEquals(0,customer.getWallet().getBalance());

}

@Test(expected=InvalidInputException.class)
public void testDepositAccount11()
{
	Customer customer = service.depositAmount("99001122",new BigDecimal(3000));

}

@Test(expected=InvalidInputException.class)
public void testDepositAccount12()
{		
Customer customer = service.depositAmount("9900112212", new BigDecimal(-3));

}

@Test
public void testDepositAccount13()
{
	Customer cust1=new Customer("Amit","9900112212",new Wallet(new BigDecimal(12200)));
	Customer cust2=new Customer();
	Customer customer = service.depositAmount("9900112212", new BigDecimal( 200));
	cust2.setMobileNo("9900112212");
	cust2.setName("Amit");
	cust2.setWallet(new Wallet(new BigDecimal(12400)));
	assertEquals(cust2,cust1);

}

@Test(expected=InvalidInputException.class)
public void testWithdraw14()
{
	service.withdrawAmount("900000000", new BigDecimal(2000));
}

@Test(expected=InvalidInputException.class)
public void testWithdraw15()
{
	service.withdrawAmount("9963242422", new BigDecimal(-2000));
}

@Test(expected=InsufficientBalanceException.class)
public void testWithdraw16()
{
	service.withdrawAmount("9963242422", new BigDecimal(100000));
}

@Test
public void testWithdraw17() {

	Customer cust1=service.withdrawAmount("9963242422", new BigDecimal(1000));
	BigDecimal actual=cust1.getWallet().getBalance();
	assertNotEquals(0, actual);
}
@Test(expected=InvalidInputException.class)
public void testFundTransfer18() {
	service.fundTransfer(null, null,new BigDecimal(7000));
}
@Test(expected=InvalidInputException.class)
public void testFundTransfer19() {
	service.fundTransfer(null,"9963242422" ,new BigDecimal(7000));
}
@Test(expected=InvalidInputException.class)
public void testFundTransfer20() {
	service.fundTransfer("9963242422",null,new BigDecimal(1000));
}
@Test(expected=InvalidInputException.class)
public void testFundTransfer21() {
	service.fundTransfer("9963242422","9900112212",new BigDecimal(-8000));
}
@Test
public void testFundTransfer22() {
	Customer cust1=service.fundTransfer("9900112212","9963242422",new BigDecimal(2000));
	Customer cust2 = new Customer();
	cust2.setMobileNo("9900112212");
	cust2.setName("Amit");
	cust2.setWallet(new Wallet(new BigDecimal(8400)));
	assertEquals(cust2,cust1);
}
@Test
public void testisValid23()
{
	Customer cust = new Customer("radhika", "9966823975",new Wallet(new BigDecimal(6000)));
	boolean result = service.isValid(cust);
	assertEquals(true,result);
}
@Test(expected=InvalidInputException.class)
public void testisValid24()
{
	Customer cust = new Customer("radhika", "_99668239",new Wallet(new BigDecimal(6000)));
	service.isValid(cust);

}
@Test(expected=InvalidInputException.class)
public void testisValid25()
{
	Customer cust = new Customer("radhika", "9966823975",new Wallet(new BigDecimal(-8)));
	service.isValid(cust);
}
@Test(expected=InvalidInputException.class)
public void testisValid26()
{
	Customer cust = new Customer(" ","9966823975",new Wallet(new BigDecimal(600)));
	service.isValid(cust);

}
@Test(expected=InvalidInputException.class)
public void testisValid27()
{
	Customer cust = new Customer("radhika", " ",new Wallet(new BigDecimal(600)));
	service.isValid(cust);

}
@Test
public void testisValid28()
{
	Customer cust = new Customer("radhika", "9866823975",new Wallet(new BigDecimal(0)));
	boolean result = service.isValid(cust);
	assertEquals(true,result);

}
@Test(expected=InvalidInputException.class)
public void testTransactionHistory29()
{
	service.transactionHistory("65756");
}
@Test(expected=InvalidInputException.class)
public void testTransactionHistory30()
{
	service.transactionHistory(null);
}
@Test(expected=InvalidInputException.class)
public void testTransactionHistory31()
{
	service.transactionHistory(" ");
}
}

