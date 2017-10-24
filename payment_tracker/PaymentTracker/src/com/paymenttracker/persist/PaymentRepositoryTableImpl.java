package com.paymenttracker.persist;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.paymenttracker.domain.PaymentRecord;


public class PaymentRepositoryTableImpl implements PaymentRepository{
	
	private final Map<String, PaymentRecord> paymentsTable = new Hashtable<>();

	@Override
	public synchronized void persist(PaymentRecord record) {
		if(paymentsTable.containsKey(record.getCurrency())){
			PaymentRecord recordPersisted = paymentsTable.get(record.getCurrency());
			recordPersisted.setAmount(recordPersisted.getAmount().add(record.getAmount()));
			paymentsTable.put(recordPersisted.getCurrency(), recordPersisted);
		}else{
			paymentsTable.put(record.getCurrency(), record);
		}
		
	}

	@Override
	public List<PaymentRecord> getAllRecords() {
		return new ArrayList<>(paymentsTable.values());
	}

}
