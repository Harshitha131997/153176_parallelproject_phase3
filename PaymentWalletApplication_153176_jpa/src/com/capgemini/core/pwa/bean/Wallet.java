package com.capgemini.core.pwa.bean;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Wallet implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="wallet_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name ="balance")	
	private BigDecimal balance;
	public int getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Wallet other = (Wallet) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return ", balance="+balance;
	}
	public Wallet(BigDecimal balance) {
		this.balance = balance;
	}
	public Wallet() {
		super();
	}
}