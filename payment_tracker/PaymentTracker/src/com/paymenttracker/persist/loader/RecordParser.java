package com.paymenttracker.persist.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import com.paymenttracker.domain.PaymentRecord;
import com.paymenttracker.persist.PaymentRepository;

public class RecordParser {

	private PaymentRepository repository;

	public RecordParser(PaymentRepository repository) {
		this.repository = repository;
	}

	public void parseAndStore(FileReader fileReader) {

		BufferedReader bufferReader = new BufferedReader(fileReader);
		int lineNumber = 0;
		try {
			for (String line; (line = bufferReader.readLine()) != null;) {
				parseRecordAndPersist(line);
				lineNumber++;
			}
		} catch (IOException e) {
			System.out.println("Invalid input on line " + lineNumber + 1);
			e.printStackTrace();
		}

	}

	public void parseRecordAndPersist(String line){

		String[] parts = line.split(" ");
		if (parts.length < 2) {
			System.err.println("ERROR parsing line");
			return;
		} else {
			String currency = parts[0];
			if (currency.length() != 3 && !currency.matches("\\w+")) {
				System.err.println("ERROR parsing line");
				return;
			}

			if (!parts[1].matches("-?\\d+")) {
				System.err.println("ERROR parsing line");
				return;
			}
			BigDecimal amount = new BigDecimal(parts[1]);
			BigDecimal usdRate = null;
			if (parts.length == 3 && parts[2] != null
					&& parts[2].matches("\\(\\d+\\)")) {
				String usdRateStr = parts[2].replaceAll("\\(|\\)", "");
				usdRate = new BigDecimal(usdRateStr);
			}
			repository.persist(new PaymentRecord(currency, amount, usdRate));
		}
	}

}
