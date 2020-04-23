package CISC;

class Consumer extends Thread 
{
   private Buffer buffer;
   
   Consumer(Buffer b) 
   { 
        buffer = b; 
   }

   public void run() 
   {
     for(int i = 0; i < 10; i++) 
     {
        buffer.Get(); 
     }
   }
}
