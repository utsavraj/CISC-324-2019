// This file defines class "Reader".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a 
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables 
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Reader extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.

  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Reader.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Reader(int name) 
  {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();  // Create an instance of RandomSleep.
  }  // end of the constructor for class "Reader"



  public void run () 
  {
    for (int I = 0;  I < 2; I++) 
    {
     //System.out.println("Reader " + myName + " wants to read");

 //The reader starts by acquiring the mutex semaphore to get exclusive access to the shared counters AW, WW, and AR
      try
      {
      	Synch.mutex.acquire();
      }
      catch(Exception e)
      {
    	  
      }
       
// If a writer is active or a writer is waiting, the reader increments the number of waiting readers
          
      if (Synch.AW + Synch.WW>0)
      {
    	  Synch.WR++;
      }
      else 
      {
//If no active or waiting writers are there, the reader releases the semaphore OktoRead (produce one marble) and increments the number of active readers
    	  Synch.OktoRead.release();
    	  Synch.AR++;
      }
//The reader releases the mutex since its has finished manipulating the shared counters AW, WW, and AR.
      Synch.mutex.release();

//Now the reader tries to grab a marble, if there is one left, its takes it and start reading, if noting is left (OkToRead==0) it gets blocked in the OktoRead queue.
	  try
	  {
		  Synch.OktoRead.acquire(); 
	  }
	  catch(Exception e)
	  {
		  
	  }
	  
//If the reader did not get blocked or if it got blocked then released, its starts reading 
	  System.out.println("Reader " + myName + " is reading");
	  rSleep.doSleep(1, 200);   


//Once a reader finishes, it tests whether it is the last reader to release the writers (because they have priority)
 

//Since the reader has to perform some tests on the shared counters AR and WW, it has to have an exclusive access to thos counter, its uses the mutext semaphore
     try
      {
      		Synch.mutex.acquire();
      }
      catch(Exception e)
      {
    	  
      }
      

//A reader is leaving, decrements the number of active readers
      Synch.AR--;



      System.out.println("Reader " + myName + " is finished reading.  ");

//The reader test whether it is the last active reader and that there is at least one writer waiting     
      if (Synch.AR==0 && Synch.WW>0) 
      {
//if it is the case, it release the semaphore OkToRead ==> one writer is released from that queue
    	  Synch.OktoWrite.release();
//Increments the number of active writers and decrements the number of waiting writers 
          Synch.AW++;
          Synch.WW++;
      }

//The reader releases the mutex since its has finished manipulating the shared counters AW, WW, and AR.
       Synch.mutex.release();

// Simulate "doing something else".
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Reader"
