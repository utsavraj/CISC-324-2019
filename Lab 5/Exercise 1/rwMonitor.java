// This file defines class "rwMonitor", a monitor for the reader/writer
// problem.  It defines methods startRead, endRead, startWrite, endWrite.
//
// The synchronization (readers have priority) is the same as was used
// in lab 3.  Where lab 3 used semaphores, a monitor is used here.
//
// startRead, endRead, startWrite, and endWrite are declared to be 
// "synchronized" methods.  The "synchronized" keyword tells the Java 
// compiler to provide mutually exclusive access.  At any given time, at most
// one thread can be executing any of the code inside these synchronized
// methods.  For example, if thread R1 is executing startRead, then any
// other threads that call startRead, endRead, startWrite, or endWrite
// have to wait.  The compiler generates code to do this, both to make the
// calling threads wait, and to arrange that when R1 finishes execution of
// StartRead, one of the waiting threads is allowed to continue executing.
//package CISC;

public class rwMonitor {
	  private int readcount;   // A count of the number of readers who are
	                           // currently reading.
	  private int writecount;  // A count of the number of writers who are
	                           // currently writing (0 or 1).

	  
	  // ------------    NEW STUFF ---------- //
	  // Realize that starvation of writers by readers is avoided by yielding to earlier requests.
	  
	  private int waitingreadcount; // A count of readers waiting
	  private int waitingwritecount; // A count of writers waiting
	  
	  private boolean WriteOK; //Tells u when it is OK to write
	  
	  private long startWaitingReadersTime; //Time for which the reader has been waiting
	  
	  //Gets the start 
	  private static final long startTime = System.currentTimeMillis();
	  
	  //Calculate the current the time from the start time
	   protected static final long age() {
		      return System.currentTimeMillis() - startTime;
		   }

	  // This is the constructor for class rwMonitor.
	  public rwMonitor() {
	    readcount = 0;
	    writecount = 0;
	    waitingreadcount = 0;
	    waitingwritecount = 0;
	    WriteOK = true; //It is OK to write because A writer may appear in the start
	    startWaitingReadersTime = 0;
	  }  // end of the constructor for class "rwMonitor"


	  //               ---- method startRead ----
	  public synchronized void startRead () {
		long readerArrivalTime = 0;
		
		//Using if statement saves cycles.
		// We look for additinal idea of waiting writers
	    if (writecount > 0 || waitingwritecount > 0 ) {
	    	//Number of readers waiting increases as it has to wait
	    	waitingreadcount++;
	    	//Time it came to the system.
	    	readerArrivalTime = age();
	    	
	    	// If its age is greater than waiting time for the reader
	    	while(readerArrivalTime >= startWaitingReadersTime)
	    		try {  wait(); } catch (Exception e) {};  // wait for notify()
	    	waitingreadcount--; //if it catches any notify, in active.
	    }  // end of the while loop
	    readcount++;
	  }  // end of "startRead" method

	 

	  //               ---- method endRead ----
	  public synchronized void endRead () {
	    readcount--;
	    
	    //Active writer - not okay to write
	    WriteOK = readcount == 0;
	   
	    if (readcount == 0) {
	        notifyAll(); 
	    }  // if readcount = 0
	  }  // end of "endRead" method



	  //               ---- method startWrite ----
	  // The startWrite method waits until no readers or writers are active.
	  // Then the writer can go ahead.  Record this by incrementing writecount.
	  public synchronized void startWrite () {
	    if (readcount > 0 || writecount > 0) {
	    	waitingwritecount++; //Writer wait
	    	WriteOK = false; //Not okay to write
	    	while (!WriteOK) {
	    		try {  wait(); } catch (Exception e) {}; // Wait for Notify()
	    	}
	    	waitingwritecount--; // Notfied so no longer waiting
	    }  
	    WriteOK = false; //Active otherwise so so not OK to write
	    writecount++;
	  }  // end of "startWrite" method



	  //               ---- method endWrite ----
	  // The endWrite method decrements writecount and notifies all waiting
	  // processes. 
	  //
	  // The notifyAll wakes up all waiting threads (both readers and
	  // writers).  All the readers will start, or one of the writers will start.
	  // The other threads will wait() again.
	  //
	  // As an example, suppose that 3  reader and 2 writer threads are waiting.
	  // Each of these threads is part-way through executing a synchronized
	  // routine (startRead, endRead, startWrite, endWrite) and is stuck at
	  // a "wait" statement, where it has "stepped out" of the monitor.  (In other
	  // words, it no longer holds the lock for the monitor.)
	  // The notifyAll() wakes up all 5 of the waiting threads, in some 
	  // arbitrary order.  The threads execute one at a time (stepping
	  // back in to the monitor, to execute more of the synchronized
	  // routine that they called.)  If it happens that a reader thread 
	  // gets the monitor lock first, it finds that writecount == 0 so it sets
	  // readcount to 1 and starts reading.  The writers that are awakened notice
	  // that readcount>0,  so they again execute wait().  The other readers 
	  // notice that writecount is still zero, so they start.  Now we have three 
	  // readers active, readcount = 3, writecount = 0, and two writers still 
	  // waiting in the startWrite method.
	  public synchronized void endWrite () {
		  
	    writecount--; //Finished writing so no longer active
	    
	    //If there's is any reader waiting, no okay to write
	    WriteOK = waitingreadcount == 0;
	    
	    //Recalculate the age.
	    startWaitingReadersTime = age();
	    notifyAll();  // notify all processes that are waiting.  This will
	                  // start all waiting readers, or one waiting writer.
	  }  // end of "endWrite" method

	}  // end of class "rwMonitor"

