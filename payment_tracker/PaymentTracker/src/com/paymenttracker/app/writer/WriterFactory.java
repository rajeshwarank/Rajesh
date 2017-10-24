package com.paymenttracker.app.writer;

import com.paymenttracker.persist.viewer.PaymentRepositoryViewer;


public class WriterFactory {

	public static final String CONSOLE_TYPE="CONSOLE";
	
	public static PaymentRecordWriter getWriter(String type, PaymentRepositoryViewer viewer){
		switch (type) {
		case CONSOLE_TYPE:
			return new ConsolePaymentRecordWriter(viewer);
		// case of other implementations of payment records "writers"
		default:
			return new ConsolePaymentRecordWriter(viewer);
		}
	}
	
}
