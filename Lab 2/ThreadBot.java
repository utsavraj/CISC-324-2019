
public class ThreadBot extends Thread
{

	int Identity;
	char T[] = {'a', 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h' , 'i' , 'j' , 'k' , 'l' , 'm' , 'n' , 'o' , 'p' , 'q' , 'r' , 's' , 't' , 'u' , 'v' , 'w' , 'x' , 'y' ,'z'}; 
	char init_char;
	int counter=0;
	
	public ThreadBot(int id, char c) 
	{
		Identity = id;
		init_char = c;
	}
	
	public void run()
	{
		
		
			String pass;
			
			for (int i = 0; i < 26; i++) 
			{
				pass = "" + init_char;
				
				pass = pass + T[i];

				String pass_2_chars = pass;
				
				for (int j = 0; j < 26; j++) 
				{
					pass = pass + T[j];
					
					String pass_3_chars = pass;

					for (int k = 0; k < 26; k++) 
					{
						pass = pass + T[k];
						
						String pass_4_chars = pass;
						
						for (int l = 0; l < 26; l++) 
						{
							pass = pass + T[l];
							
							System.out.println("Testing ["+ pass +"] by Thread(" + this.Identity +") ...");
							counter++;
							if ((int)(pass + ThreadAttacker.challenge).hashCode() == ThreadAttacker.captured) 
							{
								ThreadAttacker.found = true;
								
								System.out.println("Password found ["+ pass +"] by Thread(" + this.Identity +") after "+counter+ " tries");
											
							}
							//optimality
							if (ThreadAttacker.found==true) this.stop();
							pass = pass_4_chars;
						}
						
						pass = pass_3_chars;
						
					}
					
					pass = pass_2_chars;
				}
				
						
				pass = "" + init_char;;
			}
		
	}

}

