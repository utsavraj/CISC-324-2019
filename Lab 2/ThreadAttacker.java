
public class ThreadAttacker {

	public static String password = "virus";
	public static String challenge = "challenge_sequence";
	public static int captured;
	
	public static boolean found = false;
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		String x = password + challenge;
		captured = x.hashCode();
	
		
		

		ThreadBot t_1 = new ThreadBot(1,'k');
		ThreadBot t_2 = new ThreadBot(2, 't');
		ThreadBot t_3 = new ThreadBot(3, 'v');
		
		//Priority testing
		/*
		t_1.setPriority(9);
		t_2.setPriority(9);
		t_3.setPriority(1);
	*/
		
		t_1.start();
		t_2.start();
		t_3.start();
		
		
		
	}

}

