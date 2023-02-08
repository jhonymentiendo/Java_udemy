package m2;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		Message msg = new Message();
		(new Thread(new Writer(msg))).start();
		(new Thread(new Reader(msg))).start();

	}
}

class Message {
	private String message;
	private boolean empty = true;

	public synchronized String read() {
		while (empty) {
			try {
				wait();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		empty = true;
		notifyAll();
		return message;
	}

	public synchronized void write(String msg) {
		while (!empty) {
			try {
				wait();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		empty = false;
		message = msg;
		notifyAll();
	}
}

class Writer implements Runnable{
	private Message message;
	public Writer(Message message) {
		this.message = message;
	}
	@Override
	public void run() {
		String mensajes[]= {"mensaje 1",
				"mensaje 2",
				"mensaje 3",
				"mensaje 4",
				"mensaje 5",
				"mensaje 6"};
		Random random = new Random();
		for(int i=0; i<mensajes.length; i++) {
			message.write(mensajes[i]);
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}	
		}
		System.out.println("termina");
	}	
}

class Reader implements Runnable{
	private Message message;
	public Reader(Message message) {
		this.message = message;
	}
	
	public void run() {
		Random random = new Random();
		for(String latestMessage = message.read();
				!latestMessage.equals("termina");
				latestMessage = message.read()) {
			System.out.println(latestMessage);
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}	
		}	
	}
}