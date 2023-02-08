package hilos;

public class AnotherThread extends Thread{

	@Override
	public void run() {
		System.out.println("Hola desde " + this.getClass().getCanonicalName() + " - " + currentThread().getName());
		
		
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("otro hilo me desperto");
			return;
		}
		
		
		
		
		System.out.println("hilo despierto");
	}
	
}
