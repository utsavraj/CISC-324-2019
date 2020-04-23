//                  Monitor Code for the Readers and Writers
//                           (Readers have priority)
//
// This program demonstrates synchronizattion of processes that read and 
// write a shared data structure.  Several readers can access the data 
// simultaneously, or one writer can have exclusive access.
//
// In lab 3 you were given code that implemented the synchronization using
// semaphores.  Here is the same algorithm coded to use Java monitors.

// This code uses class Reader and Writer.

//package CISC;
public class MainMethod {
  public static void main (String argv[]) {

    // Create a shared data structure.  This simulation only uses an integer
    // as the "shared data".  The value of this integer is used in the output,
    // to verify that writers get exclusive access.
    // "sleep" statements are used to simulate the time that would be taken
    // to access and update a more complex data structure.
    SharedDataStruct sharedData;         // declare sharedData
    sharedData = new SharedDataStruct(); // assign a value to sharedData


    // Create several Reader and Writer threads.  The parameters are
    //    i - an identification number for the reader or writer thread
    //    sharedData - the data structure that is supposed to be accessed

    Reader R;  // R can hold an instance of class Reader
    Writer W;  

    for (int i=1; i<=4; i++) {
      W = new Writer(i, sharedData);
      W.start();
      R = new Reader(i, sharedData);
      R.start();
    }

    System.out.println("This is main speaking");
  }  // end of "main"
}  // end of "MainMethod"
