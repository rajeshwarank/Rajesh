package com.paymenttracker.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.paymenttracker.app.writer.PaymentRecordWriter;
import com.paymenttracker.app.writer.WriterFactory;
import com.paymenttracker.persist.PaymentRepository;
import com.paymenttracker.persist.PaymentRepositoryTableImpl;
import com.paymenttracker.persist.loader.RecordParser;
import com.paymenttracker.persist.viewer.PaymentRepositoryViewerImpl;

public class App {

	private static PaymentRepository repository;
	private static Thread writerThread;
	
	private static final String DEFAULT_FILE = "records"; 
	private static final String QUIT = "quit";
	

	public static void main(String[] args) {
		initRepository();
		execute();
	}

	private static void initRepository() {
		repository = new PaymentRepositoryTableImpl();
	}

	private static void initWriter() {
		PaymentRecordWriter writer = WriterFactory.getWriter(WriterFactory.CONSOLE_TYPE, new PaymentRepositoryViewerImpl(repository));
		writerThread = new Thread(writer);
		writerThread.start();
	}

	private static void execute() {
		
		try (BufferedReader consoleBufferReader = new BufferedReader(new InputStreamReader(System.in))){
			System.out.println("Enter the path to initial payment records file (if none, the default file will be used): ");
			
			RecordParser parser = new RecordParser(repository);
			String line  = consoleBufferReader.readLine();
			
			if(line.equalsIgnoreCase(QUIT)){
				return;
			}
			loadAndParseFile(line, parser);
			initWriter();
			
			System.out.println("Enter new payment record or type 'QUIT' for exit.");
			while(!(line = consoleBufferReader.readLine()).equalsIgnoreCase("quit")){
					parser.parseRecordAndPersist(line);	
			}
			
		} catch (IOException e) {
			System.out.println("Error while parsing user input.");
			e.printStackTrace();
		}finally{
			if(writerThread != null){
				writerThread.interrupt();
			}
		}
		System.out.println("Finished.");
	}
	
	private static void loadAndParseFile(String fileName, RecordParser parser){
		if(fileName == null || fileName.length()==0){
			// no file path provided by user
			fileName = DEFAULT_FILE;
		}
		try(FileReader reader = new FileReader(fileName)){
			parser.parseAndStore(reader);
		} catch (IOException e) {
			System.err.println("File reading error.");
		}
	
	}

}
