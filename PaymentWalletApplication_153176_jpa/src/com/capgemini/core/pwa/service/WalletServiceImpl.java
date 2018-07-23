package com.capgemini.core.pwa.service;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import com.capgemini.core.pwa.bean.Customer;
import com.capgemini.core.pwa.bean.TransactionDetails;
import com.capgemini.core.pwa.bean.Wallet;
import com.capgemini.core.pwa.exception.InsufficientBalanceException;
import com.capgemini.core.pwa.exception.InvalidInputException;
import com.capgemini.core.pwa.repository.WalletRepo;
import com.capgemini.core.pwa.repository.WalletRepoImpl;
public class WalletServiceImpl implements WalletService{
 List list;
	WalletRepo walletrepo;
	public WalletServiceImpl() {		
		walletrepo= new WalletRepoImpl();
	}
	TransactionDetails details = new TransactionDetails();
@Override
	public Customer createAccount(String name, String mobileno, BigDecimal amount) { 
		Customer customer = new Customer();
         customer.setName(name);
		customer.setMobileNo(mobileno);
		Wallet wallet = new Wallet();
		wallet.setBalance(amount);
		customer.setWallet(wallet);
		if(isValid(customer)) {			
		walletrepo.beginTransaction();
		 boolean res =walletrepo.save(customer);
		 walletrepo.commitTransaction(); 
		}
		//-----------------  -Transaction History Storing--------------------
		long millis=System.currentTimeMillis();
		Date date=new Date(millis);
	details.setMobileNo(mobileno);
		details.setTransactiontype("Creation");
		details.setDate(date);
		details.setAmount(amount);
		walletrepo.beginTransaction();
		walletrepo.saveTransaction(details);
		 walletrepo.commitTransaction();
		return customer;
	}
	@Override
	public Customer showBalance(String mobileno) {
		if(isMobileNumberInvalid(mobileno)==true)
				throw new InvalidInputException("invalid mobile number");
		Customer customer = new Customer();
		long millis=System.currentTimeMillis();
		Date date=new Date(millis);
		walletrepo.beginTransaction();
		customer = walletrepo.findOne(mobileno);
		 walletrepo.commitTransaction();
			BigDecimal bal=customer.getWallet().getBalance();
			details.setMobileNo(mobileno);
			details.setTransactiontype("Balance check");
			details.setDate(date);
		details.setAmount(bal);
		walletrepo.beginTransaction();
		walletrepo.saveTransaction(details);
		 walletrepo.commitTransaction();
	return customer;
	
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {
		// TODO Auto-generated method stub
		
Customer customer1;
Customer customer2;
long millis=System.currentTimeMillis();
Date date=new Date(millis);
walletrepo.beginTransaction();
		customer1 = withdrawAmount(sourceMobileNo ,amount);
		 walletrepo.commitTransaction();

		walletrepo.beginTransaction();
		customer2= depositAmount(targetMobileNo ,amount);
		 walletrepo.commitTransaction();
details.setMobileNo(sourceMobileNo);
details.setTransactiontype("Withdraw-Fund Transfer");
details.setDate(date);
details.setAmount(amount);
walletrepo.beginTransaction();
walletrepo.saveTransaction(details);
walletrepo.commitTransaction();
TransactionDetails detail = new TransactionDetails();
detail.setMobileNo(targetMobileNo);
detail.setTransactiontype("Deposit-Fund Transfer");
detail.setDate(date);
detail.setAmount(amount);
return customer1;
}
@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		if(isMobileNumberInvalid(mobileNo)==true)
			
			throw new InvalidInputException("invalid mobile number");
		if(isBalanceInvalid(amount)==true)
			throw new InvalidInputException("amount is not valid");
	Customer customer1;
		customer1 = walletrepo.findOne(mobileNo);
BigDecimal bal = customer1.getWallet().getBalance().add(amount);
customer1.getWallet().setBalance(bal);
customer1.setWallet(customer1.getWallet());
long millis=System.currentTimeMillis();
Date date=new Date(millis);
TransactionDetails detail = new TransactionDetails();
detail.setMobileNo(mobileNo);
detail.setTransactiontype("Deposit");
detail.setDate(date);
detail.setAmount(amount);
walletrepo.beginTransaction();
walletrepo.saveTransaction(detail);
walletrepo.commitTransaction();
walletrepo.beginTransaction();
walletrepo.save(customer1);
walletrepo.commitTransaction();
		return customer1;	
	}
	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
	if(isMobileNumberInvalid(mobileNo)==true)
			throw new InvalidInputException("invalid mobile number");
		if(isBalanceInvalid(amount))
			throw new InvalidInputException("amount is not valid");
		Customer customer1;
		Wallet wallet= new Wallet();
		walletrepo.beginTransaction();
		customer1 = walletrepo.findOne(mobileNo);
		 walletrepo.commitTransaction();
		if(customer1.getWallet().getBalance().compareTo(amount)>0){
BigDecimal bal = customer1.getWallet().getBalance().subtract(amount);
wallet.setBalance(bal);
customer1.setWallet(wallet);
long millis=System.currentTimeMillis();
Date date=new Date(millis);
TransactionDetails detail = new TransactionDetails();
detail.setMobileNo(mobileNo);
detail.setTransactiontype("Withdraw");
detail.setDate(date);
detail.setAmount(amount);
walletrepo.beginTransaction();
walletrepo.saveTransaction(detail);
walletrepo.commitTransaction();
walletrepo.beginTransaction();
walletrepo.save(customer1);
walletrepo.commitTransaction();
} 
else
	throw new InsufficientBalanceException("balance is not adequate");
return customer1;	
	}
	@Override
	public boolean isValid(Customer customer) {
	if( customer.getName() == null ||  customer.getName().trim().isEmpty() )
	throw new InvalidInputException( "Name Cannot be Empty" );
if( customer.getMobileNo() == null ||  isMobileNumberInvalid( customer.getMobileNo() ) || customer.getMobileNo().trim().isEmpty())
		throw new InvalidInputException( "Mobile Number is invalid" );
		if(customer.getWallet() == null || isBalanceInvalid(customer.getWallet().getBalance()))
	throw new InvalidInputException("Balance is invalid ");
	else
   return true;
	}
 public static boolean isMobileNumberInvalid( String phoneNumber ){
		if(String.valueOf(phoneNumber).matches("^[7-9]{1}[0-9]{9}$"))
			return false;
	else 
return true;
	}
	public static boolean isBalanceInvalid(BigDecimal balance)
	{	
		if(balance.compareTo(new BigDecimal(0))<0)
			return true;

		else
			return false;			
	}
@Override
	public List<TransactionDetails> transactionHistory(String mobileNo)
		{
		if(isMobileNumberInvalid(mobileNo)==true)
		  throw new InvalidInputException("invalid mobile number");
		walletrepo.beginTransaction();
		list=walletrepo.printTransactionHistory(mobileNo);
		 walletrepo.commitTransaction();
        return list;
}  
}



		
		
		

