package com.paymenttracker.persist;

import java.util.List;

import com.paymenttracker.domain.PaymentRecord;


public interface PaymentRepository {
	
	void persist(PaymentRecord record);
	
	List<PaymentRecord> getAllRecords();
}
