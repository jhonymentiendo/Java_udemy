public class Main {

	public static void main(String[] args) {
		Countdown cntd = new Countdown();
		
//		cntd.docountdown();
		
		CountdownThread t1 = new CountdownThread(cntd);
		t1.setName("thread 1");
		CountdownThread t2 = new CountdownThread(cntd);
		t2.setName("thread 2");
		
		t1.start();
		t2.start();

	}
}

class Countdown{
	
	private int i;
	
	public void docountdown(){
		String color;
		
		switch (Thread.currentThread().getName()) {
		case "thread 1":
			color = ThreadColor.ANSI_RS;
			break;
		case "thread 2":
			color = ThreadColor.ANSI_R;
			break;
		default:
			color = ThreadColor.ANSI_G;
			break;
		}
		
		synchronized (this) {
			for (i=10;i>0;i--) {
				System.out.println(color + Thread.currentThread().getName() + ": i =" + i);
				
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
		System.out.println(color + "termina" + Thread.currentThread().getName() + ": i =" + i);
		
	}
}


class CountdownThread extends Thread{
	private Countdown threadcountdown;
	
	public CountdownThread(Countdown countdown) {
		this.threadcountdown=countdown;
	}
	
	@Override
	public void run() {
		threadcountdown.docountdown();
	}
	
}