package CISC;

class Producer extends Thread 
{
   private Buffer buffer;

   Producer(Buffer b) 
   {
        buffer = b; 
   }

   public void run() 
   {
     for(int i = 0; i < 10; i++) 
     {
        buffer.Put((char)('A'+ i%26 )); 
     }
   }
}    


