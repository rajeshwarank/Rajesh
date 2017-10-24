package com.paymenttracker.app.writer;

import java.util.List;

import com.paymenttracker.domain.PaymentRecord;
import com.paymenttracker.persist.viewer.PaymentRepositoryViewer;


class ConsolePaymentRecordWriter implements PaymentRecordWriter {

	private PaymentRepositoryViewer repositoryObserver;

	public ConsolePaymentRecordWriter(PaymentRepositoryViewer observer) {
		this.repositoryObserver = observer;
	}

	@Override
	public void writeAllRecordsOut() {

		if (repositoryObserver == null) {
			return;
		}

		List<PaymentRecord> records = repositoryObserver.getAllPaymentRecords();
		for (PaymentRecord paymentRecord : records) {
			String recordString = paymentRecord.getCurrency().toUpperCase() + " " + paymentRecord.getAmount() + (paymentRecord.getUsdEquivalent()!=null?" (USD " + paymentRecord.getUsdEquivalent()+ ")":"");
			System.out.println(recordString);
		}

	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
					writeAllRecordsOut();
					Thread.sleep(60000);
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting application");
		}
	}

}
