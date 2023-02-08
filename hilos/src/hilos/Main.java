package hilos;

public class Main {

	public static void main(String[] args) {

		
		System.out.println("main");

		AnotherThread a = new AnotherThread();
		a.setName("=====AnotherThread=====");
		a.start();

		new Thread() {
			public void run() {
				System.out.println("hola desde el hilo principal desde una clase anonima");
			};
		}.start();
		
		Thread myrt = new Thread(new MyRunnable() {
			@Override
			public void run() {
				System.out.println("********Hola desde " + this.getClass().getCanonicalName());
				
				try {
					a.join(2000);
					System.out.println("******** otro hilo termino o se acabó el tiempo y myrt corre otravez");
				} catch (Exception e) {
					System.out.println("******** no pude esperar porque fui interrumpido");
				}
				
				
				
			}
		});
		myrt.start();
//		a.interrupt();
		
		
		
		
		System.out.println("otra vez main");
		System.out.println("2otra vez main");
		System.out.println("3otra vez main");
		System.out.println("4otra vez main");
		System.out.println("5otra vez main");
		System.out.println("6otra vez main");
		
	}

}
