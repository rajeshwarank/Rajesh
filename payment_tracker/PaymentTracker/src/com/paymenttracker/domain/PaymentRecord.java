package com.paymenttracker.domain;

import java.math.BigDecimal;

public class PaymentRecord {

	private String currency;
	private BigDecimal amount;
	private BigDecimal usdRate;

	public PaymentRecord(String currency, BigDecimal amount){
		this.currency= currency;
		this.amount = amount;
	}
	
	public PaymentRecord(String currency, BigDecimal amount, BigDecimal usdRate){
		this(currency, amount);
		this.usdRate = usdRate;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getUsdRate() {
		return usdRate;
	}

	public void setUsdRate(BigDecimal usdRate) {
		this.usdRate = usdRate;
	}
	
	public BigDecimal getUsdEquivalent(){
		if(getUsdRate()!=null){
			BigDecimal result = getAmount().multiply(getUsdRate());
			return result;
		}
		return null;
	}

	@Override
	public String toString() {
		return "PaymentRecord [currency=" + currency + ", amount=" + amount
				+ ", usdRate=" + usdRate + "]";
	}

}
