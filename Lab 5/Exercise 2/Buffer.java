package CISC;


class Buffer 
{
   private char [] buffer;
   private int count = 0, in = 0, out = 0;

   Buffer(int size)
   {
      buffer = new char[size];
   }
 
   public synchronized void Put(char c) 
  {
	   // BLOCK if the buffer is full
	   // So that Consumer can start removing those letters from the buffer
	   if (count == buffer.length) {
		   try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   

     System.out.println("Producing " + c + " ...");
     buffer[in] = c; 
     in = (in + 1) % buffer.length; 
     count++;
     notify();

   }
    
   public synchronized char Get() 
   {
	   
	 // BLOCK if empty (the number of letters (count) in the buffer is zero)
	 // So that Producer can add letters into the buffer
	 if (count == 0) {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
     System.out.println("Am inside Get\n");
     
     char c = buffer[out]; 
     out = (out + 1) % buffer.length;
     count--;
     
     System.out.println("Consuming " + c + " ...");
     notify();
     return c;
   }
}

