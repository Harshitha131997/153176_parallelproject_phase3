package com.capgemini.core.pwa.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
@Entity
@Table(name="Transactions_history")
public class TransactionDetails implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="transaction_id")
	private int id;
	@Column(name="MOBILE_NUMBER")
	String mobileNo;
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	@Column(name="transaction_type")
	String transactiontype;
	@Column(name="dateoftransaction")
	Date date;
	@Column(name="amount")
	BigDecimal amount;
	public TransactionDetails() {
		super();
	}
	public TransactionDetails(String mobileNo, String transactiontype, Date date, BigDecimal amount) {
		super();
		this.mobileNo = mobileNo;
		this.transactiontype = transactiontype;
		this.date = date;
		this.amount = amount;
	}
	public TransactionDetails(int id, String mobileNo, String transactiontype, Date date, BigDecimal amount) {
		super();
		this.id = id;
		this.mobileNo = mobileNo;
		this.transactiontype = transactiontype;
		this.date = date;
		this.amount = amount;
	}
	public String getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mobileNo == null) ? 0 : mobileNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionDetails other = (TransactionDetails) obj;
		if (mobileNo == null) {
			if (other.mobileNo != null)
				return false;
		} else if (!mobileNo.equals(other.mobileNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TransactionDetails [mobileNo=" + mobileNo + ", transactiontype=" + transactiontype + ", date=" + date
				+ ", amount=" + amount + "]";
	}
}
