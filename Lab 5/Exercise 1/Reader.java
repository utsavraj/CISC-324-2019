// This file defines class "Reader".

// This code uses class SharedDataStruct, which provides access methods
// for the shared data.

//package CISC;

public class Reader extends Thread {
  int myName;  // The name of this thread, passed as a parameter by
               // the process creating this thread
  SharedDataStruct sharedData;  // The shared data.

  // This is the constructor for class Reader.
  public Reader(int name, SharedDataStruct SD) {
    myName = name;    // copy the parameter value to local variable "MyName"
    sharedData = SD;  // sharedData refers to the SharedDataStruct object
                      // that the readers and writers are using.
  }  // end of the constructor for class "Reader"


  public void run () {
    int val;  // The value read from the database

    for (int I = 0;  I < 5; I++) {
      System.out.println("Reader " + myName + 
         " is now requesting to read the database.");
      val = sharedData.dataRead();

      System.out.println("Reader " + myName + " read the value " + val +
          " and is doing something else for a while");

      // Simulate "doing something else" by delaying for a while.
      try { sleep((int)(500.0*Math.random()+1)); } catch(Exception e) {break;}
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Reader"

