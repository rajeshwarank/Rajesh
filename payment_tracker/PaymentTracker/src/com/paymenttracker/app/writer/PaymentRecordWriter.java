package com.paymenttracker.app.writer;

public interface PaymentRecordWriter extends Runnable{
	
	void writeAllRecordsOut();

}
