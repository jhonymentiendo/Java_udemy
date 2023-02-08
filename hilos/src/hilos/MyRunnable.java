package hilos;

public class MyRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println(ThreadColor.ANSI_RESET + "-------Hola desde " + this.getClass().getCanonicalName());
	}
	
}
