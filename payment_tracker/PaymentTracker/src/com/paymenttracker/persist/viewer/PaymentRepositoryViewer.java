package com.paymenttracker.persist.viewer;

import java.util.List;

import com.paymenttracker.domain.PaymentRecord;


public interface PaymentRepositoryViewer {

	List<PaymentRecord> getAllPaymentRecords();
	
}
