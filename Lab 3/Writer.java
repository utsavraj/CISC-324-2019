// This file defines class "writer".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a 
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Writer extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.

  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Writer.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Writer(int name) 
  {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();   // Create and instance of RandomSleep.
  }  // end of the constructor for class "Writer"



  public void run () 
  {
    for (int I = 0;  I < 2; I++) 
    {

     //The writer starts by acquiring the mutex semaphore to get exclusive access to the shared counters AW, WW, and AR
      System.out.println("Writer " + myName + " wants to write");
      try
      {
      	Synch.mutex.acquire();
      }
      catch(Exception e)
      {
    	  
      }
      
//If there are readers which are currently reading or an active writer that is waiting (at least one), the writer inrements the number of waiting writers
      if (Synch.AR + Synch.AW >0) 
      {
    	  Synch.WW++;
      }
      else 
      {
//If no active readers or waiting writers are there, the writer releases the semaphore OktoWrite (produce one marble) and increments the number of active writers

    	  Synch.OktoWrite.release();
    	  Synch.AW++;
      }
      
//The writer releases the mutex since its has finished manipulating the shared counters AW, WW, and AR.

      Synch.mutex.release();
 
//Now the writer tries to grab a marble, if there is one left, its takes it and start writing, if noting is left (OkToWrite==0) it gets blocked in the OktoWrite queue.
     
      try
      {
      	Synch.OktoWrite.acquire();
      }
      catch(Exception e)
      {
    	  
      }
      

//If the writer did not get blocked or if it got blocked then released, its starts writing

      // Simulate the time taken by writing.
      System.out.println("Writer " + myName + " is now writing");
      rSleep.doSleep(1, 200);

    

//Once a writer finishes, it tests FIRST whether writers are waiting, in this case, it releases one of them (the one in the head of the OktoWrite queue), else if there are only readers, it releases them all 

//Since the writer has to perform some tests on the shared counters AR and WW, it has to have an exclusive access to thos counter, its uses the mutext semaphore
     
      try
      {
      	Synch.mutex.acquire();
      }
      catch(Exception e)
      {
    	  
      }

//It decrements the value of active writers (since it is leaving)
      Synch.AW--;
 
//Tests whether waiting writer are there     
      if (Synch.WW>0) 
      {
//if yes, release one writer, increments the number of active writers and decrements the number of waiting writers
    	  Synch.OktoWrite.release();
    	  Synch.AW++;
    	  Synch.WW--;
      }
//else if no writer is waiting, and readers instead are waiting, the writer releases them all
      else if (Synch.WR>0)
      {
    	 
    	 do {
    		  
    		  	Synch.OktoRead.release();
    		  	Synch.AR++;
    		  	Synch.WR--;
    		  	
    	  } while(Synch.WR>0);
 
      }
 
//The writer releases the mutex since its has finished manipulating the shared counters AW, WW, and AR.
     Synch.mutex.release();
      

      // Simulate "doing something else"
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Writer"
