package m1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static m1.Main.EOF;

public class Main {
	public static final String EOF = "EOF";

	public static void main(String[] args) {
		List<String> buffer = new ArrayList<String>();
		ReentrantLock bufferlock = new ReentrantLock();
		MyProducer mp = new MyProducer(buffer, ThreadColor.ANSI_BL, bufferlock);
		MyConsumer mc1 = new MyConsumer(buffer, ThreadColor.ANSI_R, bufferlock);
		MyConsumer mc2 = new MyConsumer(buffer, ThreadColor.ANSI_RS, bufferlock);

		new Thread(mp).start();
		new Thread(mc1).start();
		new Thread(mc2).start();

	}
}

class MyProducer implements Runnable {
	private List<String> buffer;
	private String color;
	private ReentrantLock bufferlock;

	public MyProducer(List<String> buffer, String color, ReentrantLock bufferlock) {
		this.buffer = buffer;
		this.color = color;
		this.bufferlock=bufferlock;
	}

	@Override
	public void run() {
		Random random = new Random();
		String mensajes[] = { "mensaje 1", "mensaje 2", "mensaje 3", "mensaje 4", "mensaje 5", "mensaje 6" };

		for (String msg : mensajes) {
			try {
				System.out.println(color + "agragando" + msg);
				bufferlock.lock();
				try {
					buffer.add(msg);	
				} finally {
					// TODO: handle finally clause
					bufferlock.unlock();
				}
				
				Thread.sleep(random.nextInt(1000));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println(color + "agregando EOF y saliendo...");
		bufferlock.lock();
		try {
			buffer.add(EOF);
		} finally {
			// TODO: handle finally clause
			bufferlock.unlock();
		}
		

	}
}

class MyConsumer implements Runnable {
	private List<String> buffer;
	private String color;
	private ReentrantLock bufferlock;

	public MyConsumer(List<String> buffer, String color, ReentrantLock bufferlock) {
		this.buffer = buffer;
		this.color = color;
		this.bufferlock=bufferlock;
	}

	@Override
	public void run() {
		
		int counter = 0;
		
		while (true) {
			//bufferlock.lock();
			if(bufferlock.tryLock()) {
				try {
					if (buffer.isEmpty()) {
						continue;	
					}
					System.out.println(color + "Contador = " + counter);
					counter=0;
					if (buffer.get(0).equals(EOF)) {
						System.out.println(color + "saliendo");
						break;
					} else {
						System.out.println(color + "removido" + buffer.remove(0));
					}
					} finally {
						bufferlock.unlock();
					}	
			}else {counter++;}
			
		}
	}
}
